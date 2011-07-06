/**
 * Vulpe Framework - Copyright (c) Active Thread
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.vulpe.controller;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vulpe.commons.VulpeConstants;
import org.vulpe.commons.VulpeContext;
import org.vulpe.commons.VulpeServiceLocator;
import org.vulpe.commons.VulpeConstants.Controller;
import org.vulpe.commons.VulpeConstants.Error;
import org.vulpe.commons.VulpeConstants.Security;
import org.vulpe.commons.VulpeConstants.Code.Generator;
import org.vulpe.commons.VulpeConstants.Configuration.Ever;
import org.vulpe.commons.VulpeConstants.Configuration.Now;
import org.vulpe.commons.VulpeConstants.Controller.Button;
import org.vulpe.commons.VulpeConstants.Controller.Result;
import org.vulpe.commons.VulpeConstants.Model.Entity;
import org.vulpe.commons.VulpeConstants.Upload.File;
import org.vulpe.commons.VulpeConstants.View.Layout;
import org.vulpe.commons.annotations.Quantity.QuantityType;
import org.vulpe.commons.beans.ButtonConfig;
import org.vulpe.commons.beans.DownloadInfo;
import org.vulpe.commons.beans.Paging;
import org.vulpe.commons.beans.Tab;
import org.vulpe.commons.factory.AbstractVulpeBeanFactory;
import org.vulpe.commons.helper.VulpeCacheHelper;
import org.vulpe.commons.helper.VulpeConfigHelper;
import org.vulpe.commons.util.VulpeHashMap;
import org.vulpe.commons.util.VulpeReflectUtil;
import org.vulpe.commons.util.VulpeValidationUtil;
import org.vulpe.controller.annotations.ResetSession;
import org.vulpe.controller.commons.DuplicatedBean;
import org.vulpe.controller.commons.EverParameter;
import org.vulpe.controller.commons.I18NService;
import org.vulpe.controller.commons.VulpeBaseControllerConfig;
import org.vulpe.controller.commons.VulpeBaseDetailConfig;
import org.vulpe.controller.commons.VulpeControllerConfig.ControllerType;
import org.vulpe.controller.util.ControllerUtil;
import org.vulpe.controller.validator.EntityValidator;
import org.vulpe.exception.VulpeSystemException;
import org.vulpe.model.annotations.Autocomplete;
import org.vulpe.model.annotations.CachedClass;
import org.vulpe.model.annotations.NotDeleteIf;
import org.vulpe.model.annotations.NotExistEquals;
import org.vulpe.model.annotations.QueryParameter;
import org.vulpe.model.entity.VulpeEntity;
import org.vulpe.model.entity.impl.AbstractVulpeBaseAuditEntity;
import org.vulpe.model.services.GenericService;
import org.vulpe.model.services.VulpeService;
import org.vulpe.security.context.VulpeSecurityContext;
import org.vulpe.view.annotations.View;

import com.google.gson.Gson;

/**
 * Base Controller
 * 
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings( { "unchecked", "serial" })
public abstract class AbstractVulpeBaseController<ENTITY extends VulpeEntity<ID>, ID extends Serializable & Comparable>
		implements VulpeController {

	protected static final Logger LOG = Logger.getLogger(AbstractVulpeBaseController.class);

	@Autowired
	protected I18NService i18nService;

	@Autowired
	protected VulpeContext vulpeContext;

	protected ControllerUtil controllerUtil = new ControllerUtil();

	private Collection<String> actionInfoMessages = new ArrayList<String>();

	/**
	 * Global attributes map
	 */
	public EverParameter ever = null;

	/**
	 * Temporal attributes map
	 */
	public VulpeHashMap<String, Object> now = new VulpeHashMap<String, Object>();

	/**
	 * Calendar
	 */
	public final Calendar calendar = Calendar.getInstance();

	{
		now.put(Now.SHOW_CONTENT_TITLE, true);
		now.put(Now.SYSTEM_DATE, calendar.getTime());
		now.put(Now.CURRENT_DAY, calendar.get(Calendar.DAY_OF_MONTH));
		now.put(Now.CURRENT_MONTH, calendar.get(Calendar.MONTH));
		now.put(Now.CURRENT_YEAR, calendar.get(Calendar.YEAR));
	}

	/**
	 * Used to set and/or initialize variables in the controller. On this point
	 * the components controlled by Spring are now available.
	 */
	@PostConstruct
	protected void postConstruct() {
		ever = EverParameter.getInstance(getSession());
		now.put(Now.CONTROLLER_TYPE, getControllerType());
		now.put(Now.TITLE_KEY, getControllerConfig().getTitleKey());
		now.put(Now.REPORT_TITLE_KEY, getControllerConfig().getReportTitleKey());
		now.put(Now.MASTER_TITLE_KEY, getControllerConfig().getMasterTitleKey());
		now.put(Now.FORM_NAME, getControllerConfig().getFormName());
		now.put(Now.NO_CACHE, Math.random() * Math.random());
		if (getControllerConfig().isRequireOneFilter()) {
			now.put(Now.REQUIRE_ONE_FILTER, true);
		}
		final View view = this.getClass().getAnnotation(View.class);
		if (view != null) {
			now.put(Now.FIELD_TO_FOCUS, view.fieldToFocus());
		}
		ever.put(Ever.MAX_INACTIVE_INTERVAL, getSession().getMaxInactiveInterval());
	}

	/**
	 * List of entities
	 */
	private List<ENTITY> entities;
	/**
	 * Current MAIN Entity
	 */
	private ENTITY entity;
	/**
	 * Current Select entity
	 */
	private ENTITY entitySelect;
	/**
	 * Identifier for selections
	 */
	private ID id;
	/**
	 * Paginated Bean
	 */
	private Paging<ENTITY> paging;
	/**
	 * If true, define as read only
	 */
	private boolean onlyToSee = false;
	/**
	 * If true, define entity master as read only
	 */
	private boolean onlyUpdateDetails = false;
	/**
	 * Selected tab name
	 */
	private String selectedTab;
	/**
	 * Detail
	 */
	private String detail;
	/**
	 * Detail index to delete
	 */
	private Integer detailIndex;
	/**
	 * Detail layer
	 */
	private String detailLayer;
	/**
	 *
	 */
	private String downloadKey;
	/**
	 * Download content type.
	 */
	private String downloadContentType;
	/**
	 *
	 */
	private String downloadContentDisposition;
	/**
	 * Download information.
	 */
	private DownloadInfo downloadInfo;
	/**
	 * 
	 */
	private String propertyName;
	/**
	 *
	 */
	private boolean uploaded;

	private boolean tabularFilter;

	private Object jsonRoot;

	private boolean cleaned = false;

	private boolean exported = false;

	private String reportFormat = "PDF";

	public VulpeHashMap<Operation, String> defaultMessage = new VulpeHashMap<Operation, String>();

	{
		defaultMessage.put(Operation.CREATE_POST, "{vulpe.message.create.post}");
		defaultMessage.put(Operation.CLONE, "{vulpe.message.clone}");
		defaultMessage.put(Operation.UPDATE_POST, "{vulpe.message.update.post}");
		defaultMessage.put(Operation.TABULAR_POST, "{vulpe.message.tabular.post}");
		defaultMessage.put(Operation.DELETE, "{vulpe.message.delete}");
		defaultMessage.put(Operation.DELETE_FILE, "{vulpe.message.delete.file}");
		defaultMessage.put(Operation.READ, "{vulpe.message.empty.list}");
		defaultMessage.put(Operation.READ_DELETED, "{vulpe.message.empty.list.deleted}");
		defaultMessage.put(Operation.REPORT_EMPTY, "{vulpe.message.empty.report.data}");
		defaultMessage.put(Operation.REPORT_SUCCESS,
				"{vulpe.message.report.generated.successfully}");
	}

	public String getDefaultMessage(final Operation operation) {
		return defaultMessage.getSelf(operation);
	}

	public String getDefaultMessage() {
		return getDefaultMessage(getOperation());
	}

	public void setDefaultMessage(final String message) {
		defaultMessage.put(getOperation(), message);
	}

	public void setDefaultMessage(final Operation operation, final String message) {
		defaultMessage.put(operation, message);
	}

	public ID getId() {
		return id;
	}

	public void setId(final ID id) {
		this.id = id;
	}

	public List<ID> getSelected() {
		final List<ID> selected = new ArrayList<ID>();
		if (VulpeValidationUtil.isNotEmpty(getEntities())) {
			for (final ENTITY entity : getEntities()) {
				if (entity.isSelected()) {
					selected.add(entity.getId());
				}
			}
		}
		return selected;
	}

	public List<ENTITY> getEntities() {
		return entities;
	}

	public void setEntities(final List<ENTITY> entities) {
		this.entities = entities;
	}

	public ENTITY getEntity() {
		return entity;
	}

	public void setEntity(final ENTITY entity) {
		this.entity = entity;
	}

	public Paging<ENTITY> getPaging() {
		return paging;
	}

	public void setPaging(final Paging<ENTITY> paging) {
		this.paging = paging;
	}

	public boolean isOnlyToSee() {
		return onlyToSee;
	}

	public void setOnlyToSee(final boolean onlyToSee) {
		this.onlyToSee = onlyToSee;
	}

	public void setSelectedTab(final String selectedTab) {
		this.selectedTab = selectedTab;
	}

	public String getSelectedTab() {
		return selectedTab;
	}

	public void setEntitySelect(final ENTITY entitySelect) {
		this.entitySelect = entitySelect;
	}

	public ENTITY getEntitySelect() {
		return entitySelect;
	}

	public void setTabularSize(final Integer tabularSize) {
		ever.putWeakRef(Ever.TABULAR_SIZE, tabularSize);
	}

	public Integer getTabularSize() {
		return ever.<Integer> getSelf(Ever.TABULAR_SIZE);
	}

	public void setCurrentPage(final Integer page) {
		ever.putWeakRef(Ever.CURRENT_PAGE, page);
	}

	public Integer getCurrentPage() {
		return ever.<Integer> getSelf(Ever.CURRENT_PAGE);
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(final String detail) {
		this.detail = detail;
	}

	public void setDetailIndex(final Integer detailIndex) {
		this.detailIndex = detailIndex;
	}

	public Integer getDetailIndex() {
		return detailIndex;
	}

	/**
	 * Method to retrieve download info.
	 * 
	 * @since 1.0
	 * @return DownlodInfo object.
	 */
	public DownloadInfo getDownloadInfo() {
		return downloadInfo;
	}

	/**
	 * Set download info.
	 * 
	 * @param downloadInfo
	 *            Download Info.
	 * 
	 * @since 1.0
	 */
	public void setDownloadInfo(final DownloadInfo downloadInfo) {
		this.downloadInfo = downloadInfo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeSimpleController#getDownloadKey()
	 */
	public String getDownloadKey() {
		return downloadKey;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeSimpleController#setDownloadKey(java.lang
	 * .String)
	 */
	public void setDownloadKey(final String downloadKey) {
		this.downloadKey = downloadKey;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeSimpleController#getDownloadContentType()
	 */
	public String getDownloadContentType() {
		return downloadContentType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeSimpleController#setDownloadContentType
	 * (java.lang.String)
	 */
	public void setDownloadContentType(final String downloadContentType) {
		this.downloadContentType = downloadContentType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.VulpeSimpleController#getDownloadContentDisposition
	 * ()
	 */
	public String getDownloadContentDisposition() {
		return downloadContentDisposition;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.VulpeSimpleController#setDownloadContentDisposition
	 * (java.lang.String)
	 */
	public void setDownloadContentDisposition(final String downloadContentDisposition) {
		this.downloadContentDisposition = downloadContentDisposition;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeSimpleController#isUploaded()
	 */
	public boolean isUploaded() {
		return uploaded;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeSimpleController#setUploaded(boolean)
	 */
	public void setUploaded(final boolean uploaded) {
		this.uploaded = uploaded;
	}

	/**
	 * Extension point to report load.
	 * 
	 * @since 1.0
	 */
	protected abstract DownloadInfo doReportLoad();

	/**
	 * Method to validate quantity of details.
	 * 
	 * @param beans
	 * @param detailConfig
	 * @return
	 */
	protected boolean validateQuantity(final List<ENTITY> beans,
			final VulpeBaseDetailConfig detailConfig) {
		if (detailConfig.getQuantity() != null) {
			final String tabName = getTabs().containsKey(detailConfig.getTitleKey()) ? ((Tab) getTabs()
					.get(detailConfig.getTitleKey())).getTitle()
					: getText(detailConfig.getTitleKey());
			if (detailConfig.getQuantity().minimum() > 1
					&& detailConfig.getQuantity().maximum() > 1
					&& detailConfig.getQuantity().minimum() < detailConfig.getQuantity().maximum()) {
				if (beans == null || beans.size() < detailConfig.getQuantity().minimum()
						|| beans.size() > detailConfig.getQuantity().maximum()) {
					if (detailConfig.getQuantity().minimum() == detailConfig.getQuantity()
							.maximum()) {
						addActionError("vulpe.error.details.cardinality.custom.equal",
								getText(detailConfig.getTitleKey()), detailConfig.getQuantity()
										.minimum());
					} else {
						addActionError("vulpe.error.details.cardinality.custom", tabName,
								detailConfig.getQuantity().minimum(), detailConfig.getQuantity()
										.maximum());
					}
					return false;
				}
			} else if (detailConfig.getQuantity().minimum() > 1
					&& detailConfig.getQuantity().maximum() == 0
					&& (beans == null || beans.size() < detailConfig.getQuantity().minimum())) {
				addActionError("vulpe.error.details.cardinality.custom.minimum", tabName,
						detailConfig.getQuantity().minimum());
				return false;
			} else if (detailConfig.getQuantity().minimum() == 0
					&& detailConfig.getQuantity().maximum() > 1
					&& (beans == null || beans.size() > detailConfig.getQuantity().maximum())) {
				addActionError("vulpe.error.details.cardinality.custom.maximum", tabName,
						detailConfig.getQuantity().maximum());
				return false;
			} else {
				if (QuantityType.ONE.equals(detailConfig.getQuantity().type())
						|| (detailConfig.getQuantity().minimum() == 1 && detailConfig.getQuantity()
								.maximum() == 1)) {
					boolean valid = true;
					if (beans == null || beans.size() == 0) {
						addActionError("vulpe.error.details.cardinality.one.less", tabName);
						valid = false;
					} else if (beans.size() > 1) {
						addActionError("vulpe.error.details.cardinality.one.only", tabName);
						valid = false;
					}
					return valid;
				} else if (QuantityType.ONE_OR_MORE.equals(detailConfig.getQuantity().type())) {
					if (beans == null || beans.size() == 0) {
						addActionError("vulpe.error.details.cardinality.one.more", tabName);
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * Method to check duplicated details.
	 * 
	 * @param parent
	 *            Parent
	 * @param baseEntity
	 * @param detailConfig
	 *            Configuration of detail.
	 * 
	 * @since 1.0
	 */
	protected abstract boolean duplicatedDetail(final Object parent, final ENTITY baseEntity,
			final VulpeBaseDetailConfig detailConfig);

	/**
	 * Method to validate duplicated details.
	 * 
	 * @param beans
	 * @param detailConfig
	 * @return
	 */
	protected boolean duplicatedDetailItens(final Collection<VulpeEntity<?>> beans,
			final VulpeBaseDetailConfig detailConfig) {
		final String[] despiseFields = detailConfig.getDespiseFields();
		final Collection<DuplicatedBean> duplicatedBeans = controllerUtil.duplicatedItens(beans,
				despiseFields);
		if (duplicatedBeans != null && !duplicatedBeans.isEmpty()) {
			if (getControllerType().equals(ControllerType.TABULAR) && duplicatedBeans.size() == 1) {
				return false;
			}
			final StringBuilder lines = new StringBuilder();
			int count = 1;
			for (DuplicatedBean duplicatedBean : duplicatedBeans) {
				if (duplicatedBeans.size() > 1 && duplicatedBeans.size() == count) {
					lines.append(" " + getText("label.vulpe.and") + " "
							+ duplicatedBean.getRowNumber());
				} else {
					lines.append(StringUtils.isBlank(lines.toString()) ? String
							.valueOf(duplicatedBean.getRowNumber()) : ", "
							+ duplicatedBean.getRowNumber());
				}
				++count;
			}
			if (getControllerType().equals(ControllerType.TABULAR)) {
				addActionError("vulpe.error.tabular.duplicated", lines.toString());
			} else {
				final String tabName = getTabs().containsKey(detailConfig.getTitleKey()) ? ((Tab) getTabs()
						.get(detailConfig.getTitleKey())).getTitle()
						: getText(detailConfig.getTitleKey());
				if (detailConfig.getParentDetailConfig() != null) {
					final String parentTabName = getTabs().containsKey(
							detailConfig.getParentDetailConfig().getTitleKey()) ? ((Tab) getTabs()
							.get(detailConfig.getParentDetailConfig().getTitleKey())).getTitle()
							: getText(detailConfig.getParentDetailConfig().getTitleKey());
					addActionError("vulpe.error.subdetails.duplicated.tab", tabName, parentTabName,
							lines.toString());
				} else {
					addActionError("vulpe.error.details.duplicated.tab", tabName, lines.toString());
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * Returns current action configuration.
	 * 
	 * @since 1.0
	 * @return ActionConfig object for current action.
	 */
	public VulpeBaseControllerConfig<ENTITY, ID> getControllerConfig() {
		return controllerUtil.getControllerConfig(this);
	}

	/**
	 * Returns current detail configuration.
	 * 
	 * @since 1.0
	 * @return
	 */
	public VulpeBaseDetailConfig getDetailConfig() {
		return getControllerConfig().getDetailConfig(getDetail());
	}

	/**
	 * Method to remove detail despised.
	 * 
	 * @param beans
	 *            details
	 * @param detailConfig
	 *            Configuration of detail.
	 * 
	 * @since 1.0
	 */
	protected List<VulpeEntity<?>> despiseDetailItens(final Collection<VulpeEntity<?>> beans,
			final VulpeBaseDetailConfig detailConfig) {
		return controllerUtil.despiseItens(beans, detailConfig.getDespiseFields(),
				getControllerType().equals(ControllerType.TABULAR));
	}

	/**
	 * Method to remove detail despised.
	 * 
	 * @param parent
	 *            Parent
	 * @param detailConfig
	 *            Configuration of detail.
	 * 
	 * @since 1.0
	 */
	protected abstract void despiseDetail(final Object parent, final ENTITY baseEntity,
			final VulpeBaseDetailConfig detailConfig);

	/**
	 * Validate if entity already exists
	 * 
	 * @return
	 */
	protected boolean validateNotExistEquals() {
		return getService(GenericService.class).notExistEquals(getEntity());
	}

	public VulpeHashMap<String, ButtonConfig> getButtons() {
		if (now.containsKey(Now.BUTTONS)) {
			return now.getSelf(Now.BUTTONS);
		}
		final VulpeHashMap<String, ButtonConfig> buttons = new VulpeHashMap<String, ButtonConfig>();
		now.put(Now.BUTTONS, buttons);
		return buttons;
	}

	public void renderDetailButton(final String detail, final String button) {
		getButtons().put(button.concat(detail), new ButtonConfig(true, true, false));
	}

	public void notRenderDetailButton(final String detail, final String button) {
		getButtons().put(button.concat(detail), new ButtonConfig(false));
	}

	public void showDetailButton(final String detail, final String button) {
		getButtons().put(button.concat(detail), new ButtonConfig(true, true));
	}

	public void hideDetailButton(final String detail, final String button) {
		getButtons().put(button.concat(detail), new ButtonConfig(true, false));
	}

	public void enableDetailButton(final String detail, final String button) {
		getButtons().put(button.concat(detail), new ButtonConfig(true, true, false));
	}

	public void disableDetailButton(final String detail, final String button) {
		getButtons().put(button.concat(detail), new ButtonConfig(true, true, true));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeController#configButton(java.lang.String,
	 * boolean[])
	 */
	public void configButton(final String button, final boolean... values) {
		ButtonConfig buttonConfig = new ButtonConfig();
		String key = button;
		if (getControllerType().equals(ControllerType.TABULAR)) {
			String deleteButtonKey = Button.DELETE.concat(getControllerConfig().getTabularConfig()
					.getBaseName());
			if (getButtons().containsKey(key)) {
				buttonConfig = getButtons().getSelf(key);
			}
			switch (values.length) {
			case 1:
				buttonConfig = new ButtonConfig(values[0]);
				break;
			case 2:
				buttonConfig = new ButtonConfig(values[0], values[1]);
				break;
			case 3:
				buttonConfig = new ButtonConfig(values[0], values[1], values[2]);
				break;
			}
			getButtons().put(deleteButtonKey, buttonConfig);
		}
		if (Button.ADD_DETAIL.equals(button)) {
			key = Button.ADD_DETAIL.concat(getControllerConfig().getTabularConfig().getBaseName());
		}
		if (getButtons().containsKey(key)) {
			buttonConfig = getButtons().getSelf(key);
		}
		switch (values.length) {
		case 1:
			buttonConfig = new ButtonConfig(values[0]);
			break;
		case 2:
			buttonConfig = new ButtonConfig(values[0], values[1]);
			break;
		case 3:
			buttonConfig = new ButtonConfig(values[0], values[1], values[2]);
			break;
		}
		getButtons().put(key, buttonConfig);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeController#configButton(java.lang.String,
	 * java.lang.String, boolean)
	 */
	public void configButton(final String button, final String config, final boolean value) {
		ButtonConfig buttonConfig = new ButtonConfig();
		String key = button;
		if (getControllerType().equals(ControllerType.TABULAR)) {
			String deleteButtonKey = Button.DELETE.concat(getControllerConfig().getTabularConfig()
					.getBaseName());
			if (getButtons().containsKey(key)) {
				buttonConfig = getButtons().getSelf(key);
			}
			if (StringUtils.isNotBlank(config)) {
				if (config.equals(ButtonConfig.RENDER)) {
					buttonConfig.setRender(value);
					if (buttonConfig.getShow() == null) {
						buttonConfig.setShow(true);
					}
				} else if (config.equals(ButtonConfig.SHOW)) {
					buttonConfig.setShow(value);
				} else if (config.equals(ButtonConfig.DISABLED)) {
					buttonConfig.setDisabled(value);
				}
			}
			getButtons().put(deleteButtonKey, buttonConfig);
		}
		if (Button.ADD_DETAIL.equals(button)) {
			key = Button.ADD_DETAIL.concat(getControllerConfig().getTabularConfig().getBaseName());
		}
		if (getButtons().containsKey(key)) {
			buttonConfig = getButtons().getSelf(key);
		}
		if (StringUtils.isNotBlank(config)) {
			if (config.equals(ButtonConfig.RENDER)) {
				buttonConfig.setRender(value);
				if (buttonConfig.getShow() == null) {
					buttonConfig.setShow(true);
				}
			} else if (config.equals(ButtonConfig.SHOW)) {
				buttonConfig.setShow(value);
			} else if (config.equals(ButtonConfig.DISABLED)) {
				buttonConfig.setDisabled(value);
			}
		}
		getButtons().put(key, buttonConfig);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeController#showButtons(java.lang.String[])
	 */
	public void showButtons(final String... buttons) {
		for (final String button : buttons) {
			configButton(button, ButtonConfig.SHOW, true);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeController#showButtons(java.lang.String[])
	 */
	public void renderButtons(final String... buttons) {
		for (final String button : buttons) {
			configButton(button, ButtonConfig.RENDER, true);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.VulpeController#renderButtons(org.vulpe.controller
	 * .commons.VulpeControllerConfig.ControllerType, java.lang.String[])
	 */
	public void renderButtons(final ControllerType controllerType, final String... buttons) {
		for (final String button : buttons) {
			renderButtons(controllerType + "_" + button);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.VulpeController#enableButtons(java.lang.String[])
	 */
	public void enableButtons(final String... buttons) {
		for (final String button : buttons) {
			configButton(button, ButtonConfig.DISABLED, false);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.VulpeController#showButtons(org.vulpe.controller
	 * .commons.VulpeControllerConfig.ControllerType, java.lang.String[])
	 */
	public void showButtons(final ControllerType controllerType, final String... buttons) {
		for (final String button : buttons) {
			showButtons(controllerType + "_" + button);
		}
	}

	/**
	 *
	 */
	private void manageButtons() {
		manageButtons(getOperation());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.VulpeController#manageButtons(org.vulpe.controller
	 * .VulpeSimpleController.Operation)
	 */
	public void manageButtons(final Operation operation) {
		getButtons().clear();
		if (getControllerType().equals(ControllerType.MAIN)) {
			if (getControllerConfig().getDetails() != null) {
				for (final VulpeBaseDetailConfig detail : getControllerConfig().getDetails()) {
					if (Operation.VIEW.equals(operation)) {
						notRenderDetailButton(detail.getBaseName(), Button.ADD_DETAIL);
						notRenderDetailButton(detail.getBaseName(), Button.DELETE);
					} else {
						renderDetailButton(detail.getBaseName(), Button.ADD_DETAIL);
						renderDetailButton(detail.getBaseName(), Button.DELETE);
					}
				}
			}
			if ((Operation.CREATE.equals(operation) || Operation.DELETE.equals(operation) || Operation.PREPARE
					.equals(operation))
					|| ((Operation.CREATE.equals(getOperation()) || Operation.CREATE_POST
							.equals(getOperation())) && Operation.ADD_DETAIL.equals(operation))) {
				renderButtons(Button.BACK, Button.CREATE_POST, Button.CLEAR);
			} else if (Operation.UPDATE.equals(operation)
					|| ((Operation.UPDATE.equals(getOperation()) || Operation.UPDATE_POST
							.equals(getOperation())) && Operation.ADD_DETAIL.equals(operation))) {
				renderButtons(Button.BACK, Button.CREATE, Button.UPDATE_POST, Button.DELETE);
				if (VulpeConfigHelper.getProjectConfiguration().view().layout().showButtonClone()) {
					renderButtons(Button.CLONE);
				}
			} else if (Operation.VIEW.equals(operation)) {
				renderButtons();
			}
		} else if (getControllerType().equals(ControllerType.SELECT)) {
			renderButtons(Button.READ, Button.CLEAR, Button.CREATE, Button.UPDATE, Button.DELETE);
			if (getControllerConfig().getControllerAnnotation().select().showReport()) {
				renderButtons(Button.REPORT);
			} else {
				notRenderButtons(Button.REPORT);
			}
			if (isPopup()) {
				notRenderButtons(Button.CREATE, Button.UPDATE, Button.DELETE);
			}
		} else if (getControllerType().equals(ControllerType.REPORT)) {
			renderButtons(Button.READ, Button.CLEAR);
		} else if (getControllerType().equals(ControllerType.TABULAR)) {
			renderButtons(Button.TABULAR_RELOAD, Button.DELETE, Button.TABULAR_POST,
					Button.ADD_DETAIL);
			if (getControllerConfig().isTabularShowFilter()) {
				renderButtons(Button.TABULAR_FILTER);
			}
		} else if (getControllerType().equals(ControllerType.TWICE)) {
			if (Operation.DELETE.equals(operation) || Operation.CREATE.equals(operation)
					|| Operation.TWICE.equals(operation)) {
				renderButtons(ControllerType.MAIN, Button.CREATE_POST, Button.CLEAR);
			} else if (Operation.UPDATE.equals(operation)) {
				renderButtons(ControllerType.MAIN, Button.CREATE, Button.UPDATE_POST, Button.DELETE);
				if (VulpeConfigHelper.getProjectConfiguration().view().layout().showButtonClone()) {
					renderButtons(Button.CLONE);
				}
			} else if (Operation.VIEW.equals(operation)) {
				renderButtons();
			}
			renderButtons(ControllerType.SELECT, Button.READ, Button.BACK, Button.UPDATE,
					Button.DELETE);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeController#hideButtons(java.lang.String[])
	 */
	public void hideButtons(final String... buttons) {
		for (final String button : buttons) {
			configButton(button, ButtonConfig.SHOW, false);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.VulpeController#notRenderButtons(java.lang.String[])
	 */
	public void notRenderButtons(final String... buttons) {
		for (final String button : buttons) {
			configButton(button, ButtonConfig.RENDER, false);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.VulpeController#disableButtons(java.lang.String[])
	 */
	public void disableButtons(final String... buttons) {
		for (final String button : buttons) {
			configButton(button, ButtonConfig.DISABLED, true);
		}
	}

	/**
	 * Method to show error
	 * 
	 * @param message
	 * @return
	 */
	protected void showError(final String message) {
		manageButtons(getOperation());
		addActionError(message);
		controlResultForward();
	}

	protected abstract boolean validateDetails();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeSimpleController#validateEntity()
	 */
	public boolean validateEntity() {
		if ((getOperation().equals(Operation.CREATE_POST) || getOperation().equals(
				Operation.UPDATE_POST))) {
			if (validateNotExistEquals()) {
				final NotExistEquals notExistEqual = getControllerConfig().getEntityClass()
						.getAnnotation(NotExistEquals.class);
				String message = "{vulpe.error.entity.exists}";
				if (StringUtils.isNotEmpty(notExistEqual.message())) {
					message = notExistEqual.message();
				}
				addActionError(message);
				return false;
			}
			managePaging(false);
		}
		return EntityValidator.validate(getEntity()) && validateDetails();
	}

	/**
	 * 
	 * @param persisted
	 */
	private void managePaging(final boolean persisted) {
		for (final VulpeBaseDetailConfig detailConfig : getControllerConfig().getDetails()) {
			if (isDeleted()) {
				removeDetailPaging(detailConfig.getName());
				prepareDetailPaging();
			} else {
				final List<ENTITY> details = VulpeReflectUtil.getFieldValue(getEntity(),
						detailConfig.getParentDetailConfig() != null ? detailConfig
								.getParentDetailConfig().getName() : detailConfig.getName());
				if (detailConfig.getPageSize() > 0
						&& detailConfig.getPropertyName().startsWith("entity.")) {
					final Paging<ENTITY> paging = getDetailPaging(detailConfig.getName());
					if (persisted) {
						paging.setRealList(details);
					} else {
						repairDetailPaging(details, paging);
						details.clear();
						details.addAll(paging.getRealList());
						for (final ENTITY entity : details) {
							if (entity.isFakeId()) {
								entity.setId(null);
							}
						}
					}
					mountDetailPaging(detailConfig, paging);
				}
			}
		}
	}

	protected void updateAuditInformation(final ENTITY entity) {
		if (entity instanceof AbstractVulpeBaseAuditEntity) {
			final AbstractVulpeBaseAuditEntity auditEntity = (AbstractVulpeBaseAuditEntity) entity;
			auditEntity.setUserOfLastUpdate(getUserAuthenticated());
			auditEntity.setDateOfLastUpdate(Calendar.getInstance().getTime());
		}
	}

	protected void tabularPagingMount(final boolean add) {
		if (getControllerType().equals(ControllerType.TABULAR)
				&& getControllerConfig().getTabularPageSize() > 0) {
			if (add) {
				setTabularSize(getTabularSize()
						+ getControllerConfig().getControllerAnnotation().tabular().newRecords());
			} else {
				setTabularSize(getEntities().size());
			}
			Integer page = getCurrentPage();
			if (getPaging() != null) {
				page = getPaging().getPage();
			}
			if (page == null) {
				page = 0;
			}
			setPaging(new Paging<ENTITY>(getTabularSize(), getControllerConfig()
					.getTabularPageSize(), page));
			getPaging().setList(getEntities());
		}
	}

	public void json() {
		final Object object = onJson();
		if (VulpeValidationUtil.isNotEmpty(object)) {
			renderJSON(object);
		}
	}

	protected Object onJson() {
		return null;
	}

	protected void autocompleteBefore() {
		// extension point
	}

	protected void autocompleteAfter() {
		// extension point
	}

	public void autocomplete() {
		autocompleteBefore();
		String value = "";
		final ENTITY autocompleteEntity = (ENTITY) prepareEntity(Operation.READ).clone();
		if (StringUtils.isBlank(autocompleteEntity.getQueryConfigurationName())) {
			autocompleteEntity.setQueryConfigurationName("autocomplete");
		}
		List<VulpeHashMap<String, Object>> values = autocompleteValueList();
		if (VulpeValidationUtil.isEmpty(values)) {
			List<ENTITY> autocompleteList = autocompleteList();
			String description = autocompleteEntity.getAutocomplete();
			if (description.contains(",")) {
				autocompleteEntity.setAutocomplete(description
						.substring(description.indexOf(",") + 1));
				description = description.substring(0, description.indexOf(","));
			}

			if (VulpeValidationUtil.isEmpty(autocompleteList)) {
				autocompleteList = (List<ENTITY>) invokeServices(
						getServiceMethodName(Operation.READ), new Class[] { getControllerConfig()
								.getEntityClass() }, new Object[] { autocompleteEntity });
			}
			values = new ArrayList<VulpeHashMap<String, Object>>();
			if (VulpeValidationUtil.isNotEmpty(autocompleteList)) {
				final List<Field> autocompleteFields = VulpeReflectUtil.getFieldsWithAnnotation(
						getControllerConfig().getEntityClass(), Autocomplete.class);
				for (final ENTITY entity : autocompleteList) {
					final VulpeHashMap<String, Object> map = new VulpeHashMap<String, Object>();
					try {
						map.put("id", entity.getId());
						map.put("value", PropertyUtils.getProperty(entity, description));
						if (VulpeValidationUtil.isNotEmpty(autocompleteFields)) {
							for (final Field field : autocompleteFields) {
								if (!field.getName().equals(getEntitySelect().getAutocomplete())) {
									map.put(field.getName(), PropertyUtils.getProperty(entity,
											field.getName()));
								}
							}
						}
						if (getEntitySelect().getId() != null) {
							value = map.getSelf("value");
							break;
						}
					} catch (Exception e) {
						LOG.error(e);
					}
					values.add(map);
				}
			} else {
				renderPlainText("");
			}
		} else if (getEntitySelect().getId() != null) {
			for (final VulpeHashMap<String, Object> map : values) {
				final Long id = map.getSelf("id");
				if (id.equals(getEntitySelect().getId())) {
					value = map.getSelf("value");
					break;
				}
			}
		}
		autocompleteAfter();
		if (getEntitySelect().getId() == null) {
			renderJSON(values);
		} else {
			renderPlainText(value);
		}
	}

	protected List<ENTITY> autocompleteList() {
		return null;
	}

	protected List<VulpeHashMap<String, Object>> autocompleteValueList() {
		return null;
	}

	/**
	 * Extension point to prepare entity.
	 * 
	 * @since 1.0
	 */
	protected ENTITY prepareEntity(final Operation operation) {
		ENTITY entity = Operation.READ.equals(operation) ? getEntitySelect() : getEntity();
		try {
			if (operation.equals(Operation.SELECT) || operation.equals(Operation.CREATE)) {
				entity = getControllerConfig().getEntityClass().newInstance();
			} else if (entity == null) {
				entity = getControllerConfig().getEntityClass().newInstance();
			}
			updateAuditInformation(entity);
			if (Operation.READ.equals(operation) && getEntitySelect() == null) {
				setEntitySelect(getControllerConfig().getEntityClass().newInstance());
				entity = getEntitySelect();
			} else if (getEntitySelect() != null
					&& StringUtils.isNotEmpty(getEntitySelect().getAutocomplete())) {
				if (getId() != null && getEntitySelect().getId() == null) {
					getEntitySelect().setId(getId());
				}
			} else if (Operation.UPDATE.equals(operation)
					|| (Operation.DELETE.equals(operation) && (getControllerType().equals(
							ControllerType.SELECT)
							|| getControllerType().equals(ControllerType.TABULAR) || getControllerType()
							.equals(ControllerType.TWICE)))) {
				entity.setId(getId());
			}
		} catch (Exception e) {
			throw new VulpeSystemException(e);
		}
		if (StringUtils.isEmpty(entity.getQueryConfigurationName())
				|| "default".equals(entity.getQueryConfigurationName())) {
			entity.setQueryConfigurationName(getControllerConfig().getControllerAnnotation()
					.queryConfigurationName());
		}
		return entity;
	}

	/**
	 * Extension point to prepare detail.
	 * 
	 * @param detail
	 *            Detail.
	 * @since 1.0
	 */
	protected ENTITY prepareDetail(final ENTITY detail) {
		return detail;
	}

	/**
	 * Configure detail to view.
	 */
	protected void configureDetail() {
		setRequestAttribute(Layout.TARGET_CONFIG, getDetailConfig());
		setRequestAttribute(Layout.TARGET_CONFIG_PROPERTY_NAME, getDetail());
	}

	protected void mountDetailPaging(final VulpeBaseDetailConfig detailConfig,
			final Paging<ENTITY> paging) {
		final List<ENTITY> list = new ArrayList<ENTITY>();
		if (getPaging() != null && getPaging().getPage() != null) {
			paging.setPage(getPaging().getPage());
		}
		int count = 1;
		int total = 0;
		for (final ENTITY entity : paging.getRealList()) {
			if (count > ((paging.getPage() - 1) * paging.getPageSize())) {
				if (total == detailConfig.getPageSize()) {
					break;
				}
				list.add(entity);
				++total;
			}
			++count;
		}
		paging.processPage();
		paging.setList(list);
	}

	protected void repairDetailPaging(final List<ENTITY> values, final Paging<ENTITY> paging) {
		if (VulpeValidationUtil.isNotEmpty(values)) {
			int index = 0;
			for (final ENTITY real : paging.getRealList()) {
				for (final ENTITY modified : values) {
					if (VulpeValidationUtil.isNotEmpty(modified)) {
						if (real.getId() != null && real.getId().equals(modified.getId())) {
							paging.getRealList().set(index, modified);
							break;
						}
					}
				}
				++index;
			}
		}
	}

	protected void removeDetailPaging() {
		removeDetailPaging(getDetail());
	}

	protected void removeDetailPaging(final String detail) {
		ever.remove(detail + Controller.DETAIL_PAGING_LIST);
	}

	protected <T> T getDetailPaging(final String detail) {
		return ever.<T> getSelf(detail + Controller.DETAIL_PAGING_LIST);
	}

	protected <T> T getDetailPaging() {
		return (T) getDetailPaging(getDetail());
	}

	protected void setDetailPaging(final String detail, final Paging<ENTITY> paging) {
		ever.putWeakRef(detail + Controller.DETAIL_PAGING_LIST, paging);
	}

	public void paging() {
		pagingBefore();
		if (VulpeValidationUtil.isNotEmpty(getDetail())) {
			final Paging<ENTITY> paging = getDetailPaging();
			final List<ENTITY> values = VulpeReflectUtil.getFieldValue(entity, getDetail());
			repairDetailPaging(values, paging);
			if (!getDetail().startsWith("entity")) {
				setDetail("entity." + getDetail());
			}
			configureDetail();
			final VulpeBaseDetailConfig detailConfig = getDetailConfig();
			mountDetailPaging(detailConfig, paging);
			manageButtons(Operation.ADD_DETAIL);
			if (isAjax()) {
				if (detailConfig == null || detailConfig.getViewPath() == null) {
					controlResultForward();
				} else {
					setResultForward(detailConfig.getViewPath());
				}
			}
		}
		pagingAfter();
	}

	protected void pagingBefore() {
	}

	protected void pagingAfter() {
	}

	protected void prepareDetailPaging() {
		if (VulpeValidationUtil.isNotEmpty(getControllerConfig().getDetails())) {
			for (final VulpeBaseDetailConfig detailConfig : getControllerConfig().getDetails()) {
				if (detailConfig.getPageSize() > 0
						&& detailConfig.getPropertyName().startsWith("entity.")) {
					final List<ENTITY> values = VulpeReflectUtil.getFieldValue(entity, detailConfig
							.getName());
					if (VulpeValidationUtil.isNotEmpty(values)) {
						int id = 1;
						for (final ENTITY entity : values) {
							if (entity.getId() == null) {
								entity.setId((ID) new Long(id));
								entity.setFakeId(true);
								++id;
							}
						}
						final List<ENTITY> list = new ArrayList<ENTITY>();
						int count = 0;
						for (final ENTITY entity : values) {
							if (count == detailConfig.getPageSize()) {
								break;
							}
							list.add(entity);
							++count;
						}
						final Paging<ENTITY> paging = new Paging<ENTITY>(values.size(),
								detailConfig.getPageSize(), 0);
						paging.setList(list);
						paging.setRealList(values);
						setDetailPaging(detailConfig.getName(), paging);
					}
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeController#addDetail()
	 */
	public void addDetail() {
		addDetailBefore();
		final VulpeBaseDetailConfig detailConfig = onAddDetail(false);
		manageButtons(Operation.ADD_DETAIL);
		if (isAjax()) {
			if (detailConfig == null || detailConfig.getViewPath() == null) {
				controlResultForward();
			} else {
				setResultForward(detailConfig.getViewPath());
			}
		} else {
			controlResultForward();
		}
		addDetailAfter();
	}

	/**
	 * Extension point to code before add detail.
	 * 
	 * @since 1.0
	 */
	protected void addDetailBefore() {
		if (!getControllerType().equals(ControllerType.MAIN)
				&& !getControllerType().equals(ControllerType.TABULAR)) {
			throw new VulpeSystemException(Error.CONTROLLER);
		}
	}

	/**
	 * Extension point to code after add detail.
	 * 
	 * @since 1.0
	 */
	protected void addDetailAfter() {
		// extension point
	}

	protected abstract VulpeBaseDetailConfig onAddDetail(final boolean start);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeController#clear()
	 */
	@ResetSession(before = true)
	public void clear() {
		// setCleaned(true);
		if (getControllerType().equals(ControllerType.MAIN)) {
			setEntitySelect(prepareEntity(Operation.CREATE));
			create();
		} else if (getControllerType().equals(ControllerType.SELECT)) {
			ever.remove(getSelectFormKey());
			ever.remove(getSelectTableKey());
			ever.remove(getSelectPagingKey());
			setEntitySelect(prepareEntity(Operation.SELECT));
			select();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeController#create()
	 */
	@ResetSession(before = true)
	public void create() {
		if (getControllerType() == null || !getControllerType().equals(ControllerType.TWICE)) {
			changeControllerType(ControllerType.MAIN);
		}
		setOperation(Operation.CREATE);
		createBefore();
		onCreate();
		setSelectedTab(null);
		manageButtons(Operation.CREATE);
		if (getControllerType().equals(ControllerType.TWICE)) {
			setBodyTwice(ControllerType.MAIN);
			setResultForward(Layout.PROTECTED_JSP_COMMONS.concat(Layout.BODY_JSP));
		} else {
			controlResultForward();
		}
		createAfter();
	}

	/**
	 * Extension point to create record.
	 * 
	 * @since 1.0
	 */
	protected void onCreate() {
		if (getControllerType().equals(ControllerType.MAIN)
				|| getControllerType().equals(ControllerType.TWICE)) {
			try {
				setEntity(getControllerConfig().getEntityClass().newInstance());
				setEntity(prepareEntity(getOperation()));
				if (VulpeValidationUtil.isNotEmpty(getControllerConfig().getDetails())) {
					createDetails(getControllerConfig().getDetails(), false);
					setDetail("");
				}
				prepareDetailPaging();
			} catch (Exception e) {
				throw new VulpeSystemException(e);
			}
			setExecuted(false);
		}
	}

	/**
	 * Extension point to code before create.
	 * 
	 * @since 1.0
	 */
	protected void createBefore() {
		if (!getControllerType().equals(ControllerType.MAIN)
				&& !getControllerType().equals(ControllerType.TWICE)) {
			throw new VulpeSystemException(Error.CONTROLLER);
		}
	}

	/**
	 * Extension point to code after create.
	 * 
	 * @since 1.0
	 */
	protected void createAfter() {
		// extension point
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeController#cloneIt()
	 */
	@ResetSession(before = true)
	public void cloneIt() {
		if (getControllerType() == null || !getControllerType().equals(ControllerType.TWICE)) {
			changeControllerType(ControllerType.MAIN);
		}
		setOperation(Operation.CLONE);
		cloneItBefore();
		if (getControllerType().equals(ControllerType.TWICE)) {
			setBodyTwice(ControllerType.MAIN);
			setResultForward(Layout.PROTECTED_JSP_COMMONS.concat(Layout.BODY_JSP));
		} else {
			controlResultForward();
		}
		if (onCloneIt()) {
			manageButtons(Operation.CREATE);
			addActionMessage(getDefaultMessage());
			setSelectedTab(null);
			cloneItAfter();
		}
	}

	/**
	 * Extension point to clone record.
	 * 
	 * @since 1.0
	 */
	protected boolean onCloneIt() {
		if (getControllerType().equals(ControllerType.MAIN)
				|| getControllerType().equals(ControllerType.TWICE)) {
			try {
				setEntity((ENTITY) getEntity().clone());
				getEntity().setId(null);
			} catch (Exception e) {
				throw new VulpeSystemException(e);
			}
			setExecuted(false);
		}
		return true;
	}

	/**
	 * Extension point to code before clone.
	 * 
	 * @since 1.0
	 */
	protected void cloneItBefore() {
		if (!getControllerType().equals(ControllerType.MAIN)
				&& !getControllerType().equals(ControllerType.TWICE)) {
			throw new VulpeSystemException(Error.CONTROLLER);
		}
	}

	/**
	 * Extension point to code after clone.
	 * 
	 * @since 1.0
	 */
	protected void cloneItAfter() {
		// extension point
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeController#createPost()
	 */
	@ResetSession
	public void createPost() {
		if (getControllerType() == null || !getControllerType().equals(ControllerType.TWICE)) {
			changeControllerType(ControllerType.MAIN);
		}
		setOperation(Operation.CREATE_POST);
		createPostBefore();
		controlResultForward();
		if (validateEntity() && onCreatePost()) {
			manageButtons(Operation.UPDATE);
			addActionMessage(getDefaultMessage());
			if (getControllerConfig().getEntityClass().isAnnotationPresent(CachedClass.class)) {
				if (validateCacheClass(getEntity())) {
					final String entityName = getControllerConfig().getEntityClass()
							.getSimpleName();
					List<ENTITY> list = (List<ENTITY>) getCachedClasses().get(entityName);
					if (VulpeValidationUtil.isEmpty(list)) {
						list = new ArrayList<ENTITY>();
					}
					list.add(getEntity());
					Collections.sort(list);
					getCachedClasses().put(entityName, list);
				}
			}
			createPostAfter();
			if (getControllerType().equals(ControllerType.TWICE)) {
				onRead();
			}
		} else {
			prepareDetailPaging();
			manageButtons(Operation.CREATE);
		}
		if (getControllerConfig().isNewOnPost()) {
			create();
		}
	}

	/**
	 * Extension point to code in confirm create.
	 * 
	 * @since 1.0
	 * @return Entity created.
	 */
	protected boolean onCreatePost() {
		setEntity((ENTITY) invokeServices(getServiceMethodName(Operation.CREATE),
				new Class[] { getControllerConfig().getEntityClass() },
				new Object[] { prepareEntity(Operation.CREATE_POST) }));
		setId(getEntity().getId());
		managePaging(true);
		setExecuted(true);
		return true;
	}

	/**
	 * Extension point to code before confirm create.
	 * 
	 * @since 1.0
	 */
	protected void createPostBefore() {
		if (!getControllerType().equals(ControllerType.MAIN)
				&& !getControllerType().equals(ControllerType.TWICE)) {
			throw new VulpeSystemException(Error.CONTROLLER);
		}
	}

	/**
	 * Extension point to code after confirm create.
	 * 
	 * @since 1.0
	 */
	protected void createPostAfter() {
		// extension point
	}

	protected abstract void createDetails(final List<VulpeBaseDetailConfig> details,
			final boolean subDetail);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeController#update()
	 */
	@ResetSession(before = true)
	public void update() {
		if (getControllerType() == null || !getControllerType().equals(ControllerType.TWICE)) {
			changeControllerType(ControllerType.MAIN);
		}
		setOperation(Operation.UPDATE);
		updateBefore();
		if (getEntity() == null && getId() == null) {
			create();
		}
		onUpdate();
		setSelectedTab(null);
		manageButtons();
		if (getControllerType().equals(ControllerType.TWICE)) {
			setBodyTwice(ControllerType.MAIN);
			setResultForward(Layout.PROTECTED_JSP_COMMONS.concat(Layout.BODY_JSP));
		} else {
			controlResultForward();
		}
		updateAfter();
	}

	/**
	 * Make visualization read only.
	 * 
	 * @since 1.0
	 * @return
	 */
	@ResetSession(before = true)
	public void view() {
		setOnlyToSee(true);
		update();
		manageButtons(Operation.VIEW);
	}

	/**
	 * Extension point to prepare update.
	 * 
	 * @since 1.0
	 */
	protected void onUpdate() {
		if (getControllerType().equals(ControllerType.MAIN)
				|| getControllerType().equals(ControllerType.TWICE)) {
			setEntity((ENTITY) invokeServices(getServiceMethodName(Operation.FIND),
					new Class[] { getControllerConfig().getEntityClass() },
					new Object[] { prepareEntity(getOperation()) }));
			prepareDetailPaging();
			setExecuted(false);
		}
	}

	/**
	 * Extension point to code before update.
	 * 
	 * @since 1.0
	 */
	protected void updateBefore() {
		if (!getControllerType().equals(ControllerType.MAIN)
				&& !getControllerType().equals(ControllerType.TWICE)) {
			throw new VulpeSystemException(Error.CONTROLLER);
		}
	}

	/**
	 * Extension point to code after update.
	 * 
	 * @since 1.0
	 */
	protected void updateAfter() {
		// extension point
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeController#updatePost()
	 */
	@ResetSession
	public void updatePost() {
		if (getControllerType() == null || !getControllerType().equals(ControllerType.TWICE)) {
			changeControllerType(ControllerType.MAIN);
		}
		setOperation(Operation.UPDATE_POST);
		updatePostBefore();
		controlResultForward();
		manageButtons(Operation.UPDATE);
		if (validateEntity() && onUpdatePost()) {
			addActionMessage(getDefaultMessage());
			if (getControllerConfig().getEntityClass().isAnnotationPresent(CachedClass.class)) {
				boolean valid = validateCacheClass(getEntity());
				final String entityName = getControllerConfig().getEntityClass().getSimpleName();
				List<ENTITY> list = (List<ENTITY>) getCachedClasses().get(entityName);
				if (VulpeValidationUtil.isEmpty(list) && valid) {
					list = new ArrayList<ENTITY>();
					list.add(getEntity());
				} else {
					int count = 0;
					boolean exist = false;
					for (final ENTITY baseEntity : list) {
						if (baseEntity.getId().equals(getEntity().getId())) {
							exist = true;
							if (valid) {
								list.set(count, getEntity());
							} else {
								list.remove(count);
							}
							break;
						}
						++count;
					}
					if (!exist) {
						list.add(getEntity());
					}
				}
				Collections.sort(list);
				getCachedClasses().put(entityName, list);
			}
			if (!getControllerConfig().isOnlyUpdateDetails()) {
				final List<ENTITY> entities = ever.getSelf(getSelectTableKey());
				if (entities != null && !entities.isEmpty()) {
					final List<ENTITY> entitiesOld = new ArrayList<ENTITY>(entities);
					int index = 0;
					for (final ENTITY entity : entitiesOld) {
						if (entity.getId().equals(getEntity().getId())) {
							entities.remove(index);
							entities.add(index, getEntity());
						}
						++index;
					}
					ever.put(getSelectTableKey(), entities);
				}
			}
			updatePostAfter();
			if (getControllerType().equals(ControllerType.TWICE)) {
				onRead();
			}
		} else {
			prepareDetailPaging();
		}
	}

	/**
	 * Extension point prepare confirm update.
	 * 
	 * @since 1.0
	 */
	protected boolean onUpdatePost() {
		final ENTITY entity = prepareEntity(Operation.UPDATE_POST);
		if (getControllerConfig().isOnlyUpdateDetails()) {
			final List<String> details = new ArrayList<String>();
			for (final VulpeBaseDetailConfig detailConfig : getControllerConfig().getDetails()) {
				details.add(detailConfig.getName());
			}
			entity.getMap().put(Entity.ONLY_UPDATE_DETAILS, details);
		}
		if (VulpeValidationUtil.isNotEmpty(getControllerConfig().getDetails())) {
			for (final VulpeBaseDetailConfig detailConfig : getControllerConfig().getDetails()) {
				final List<ENTITY> details = VulpeReflectUtil.getFieldValue(entity, detailConfig
						.getParentDetailConfig() != null ? detailConfig.getParentDetailConfig()
						.getName() : detailConfig.getName());
				if (VulpeValidationUtil.isNotEmpty(details)) {
					if (detailConfig.getParentDetailConfig() == null) {
						for (final ENTITY detail : details) {
							updateAuditInformation(detail);
						}
					} else {
						for (final ENTITY detail : details) {
							final List<ENTITY> subDetails = VulpeReflectUtil.getFieldValue(detail,
									detailConfig.getName());
							if (VulpeValidationUtil.isNotEmpty(subDetails)) {
								for (final ENTITY subDetail : subDetails) {
									updateAuditInformation(subDetail);
								}
								VulpeReflectUtil.setFieldValue(detail, detailConfig.getName(),
										subDetails);
							}
						}
					}
					VulpeReflectUtil.setFieldValue(entity, detailConfig.getName(), details);
				}
			}
		}
		setEntity((ENTITY) invokeServices(getServiceMethodName(Operation.UPDATE),
				new Class[] { getControllerConfig().getEntityClass() }, new Object[] { entity }));
		managePaging(true);
		setExecuted(true);
		return true;
	}

	/**
	 * Extension point to code before confirm update.
	 * 
	 * @since 1.0
	 */
	protected void updatePostBefore() {
		if (!getControllerType().equals(ControllerType.MAIN)
				&& !getControllerType().equals(ControllerType.TWICE)) {
			throw new VulpeSystemException(Error.CONTROLLER);
		}
	}

	/**
	 * Extension point to code after confirm update.
	 * 
	 * @since 1.0
	 */
	protected void updatePostAfter() {
		// extension point
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeController#delete()
	 */
	public void delete() {
		setOperation(Operation.DELETE);
		deleteBefore();
		manageButtons();
		if (onDelete()) {
			addActionMessage(getDefaultMessage());
			setSelectedTab(null);
			if (getControllerConfig().getEntityClass().isAnnotationPresent(CachedClass.class)) {
				final String entityName = getControllerConfig().getEntityClass().getSimpleName();
				final List<ENTITY> list = (List<ENTITY>) getCachedClasses().get(entityName);
				if (VulpeValidationUtil.isNotEmpty(list)) {
					for (final Iterator<ENTITY> iterator = list.iterator(); iterator.hasNext();) {
						final ENTITY entity = iterator.next();
						if (getControllerType().equals(ControllerType.SELECT)) {
							if (VulpeValidationUtil.isNotEmpty(getSelected())) {
								for (final ID id : getSelected()) {
									if (entity.getId().equals(id)) {
										iterator.remove();
										break;
									}
								}
							} else if (entity.getId().equals(getId())) {
								iterator.remove();
							}
						} else {
							if (entity.getId().equals(getEntity().getId())) {
								iterator.remove();
							}
						}
					}
				}
				getCachedClasses().put(entityName, list);
			}
			try {
				setEntity(getControllerConfig().getEntityClass().newInstance());
			} catch (Exception e) {
				throw new VulpeSystemException(e);
			}
			deleteAfter();
			setDeleted(true);
			if (getControllerType().equals(ControllerType.MAIN)) {
				managePaging(false);
				controlResultForward();
			} else if (getControllerType().equals(ControllerType.TWICE)
					&& getEntity().getId() != null) {
				onRead();
				controlResultForward();
			} else {
				read();
			}
		} else {
			if (getControllerType().equals(ControllerType.MAIN)) {
				controlResultForward();
			} else {
				setResultForward(getControllerConfig().getViewItemsPath());
			}
		}
	}

	/**
	 * Extension point to delete.
	 * 
	 * @since 1.0
	 */
	protected boolean onDelete() {
		boolean valid = true;
		final ENTITY entity = prepareEntity(Operation.DELETE);
		final List<ENTITY> entities = new ArrayList<ENTITY>();
		if (VulpeValidationUtil.isNotEmpty(getSelected())) {
			if (!onDeleteMany(entities)) {
				valid = false;
			}
		} else if (getControllerType().equals(ControllerType.TABULAR)) {
			setTabularSize(getTabularSize() - 1);
		} else {
			if (!onDeleteOne()) {
				valid = false;
			}
		}
		if (valid) {
			final NotDeleteIf notDeleteIf = entity.getClass().getAnnotation(NotDeleteIf.class);
			if (VulpeValidationUtil.isNotEmpty(entities)) {
				invokeServices(getServiceMethodName(Operation.DELETE), new Class[] { List.class },
						new Object[] { entities });
				if (notDeleteIf != null) {
					final List<Integer> rows = new ArrayList<Integer>();
					for (final ENTITY entity2 : entities) {
						if (entity2.isUsed()) {
							for (final ENTITY entity3 : getEntities()) {
								if (entity2.getId().equals(entity3.getId())) {
									rows.add(entity3.getRowNumber());
								}
							}
						}
					}
					if (rows.size() > 0) {
						final StringBuilder affectedRows = new StringBuilder();
						int count = 1;
						for (final Integer line : rows) {
							if (affectedRows.length() > 0) {
								affectedRows.append(count == rows.size() ? " "
										+ getText("label.vulpe.and") + " " : ", ");
							}
							affectedRows.append(line);
							++count;
						}
						if (rows.size() == 1) {
							addActionError(notDeleteIf.messageToOneRecord(), affectedRows
									.toString());
						} else {
							addActionError(notDeleteIf.messageToManyRecords(), affectedRows
									.toString());
						}
						valid = false;
					}
				}
			} else {
				invokeServices(getServiceMethodName(Operation.DELETE),
						new Class[] { getControllerConfig().getEntityClass() },
						new Object[] { entity });
				if (notDeleteIf != null && entity.isUsed()) {
					addActionError(notDeleteIf.messageToOneRecord());
					valid = false;
				}
			}
		}
		setExecuted(true);
		return valid;
	}

	protected boolean onDeleteOne() {
		return true;
	}

	protected boolean onDeleteMany(final List<ENTITY> entities) {
		for (final ID id : getSelected()) {
			try {
				final ENTITY newEntity = getControllerConfig().getEntityClass().newInstance();
				newEntity.setId(id);
				entities.add(newEntity);
			} catch (Exception e) {
				throw new VulpeSystemException(e);
			}
		}
		if (getControllerConfig().getTabularPageSize() > 0) {
			setTabularSize(getTabularSize() - (getEntities().size() - getSelected().size()));
		}
		return true;
	}

	/**
	 * Extension point to code before delete.
	 * 
	 * @since
	 */
	protected void deleteBefore() {
		if (!getControllerType().equals(ControllerType.SELECT)
				&& !getControllerType().equals(ControllerType.MAIN)
				&& !getControllerType().equals(ControllerType.TWICE)
				&& !getControllerType().equals(ControllerType.TABULAR)) {
			throw new VulpeSystemException(Error.CONTROLLER);
		}
	}

	/**
	 * Extension point to code after delete.
	 * 
	 * @since 1.0
	 */
	protected void deleteAfter() {
		// extension point
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeController#deleteFile()
	 */
	public void deleteFile() {
		if (getControllerType() == null || !getControllerType().equals(ControllerType.TWICE)) {
			changeControllerType(ControllerType.MAIN);
		}
		setOperation(Operation.DELETE_FILE);
		deleteFileBefore();
		controlResultForward();
		manageButtons(Operation.UPDATE);
		if (validateEntity() && onDeleteFile()) {
			addActionMessage(getDefaultMessage());
			deleteFileAfter();
			if (getControllerType().equals(ControllerType.TWICE)) {
				onRead();
			}
		} else {
			prepareDetailPaging();
		}
	}

	/**
	 * 
	 * @return
	 */
	protected boolean onDeleteFile() {
		boolean valid = true;
		if (StringUtils.isNotBlank(getPropertyName())) {
			if (VulpeReflectUtil.fieldExists(getEntity().getClass(), getPropertyName())) {
				VulpeReflectUtil.setFieldValue(getEntity(), getPropertyName(), null);
				final String contentType = getPropertyName().concat(File.SUFFIX_CONTENT_TYPE);
				if (VulpeReflectUtil.fieldExists(getEntity().getClass(), contentType)) {
					VulpeReflectUtil.setFieldValue(getEntity(), contentType, null);
				}
				final String fileName = getPropertyName().concat(File.SUFFIX_FILE_NAME);
				if (VulpeReflectUtil.fieldExists(getEntity().getClass(), fileName)) {
					VulpeReflectUtil.setFieldValue(getEntity(), fileName, null);
				}
				valid = onUpdatePost();
			}
		}

		return valid;
	}

	/**
	 * Extension point to code before delete file.
	 * 
	 * @since 1.0
	 */
	protected void deleteFileBefore() {
		// extension point
	}

	/**
	 * Extension point to code after delete file.
	 * 
	 * @since 1.0
	 */
	protected void deleteFileAfter() {
		// extension point
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeController#deleteDetail()
	 */
	public void deleteDetail() {
		setOperation(Operation.UPDATE_POST);
		deleteDetailBefore();
		manageButtons(Operation.UPDATE);
		final int size = onDeleteDetail();
		if (size > 0) {
			// final String defaultMessage =
			// getDefaultMessage(Operation.DELETE_DETAIL);
			addActionMessage(size > 1 ? "{vulpe.message.delete.details}"
					: "{vulpe.message.delete.detail}");
			if (getControllerType().equals(ControllerType.TABULAR)) {
				if (!getEntities().isEmpty()) {
					final ENTITY entityTabular = getEntities().get(0);
					if (entityTabular.getClass().isAnnotationPresent(CachedClass.class)) {
						final String entityName = entityTabular.getClass().getSimpleName();
						getCachedClasses().put(entityName, getEntities());
					}
				}
			}
		}
		if (isAjax()) {
			final VulpeBaseDetailConfig detailConfig = getControllerConfig().getDetailConfig(
					getDetail());
			if (detailConfig == null || StringUtils.isBlank(detailConfig.getViewPath())) {
				controlResultForward();
			} else {
				setResultForward(detailConfig.getViewPath());
			}
		} else {
			controlResultForward();
		}
		deleteDetailAfter();
	}

	/**
	 * Extension point to delete detail items.
	 * 
	 * @since 1.0
	 * @return number of items affected
	 */
	protected abstract int onDeleteDetail();

	/**
	 * Extension point to code before delete detail items.
	 * 
	 * @since 1.0
	 */
	protected void deleteDetailBefore() {
		if (!getControllerType().equals(ControllerType.SELECT)
				&& !getControllerType().equals(ControllerType.MAIN)
				&& !getControllerType().equals(ControllerType.TABULAR)) {
			throw new VulpeSystemException(Error.CONTROLLER);
		}
	}

	/**
	 * Extension point to code after delete detail items.
	 * 
	 * @since 1.0
	 */
	protected void deleteDetailAfter() {
		// extension point
	}

	/**
	 * Method to read record.
	 * 
	 * @since 1.0
	 * @return Navigation.
	 */
	@ResetSession
	public void read() {
		if (getControllerType() == null || !getControllerType().equals(ControllerType.TWICE)) {
			setOperation(Operation.READ);
		}
		readBefore();
		onRead();
		manageButtons();
		if (getControllerType().equals(ControllerType.SELECT)) {
			setRequestAttribute(Layout.TARGET_NAME, "entitySelect");
			if (isBack()) {
				controlResultForward();
				setBack(false);
			} else if (isAjax() || isExported()) {
				setResultForward(getControllerConfig().getViewItemsPath());
			} else {
				controlResultForward();
			}
		} else if (getControllerType().equals(ControllerType.REPORT)) {
			setRequestAttribute(Layout.TARGET_NAME, "entitySelect");
			setResultName(Result.REPORT);
			if (isAjax()) {
				setResultForward(getControllerConfig().getViewItemsPath());
			} else {
				controlResultForward();
			}
		} else if (getControllerType().equals(ControllerType.TWICE)) {
			setBodyTwice(ControllerType.SELECT);
			if (isAjax()) {
				setResultForward(getControllerConfig().getViewSelectItemsPath());
			} else {
				setResultForward(getControllerConfig().getViewSelectPath());
			}
		} else {
			if (getControllerType().equals(ControllerType.TABULAR)) {
				if (VulpeValidationUtil.isNotEmpty(getControllerConfig().getDetails())
						&& VulpeValidationUtil.isEmpty(getEntities()) && !isTabularFilter()) {
					createDetails(getControllerConfig().getDetails(), false);
				} else if (VulpeValidationUtil.isEmpty(getEntities())) {
					addActionInfoMessage(getDefaultMessage(Operation.READ));
				}
			}
			controlResultForward();
		}
		readAfter();
	}

	/**
	 * Extension point to read record.
	 * 
	 * @since 1.0
	 */
	protected void onRead() {
		// if (isBack() && !isExecuted()) {
		// return;
		// }
		if (getControllerType().equals(ControllerType.TWICE)) {
			if (ever.containsKey(getSelectFormKey()) && getEntitySelect() == null) {
				setEntitySelect(ever.<ENTITY> getSelf(getSelectFormKey()));
			}
			if (getEntitySelect() == null) {
				setEntitySelect(getEntity());
			}
		}
		if (getControllerConfig().requireOneOfFilters().length > 0
				&& isFiltersEmpty(getEntitySelect())) {
			final StringBuilder filters = new StringBuilder();
			final String orLabel = getText("label.vulpe.or");
			int filterCount = 0;
			for (final String attribute : getControllerConfig().requireOneOfFilters()) {
				if (filterCount > 0) {
					filters.append(" ").append(orLabel).append(" ");
				}
				final String text = getControllerConfig().getTitleKey() + "." + attribute;
				filters.append("\"").append(getText(text)).append("\"");
				++filterCount;
			}
			addActionError("{vulpe.error.validate.require.one.of.filters}", filters.toString());
			return;
		}
		final ENTITY entity = prepareEntity(Operation.READ);
		if (!isExported()
				&& ((getControllerType().equals(ControllerType.SELECT) || getControllerType()
						.equals(ControllerType.TWICE)) && getControllerConfig().getPageSize() > 0)
				|| (getControllerType().equals(ControllerType.TABULAR) && getControllerConfig()
						.getTabularPageSize() > 0)) {
			final Integer page = getPaging() == null || getPaging().getPage() == null ? 1
					: getPaging().getPage();
			setCurrentPage(page);
			final Integer pageSize = getControllerType().equals(ControllerType.TABULAR) ? getControllerConfig()
					.getTabularPageSize()
					: getControllerConfig().getPageSize();
			final Paging<ENTITY> paging = (Paging<ENTITY>) invokeServices(
					getServiceMethodName(Operation.PAGING), new Class[] {
							getControllerConfig().getEntityClass(), Integer.class, Integer.class },
					new Object[] { entity.clone(), pageSize, page });
			setPaging(paging);
			setEntities(paging.getList());
			ever.put(getSelectPagingKey(), paging);
			if (getControllerType().equals(ControllerType.TABULAR)) {
				setTabularSize(paging.getSize());
				if (paging.getList() == null || paging.getList().isEmpty()) {
					setDetail(Controller.ENTITIES);
					if (!isTabularFilter()) {
						onAddDetail(true);
					}
				}
			}
			if (VulpeValidationUtil.isEmpty(getEntities())) {
				addActionInfoMessage(getDefaultMessage(isDeleted() ? Operation.READ_DELETED
						: Operation.READ));
			}
		} else {
			final List<ENTITY> list = (List<ENTITY>) invokeServices(
					getServiceMethodName(Operation.READ), new Class[] { getControllerConfig()
							.getEntityClass() }, new Object[] { entity.clone() });
			setEntities(list);
			if (isExported()) {
				final int size = VulpeValidationUtil.isNotEmpty(list) ? list.size() : 0;
				final Paging<ENTITY> paging = new Paging<ENTITY>();
				paging.setSize(size);
				paging.setList(list);
				setPaging(paging);
			}
		}
		if (getControllerType().equals(ControllerType.REPORT)) {
			setDownloadInfo(doReportLoad());
			if (VulpeValidationUtil.isEmpty(getEntities())) {
				addActionInfoMessage(getDefaultMessage(Operation.REPORT_EMPTY));
			} else {
				addActionMessage(getDefaultMessage(Operation.REPORT_SUCCESS));
			}
		} else {
			ever.put(getSelectFormKey(), entity.clone());
			if (getEntities() != null && !getEntities().isEmpty()) {
				ever.put(getSelectTableKey(), getEntities());
			}
		}
		setExecuted(true);
	}

	/**
	 * Extension point to code before read.
	 * 
	 * @since 1.0
	 */
	protected void readBefore() {
		if (!getControllerType().equals(ControllerType.SELECT)
				&& !getControllerType().equals(ControllerType.TWICE)
				&& !getControllerType().equals(ControllerType.TABULAR)
				&& !getControllerType().equals(ControllerType.REPORT)) {
			throw new VulpeSystemException(Error.CONTROLLER);
		}
	}

	/**
	 * Extension point to code after read.
	 * 
	 * @since
	 */
	protected void readAfter() {
		// extension point
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeController#tabularFilter()
	 */
	@ResetSession
	public void tabularFilter() {
		setTabularFilter(true);
		read();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeController#tabularPost()
	 */
	@ResetSession
	public void tabularPost() {
		if (getEntities() != null) {
			setOperation(Operation.TABULAR_POST);
			tabularPostBefore();
			controlResultForward();
			manageButtons();
			if (validateDetails() && onTabularPost()) {
				addActionMessage(getDefaultMessage());
				if (!getEntities().isEmpty()) {
					final ENTITY entityTabular = getEntities().get(0);
					if (entityTabular.getClass().isAnnotationPresent(CachedClass.class)) {
						final String entityName = entityTabular.getClass().getSimpleName();
						final List<ENTITY> list = new ArrayList<ENTITY>();
						for (final ENTITY entity : getEntities()) {
							if (validateCacheClass(entity)) {
								list.add(entity);
							}
						}
						Collections.sort(list);
						getCachedClasses().put(entityName, list);
					}
				}
			}
		}
		tabularPostAfter();
	}

	/**
	 * Extension point to logic tabulate.
	 * 
	 * @since 1.0
	 */
	protected boolean onTabularPost() {
		final int size = getEntities().size();
		final int sizeDespise = getEntities().size();
		if (getControllerConfig().getTabularPageSize() > 0) {
			setTabularSize(getTabularSize() - (size - sizeDespise));
		}
		if (!VulpeValidationUtil.isEmpty(getEntities())) {
			for (final ENTITY entity : getEntities()) {
				updateAuditInformation(entity);
			}
			final List<ENTITY> list = (List<ENTITY>) invokeServices(
					getServiceMethodName(Operation.PERSIST), new Class[] { List.class },
					new Object[] { getEntities() });
			setEntities(list);
		}
		tabularPagingMount(false);
		setExecuted(true);
		return true;
	}

	/**
	 * Extension point to code before logic tabulate.
	 * 
	 * @since 1.0
	 */
	protected void tabularPostBefore() {
		if (!getControllerType().equals(ControllerType.TABULAR)) {
			throw new VulpeSystemException(Error.CONTROLLER);
		}
	}

	/**
	 * Extension point to code after logic tabulate.
	 * 
	 * @since 1.0
	 */
	protected void tabularPostAfter() {
		// extension point
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeController#prepare()
	 */
	@ResetSession(before = true)
	public void prepare() {
		prepareBefore();
		onPrepare();
		manageButtons(Operation.PREPARE);
		if (getControllerType().equals(ControllerType.SELECT)
				|| getControllerType().equals(ControllerType.REPORT)) {
			if (isBack()) {
				setEntitySelect(ever.<ENTITY> getSelf(getSelectFormKey()));
				setEntities(ever.<List<ENTITY>> getSelf(getSelectTableKey()));
				read();
			} else {
				ever.remove(getSelectFormKey());
				ever.remove(getSelectTableKey());
			}
			controlResultForward();
		} else if (getControllerType().equals(ControllerType.TABULAR)) {
			read();
		} else if (getControllerType().equals(ControllerType.TWICE)) {
			setBodyTwice(ControllerType.SELECT);
			setResultForward(Layout.PROTECTED_JSP_COMMONS.concat(Layout.BODY_JSP));
		} else {
			controlResultForward();
		}
		prepareAfter();
	}

	@ResetSession(before = true)
	public void twice() {
		changeControllerType(ControllerType.TWICE);
		prepareBefore();
		onPrepare();
		manageButtons(Operation.TWICE);
		controlResultForward();
		prepareAfter();
	}

	@ResetSession(before = true)
	public void export() {
		setControllerType(ControllerType.SELECT);
		exportBefore();
		setOnlyToSee(true);
		setExported(true);
		onExport();
		// setResultForward(getControllerConfig().getViewItemsPath());
		// setResultName(Result.EXPORT);
		ever.put(Ever.EXPORT_CONTENT, "PDF");
		exportAfter();
	}

	protected void onExport() {
		read();
	}

	protected void exportBefore() {
		// extension point
	}

	protected void exportAfter() {
		// extension point
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeController#select()
	 */
	@ResetSession(before = true)
	@Override
	public void select() {
		changeControllerType(ControllerType.SELECT);
		selectBefore();
		onPrepare();
		manageButtons(Operation.PREPARE);
		if (isBack()) {
			setEntitySelect(ever.<ENTITY> getSelf(getSelectFormKey()));
			setEntities(ever.<List<ENTITY>> getSelf(getSelectTableKey()));
			setPaging(ever.<Paging<ENTITY>> getSelf(getSelectPagingKey()));
			if (getPaging() != null) {
				getPaging().setList(getEntities());
			}
		}
		controlResultForward();
		if (isBack()) {
			selectAfter();
			read();
			return;
		} else {
			ever.remove(getSelectFormKey());
			ever.remove(getSelectTableKey());
			ever.remove(getSelectPagingKey());
		}
		selectAfter();
		if (getControllerConfig().getControllerAnnotation().select().readOnShow() && !isCleaned()) {
			onRead();
		}
	}

	/**
	 * Extension point to code before select.
	 */
	protected void selectBefore() {
		// extension point
	}

	/**
	 * Extension point to code after select.
	 */
	protected void selectAfter() {
		// extension point
	}

	@ResetSession(before = true)
	public void report() {
		changeControllerType(ControllerType.REPORT);
		reportBefore();
		manageButtons(Operation.PREPARE);
		read();
		reportAfter();
		changeControllerType(ControllerType.SELECT);
	}

	/**
	 * Extension point to code before report.
	 */
	protected void reportBefore() {
		// extension point
	}

	/**
	 * Extension point to code after report.
	 */
	protected void reportAfter() {
		// extension point
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeController#tabular()
	 */
	@ResetSession(before = true)
	public void tabular() {
		changeControllerType(ControllerType.TABULAR);
		if (getControllerConfig().isTabularShowFilter()) {
			try {
				setEntitySelect(getControllerConfig().getEntityClass().newInstance());
			} catch (Exception e) {
				LOG.error(e);
			}
		}
		tabularBefore();
		onPrepare();
		manageButtons(Operation.TABULAR);
		tabularAfter();
		read();
	}

	/**
	 * Extension point to code before tabular.
	 */
	protected void tabularBefore() {
		// extension point
	}

	/**
	 * Extension point to code after tabular.
	 */
	protected void tabularAfter() {
		// extension point
	}

	/**
	 * Extension point to prepare show.
	 * 
	 * @since 1.0
	 */
	protected void onPrepare() {
		setEntities(null);
		try {
			if (getControllerType().equals(ControllerType.TWICE)) {
				if (getEntity() == null) {
					setEntity(getControllerConfig().getEntityClass().newInstance());
				}
			}
			if (getEntitySelect() == null) {
				setEntitySelect(getControllerConfig().getEntityClass().newInstance());
			}
		} catch (Exception e) {
			if (getControllerType().equals(ControllerType.TWICE)) {
				setEntity(null);
			}
			setEntitySelect(null);
		}
		setPaging(null);
		setExecuted(false);
	}

	/**
	 * Extension point to code before prepare.
	 */
	protected void prepareBefore() {
		// extension point
	}

	/**
	 * Extension point to code after prepare.
	 */
	protected void prepareAfter() {
		// extension point
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeSimpleController#upload()
	 */
	public void upload() {
		uploadBefore();
		onUpload();
		uploadAfter();
		setResultName(Result.UPLOAD);
	}

	/**
	 * Extension point to upload.
	 * 
	 * @since 1.0
	 */
	protected void onUpload() {
		setUploaded(true);
	}

	/**
	 * Extension point to code before upload.
	 * 
	 * @since 1.0
	 */
	protected void uploadAfter() {
		LOG.debug("uploadAfter");
	}

	/**
	 * Extension point to code after upload.
	 * 
	 * @since 1.0
	 */
	protected void uploadBefore() {
		LOG.debug("uploadBefore");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeSimpleController#download()
	 */
	public void download() {
		downloadBefore();
		onDownload();
		downloadAfter();
		setResultName(Result.DOWNLOAD);
	}

	/**
	 * Extension point to download.
	 * 
	 * @since 1.0
	 */
	protected void onDownload() {
		final DownloadInfo downloadInfo = prepareDownloadInfo();
		setDownloadInfo(downloadInfo);
	}

	/**
	 * Extension point to prepare download.
	 * 
	 * @since 1.0
	 */
	protected abstract DownloadInfo prepareDownloadInfo();

	/**
	 * Extension point to code before download.
	 * 
	 * @since 1.0
	 */
	protected void downloadAfter() {
		LOG.debug("downloadAfter");
	}

	/**
	 * Extension point to code after download.
	 * 
	 * @since 1.0
	 */
	protected void downloadBefore() {
		LOG.debug("downloadBefore");
	}

	/**
	 * 
	 * @param entity
	 */
	public boolean isFiltersEmpty(final ENTITY entity) {
		boolean empty = true;
		for (String attribute : getControllerConfig().requireOneOfFilters()) {
			try {
				final Object value = PropertyUtils.getProperty(entity, attribute);
				if (VulpeValidationUtil.isNotEmpty(value)) {
					empty = false;
				}
			} catch (Exception e) {
				LOG.debug(e);
			}
		}
		return empty;
	}

	/**
	 * Retrieves Report Parameters.
	 * 
	 * @return
	 */
	public VulpeHashMap<String, Object> getReportParameters() {
		if (now.containsKey(Controller.REPORT_PARAMETERS)) {
			return now.getSelf(Controller.REPORT_PARAMETERS);
		}
		final VulpeHashMap<String, Object> reportParameters = new VulpeHashMap<String, Object>();
		now.put(Controller.REPORT_PARAMETERS, reportParameters);
		return reportParameters;
	}

	/**
	 * Retrieves Report Collection Data Source.
	 * 
	 * @return
	 */
	public Collection<?> getReportCollection() {
		if (now.containsKey(Controller.REPORT_COLLECTION)) {
			return now.getSelf(Controller.REPORT_COLLECTION);
		}
		return null;
	}

	/**
	 * Sets Report Collection Data Source.
	 * 
	 * @return
	 */
	public void setReportCollection(Collection<?> collection) {
		now.put(Controller.REPORT_COLLECTION, collection);
	}

	public void setOnlyUpdateDetails(boolean onlyUpdateDetails) {
		this.onlyUpdateDetails = onlyUpdateDetails;
	}

	public boolean isOnlyUpdateDetails() {
		onlyUpdateDetails = getControllerConfig().isOnlyUpdateDetails();
		return onlyUpdateDetails;
	}

	public void setDetailLayer(String detailLayer) {
		this.detailLayer = detailLayer;
	}

	public String getDetailLayer() {
		return detailLayer;
	}

	public void setTabularFilter(boolean tabularFilter) {
		this.tabularFilter = tabularFilter;
	}

	public boolean isTabularFilter() {
		return tabularFilter;
	}

	/**
	 * Method to repair cached classes used by entity.
	 * 
	 * @param entity
	 * @return Entity with cached values reloaded
	 */
	protected ENTITY repairCachedClasses(final ENTITY entity) {
		final List<Field> fields = VulpeReflectUtil.getFields(entity.getClass());
		for (final Field field : fields) {
			if (VulpeEntity.class.isAssignableFrom(field.getType())) {
				try {
					final VulpeEntity<ID> value = (VulpeEntity<ID>) PropertyUtils.getProperty(
							entity, field.getName());
					if (VulpeValidationUtil.isNotEmpty(value)
							&& !Modifier.isTransient(field.getModifiers())
							&& value.getClass().isAnnotationPresent(CachedClass.class)) {
						final List<ENTITY> cachedList = getCachedClasses().getSelf(
								value.getClass().getSimpleName());
						if (VulpeValidationUtil.isNotEmpty(cachedList)) {
							for (final ENTITY cached : cachedList) {
								if (cached.getId().equals(value.getId())) {
									PropertyUtils.setProperty(entity, field.getName(), cached);
									break;
								}
							}
						}
					}
				} catch (IllegalAccessException e) {
					LOG.error(e);
				} catch (InvocationTargetException e) {
					LOG.error(e);
				} catch (NoSuchMethodException e) {
					LOG.error(e);
				}
			}
		}
		return entity;
	}

	/**
	 * 
	 * @param entity
	 * @return
	 */
	private boolean validateCacheClass(final ENTITY entity) {
		boolean valid = true;
		final CachedClass cachedClass = getControllerConfig().getEntityClass().getAnnotation(
				CachedClass.class);
		if (cachedClass != null) {
			if (cachedClass.parameters().length > 0) {
				for (final QueryParameter queryParameter : cachedClass.parameters()) {
					if (StringUtils.isNotBlank(queryParameter.equals().name())
							&& StringUtils.isNotBlank(queryParameter.equals().value())) {
						final Object value = VulpeReflectUtil.getFieldValue(entity, queryParameter
								.equals().name());
						if (VulpeValidationUtil.isNotEmpty(value)
								&& !value.toString().equals(queryParameter.equals().value())) {
							valid = false;
							break;
						}
					}
				}
			}
		}
		return valid;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeSimpleController#backend()
	 */
	public void backend() {
		getControllerConfig().setControllerType(ControllerType.BACKEND);
		backendBefore();
		onBackend();
		controlResultForward();
		backendAfter();
	}

	/**
	 * Extension point to prepare show.
	 * 
	 * @since 1.0
	 */
	protected void onBackend() {
		setExecuted(false);
	}

	/**
	 * Extension point to code before prepare.
	 */
	protected void backendBefore() {
		LOG.debug("backendBefore");
	}

	/**
	 * Extension point to code after prepare.
	 */
	protected void backendAfter() {
		LOG.debug("backendAfter");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeSimpleController#frontend()
	 */
	public void frontend() {
		getControllerConfig().setControllerType(ControllerType.FRONTEND);
		frontendBefore();
		onFrontend();
		controlResultForward();
		frontendAfter();
	}

	/**
	 * Extension point to prepare show.
	 * 
	 * @since 1.0
	 */
	protected void onFrontend() {
		setExecuted(false);
	}

	/**
	 * Extension point to code before prepare.
	 */
	protected void frontendBefore() {
		LOG.debug("frontendBefore");
	}

	/**
	 * Extension point to code after prepare.
	 */
	protected void frontendAfter() {
		LOG.debug("frontendAfter");
	}

	/**
	 * Method to invoke services.
	 * 
	 * @param serviceName
	 *            Name of service
	 * @param argsType
	 *            Types of arguments
	 * @param argsValues
	 *            Arguments values
	 * 
	 * @since 1.0
	 * @return Object
	 */
	public Object invokeServices(final String serviceName, final Class<?>[] argsType,
			final Object[] argsValues) {
		final VulpeService service = getService();
		try {
			final Method method = service.getClass().getMethod(serviceName, argsType);
			return method.invoke(service, argsValues);
		} catch (Exception e) {
			throw new VulpeSystemException(e);
		}
	}

	protected String getServiceMethodName(final Operation operation) {
		return operation.getValue().concat(getControllerConfig().getEntityClass().getSimpleName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeSimpleController#getService()
	 */
	public VulpeService getService() {
		return getService(getControllerConfig().getServiceClass());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.VulpeSimpleController#getService(java.lang.Class )
	 */
	public <T extends VulpeService> T getService(final Class<T> serviceClass) {
		return VulpeServiceLocator.getInstance().getService(serviceClass);
	}

	/**
	 * Method to add error message.
	 * 
	 * @param key
	 *            Key in resource bundle
	 * @param args
	 *            arguments
	 * 
	 * @since 1.0
	 */
	public void addActionError(final String key, final Object... args) {
		addActionError(getText(key, args));
	}

	/**
	 * Method to add error message.
	 * 
	 * @param key
	 *            Key in resource bundle
	 * 
	 * @since 1.0
	 */
	public void addActionErrorKey(final String key) {
		addActionError(getText(key));
	}

	/**
	 * Method to add warning message.
	 * 
	 * @param key
	 *            Key in resource bundle
	 * @param args
	 *            arguments
	 * 
	 * @since 1.0
	 */
	public void addActionMessage(final String key, final Object... args) {
		addActionMessage(getText(key, args));
	}

	public void addActionInfoMessage(final String key, final Object... args) {
		addActionInfoMessage(getText(key, args));
	}

	public void addActionInfoMessage(final String aMessage) {
		if (StringUtils.isNotBlank(aMessage)) {
			if (aMessage.startsWith("{") && aMessage.endsWith("}")) {
				final String message = getText(aMessage.substring(1, aMessage.length() - 1));
				getActionInfoMessages().add(message);
			} else {
				getActionInfoMessages().add(aMessage);
			}
		}
	}

	/**
	 * Method to add warning message.
	 * 
	 * @param key
	 *            Key in resource bundle
	 * 
	 * @since 1.0
	 */
	public void addActionMessageKey(final String key) {
		addActionMessage(getText(key));
	}

	public String getTextArg(final String key, final String arg) {
		return getText(key, getText(arg));
	}

	public String getTextArg(final String key, final String arg1, final String arg2) {
		return getText(key, getText(arg1), getText(arg2));
	}

	public String getTextArg(final String key, final String arg1, final String arg2,
			final String arg3) {
		return getText(key, getText(arg1), getText(arg2), getText(arg3));
	}

	public String getTextArg(final String key, final String arg1, final String arg2,
			final String arg3, final String arg4) {
		return getText(key, getText(arg1), getText(arg2), getText(arg3), getText(arg4));
	}

	/**
	 * URL Redirect.
	 */
	private String urlRedirect;

	/**
	 * Result Result.
	 */
	private String resultForward;
	/**
	 * Result Name.
	 */
	private String resultName = Result.SUCCESS;
	/**
	 * Operation
	 */
	private Operation operation;
	private boolean ajax = false;
	private boolean back = false;
	private boolean executed = false;
	private boolean deleted = false;
	/**
	 * Popup Key
	 */
	private String popupKey;
	/**
	 *
	 */
	private String onHideMessages;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeSimpleController#getResultForward()
	 */
	public String getResultForward() {
		return resultForward;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.VulpeSimpleController#setResultForward(java.lang
	 * .String)
	 */
	public void setResultForward(final String resultForward) {
		this.resultForward = resultForward;
	}

	/**
	 * 
	 * @param page
	 */
	public void setResultPage(final String page) {
		if (StringUtils.isNotEmpty(page)) {
			setResultForward(Layout.PROTECTED_JSP
					+ (page.startsWith("/") ? page.substring(1) : page));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeSimpleController#isAjax()
	 */
	public boolean isAjax() {
		return ajax;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeSimpleController#setAjax(boolean)
	 */
	public void setAjax(final boolean ajax) {
		this.ajax = ajax;
	}

	public String getResultName() {
		return resultName;
	}

	public void setResultName(final String resultName) {
		this.resultName = resultName;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(final Operation operation) {
		this.operation = operation;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeSimpleController#getOnHideMessages()
	 */
	public String getOnHideMessages() {
		return onHideMessages;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeSimpleController#setOnHideMessages(java
	 * .lang.String)
	 */
	public void setOnHideMessages(final String onHideMessages) {
		this.onHideMessages = onHideMessages;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeSimpleController#isBack()
	 */
	public boolean isBack() {
		return back;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeSimpleController#setBack(boolean)
	 */
	public void setBack(final boolean back) {
		this.back = back;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeSimpleController#isExecuted()
	 */
	public boolean isExecuted() {
		return executed;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeSimpleController#setExecuted(boolean)
	 */
	public void setExecuted(final boolean executed) {
		this.executed = executed;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeSimpleController#getPopupKey()
	 */
	public String getPopupKey() {
		return popupKey;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeSimpleController#setPopupKey(java.lang.
	 * String)
	 */
	public void setPopupKey(final String popupKey) {
		this.popupKey = popupKey;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeSimpleController#isPopup()
	 */
	public boolean isPopup() {
		return StringUtils.isNotEmpty(getPopupKey());
	}

	public VulpeHashMap<String, Object> getCachedClasses() {
		return VulpeCacheHelper.getInstance().get(VulpeConstants.CACHED_CLASSES);
	}

	public VulpeHashMap<String, Object> getCachedEnums() {
		return VulpeCacheHelper.getInstance().get(VulpeConstants.CACHED_ENUMS);
	}

	public VulpeHashMap<String, Object> getCachedEnumsArray() {
		return VulpeCacheHelper.getInstance().get(VulpeConstants.CACHED_ENUMS_ARRAY);
	}

	/**
	 * 
	 * @param entityClass
	 * @param id
	 * @return
	 */
	public <T extends VulpeEntity<?>> T findOnCachedClasses(final Class<T> entityClass,
			final Long id) {
		final List<T> entities = getCachedClasses().getSelf(entityClass.getSimpleName());
		for (final T entity : entities) {
			if (entity.getId().equals(id)) {
				return entity;
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.VulpeSimpleController#setUrlBack(java.lang.String )
	 */
	public void setUrlBack(final String urlBack) {
		getSession().setAttribute(VulpeConstants.View.URL_BACK, urlBack);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeSimpleController#setLayerUrlBack(java.lang
	 * .String)
	 */
	public void setLayerUrlBack(final String layerUrlBack) {
		getSession().setAttribute(VulpeConstants.View.LAYER_URL_BACK, layerUrlBack);
	}

	/**
	 * Retrieves controller type
	 * 
	 * @return Controller Type
	 */
	public ControllerType getControllerType() {
		return getControllerConfig().getControllerType();
	}

	public void setControllerType(ControllerType controllerType) {
		getControllerConfig().setControllerType(controllerType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeSimpleController#controlResultForward()
	 */
	public void controlResultForward() {
		setResultForward(getControllerType().equals(ControllerType.TWICE) ? Layout.PROTECTED_JSP_COMMONS
				.concat(Layout.BODY_TWICE_JSP)
				: Layout.PROTECTED_JSP_COMMONS.concat(Layout.BODY_JSP));
	}

	/**
	 * 
	 * @param controllerType
	 */
	protected void setBodyTwice(final ControllerType controllerType) {
		setRequestAttribute(Layout.BODY_TWICE, true);
		setRequestAttribute(Layout.BODY_TWICE_TYPE, controllerType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeSimpleController#getSecurityContext()
	 */
	public VulpeSecurityContext getSecurityContext() {
		VulpeSecurityContext securityContext = ever.getSelf(Security.SECURITY_CONTEXT);
		if (securityContext == null) {
			securityContext = getBean(VulpeSecurityContext.class);
			ever.put(Security.SECURITY_CONTEXT, securityContext);
			if (securityContext != null) {
				securityContext.afterUserAuthenticationCallback();
			}
		}
		return securityContext;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeSimpleController#getUserAuthenticated()
	 */
	@Override
	public String getUserAuthenticated() {
		return getSecurityContext().getUsername();
	}

	public boolean hasRole(final String role) {
		final StringBuilder roleName = new StringBuilder();
		if (!role.startsWith(Security.ROLE_PREFIX)) {
			roleName.append(Security.ROLE_PREFIX);
		}
		roleName.append(role);
		boolean has = false;
		final VulpeSecurityContext vsc = getSecurityContext();
		if (vsc != null) {
			final Object springSecurityAutentication = VulpeReflectUtil.getFieldValue(vsc,
					"authentication");
			if (springSecurityAutentication != null) {
				final Collection<?> authorities = VulpeReflectUtil.getFieldValue(
						springSecurityAutentication, "authorities");
				if (authorities != null) {
					for (final Object authority : authorities) {
						if (authority.equals(roleName.toString())) {
							has = true;
							break;
						}
					}

				}
			}
		}
		return has;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeSimpleController#getBean(java.lang.String)
	 */
	public <T> T getBean(final String beanName) {
		return (T) AbstractVulpeBeanFactory.getInstance().getBean(beanName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeSimpleController#getBean(java.lang.Class)
	 */
	public <T> T getBean(final Class<T> clazz) {
		return (T) getBean(clazz.getSimpleName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeSimpleController#getSessionAttribute(java
	 * .lang.String)
	 */
	public <T> T getSessionAttribute(final String attributeName) {
		return (T) getSession().getAttribute(attributeName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.VulpeSimpleController#setSessionAttribute(java.lang
	 * .String, java.lang.Object)
	 */
	public void setSessionAttribute(final String attributeName, final Object attributeValue) {
		getSession().setAttribute(attributeName, attributeValue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeSimpleController#getRequestAttribute(java
	 * .lang.String)
	 */
	public <T> T getRequestAttribute(final String attributeName) {
		return (T) getRequest().getAttribute(attributeName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.VulpeSimpleController#setRequestAttribute(java.lang
	 * .String, java.lang.Object)
	 */
	public void setRequestAttribute(final String attributeName, final Object attributeValue) {
		getRequest().setAttribute(attributeName, attributeValue);
	}

	public String getText(final String key) {
		final String validKey = key.replace("{", "").replace("}", "");
		return i18nService.getText(validKey);
	}

	public String getText(final String key, final Object... args) {
		final String validKey = key.replace("{", "").replace("}", "");
		return i18nService.getText(validKey, args);
	}

	public abstract void addActionMessage(final String message);

	public abstract void addActionError(final String message);

	public VulpeHashMap<String, Tab> getTabs() {
		if (now.containsKey(Controller.TABS)) {
			return now.getSelf(Controller.TABS);
		}
		final VulpeHashMap<String, Tab> tabs = new VulpeHashMap<String, Tab>();
		now.put(Controller.TABS, tabs);
		return tabs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeSimpleController#getSelectFormKey()
	 */
	public String getSelectFormKey() {
		return getControllerKey() + Controller.SELECT_FORM;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeSimpleController#getSelectTableKey()
	 */
	public String getSelectTableKey() {
		return getControllerKey() + Controller.SELECT_TABLE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeSimpleController#getSelectPagingKey()
	 */
	public String getSelectPagingKey() {
		return getControllerKey() + Controller.SELECT_PAGING;
	}

	private String getControllerKey() {
		String key = getCurrentControllerKey();
		if (StringUtils.isNotEmpty(getControllerConfig().getViewBaseName())) {
			key = key.substring(0, key.lastIndexOf(".") + 1)
					+ getControllerConfig().getViewBaseName();
		}
		return key;
	}

	public String getCurrentControllerKey() {
		return VulpeConfigHelper.getProjectName().concat(".").concat(
				getCurrentControllerName().replace("/", "."));
	}

	/**
	 * 
	 * @return
	 */
	public String getCurrentControllerName() {
		String base = "";
		final Component component = this.getClass().getAnnotation(Component.class);
		if (component != null) {
			base = component.value().replaceAll("\\.", "/")
					.replace(Generator.CONTROLLER_SUFFIX, "");
		}
		return base;
	}

	public String getCurrentMethodName() {
		return now.getSelf(Now.CURRENT_METHOD_NAME);
	}

	public void setCurrentMethodName(final String methodName) {
		now.put(Now.CURRENT_METHOD_NAME, methodName);
	}

	protected void changeControllerType(final ControllerType controllerType) {
		getControllerConfig().setControllerType(controllerType);
		postConstruct();
	}

	public void setUrlRedirect(String urlRedirect) {
		this.urlRedirect = urlRedirect;
	}

	public String getUrlRedirect() {
		return urlRedirect;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.VulpeSimpleController#redirectTo(java.lang.String,
	 * boolean)
	 */
	public void redirectTo(final String url, final boolean ajax) {
		setUrlRedirect(url + (ajax ? "/ajax" : ""));
		setResultName(Result.REDIRECT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.VulpeSimpleController#redirectTo(java.lang.String)
	 */
	public void redirectTo(final String url) {
		redirectTo(url, isAjax());
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.VulpeSimpleController#returnToPage(java.lang.String)
	 */
	public void returnToPage(final String page) {
		final StringBuilder path = new StringBuilder();
		if (!page.contains("/") && !page.contains(".")) {
			path.append(Layout.PROTECTED_JSP);
			final String directory = getControllerConfig().getModuleName() + "/"
					+ getControllerConfig().getSimpleControllerName() + "/";
			path.append(directory);
			path.append(page).append(Layout.SUFFIX_JSP);
		} else {
			path.append(page);
		}
		setResultForward(path.toString());
	}

	public void setActionInfoMessages(Collection<String> actionInfoMessages) {
		this.actionInfoMessages = actionInfoMessages;
	}

	public Collection<String> getActionInfoMessages() {
		return actionInfoMessages;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setJsonRoot(Object jsonRoot) {
		this.jsonRoot = jsonRoot;
	}

	public Object getJsonRoot() {
		return jsonRoot;
	}

	protected void renderError() {
		setResultName(Result.ERRORS);
	}

	protected void renderMessages() {
		setResultName(Result.MESSAGES);
	}

	protected void renderSuccess() {
		setResultName(Result.SUCCESS);
	}

	protected void renderJSON(final Object jsonElement) {
		setJsonRoot(jsonElement);
		setResultName(Result.JSON);
	}

	protected void renderSimpleJSON(final Object jsonElement) {
		// now.put("RESULT_TYPE", "/*[JSON]*/");
		now.put("PLAIN_TEXT", new Gson().toJson(jsonElement));
		setResultName(Result.PLAIN_TEXT);
	}

	protected void renderJavascript(final Object object) {
		now.put("RESULT_TYPE", "/*[JS]*/");
		now.put("PLAIN_TEXT", object);
		setResultName(Result.PLAIN_TEXT);
	}

	protected void renderPlainText(final Object object) {
		now.put("RESULT_TYPE", "/*[PLAINTEXT]*/");
		now.put("PLAIN_TEXT", object);
		setResultName(Result.PLAIN_TEXT);
	}

	protected String toJson(final Object jsonElement) {
		return new Gson().toJson(jsonElement);
	}

	public void setCleaned(boolean cleaned) {
		this.cleaned = cleaned;
	}

	public boolean isCleaned() {
		return cleaned;
	}

	public void setExported(boolean exported) {
		this.exported = exported;
	}

	public boolean isExported() {
		return exported;
	}

	public void setReportFormat(String reportFormat) {
		this.reportFormat = reportFormat;
	}

	public String getReportFormat() {
		return reportFormat;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public boolean isDeleted() {
		return deleted;
	}
}