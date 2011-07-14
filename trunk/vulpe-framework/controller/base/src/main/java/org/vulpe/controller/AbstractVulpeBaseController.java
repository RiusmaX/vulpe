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
import org.vulpe.commons.VulpeContext;
import org.vulpe.commons.VulpeServiceLocator;
import org.vulpe.commons.VulpeConstants.Controller;
import org.vulpe.commons.VulpeConstants.Error;
import org.vulpe.commons.VulpeConstants.Security;
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
import org.vulpe.commons.factory.AbstractVulpeBeanFactory;
import org.vulpe.commons.helper.VulpeConfigHelper;
import org.vulpe.commons.util.VulpeHashMap;
import org.vulpe.commons.util.VulpeReflectUtil;
import org.vulpe.commons.util.VulpeValidationUtil;
import org.vulpe.controller.commons.DuplicatedBean;
import org.vulpe.controller.commons.EverParameter;
import org.vulpe.controller.commons.I18NService;
import org.vulpe.controller.commons.VulpeBaseControllerConfig;
import org.vulpe.controller.commons.VulpeBaseDetailConfig;
import org.vulpe.controller.commons.VulpeControllerConfig.ControllerType;
import org.vulpe.controller.util.VulpeUtil;
import org.vulpe.controller.util.VulpeUtil.VulpeViewUtil;
import org.vulpe.controller.util.VulpeUtil.VulpeViewUtil.VulpeViewContentUtil;
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
	 * 
	 */
	public VulpeUtil<ENTITY, ID> vulpe;

	/**
	 * Calendar
	 */
	public final Calendar calendar = Calendar.getInstance();

	{
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
		vulpe = new VulpeUtil<ENTITY, ID>(this);
		final VulpeBaseControllerConfig<ENTITY, ID> config = vulpe.controller().config();
		final VulpeViewUtil view = vulpe.view();
		view.maxInactiveInterval(getSession().getMaxInactiveInterval());
		view.formName(config.getFormName());
		if (config.isRequireOneFilter()) {
			view.requireOneFilter();
		}
		final VulpeViewContentUtil content = view.content();
		content.titleKey(config.getTitleKey());
		if (VulpeValidationUtil.isNotEmpty(config.getDetails())) {
			content.masterTitleKey(config.getMasterTitleKey());
		}
		if (vulpe.controller().type().equals(ControllerType.REPORT)) {
			content.reportTitleKey(config.getReportTitleKey());
		}
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
		return getDefaultMessage(vulpe.controller().operation());
	}

	public void setDefaultMessage(final String message) {
		defaultMessage.put(vulpe.controller().operation(), message);
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

	public void setEntitySelect(final ENTITY entitySelect) {
		this.entitySelect = entitySelect;
	}

	public ENTITY getEntitySelect() {
		return entitySelect;
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
			final String tabName = vulpe.view().tabs().containsKey(detailConfig.getTitleKey()) ? vulpe
					.view().tabs().get(detailConfig.getTitleKey()).getTitle()
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
		final Collection<DuplicatedBean> duplicatedBeans = vulpe.controller().duplicatedItens(
				beans, despiseFields);
		if (duplicatedBeans != null && !duplicatedBeans.isEmpty()) {
			if (vulpe.controller().type().equals(ControllerType.TABULAR)
					&& duplicatedBeans.size() == 1) {
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
			if (vulpe.controller().type().equals(ControllerType.TABULAR)) {
				addActionError("vulpe.error.tabular.duplicated", lines.toString());
			} else {
				final String tabName = vulpe.view().tabs().containsKey(detailConfig.getTitleKey()) ? vulpe
						.view().tabs().get(detailConfig.getTitleKey()).getTitle()
						: getText(detailConfig.getTitleKey());
				if (detailConfig.getParentDetailConfig() != null) {
					final String parentTabName = vulpe.view().tabs().containsKey(
							detailConfig.getParentDetailConfig().getTitleKey()) ? vulpe.view()
							.tabs().get(detailConfig.getParentDetailConfig().getTitleKey())
							.getTitle() : getText(detailConfig.getParentDetailConfig()
							.getTitleKey());
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
		return vulpe.controller().despiseItens(beans, detailConfig.getDespiseFields(),
				vulpe.controller().type().equals(ControllerType.TABULAR));
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

	public void renderDetailButton(final String detail, final String button) {
		vulpe.view().buttons().put(button.concat(detail), new ButtonConfig(true, true, false));
	}

	public void notRenderDetailButton(final String detail, final String button) {
		vulpe.view().buttons().put(button.concat(detail), new ButtonConfig(false));
	}

	public void showDetailButton(final String detail, final String button) {
		vulpe.view().buttons().put(button.concat(detail), new ButtonConfig(true, true));
	}

	public void hideDetailButton(final String detail, final String button) {
		vulpe.view().buttons().put(button.concat(detail), new ButtonConfig(true, false));
	}

	public void enableDetailButton(final String detail, final String button) {
		vulpe.view().buttons().put(button.concat(detail), new ButtonConfig(true, true, false));
	}

	public void disableDetailButton(final String detail, final String button) {
		vulpe.view().buttons().put(button.concat(detail), new ButtonConfig(true, true, true));
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
		if (vulpe.controller().type().equals(ControllerType.TABULAR)) {
			String deleteButtonKey = Button.DELETE.concat(vulpe.controller().config()
					.getTabularConfig().getBaseName());
			if (vulpe.view().buttons().containsKey(key)) {
				buttonConfig = vulpe.view().buttons().getSelf(key);
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
			vulpe.view().buttons().put(deleteButtonKey, buttonConfig);
		}
		if (Button.ADD_DETAIL.equals(button)) {
			key = Button.ADD_DETAIL.concat(vulpe.controller().config().getTabularConfig()
					.getBaseName());
		}
		if (vulpe.view().buttons().containsKey(key)) {
			buttonConfig = vulpe.view().buttons().getSelf(key);
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
		vulpe.view().buttons().put(key, buttonConfig);
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
		if (vulpe.controller().type().equals(ControllerType.TABULAR)) {
			String deleteButtonKey = Button.DELETE.concat(vulpe.controller().config()
					.getTabularConfig().getBaseName());
			if (vulpe.view().buttons().containsKey(key)) {
				buttonConfig = vulpe.view().buttons().getSelf(key);
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
			vulpe.view().buttons().put(deleteButtonKey, buttonConfig);
		}
		if (Button.ADD_DETAIL.equals(button)) {
			key = Button.ADD_DETAIL.concat(vulpe.controller().config().getTabularConfig()
					.getBaseName());
		}
		if (vulpe.view().buttons().containsKey(key)) {
			buttonConfig = vulpe.view().buttons().getSelf(key);
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
		vulpe.view().buttons().put(key, buttonConfig);
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
		manageButtons(vulpe.controller().operation());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.VulpeController#manageButtons(org.vulpe.controller
	 * .VulpeSimpleController.Operation)
	 */
	public void manageButtons(final Operation operation) {
		vulpe.view().buttons().clear();
		if (vulpe.controller().type().equals(ControllerType.MAIN)) {
			if (vulpe.controller().config().getDetails() != null) {
				for (final VulpeBaseDetailConfig detail : vulpe.controller().config().getDetails()) {
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
					|| ((Operation.CREATE.equals(vulpe.controller().operation()) || Operation.CREATE_POST
							.equals(vulpe.controller().operation())) && Operation.ADD_DETAIL
							.equals(operation))) {
				renderButtons(Button.BACK, Button.CREATE_POST, Button.CLEAR);
			} else if (Operation.UPDATE.equals(operation)
					|| ((Operation.UPDATE.equals(vulpe.controller().operation()) || Operation.UPDATE_POST
							.equals(vulpe.controller().operation())) && Operation.ADD_DETAIL
							.equals(operation))) {
				renderButtons(Button.BACK, Button.CREATE, Button.UPDATE_POST, Button.DELETE);
				if (VulpeConfigHelper.getProjectConfiguration().view().layout().showButtonClone()) {
					renderButtons(Button.CLONE);
				}
			} else if (Operation.VIEW.equals(operation)) {
				renderButtons();
			}
		} else if (vulpe.controller().type().equals(ControllerType.SELECT)) {
			renderButtons(Button.READ, Button.CLEAR, Button.CREATE, Button.UPDATE, Button.DELETE);
			if (vulpe.controller().config().getControllerAnnotation().select().showReport()) {
				renderButtons(Button.REPORT);
			} else {
				notRenderButtons(Button.REPORT);
			}
			if (vulpe.controller().popup()) {
				notRenderButtons(Button.CREATE, Button.UPDATE, Button.DELETE);
			}
		} else if (vulpe.controller().type().equals(ControllerType.REPORT)) {
			renderButtons(Button.READ, Button.CLEAR);
		} else if (vulpe.controller().type().equals(ControllerType.TABULAR)) {
			renderButtons(Button.TABULAR_RELOAD, Button.DELETE, Button.TABULAR_POST,
					Button.ADD_DETAIL);
			if (vulpe.controller().config().isTabularShowFilter()) {
				renderButtons(Button.TABULAR_FILTER);
			}
		} else if (vulpe.controller().type().equals(ControllerType.TWICE)) {
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
		manageButtons(vulpe.controller().operation());
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
		if ((vulpe.controller().operation().equals(Operation.CREATE_POST) || vulpe.controller()
				.operation().equals(Operation.UPDATE_POST))) {
			if (validateNotExistEquals()) {
				final NotExistEquals notExistEqual = vulpe.controller().config().getEntityClass()
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
		for (final VulpeBaseDetailConfig detailConfig : vulpe.controller().config().getDetails()) {
			if (vulpe.controller().deleted()) {
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
		if (vulpe.controller().type().equals(ControllerType.TABULAR)
				&& vulpe.controller().config().getTabularPageSize() > 0) {
			if (add) {
				vulpe.controller().tabularSize(
						vulpe.controller().tabularSize()
								+ vulpe.controller().config().getControllerAnnotation().tabular()
										.newRecords());
			} else {
				vulpe.controller().tabularSize(getEntities().size());
			}
			Integer page = vulpe.controller().currentPage();
			if (getPaging() != null) {
				page = getPaging().getPage();
			}
			if (page == null) {
				page = 0;
			}
			setPaging(new Paging<ENTITY>(vulpe.controller().tabularSize(), vulpe.controller()
					.config().getTabularPageSize(), page));
			getPaging().setList(getEntities());
		}
	}

	public void json() {
		final Object object = onJson();
		if (VulpeValidationUtil.isNotEmpty(object)) {
			vulpe.controller().renderJSON(object);
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
						getServiceMethodName(Operation.READ), new Class[] { vulpe.controller()
								.config().getEntityClass() }, new Object[] { autocompleteEntity });
			}
			values = new ArrayList<VulpeHashMap<String, Object>>();
			if (VulpeValidationUtil.isNotEmpty(autocompleteList)) {
				final List<Field> autocompleteFields = VulpeReflectUtil.getFieldsWithAnnotation(
						vulpe.controller().config().getEntityClass(), Autocomplete.class);
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
				vulpe.controller().renderPlainText("");
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
			vulpe.controller().renderJSON(values);
		} else {
			vulpe.controller().renderPlainText(value);
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
				entity = vulpe.controller().config().getEntityClass().newInstance();
			} else if (entity == null) {
				entity = vulpe.controller().config().getEntityClass().newInstance();
			}
			updateAuditInformation(entity);
			if (Operation.READ.equals(operation) && getEntitySelect() == null) {
				setEntitySelect(vulpe.controller().config().getEntityClass().newInstance());
				entity = getEntitySelect();
			} else if (getEntitySelect() != null
					&& StringUtils.isNotEmpty(getEntitySelect().getAutocomplete())) {
				if (getId() != null && getEntitySelect().getId() == null) {
					getEntitySelect().setId(getId());
				}
			} else if (Operation.UPDATE.equals(operation)
					|| (Operation.DELETE.equals(operation) && (vulpe.controller().type().equals(
							ControllerType.SELECT)
							|| vulpe.controller().type().equals(ControllerType.TABULAR) || vulpe
							.controller().type().equals(ControllerType.TWICE)))) {
				entity.setId(getId());
			}
		} catch (Exception e) {
			throw new VulpeSystemException(e);
		}
		if (StringUtils.isEmpty(entity.getQueryConfigurationName())
				|| "default".equals(entity.getQueryConfigurationName())) {
			entity.setQueryConfigurationName(vulpe.controller().config().getControllerAnnotation()
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
		vulpe.view().targetConfig(vulpe.controller().detailConfig());
		vulpe.view().targetConfigPropertyName(vulpe.controller().detail());
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
		removeDetailPaging(vulpe.controller().detail());
	}

	protected void removeDetailPaging(final String detail) {
		ever.remove(detail + Controller.DETAIL_PAGING_LIST);
	}

	protected <T> T getDetailPaging(final String detail) {
		return ever.<T> getSelf(detail + Controller.DETAIL_PAGING_LIST);
	}

	protected <T> T getDetailPaging() {
		return (T) getDetailPaging(vulpe.controller().detail());
	}

	protected void setDetailPaging(final String detail, final Paging<ENTITY> paging) {
		ever.putWeakRef(detail + Controller.DETAIL_PAGING_LIST, paging);
	}

	public void paging() {
		pagingBefore();
		if (VulpeValidationUtil.isNotEmpty(vulpe.controller().detail())) {
			final Paging<ENTITY> paging = getDetailPaging();
			final List<ENTITY> values = VulpeReflectUtil.getFieldValue(entity, vulpe.controller()
					.detail());
			repairDetailPaging(values, paging);
			if (!vulpe.controller().detail().startsWith("entity")) {
				vulpe.controller().detail("entity." + vulpe.controller().detail());
			}
			configureDetail();
			final VulpeBaseDetailConfig detailConfig = vulpe.controller().detailConfig();
			mountDetailPaging(detailConfig, paging);
			manageButtons(Operation.ADD_DETAIL);
			if (vulpe.controller().ajax()) {
				if (detailConfig == null || detailConfig.getViewPath() == null) {
					controlResultForward();
				} else {
					vulpe.controller().resultForward(detailConfig.getViewPath());
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
		if (VulpeValidationUtil.isNotEmpty(vulpe.controller().config().getDetails())) {
			for (final VulpeBaseDetailConfig detailConfig : vulpe.controller().config()
					.getDetails()) {
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
		if (vulpe.controller().ajax()) {
			if (detailConfig == null || detailConfig.getViewPath() == null) {
				controlResultForward();
			} else {
				vulpe.controller().resultForward(detailConfig.getViewPath());
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
		if (!vulpe.controller().type().equals(ControllerType.MAIN)
				&& !vulpe.controller().type().equals(ControllerType.TABULAR)) {
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
	public void clear() {
		// setCleaned(true);
		if (vulpe.controller().type().equals(ControllerType.MAIN)) {
			setEntitySelect(prepareEntity(Operation.CREATE));
			create();
		} else if (vulpe.controller().type().equals(ControllerType.SELECT)) {
			ever.remove(vulpe.controller().selectFormKey());
			ever.remove(vulpe.controller().selectTableKey());
			ever.remove(vulpe.controller().selectPagingKey());
			setEntitySelect(prepareEntity(Operation.SELECT));
			select();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeController#create()
	 */
	public void create() {
		if (vulpe.controller().type() == null
				|| !vulpe.controller().type().equals(ControllerType.TWICE)) {
			vulpe.controller().type(ControllerType.MAIN);
		}
		vulpe.controller().operation(Operation.CREATE);
		createBefore();
		onCreate();
		vulpe.controller().selectedTab(null);
		manageButtons(Operation.CREATE);
		if (vulpe.controller().type().equals(ControllerType.TWICE)) {
			vulpe.view().bodyTwice(ControllerType.MAIN);
			vulpe.controller().resultForward(Layout.PROTECTED_JSP_COMMONS.concat(Layout.BODY_JSP));
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
		if (vulpe.controller().type().equals(ControllerType.MAIN)
				|| vulpe.controller().type().equals(ControllerType.TWICE)) {
			try {
				setEntity(vulpe.controller().config().getEntityClass().newInstance());
				setEntity(prepareEntity(vulpe.controller().operation()));
				if (VulpeValidationUtil.isNotEmpty(vulpe.controller().config().getDetails())) {
					createDetails(vulpe.controller().config().getDetails(), false);
					vulpe.controller().detail("");
				}
				prepareDetailPaging();
			} catch (Exception e) {
				throw new VulpeSystemException(e);
			}
			vulpe.controller().executed(false);
		}
	}

	/**
	 * Extension point to code before create.
	 * 
	 * @since 1.0
	 */
	protected void createBefore() {
		if (!vulpe.controller().type().equals(ControllerType.MAIN)
				&& !vulpe.controller().type().equals(ControllerType.TWICE)) {
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
	public void cloneIt() {
		if (vulpe.controller().type() == null
				|| !vulpe.controller().type().equals(ControllerType.TWICE)) {
			vulpe.controller().type(ControllerType.MAIN);
		}
		vulpe.controller().operation(Operation.CLONE);
		cloneItBefore();
		if (vulpe.controller().type().equals(ControllerType.TWICE)) {
			vulpe.view().bodyTwice(ControllerType.MAIN);
			vulpe.controller().resultForward(Layout.PROTECTED_JSP_COMMONS.concat(Layout.BODY_JSP));
		} else {
			controlResultForward();
		}
		if (onCloneIt()) {
			manageButtons(Operation.CREATE);
			addActionMessage(getDefaultMessage());
			vulpe.controller().selectedTab(null);
			cloneItAfter();
		}
	}

	/**
	 * Extension point to clone record.
	 * 
	 * @since 1.0
	 */
	protected boolean onCloneIt() {
		if (vulpe.controller().type().equals(ControllerType.MAIN)
				|| vulpe.controller().type().equals(ControllerType.TWICE)) {
			try {
				setEntity((ENTITY) getEntity().clone());
				getEntity().setId(null);
			} catch (Exception e) {
				throw new VulpeSystemException(e);
			}
			vulpe.controller().executed(false);
		}
		return true;
	}

	/**
	 * Extension point to code before clone.
	 * 
	 * @since 1.0
	 */
	protected void cloneItBefore() {
		if (!vulpe.controller().type().equals(ControllerType.MAIN)
				&& !vulpe.controller().type().equals(ControllerType.TWICE)) {
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
	public void createPost() {
		if (vulpe.controller().type() == null
				|| !vulpe.controller().type().equals(ControllerType.TWICE)) {
			vulpe.controller().type(ControllerType.MAIN);
		}
		vulpe.controller().operation(Operation.CREATE_POST);
		createPostBefore();
		controlResultForward();
		if (validateEntity() && onCreatePost()) {
			manageButtons(Operation.UPDATE);
			addActionMessage(getDefaultMessage());
			if (vulpe.controller().config().getEntityClass().isAnnotationPresent(CachedClass.class)) {
				if (validateCacheClass(getEntity())) {
					final String entityName = vulpe.controller().config().getEntityClass()
							.getSimpleName();
					List<ENTITY> list = (List<ENTITY>) vulpe.cache().classes().get(entityName);
					if (VulpeValidationUtil.isEmpty(list)) {
						list = new ArrayList<ENTITY>();
					}
					list.add(getEntity());
					Collections.sort(list);
					vulpe.cache().classes().put(entityName, list);
				}
			}
			createPostAfter();
			if (vulpe.controller().type().equals(ControllerType.TWICE)) {
				onRead();
			}
		} else {
			prepareDetailPaging();
			manageButtons(Operation.CREATE);
		}
		if (vulpe.controller().config().isNewOnPost()) {
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
				new Class[] { vulpe.controller().config().getEntityClass() },
				new Object[] { prepareEntity(Operation.CREATE_POST) }));
		setId(getEntity().getId());
		managePaging(true);
		vulpe.controller().executed(true);
		return true;
	}

	/**
	 * Extension point to code before confirm create.
	 * 
	 * @since 1.0
	 */
	protected void createPostBefore() {
		if (!vulpe.controller().type().equals(ControllerType.MAIN)
				&& !vulpe.controller().type().equals(ControllerType.TWICE)) {
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
	public void update() {
		if (vulpe.controller().type() == null
				|| !vulpe.controller().type().equals(ControllerType.TWICE)) {
			vulpe.controller().type(ControllerType.MAIN);
		}
		vulpe.controller().operation(Operation.UPDATE);
		updateBefore();
		if (getEntity() == null && getId() == null) {
			create();
		}
		onUpdate();
		vulpe.controller().selectedTab(null);
		manageButtons();
		if (vulpe.controller().type().equals(ControllerType.TWICE)) {
			vulpe.view().bodyTwice(ControllerType.MAIN);
			vulpe.controller().resultForward(Layout.PROTECTED_JSP_COMMONS.concat(Layout.BODY_JSP));
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
	public void view() {
		vulpe.controller().onlyToSee(true);
		update();
		manageButtons(Operation.VIEW);
	}

	/**
	 * Extension point to prepare update.
	 * 
	 * @since 1.0
	 */
	protected void onUpdate() {
		if (vulpe.controller().type().equals(ControllerType.MAIN)
				|| vulpe.controller().type().equals(ControllerType.TWICE)) {
			setEntity((ENTITY) invokeServices(getServiceMethodName(Operation.FIND),
					new Class[] { vulpe.controller().config().getEntityClass() },
					new Object[] { prepareEntity(vulpe.controller().operation()) }));
			prepareDetailPaging();
			vulpe.controller().executed(false);
		}
	}

	/**
	 * Extension point to code before update.
	 * 
	 * @since 1.0
	 */
	protected void updateBefore() {
		if (!vulpe.controller().type().equals(ControllerType.MAIN)
				&& !vulpe.controller().type().equals(ControllerType.TWICE)) {
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
	public void updatePost() {
		if (vulpe.controller().type() == null
				|| !vulpe.controller().type().equals(ControllerType.TWICE)) {
			vulpe.controller().type(ControllerType.MAIN);
		}
		vulpe.controller().operation(Operation.UPDATE_POST);
		updatePostBefore();
		controlResultForward();
		manageButtons(Operation.UPDATE);
		if (validateEntity() && onUpdatePost()) {
			addActionMessage(getDefaultMessage());
			if (vulpe.controller().config().getEntityClass().isAnnotationPresent(CachedClass.class)) {
				boolean valid = validateCacheClass(getEntity());
				final String entityName = vulpe.controller().config().getEntityClass()
						.getSimpleName();
				List<ENTITY> list = (List<ENTITY>) vulpe.cache().classes().get(entityName);
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
				vulpe.cache().classes().put(entityName, list);
			}
			if (!vulpe.controller().config().isOnlyUpdateDetails()) {
				final List<ENTITY> entities = ever.getSelf(vulpe.controller().selectTableKey());
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
					ever.put(vulpe.controller().selectTableKey(), entities);
				}
			}
			updatePostAfter();
			if (vulpe.controller().type().equals(ControllerType.TWICE)) {
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
		if (vulpe.controller().config().isOnlyUpdateDetails()) {
			final List<String> details = new ArrayList<String>();
			for (final VulpeBaseDetailConfig detailConfig : vulpe.controller().config()
					.getDetails()) {
				details.add(detailConfig.getName());
			}
			entity.getMap().put(Entity.ONLY_UPDATE_DETAILS, details);
		}
		if (VulpeValidationUtil.isNotEmpty(vulpe.controller().config().getDetails())) {
			for (final VulpeBaseDetailConfig detailConfig : vulpe.controller().config()
					.getDetails()) {
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
				new Class[] { vulpe.controller().config().getEntityClass() },
				new Object[] { entity }));
		managePaging(true);
		vulpe.controller().executed(true);
		return true;
	}

	/**
	 * Extension point to code before confirm update.
	 * 
	 * @since 1.0
	 */
	protected void updatePostBefore() {
		if (!vulpe.controller().type().equals(ControllerType.MAIN)
				&& !vulpe.controller().type().equals(ControllerType.TWICE)) {
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
		vulpe.controller().operation(Operation.DELETE);
		deleteBefore();
		manageButtons();
		if (onDelete()) {
			addActionMessage(getDefaultMessage());
			vulpe.controller().selectedTab(null);
			if (vulpe.controller().config().getEntityClass().isAnnotationPresent(CachedClass.class)) {
				final String entityName = vulpe.controller().config().getEntityClass()
						.getSimpleName();
				final List<ENTITY> list = (List<ENTITY>) vulpe.cache().classes().get(entityName);
				if (VulpeValidationUtil.isNotEmpty(list)) {
					for (final Iterator<ENTITY> iterator = list.iterator(); iterator.hasNext();) {
						final ENTITY entity = iterator.next();
						if (vulpe.controller().type().equals(ControllerType.SELECT)) {
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
				vulpe.cache().classes().put(entityName, list);
			}
			try {
				setEntity(vulpe.controller().config().getEntityClass().newInstance());
			} catch (Exception e) {
				throw new VulpeSystemException(e);
			}
			deleteAfter();
			vulpe.controller().deleted(true);
			if (vulpe.controller().type().equals(ControllerType.MAIN)) {
				managePaging(false);
				controlResultForward();
			} else if (vulpe.controller().type().equals(ControllerType.TWICE)
					&& getEntity().getId() != null) {
				onRead();
				controlResultForward();
			} else {
				read();
			}
		} else {
			if (vulpe.controller().type().equals(ControllerType.MAIN)) {
				controlResultForward();
			} else {
				vulpe.controller().resultForward(vulpe.controller().config().getViewItemsPath());
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
		} else if (vulpe.controller().type().equals(ControllerType.TABULAR)) {
			vulpe.controller().tabularSize(vulpe.controller().tabularSize() - 1);
		} else {
			if (!onDeleteOne()) {
				valid = false;
			}
		}
		final NotDeleteIf notDeleteIf = entity.getClass().getAnnotation(NotDeleteIf.class);
		if (VulpeValidationUtil.isNotEmpty(entities)) {
			invokeServices(getServiceMethodName(Operation.DELETE), new Class[] { List.class },
					new Object[] { entities });
			if (notDeleteIf != null) {
				final List<Integer> rows = new ArrayList<Integer>();
				boolean used = false;
				for (final ENTITY entity2 : entities) {
					if (entity2.isUsed()) {
						for (final ENTITY entity3 : getEntities()) {
							if (entity2.getId().equals(entity3.getId())) {
								rows.add(entity3.getRowNumber());
								used = true;
							}
						}
					}
				}
				if (rows.isEmpty()) {
					for (final ENTITY entity2 : entities) {
						if (entity2.isConditional()) {
							for (final ENTITY entity3 : getEntities()) {
								if (entity2.getId().equals(entity3.getId())) {
									rows.add(entity3.getRowNumber());
								}
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
						addActionError(used ? notDeleteIf.usedBy().messageToOneRecordOnSelect()
								: notDeleteIf.conditions().messageToOneRecordOnSelect(),
								affectedRows.toString());
					} else {
						addActionError(used ? notDeleteIf.usedBy().messageToManyRecordsOnSelect()
								: notDeleteIf.conditions().messageToManyRecordsOnSelect(),
								affectedRows.toString());
					}
					valid = false;
				}
			}
		} else {
			invokeServices(getServiceMethodName(Operation.DELETE), new Class[] { vulpe.controller()
					.config().getEntityClass() }, new Object[] { entity });
			if (notDeleteIf != null && (entity.isUsed() || entity.isConditional())) {
				addActionError(entity.isUsed() ? notDeleteIf.usedBy().messageToRecordOnMain()
						: notDeleteIf.conditions().messageToRecordOnMain());
				valid = false;
			}
		}
		vulpe.controller().executed(true);
		return valid;
	}

	protected boolean onDeleteOne() {
		return true;
	}

	protected boolean onDeleteMany(final List<ENTITY> entities) {
		for (final ID id : getSelected()) {
			try {
				final ENTITY newEntity = vulpe.controller().config().getEntityClass().newInstance();
				newEntity.setId(id);
				entities.add(newEntity);
			} catch (Exception e) {
				throw new VulpeSystemException(e);
			}
		}
		if (vulpe.controller().config().getTabularPageSize() > 0) {
			vulpe.controller().tabularSize(
					vulpe.controller().tabularSize()
							- (getEntities().size() - getSelected().size()));
		}
		return true;
	}

	/**
	 * Extension point to code before delete.
	 * 
	 * @since
	 */
	protected void deleteBefore() {
		if (!vulpe.controller().type().equals(ControllerType.SELECT)
				&& !vulpe.controller().type().equals(ControllerType.MAIN)
				&& !vulpe.controller().type().equals(ControllerType.TWICE)
				&& !vulpe.controller().type().equals(ControllerType.TABULAR)) {
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
		if (vulpe.controller().type() == null
				|| !vulpe.controller().type().equals(ControllerType.TWICE)) {
			vulpe.controller().type(ControllerType.MAIN);
		}
		vulpe.controller().operation(Operation.DELETE_FILE);
		deleteFileBefore();
		controlResultForward();
		manageButtons(Operation.UPDATE);
		if (validateEntity() && onDeleteFile()) {
			addActionMessage(getDefaultMessage());
			deleteFileAfter();
			if (vulpe.controller().type().equals(ControllerType.TWICE)) {
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
		if (StringUtils.isNotBlank(vulpe.controller().propertyName())) {
			if (VulpeReflectUtil.fieldExists(getEntity().getClass(), vulpe.controller()
					.propertyName())) {
				VulpeReflectUtil
						.setFieldValue(getEntity(), vulpe.controller().propertyName(), null);
				final String contentType = vulpe.controller().propertyName().concat(
						File.SUFFIX_CONTENT_TYPE);
				if (VulpeReflectUtil.fieldExists(getEntity().getClass(), contentType)) {
					VulpeReflectUtil.setFieldValue(getEntity(), contentType, null);
				}
				final String fileName = vulpe.controller().propertyName().concat(
						File.SUFFIX_FILE_NAME);
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
		vulpe.controller().operation(Operation.UPDATE_POST);
		deleteDetailBefore();
		manageButtons(Operation.UPDATE);
		final int size = onDeleteDetail();
		if (size > 0) {
			// final String defaultMessage =
			// getDefaultMessage(Operation.DELETE_DETAIL);
			addActionMessage(size > 1 ? "{vulpe.message.delete.details}"
					: "{vulpe.message.delete.detail}");
			if (vulpe.controller().type().equals(ControllerType.TABULAR)) {
				if (!getEntities().isEmpty()) {
					final ENTITY entityTabular = getEntities().get(0);
					if (entityTabular.getClass().isAnnotationPresent(CachedClass.class)) {
						final String entityName = entityTabular.getClass().getSimpleName();
						vulpe.cache().classes().put(entityName, getEntities());
					}
				}
			}
		}
		if (vulpe.controller().ajax()) {
			final VulpeBaseDetailConfig detailConfig = vulpe.controller().config().getDetailConfig(
					vulpe.controller().detail());
			if (detailConfig == null || StringUtils.isBlank(detailConfig.getViewPath())) {
				controlResultForward();
			} else {
				vulpe.controller().resultForward(detailConfig.getViewPath());
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
		if (!vulpe.controller().type().equals(ControllerType.SELECT)
				&& !vulpe.controller().type().equals(ControllerType.MAIN)
				&& !vulpe.controller().type().equals(ControllerType.TABULAR)) {
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
	public void read() {
		if (vulpe.controller().type() == null
				|| !vulpe.controller().type().equals(ControllerType.TWICE)) {
			vulpe.controller().operation(Operation.READ);
		}
		readBefore();
		onRead();
		manageButtons();
		if (vulpe.controller().type().equals(ControllerType.SELECT)) {
			vulpe.view().targetName("entitySelect");
			if (vulpe.controller().back()) {
				controlResultForward();
				vulpe.controller().back(false);
			} else if (vulpe.controller().ajax() || vulpe.controller().exported()) {
				vulpe.controller().resultForward(vulpe.controller().config().getViewItemsPath());
			} else {
				controlResultForward();
			}
		} else if (vulpe.controller().type().equals(ControllerType.REPORT)) {
			vulpe.view().targetName("entitySelect");
			vulpe.controller().resultName(Result.REPORT);
			if (vulpe.controller().ajax()) {
				vulpe.controller().resultForward(vulpe.controller().config().getViewItemsPath());
			} else {
				controlResultForward();
			}
		} else if (vulpe.controller().type().equals(ControllerType.TWICE)) {
			vulpe.view().bodyTwice(ControllerType.SELECT);
			if (vulpe.controller().ajax()) {
				vulpe.controller().resultForward(
						vulpe.controller().config().getViewSelectItemsPath());
			} else {
				vulpe.controller().resultForward(vulpe.controller().config().getViewSelectPath());
			}
		} else {
			if (vulpe.controller().type().equals(ControllerType.TABULAR)) {
				if (VulpeValidationUtil.isNotEmpty(vulpe.controller().config().getDetails())
						&& VulpeValidationUtil.isEmpty(getEntities())
						&& !vulpe.controller().tabularFilter()) {
					createDetails(vulpe.controller().config().getDetails(), false);
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
		// if (vulpe.controller().back() && !isExecuted()) {
		// return;
		// }
		if (vulpe.controller().type().equals(ControllerType.TWICE)) {
			if (ever.containsKey(vulpe.controller().selectFormKey()) && getEntitySelect() == null) {
				setEntitySelect(ever.<ENTITY> getSelf(vulpe.controller().selectFormKey()));
			}
			if (getEntitySelect() == null) {
				setEntitySelect(getEntity());
			}
		}
		if (vulpe.controller().config().requireOneOfFilters().length > 0
				&& isFiltersEmpty(getEntitySelect())) {
			final StringBuilder filters = new StringBuilder();
			final String orLabel = getText("label.vulpe.or");
			int filterCount = 0;
			for (final String attribute : vulpe.controller().config().requireOneOfFilters()) {
				if (filterCount > 0) {
					filters.append(" ").append(orLabel).append(" ");
				}
				final String text = vulpe.controller().config().getTitleKey() + "." + attribute;
				filters.append("\"").append(getText(text)).append("\"");
				++filterCount;
			}
			addActionError("{vulpe.error.validate.require.one.of.filters}", filters.toString());
			return;
		}
		final ENTITY entity = prepareEntity(Operation.READ);
		if (!vulpe.controller().exported()
				&& ((vulpe.controller().type().equals(ControllerType.SELECT) || vulpe.controller()
						.type().equals(ControllerType.TWICE)) && vulpe.controller().config()
						.getPageSize() > 0)
				|| (vulpe.controller().type().equals(ControllerType.TABULAR) && vulpe.controller()
						.config().getTabularPageSize() > 0)) {
			final Integer page = getPaging() == null || getPaging().getPage() == null ? 1
					: getPaging().getPage();
			vulpe.controller().currentPage(page);
			final Integer pageSize = vulpe.controller().type().equals(ControllerType.TABULAR) ? vulpe
					.controller().config().getTabularPageSize()
					: vulpe.controller().config().getPageSize();
			final Paging<ENTITY> paging = (Paging<ENTITY>) invokeServices(
					getServiceMethodName(Operation.PAGING), new Class[] {
							vulpe.controller().config().getEntityClass(), Integer.class,
							Integer.class }, new Object[] { entity.clone(), pageSize, page });
			setPaging(paging);
			setEntities(paging.getList());
			ever.put(vulpe.controller().selectPagingKey(), paging);
			if (vulpe.controller().type().equals(ControllerType.TABULAR)) {
				vulpe.controller().tabularSize(paging.getSize());
				if (paging.getList() == null || paging.getList().isEmpty()) {
					vulpe.controller().detail(Controller.ENTITIES);
					if (!vulpe.controller().tabularFilter()) {
						onAddDetail(true);
					}
				}
			}
			if (VulpeValidationUtil.isEmpty(getEntities())) {
				addActionInfoMessage(getDefaultMessage(vulpe.controller().deleted() ? Operation.READ_DELETED
						: Operation.READ));
			}
		} else {
			final List<ENTITY> list = (List<ENTITY>) invokeServices(
					getServiceMethodName(Operation.READ), new Class[] { vulpe.controller().config()
							.getEntityClass() }, new Object[] { entity.clone() });
			setEntities(list);
			if (vulpe.controller().exported()) {
				final int size = VulpeValidationUtil.isNotEmpty(list) ? list.size() : 0;
				final Paging<ENTITY> paging = new Paging<ENTITY>(size, vulpe.controller().config()
						.getPageSize(), 1);
				paging.setList(list);
				setPaging(paging);
			}
		}
		if (vulpe.controller().type().equals(ControllerType.REPORT)) {
			setDownloadInfo(doReportLoad());
			if (VulpeValidationUtil.isEmpty(getEntities())) {
				addActionInfoMessage(getDefaultMessage(Operation.REPORT_EMPTY));
			} else {
				addActionMessage(getDefaultMessage(Operation.REPORT_SUCCESS));
				final int size = getEntities().size();
				final Paging<ENTITY> paging = new Paging<ENTITY>(size, vulpe.controller().config()
						.getPageSize(), 1);
				paging.setList(getEntities());
				setPaging(paging);
			}
		} else {
			ever.put(vulpe.controller().selectFormKey(), entity.clone());
			if (getEntities() != null && !getEntities().isEmpty()) {
				ever.put(vulpe.controller().selectTableKey(), getEntities());
			}
		}
		vulpe.controller().executed(true);
	}

	/**
	 * Extension point to code before read.
	 * 
	 * @since 1.0
	 */
	protected void readBefore() {
		if (!vulpe.controller().type().equals(ControllerType.SELECT)
				&& !vulpe.controller().type().equals(ControllerType.TWICE)
				&& !vulpe.controller().type().equals(ControllerType.TABULAR)
				&& !vulpe.controller().type().equals(ControllerType.REPORT)) {
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
	public void tabularFilter() {
		vulpe.controller().tabularFilter(true);
		read();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeController#tabularPost()
	 */
	public void tabularPost() {
		if (getEntities() != null) {
			vulpe.controller().operation(Operation.TABULAR_POST);
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
						vulpe.cache().classes().put(entityName, list);
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
		if (vulpe.controller().config().getTabularPageSize() > 0) {
			vulpe.controller().tabularSize(vulpe.controller().tabularSize() - (size - sizeDespise));
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
		vulpe.controller().executed(true);
		return true;
	}

	/**
	 * Extension point to code before logic tabulate.
	 * 
	 * @since 1.0
	 */
	protected void tabularPostBefore() {
		if (!vulpe.controller().type().equals(ControllerType.TABULAR)) {
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
	public void prepare() {
		prepareBefore();
		onPrepare();
		manageButtons(Operation.PREPARE);
		if (vulpe.controller().type().equals(ControllerType.SELECT)
				|| vulpe.controller().type().equals(ControllerType.REPORT)) {
			if (vulpe.controller().back()) {
				setEntitySelect(ever.<ENTITY> getSelf(vulpe.controller().selectFormKey()));
				setEntities(ever.<List<ENTITY>> getSelf(vulpe.controller().selectTableKey()));
				read();
			} else {
				ever.remove(vulpe.controller().selectFormKey());
				ever.remove(vulpe.controller().selectTableKey());
			}
			controlResultForward();
		} else if (vulpe.controller().type().equals(ControllerType.TABULAR)) {
			read();
		} else if (vulpe.controller().type().equals(ControllerType.TWICE)) {
			vulpe.view().bodyTwice(ControllerType.SELECT);
			vulpe.controller().resultForward(Layout.PROTECTED_JSP_COMMONS.concat(Layout.BODY_JSP));
		} else {
			controlResultForward();
		}
		prepareAfter();
	}

	public void twice() {
		vulpe.controller().type(ControllerType.TWICE);
		prepareBefore();
		onPrepare();
		manageButtons(Operation.TWICE);
		controlResultForward();
		prepareAfter();
	}

	public void export() {
		vulpe.controller().type(ControllerType.SELECT);
		exportBefore();
		vulpe.controller().onlyToSee(true);
		vulpe.controller().exported(true);
		onExport();
		// vulpe.controller().resultForward(vulpe.controller().config().getViewItemsPath());
		// vulpe.controller().resultName(Result.EXPORT);
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
	@Override
	public void select() {
		vulpe.controller().type(ControllerType.SELECT);
		vulpe.view().targetName("entitySelect");
		selectBefore();
		onPrepare();
		manageButtons(Operation.PREPARE);
		if (vulpe.controller().back()) {
			setEntitySelect(ever.<ENTITY> getSelf(vulpe.controller().selectFormKey()));
			setEntities(ever.<List<ENTITY>> getSelf(vulpe.controller().selectTableKey()));
			setPaging(ever.<Paging<ENTITY>> getSelf(vulpe.controller().selectPagingKey()));
			if (getPaging() != null) {
				getPaging().setList(getEntities());
			}
		}
		controlResultForward();
		if (vulpe.controller().back()) {
			selectAfter();
			read();
			return;
		} else {
			ever.remove(vulpe.controller().selectFormKey());
			ever.remove(vulpe.controller().selectTableKey());
			ever.remove(vulpe.controller().selectPagingKey());
		}
		selectAfter();
		if (vulpe.controller().config().getControllerAnnotation().select().readOnShow()
				&& !vulpe.controller().cleaned()) {
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

	public void report() {
		vulpe.controller().type(ControllerType.REPORT);
		reportBefore();
		manageButtons(Operation.PREPARE);
		read();
		reportAfter();
		vulpe.controller().type(ControllerType.SELECT);
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
	public void tabular() {
		vulpe.controller().type(ControllerType.TABULAR);
		if (vulpe.controller().config().isTabularShowFilter()) {
			try {
				setEntitySelect(vulpe.controller().config().getEntityClass().newInstance());
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
			if (vulpe.controller().type().equals(ControllerType.TWICE)) {
				if (getEntity() == null) {
					setEntity(vulpe.controller().config().getEntityClass().newInstance());
				}
			}
			if (getEntitySelect() == null) {
				setEntitySelect(vulpe.controller().config().getEntityClass().newInstance());
			}
		} catch (Exception e) {
			if (vulpe.controller().type().equals(ControllerType.TWICE)) {
				setEntity(null);
			}
			setEntitySelect(null);
		}
		setPaging(null);
		vulpe.controller().executed(false);
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
		vulpe.controller().renderBoolean(vulpe.controller().uploaded());
	}

	/**
	 * Extension point to upload.
	 * 
	 * @since 1.0
	 */
	protected void onUpload() {
		vulpe.controller().uploaded(true);
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
		vulpe.controller().resultName(Result.DOWNLOAD);
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
		for (String attribute : vulpe.controller().config().requireOneOfFilters()) {
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
						final List<ENTITY> cachedList = vulpe.cache().classes().getSelf(
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
		final CachedClass cachedClass = vulpe.controller().config().getEntityClass().getAnnotation(
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
		vulpe.controller().config().setControllerType(ControllerType.BACKEND);
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
		vulpe.controller().executed(false);
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
		vulpe.controller().config().setControllerType(ControllerType.FRONTEND);
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
		vulpe.controller().executed(false);
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
		return operation.getValue().concat(
				vulpe.controller().config().getEntityClass().getSimpleName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeSimpleController#getService()
	 */
	public VulpeService getService() {
		return getService(vulpe.controller().config().getServiceClass());
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeSimpleController#controlResultForward()
	 */
	public void controlResultForward() {
		vulpe
				.controller()
				.resultForward(
						vulpe.controller().type().equals(ControllerType.TWICE) ? Layout.PROTECTED_JSP_COMMONS
								.concat(Layout.BODY_TWICE_JSP)
								: Layout.PROTECTED_JSP_COMMONS.concat(Layout.BODY_JSP));
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
			final String directory = vulpe.controller().config().getModuleName() + "/"
					+ vulpe.controller().config().getSimpleControllerName() + "/";
			path.append(directory);
			path.append(page).append(Layout.SUFFIX_JSP);
		} else {
			path.append(page);
		}
		vulpe.controller().resultForward(path.toString());
	}

	public void setActionInfoMessages(Collection<String> actionInfoMessages) {
		this.actionInfoMessages = actionInfoMessages;
	}

	public Collection<String> getActionInfoMessages() {
		return actionInfoMessages;
	}

}