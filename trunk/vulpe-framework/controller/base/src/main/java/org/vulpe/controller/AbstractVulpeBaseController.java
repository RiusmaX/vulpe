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
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.vulpe.commons.VulpeConstants.Controller;
import org.vulpe.commons.VulpeConstants.Error;
import org.vulpe.commons.VulpeConstants.Configuration.Ever;
import org.vulpe.commons.VulpeConstants.Controller.Button;
import org.vulpe.commons.VulpeConstants.Controller.Forward;
import org.vulpe.commons.VulpeConstants.Model.Entity;
import org.vulpe.commons.VulpeConstants.View.Layout;
import org.vulpe.commons.annotations.Quantity.QuantityType;
import org.vulpe.commons.beans.DownloadInfo;
import org.vulpe.commons.beans.Paging;
import org.vulpe.commons.beans.Tab;
import org.vulpe.commons.helper.VulpeConfigHelper;
import org.vulpe.commons.util.VulpeHashMap;
import org.vulpe.commons.util.VulpeReflectUtil;
import org.vulpe.commons.util.VulpeValidationUtil;
import org.vulpe.controller.annotations.ResetSession;
import org.vulpe.controller.commons.DuplicatedBean;
import org.vulpe.controller.commons.VulpeBaseControllerConfig;
import org.vulpe.controller.commons.VulpeBaseDetailConfig;
import org.vulpe.controller.commons.VulpeControllerConfig.ControllerType;
import org.vulpe.controller.validator.EntityValidator;
import org.vulpe.exception.VulpeSystemException;
import org.vulpe.model.annotations.Autocomplete;
import org.vulpe.model.annotations.CachedClass;
import org.vulpe.model.annotations.NotExistEquals;
import org.vulpe.model.annotations.QueryParameter;
import org.vulpe.model.entity.VulpeEntity;
import org.vulpe.model.entity.impl.AbstractVulpeBaseAuditEntity;
import org.vulpe.model.services.GenericService;

/**
 * Base Controller
 *
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings( { "unchecked", "serial" })
public abstract class AbstractVulpeBaseController<ENTITY extends VulpeEntity<ID>, ID extends Serializable & Comparable>
		extends AbstractVulpeBaseSimpleController implements VulpeController {

	protected static final Logger LOG = Logger.getLogger(AbstractVulpeBaseController.class);
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
	 * List of selected identifiers
	 */
	private List<ID> selected;
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
	private boolean uploaded;

	private boolean tabularFilter;

	public VulpeHashMap<Operation, String> defaultMessage = new VulpeHashMap<Operation, String>();

	{
		defaultMessage.put(Operation.CREATE_POST, "{vulpe.message.create.post}");
		defaultMessage.put(Operation.CLONE, "{vulpe.message.clone}");
		defaultMessage.put(Operation.UPDATE_POST, "{vulpe.message.update.post}");
		defaultMessage.put(Operation.TABULAR_POST, "{vulpe.message.tabular.post}");
		defaultMessage.put(Operation.DELETE, "{vulpe.message.delete}");
	}

	public String getDefaultMessage(final Operation operation) {
		return defaultMessage.getSelf(operation);
	}

	public String getDefaultMessage() {
		return getDefaultMessage(getOperation());
	}

	public ID getId() {
		return id;
	}

	public void setId(final ID id) {
		this.id = id;
	}

	public List<ID> getSelected() {
		return selected;
	}

	public void setSelected(final List<ID> selected) {
		this.selected = selected;
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

	public void addActionError(final String key, final Object... args) {
		addActionError(getText(key, args));
	}

	/**
	 * Method to validate quantity of details.
	 *
	 * @param beans
	 * @param detailConfig
	 * @return
	 */
	protected boolean validateQuantity(final Collection<VulpeEntity<?>> beans, final VulpeBaseDetailConfig detailConfig) {
		if (detailConfig.getQuantity() != null) {
			final String tabName = getTabs().containsKey(detailConfig.getTitleKey()) ? ((Tab) getTabs().get(
					detailConfig.getTitleKey())).getTitle() : getText(detailConfig.getTitleKey());
			if (detailConfig.getQuantity().minimum() > 1 && detailConfig.getQuantity().maximum() > 1
					&& detailConfig.getQuantity().minimum() < detailConfig.getQuantity().maximum()) {
				if (beans == null || beans.size() < detailConfig.getQuantity().minimum()
						|| beans.size() > detailConfig.getQuantity().maximum()) {
					if (detailConfig.getQuantity().minimum() == detailConfig.getQuantity().maximum()) {
						addActionError("vulpe.error.details.cardinality.custom.equal", getText(detailConfig
								.getTitleKey()), detailConfig.getQuantity().minimum());
					} else {
						addActionError("vulpe.error.details.cardinality.custom", tabName, detailConfig.getQuantity()
								.minimum(), detailConfig.getQuantity().maximum());
					}
					return false;
				}
			} else if (detailConfig.getQuantity().minimum() > 1 && detailConfig.getQuantity().maximum() == 0
					&& (beans == null || beans.size() < detailConfig.getQuantity().minimum())) {
				addActionError("vulpe.error.details.cardinality.custom.minimum", tabName, detailConfig.getQuantity()
						.minimum());
				return false;
			} else if (detailConfig.getQuantity().minimum() == 0 && detailConfig.getQuantity().maximum() > 1
					&& (beans == null || beans.size() > detailConfig.getQuantity().maximum())) {
				addActionError("vulpe.error.details.cardinality.custom.maximum", tabName, detailConfig.getQuantity()
						.maximum());
				return false;
			} else {
				if (QuantityType.ONE.equals(detailConfig.getQuantity().type())
						|| (detailConfig.getQuantity().minimum() == 1 && detailConfig.getQuantity().maximum() == 1)) {
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
	 * Method to validate duplicated details.
	 *
	 * @param beans
	 * @param detailConfig
	 * @return
	 */
	protected boolean validateDuplicatedDetailItens(final Collection<VulpeEntity<?>> beans,
			final VulpeBaseDetailConfig detailConfig) {
		final String[] despiseFields = detailConfig.getDespiseFields();
		final Collection<DuplicatedBean> duplicatedBeans = controllerUtil.duplicatedItens(beans, despiseFields);
		if (duplicatedBeans != null && !duplicatedBeans.isEmpty()) {
			if (getControllerType().equals(ControllerType.TABULAR) && duplicatedBeans.size() == 1) {
				return true;
			}
			final StringBuilder lines = new StringBuilder();
			int count = 1;
			for (DuplicatedBean duplicatedBean : duplicatedBeans) {
				if (duplicatedBeans.size() > 1 && duplicatedBeans.size() == count) {
					lines.append(" " + getText("label.vulpe.and") + " " + duplicatedBean.getLine());
				} else {
					lines.append(StringUtils.isBlank(lines.toString()) ? String.valueOf(duplicatedBean.getLine())
							: ", " + duplicatedBean.getLine());
				}
				++count;
			}
			if (getControllerType().equals(ControllerType.TABULAR)) {
				addActionError("vulpe.error.tabular.duplicated", lines.toString());
			} else {
				final String tabName = getTabs().containsKey(detailConfig.getTitleKey()) ? ((Tab) getTabs().get(
						detailConfig.getTitleKey())).getTitle() : getText(detailConfig.getTitleKey());
				addActionError("vulpe.error.details.duplicated", tabName, lines.toString());
			}
			return false;
		}
		return true;
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
		return controllerUtil.despiseItens(beans, detailConfig.getDespiseFields(), getControllerType().equals(
				ControllerType.TABULAR));
	}

	protected abstract void despiseDetail(final Object parent, final ENTITY baseEntity,
			final VulpeBaseDetailConfig detailConfig);

	/**
	 * Method to remove detail despised.
	 *
	 * @since 1.0
	 */
	protected void despiseDetails() {
		for (VulpeBaseDetailConfig detail : getControllerConfig().getDetails()) {
			if (detail.getParentDetailConfig() == null) {
				despiseDetail(this, getEntity(), detail);
			}
		}
	}

	/**
	 * Validate if entity already exists
	 *
	 * @return
	 */
	protected boolean validateExists() {
		return getService(GenericService.class).exists(getEntity());
	}

	public Map<String, Object> getButtons() {
		if (now.containsKey("buttons")) {
			return now.getSelf("buttons");
		}
		final Map<String, Object> buttons = new HashMap<String, Object>();
		now.put("buttons", buttons);
		return buttons;
	}

	public boolean isAddDetailShow() {
		return (Boolean) getButtons().get(
				Button.ADD_DETAIL.concat(getControllerConfig().getTabularConfig().getBaseName()));
	}

	public boolean isAddDetailShow(final String detail) {
		return (Boolean) getButtons().get(Button.ADD_DETAIL.concat(detail));
	}

	public void addDetailShow(final String detail) {
		getButtons().put(Button.ADD_DETAIL.concat(detail), Boolean.TRUE);
	}

	public void addDetailHide(final String detail) {
		getButtons().put(Button.ADD_DETAIL.concat(detail), Boolean.FALSE);
	}

	public boolean isDeleteDetailShow(final String detail) {
		return (Boolean) getButtons().get(Button.DELETE.concat(detail));
	}

	public void deleteDetailShow(final String detail) {
		getButtons().put(Button.DELETE.concat(detail), Boolean.TRUE);
	}

	public void deleteDetailHide(final String detail) {
		getButtons().put(Button.DELETE.concat(detail), Boolean.FALSE);
	}

	/**
	 * Method to manage button.
	 *
	 * @param button
	 *            Button
	 * @param show
	 *            Show (true|false)
	 * @since 1.0
	 */
	private void manageButton(final String button, final boolean show) {
		if (getControllerType().equals(ControllerType.TABULAR)) {
			getButtons().put(Button.DELETE.concat(getControllerConfig().getTabularConfig().getBaseName()),
					(Boolean) show);
		}
		if (Button.ADD_DETAIL.equals(button)) {
			getButtons().put(Button.ADD_DETAIL.concat(getControllerConfig().getTabularConfig().getBaseName()),
					(Boolean) show);
		} else {
			getButtons().put(button, show);
		}
	}

	/**
	 * Method to show button.
	 *
	 * @param button
	 *            Button.
	 * @since 1.0
	 */
	public void showButton(final String button) {
		manageButton(button, true);
	}

	/**
	 * Method to show buttons.
	 *
	 * @param buttons
	 *            Buttons.
	 * @since 1.0
	 */
	public void showButtons(final String... buttons) {
		for (final String button : buttons) {
			showButton(button);
		}
	}

	public void showButtons(final ControllerType controllerType, final String... buttons) {
		for (final String button : buttons) {
			showButton(controllerType + "_" + button);
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
						addDetailHide(detail.getBaseName());
						deleteDetailHide(detail.getBaseName());
					} else {
						addDetailShow(detail.getBaseName());
						deleteDetailShow(detail.getBaseName());
					}
				}
			}
			if ((Operation.CREATE.equals(operation) || Operation.DELETE.equals(operation) || Operation.PREPARE
					.equals(operation))
					|| ((Operation.CREATE.equals(getOperation()) || Operation.CREATE_POST.equals(getOperation())) && Operation.ADD_DETAIL
							.equals(operation))) {
				showButtons(Button.BACK, Button.CREATE_POST, Button.CLEAR);
			} else if (Operation.UPDATE.equals(operation)
					|| ((Operation.UPDATE.equals(getOperation()) || Operation.UPDATE_POST.equals(getOperation())) && Operation.ADD_DETAIL
							.equals(operation))) {
				showButtons(Button.BACK, Button.CREATE, Button.UPDATE_POST, Button.DELETE);
				if (VulpeConfigHelper.getProjectConfiguration().view().showButtonClone()) {
					showButton(Button.CLONE);
				}
			} else if (Operation.VIEW.equals(operation)) {
				showButtons();
			}
		} else if (getControllerType().equals(ControllerType.SELECT)) {
			showButtons(Button.READ, Button.CLEAR, Button.CREATE, Button.UPDATE, Button.DELETE);
			if (getControllerConfig().getController().select().showReport()) {
				showButton(Button.REPORT);
			}
			if (isPopup()) {
				hideButtons(Button.CREATE, Button.UPDATE, Button.DELETE);
			}
		} else if (getControllerType().equals(ControllerType.REPORT)) {
			showButtons(Button.READ, Button.CLEAR);
		} else if (getControllerType().equals(ControllerType.TABULAR)) {
			showButtons(Button.TABULAR_RELOAD, Button.DELETE, Button.TABULAR_POST, Button.ADD_DETAIL);
			if (getControllerConfig().isTabularShowFilter()) {
				showButton(Button.TABULAR_FILTER);
			}
		} else if (getControllerType().equals(ControllerType.TWICE)) {
			if (Operation.DELETE.equals(operation) || Operation.CREATE.equals(operation)
					|| Operation.TWICE.equals(operation)) {
				showButtons(ControllerType.MAIN, Button.CREATE_POST, Button.CLEAR);
			} else if (Operation.UPDATE.equals(operation)) {
				showButtons(ControllerType.MAIN, Button.CREATE, Button.UPDATE_POST, Button.DELETE);
				if (VulpeConfigHelper.getProjectConfiguration().view().showButtonClone()) {
					showButton(Button.CLONE);
				}
			} else if (Operation.VIEW.equals(operation)) {
				showButtons();
			}
			showButtons(ControllerType.SELECT, Button.READ, Button.BACK, Button.UPDATE, Button.DELETE);
		}
	}

	/**
	 * Method to hide button.
	 *
	 * @param button
	 *            Button.
	 * @since 1.0
	 */
	public void hideButton(final String button) {
		manageButton(button, false);
	}

	/**
	 * Method to hide buttons.
	 *
	 * @param buttons
	 *            Buttons.
	 * @since 1.0
	 */
	public void hideButtons(final String... buttons) {
		for (String button : buttons) {
			hideButton(button);
		}
	}

	/**
	 * Method to show error
	 *
	 * @param message
	 * @return
	 */
	protected String showError(final String message) {
		manageButtons(getOperation());
		addActionError(getText(message));
		controlResultForward();
		return Forward.SUCCESS;
	}

	protected abstract boolean validateDetails();

	/*
	 * (non-Javadoc)
	 *
	 * @see org.vulpe.controller.VulpeSimpleController#validateEntity()
	 */
	public boolean validateEntity() {
		if ((getOperation().equals(Operation.CREATE_POST) || getOperation().equals(Operation.UPDATE_POST))) {
			if (validateExists()) {
				final NotExistEquals notExistEqual = getControllerConfig().getEntityClass().getAnnotation(
						NotExistEquals.class);
				String message = "{vulpe.error.entity.exists}";
				if (StringUtils.isNotEmpty(notExistEqual.message())) {
					message = notExistEqual.message();
				}
				addActionError(message);
				return false;
			}
			for (final VulpeBaseDetailConfig detailConfig : getControllerConfig().getDetails()) {
				final List<ENTITY> details = VulpeReflectUtil.getFieldValue(entity, detailConfig
						.getParentDetailConfig() != null ? detailConfig.getParentDetailConfig().getName()
						: detailConfig.getName());
				if (detailConfig.getPageSize() > 0 && detailConfig.getPropertyName().startsWith("entity.")) {
					final Paging<ENTITY> paging = ever.getSelf(detailConfig.getName() + Controller.DETAIL_PAGING_LIST);
					repairDetailPaging(details, paging);
					details.clear();
					details.addAll(paging.getRealList());
					for (final ENTITY entity : details) {
						if (entity.isFakeId()) {
							entity.setId(null);
						}
					}
					mountDetailPaging(detailConfig, paging);
				}
			}
		}
		return EntityValidator.validate(getEntity()) && validateDetails();
	}

	protected void updateAuditInformation(final ENTITY entity) {
		if (entity instanceof AbstractVulpeBaseAuditEntity) {
			final AbstractVulpeBaseAuditEntity auditEntity = (AbstractVulpeBaseAuditEntity) entity;
			auditEntity.setUserOfLastUpdate(getUserAuthenticated());
			auditEntity.setDateOfLastUpdate(Calendar.getInstance().getTime());
		}
	}

	protected void tabularPagingMount(final boolean add) {
		if (getControllerType().equals(ControllerType.TABULAR) && getControllerConfig().getTabularPageSize() > 0) {
			if (add) {
				setTabularSize(getTabularSize() + getControllerConfig().getController().tabular().newRecords());
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
			setPaging(new Paging<ENTITY>(getTabularSize(), getControllerConfig().getTabularPageSize(), page));
			getPaging().setList(getEntities());
		}
	}

	public String json() {
		final Object object = onJson();
		if (VulpeValidationUtil.isNotEmpty(object)) {
			try {
				JSONArray jsonArray = new JSONArray(object);
				now.put("JSON", jsonArray.toString());
			} catch (JSONException e) {
				LOG.error(e);
			}
		}
		return Forward.JSON;
	}

	protected Object onJson() {
		return null;
	}

	public String autocomplete() {
		List<VulpeHashMap<String, Object>> values = autocompleteValueList();
		if (VulpeValidationUtil.isEmpty(values)) {
			List<ENTITY> autocompleteList = autocompleteList();
			if (VulpeValidationUtil.isEmpty(autocompleteList)) {
				autocompleteList = (List<ENTITY>) invokeServices(Operation.READ.getValue().concat(
						getControllerConfig().getEntityClass().getSimpleName()), new Class[] { getControllerConfig()
						.getEntityClass() }, new Object[] { prepareEntity(Operation.READ).clone() });
			}
			values = new ArrayList<VulpeHashMap<String, Object>>();
			if (VulpeValidationUtil.isNotEmpty(autocompleteList)) {
				final List<Field> autocompleteFields = VulpeReflectUtil.getFieldsWithAnnotation(getControllerConfig()
						.getEntityClass(), Autocomplete.class);
				for (final ENTITY entity : autocompleteList) {
					final VulpeHashMap<String, Object> map = new VulpeHashMap<String, Object>();
					try {
						map.put("id", entity.getId());
						map.put("value", PropertyUtils.getProperty(entity, getEntitySelect().getAutocomplete()));
						if (VulpeValidationUtil.isNotEmpty(autocompleteFields)) {
							for (final Field field : autocompleteFields) {
								if (!field.getName().equals(getEntitySelect().getAutocomplete())) {
									map.put(field.getName(), PropertyUtils.getProperty(entity, field.getName()));
								}
							}
						}
						if (getEntitySelect().getId() != null) {
							now.put("JSON", map.get("value"));
							break;
						}
					} catch (Exception e) {
						LOG.error(e);
					}
					values.add(map);
				}
			}
		}
		if (getEntitySelect().getId() == null) {
			now.put("JSON", new JSONArray(values));
		}
		return Forward.JSON;
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
			} else if (getEntitySelect() != null && StringUtils.isNotEmpty(getEntitySelect().getAutocomplete())) {
				if (getId() != null && getEntitySelect().getId() == null) {
					getEntitySelect().setId(getId());
				}
			} else if (Operation.UPDATE.equals(operation)
					|| (Operation.DELETE.equals(operation) && (getControllerType().equals(ControllerType.SELECT)
							|| getControllerType().equals(ControllerType.TABULAR) || getControllerType().equals(
							ControllerType.TWICE)))) {
				entity.setId(getId());
			}
		} catch (Exception e) {
			throw new VulpeSystemException(e);
		}
		if (StringUtils.isEmpty(entity.getQueryConfigurationName())
				|| "default".equals(entity.getQueryConfigurationName())) {
			entity.setQueryConfigurationName(getControllerConfig().getController().queryConfigurationName());
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

	private void mountDetailPaging(final VulpeBaseDetailConfig detailConfig, final Paging<ENTITY> paging) {
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
						if (real.getId().equals(modified.getId())) {
							paging.getRealList().set(index, modified);
							break;
						}
					}
				}
				++index;
			}
		}
	}

	public String paging() {
		pagingBefore();
		if (VulpeValidationUtil.isNotEmpty(getDetail())) {
			final Paging<ENTITY> paging = ever.getSelf(getDetail() + Controller.DETAIL_PAGING_LIST);
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
		return getResultName();
	}

	protected void pagingBefore() {
	}

	protected void pagingAfter() {
	}

	protected void prepareDetailPaging() {
		if (VulpeValidationUtil.isNotEmpty(getControllerConfig().getDetails())) {
			for (final VulpeBaseDetailConfig detailConfig : getControllerConfig().getDetails()) {
				if (detailConfig.getPageSize() > 0 && detailConfig.getPropertyName().startsWith("entity.")) {
					final List<ENTITY> values = VulpeReflectUtil.getFieldValue(entity, detailConfig.getName());
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
						final Paging<ENTITY> paging = new Paging<ENTITY>(values.size(), detailConfig.getPageSize(), 0);
						paging.setList(list);
						paging.setRealList(values);
						ever.putWeakRef(detailConfig.getName() + Controller.DETAIL_PAGING_LIST, paging);
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
	public String addDetail() {
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
		return getResultName();
	}

	/**
	 * Extension point to code before add detail.
	 *
	 * @since 1.0
	 */
	protected void addDetailBefore() {
		if (!getControllerType().equals(ControllerType.MAIN) && !getControllerType().equals(ControllerType.TABULAR)) {
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
	public String clear() {
		if (getControllerType().equals(ControllerType.MAIN)) {
			setEntitySelect(prepareEntity(Operation.CREATE));
			return create();
		} else if (getControllerType().equals(ControllerType.SELECT)) {
			getSession().removeAttribute(getSelectFormKey());
			getSession().removeAttribute(getSelectTableKey());
			getSession().removeAttribute(getSelectPagingKey());
			setEntitySelect(prepareEntity(Operation.SELECT));
			return select();
		}
		return getResultName();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.vulpe.controller.VulpeController#create()
	 */
	@ResetSession(before = true)
	public String create() {
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
		return getResultName();
	}

	/**
	 * Extension point to create record.
	 *
	 * @since 1.0
	 */
	protected void onCreate() {
		if (getControllerType().equals(ControllerType.MAIN) || getControllerType().equals(ControllerType.TWICE)) {
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
		if (!getControllerType().equals(ControllerType.MAIN) && !getControllerType().equals(ControllerType.TWICE)) {
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
	public String cloneIt() {
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
		return getResultName();
	}

	/**
	 * Extension point to clone record.
	 *
	 * @since 1.0
	 */
	protected boolean onCloneIt() {
		if (getControllerType().equals(ControllerType.MAIN) || getControllerType().equals(ControllerType.TWICE)) {
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
		if (!getControllerType().equals(ControllerType.MAIN) && !getControllerType().equals(ControllerType.TWICE)) {
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
	public String createPost() {
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
					final String entityName = getControllerConfig().getEntityClass().getSimpleName();
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
		return getControllerConfig().isNewOnPost() ? create() : getResultName();
	}

	/**
	 * Extension point to code in confirm create.
	 *
	 * @since 1.0
	 * @return Entity created.
	 */
	protected boolean onCreatePost() {
		setEntity((ENTITY) invokeServices(Operation.CREATE.getValue().concat(
				getControllerConfig().getEntityClass().getSimpleName()), new Class[] { getControllerConfig()
				.getEntityClass() }, new Object[] { prepareEntity(Operation.CREATE_POST) }));
		setId(getEntity().getId());
		setExecuted(true);
		return true;
	}

	/**
	 * Extension point to code before confirm create.
	 *
	 * @since 1.0
	 */
	protected void createPostBefore() {
		if (!getControllerType().equals(ControllerType.MAIN) && !getControllerType().equals(ControllerType.TWICE)) {
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

	protected abstract void createDetails(final List<VulpeBaseDetailConfig> details, final boolean subDetail);

	/*
	 * (non-Javadoc)
	 *
	 * @see org.vulpe.controller.VulpeController#update()
	 */
	@ResetSession(before = true)
	public String update() {
		if (getControllerType() == null || !getControllerType().equals(ControllerType.TWICE)) {
			changeControllerType(ControllerType.MAIN);
		}
		setOperation(Operation.UPDATE);
		updateBefore();
		if (getEntity() == null && getId() == null) {
			return create();
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
		return getResultName();
	}

	/**
	 * Make visualization read only.
	 *
	 * @since 1.0
	 * @return
	 */
	@ResetSession(before = true)
	public String view() {
		setOnlyToSee(true);
		final String update = update();
		manageButtons(Operation.VIEW);
		return update;
	}

	/**
	 * Extension point to prepare update.
	 *
	 * @since 1.0
	 */
	protected void onUpdate() {
		if (getControllerType().equals(ControllerType.MAIN) || getControllerType().equals(ControllerType.TWICE)) {
			setEntity((ENTITY) invokeServices(Operation.FIND.getValue().concat(
					getControllerConfig().getEntityClass().getSimpleName()), new Class[] { getControllerConfig()
					.getEntityClass() }, new Object[] { prepareEntity(getOperation()) }));
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
		if (!getControllerType().equals(ControllerType.MAIN) && !getControllerType().equals(ControllerType.TWICE)) {
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
	public String updatePost() {
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
				final List<ENTITY> entities = getSessionAttribute(getSelectTableKey());
				if (entities != null && !entities.isEmpty()) {
					final List<ENTITY> entitiesOld = new ArrayList<ENTITY>(entities);
					int index = 0;
					for (final ENTITY entity : entitiesOld) {
						if (entity.getId().equals(getEntity().getId())) {
							entities.remove(index);
							entities.add(index, getEntity());
							// entities.add(index,
							// repairCachedClasses(getEntity()));
						}
						++index;
					}
					setSessionAttribute(getSelectTableKey(), entities);
				}
			}
			updatePostAfter();
			if (getControllerType().equals(ControllerType.TWICE)) {
				onRead();
			}
		} else {
			prepareDetailPaging();
			manageButtons(Operation.UPDATE);
		}
		return getResultName();
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
						.getParentDetailConfig() != null ? detailConfig.getParentDetailConfig().getName()
						: detailConfig.getName());
				if (VulpeValidationUtil.isNotEmpty(details)) {
					if (detailConfig.getParentDetailConfig() == null) {
						for (final ENTITY detail : details) {
							updateAuditInformation(detail);
						}
					} else {
						for (final ENTITY detail : details) {
							final List<ENTITY> subDetails = VulpeReflectUtil.getFieldValue(detail, detailConfig
									.getName());
							if (VulpeValidationUtil.isNotEmpty(subDetails)) {
								for (final ENTITY subDetail : subDetails) {
									updateAuditInformation(subDetail);
								}
								VulpeReflectUtil.setFieldValue(detail, detailConfig.getName(), subDetails);
							}
						}
					}
					VulpeReflectUtil.setFieldValue(entity, detailConfig.getName(), details);
				}
			}
		}
		setEntity((ENTITY) invokeServices(Operation.UPDATE.getValue().concat(
				getControllerConfig().getEntityClass().getSimpleName()), new Class[] { getControllerConfig()
				.getEntityClass() }, new Object[] { entity }));
		setExecuted(true);
		return true;
	}

	/**
	 * Extension point to code before confirm update.
	 *
	 * @since 1.0
	 */
	protected void updatePostBefore() {
		if (!getControllerType().equals(ControllerType.MAIN) && !getControllerType().equals(ControllerType.TWICE)) {
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
	public String delete() {
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
		}
		if (getControllerType().equals(ControllerType.MAIN)) {
			controlResultForward();
			return getResultName();
		} else if (getControllerType().equals(ControllerType.TWICE) && getEntity().getId() != null) {
			onRead();
			controlResultForward();
			return getResultName();
		}
		return read();
	}

	/**
	 * Extension point to delete.
	 *
	 * @since 1.0
	 */
	protected boolean onDelete() {
		final ENTITY entity = prepareEntity(Operation.DELETE);
		final List<ENTITY> entities = new ArrayList<ENTITY>();
		if (VulpeValidationUtil.isNotEmpty(getSelected())) {
			if (!onDeleteMany(entities)) {
				return false;
			}
		} else if (getControllerType().equals(ControllerType.TABULAR)) {
			setTabularSize(getTabularSize() - 1);
		} else {
			if (!onDeleteOne()) {
				return false;
			}
		}
		invokeServices(Operation.DELETE.getValue().concat(getControllerConfig().getEntityClass().getSimpleName()),
				new Class[] { VulpeValidationUtil.isEmpty(entities) ? getControllerConfig().getEntityClass()
						: List.class }, new Object[] { VulpeValidationUtil.isEmpty(entities) ? entity : entities });

		setExecuted(true);
		return true;
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
		if (!getControllerType().equals(ControllerType.SELECT) && !getControllerType().equals(ControllerType.MAIN)
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
	 * @see org.vulpe.controller.VulpeController#deleteDetail()
	 */
	public String deleteDetail() {
		setOperation(Operation.UPDATE_POST);
		deleteDetailBefore();
		manageButtons(Operation.UPDATE);
		final int size = onDeleteDetail();
		if (size > 0) {
			// final String defaultMessage =
			// getDefaultMessage(Operation.DELETE_DETAIL);
			addActionMessage(size > 1 ? "{vulpe.message.delete.details}" : "{vulpe.message.delete.detail}");
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
			final VulpeBaseDetailConfig detailConfig = getControllerConfig().getDetailConfig(getDetail());
			if (detailConfig == null || StringUtils.isBlank(detailConfig.getViewPath())) {
				controlResultForward();
			} else {
				setResultForward(detailConfig.getViewPath());
			}
		} else {
			controlResultForward();
		}
		deleteDetailAfter();
		return getResultName();
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
		if (!getControllerType().equals(ControllerType.SELECT) && !getControllerType().equals(ControllerType.MAIN)
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
	public String read() {
		if (getControllerType() == null || !getControllerType().equals(ControllerType.TWICE)) {
			setOperation(Operation.READ);
		}
		readBefore();
		onRead();
		manageButtons();
		if (getControllerType().equals(ControllerType.SELECT)) {
			if (isBack()) {
				controlResultForward();
				setBack(false);
			} else if (isAjax()) {
				setResultForward(getControllerConfig().getViewItemsPath());
			} else {
				controlResultForward();
			}
		} else if (getControllerType().equals(ControllerType.REPORT)) {
			setResultName(Forward.REPORT);
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
				}
			}
			controlResultForward();
		}
		readAfter();
		return getResultName();
	}

	/**
	 * Extension point to read record.
	 *
	 * @since 1.0
	 */
	protected void onRead() {
		if (isBack() && !isExecuted()) {
			return;
		}
		if (getControllerType().equals(ControllerType.TWICE)) {
			if (getSessionAttribute(getSelectFormKey()) != null && getEntitySelect() == null) {
				setEntitySelect((ENTITY) getSessionAttribute(getSelectFormKey()));
			}
			if (getEntitySelect() == null) {
				setEntitySelect(getEntity());
			}
		}
		if (getControllerConfig().requireOneOfFilters().length > 0 && isFiltersEmpty(getEntitySelect())) {
			final StringBuilder filters = new StringBuilder();
			final String orLabel = getText("label.vulpe.or");
			int filterCount = 0;
			for (String attribute : getControllerConfig().requireOneOfFilters()) {
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
		if (((getControllerType().equals(ControllerType.SELECT) || getControllerType().equals(ControllerType.TWICE)) && getControllerConfig()
				.getPageSize() > 0)
				|| (getControllerType().equals(ControllerType.TABULAR) && getControllerConfig().getTabularPageSize() > 0)) {
			final Integer page = getPaging() == null || getPaging().getPage() == null ? 1 : getPaging().getPage();
			setCurrentPage(page);
			final Integer pageSize = getControllerType().equals(ControllerType.TABULAR) ? getControllerConfig()
					.getTabularPageSize() : getControllerConfig().getPageSize();
			final Paging<ENTITY> paging = (Paging<ENTITY>) invokeServices(Operation.PAGING.getValue().concat(
					getControllerConfig().getEntityClass().getSimpleName()), new Class[] {
					getControllerConfig().getEntityClass(), Integer.class, Integer.class }, new Object[] {
					entity.clone(), pageSize, page });
			setPaging(paging);
			setEntities(paging.getList());
			setSessionAttribute(getSelectPagingKey(), paging);
			if (getControllerType().equals(ControllerType.TABULAR)) {
				setTabularSize(paging.getSize());
				if (paging.getList() == null || paging.getList().isEmpty()) {
					setDetail(Controller.ENTITIES);
					if (!isTabularFilter()) {
						onAddDetail(true);
					}
				}
			}
		} else {
			final List<ENTITY> list = (List<ENTITY>) invokeServices(Operation.READ.getValue().concat(
					getControllerConfig().getEntityClass().getSimpleName()), new Class[] { getControllerConfig()
					.getEntityClass() }, new Object[] { entity.clone() });
			setEntities(list);
		}
		if (getControllerType().equals(ControllerType.REPORT)) {
			final DownloadInfo downloadInfo = doReportLoad();
			setDownloadInfo(downloadInfo);
		} else {
			setSessionAttribute(getSelectFormKey(), entity.clone());
			if (getEntities() != null && !getEntities().isEmpty()) {
				setSessionAttribute(getSelectTableKey(), getEntities());
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
		if (!getControllerType().equals(ControllerType.SELECT) && !getControllerType().equals(ControllerType.TWICE)
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
	public String tabularFilter() {
		setTabularFilter(true);
		return read();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.vulpe.controller.VulpeController#tabularPost()
	 */
	@ResetSession
	public String tabularPost() {
		if (getEntities() != null) {
			setOperation(Operation.TABULAR_POST);
			tabularPostBefore();
			controlResultForward();
			manageButtons();
			if (validateDetails()) {
				if (onTabularPost()) {
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
		}
		tabularPostAfter();
		return getResultName();
	}

	/**
	 * Extension point to logic tabulate.
	 *
	 * @since 1.0
	 */
	protected boolean onTabularPost() {
		final int size = getEntities().size();
		despiseDetails();
		final int sizeDespise = getEntities().size();
		if (getControllerConfig().getTabularPageSize() > 0) {
			setTabularSize(getTabularSize() - (size - sizeDespise));
		}
		for (final ENTITY entity : getEntities()) {
			updateAuditInformation(entity);
		}
		final List<ENTITY> list = (List<ENTITY>) invokeServices(Operation.PERSIST.getValue().concat(
				getControllerConfig().getEntityClass().getSimpleName()), new Class[] { List.class },
				new Object[] { getEntities() });
		setEntities(list);
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
	public String prepare() {
		prepareBefore();
		onPrepare();
		manageButtons(Operation.PREPARE);
		if (getControllerType().equals(ControllerType.SELECT) || getControllerType().equals(ControllerType.REPORT)) {
			if (isBack()) {
				setEntitySelect((ENTITY) getSessionAttribute(getSelectFormKey()));
				setEntities((List<ENTITY>) getSessionAttribute(getSelectTableKey()));
				return read();
			} else {
				getSession().removeAttribute(getSelectFormKey());
				getSession().removeAttribute(getSelectTableKey());
			}
			controlResultForward();
		} else if (getControllerType().equals(ControllerType.TABULAR)) {
			return read();
		} else if (getControllerType().equals(ControllerType.TWICE)) {
			setBodyTwice(ControllerType.SELECT);
			setResultForward(Layout.PROTECTED_JSP_COMMONS.concat(Layout.BODY_JSP));
		} else {
			controlResultForward();
		}
		prepareAfter();
		return getResultName();
	}

	@ResetSession(before = true)
	public String twice() {
		changeControllerType(ControllerType.TWICE);
		prepareBefore();
		onPrepare();
		manageButtons(Operation.TWICE);
		controlResultForward();
		prepareAfter();
		return getResultName();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.vulpe.controller.VulpeController#select()
	 */
	@ResetSession(before = true)
	public String select() {
		changeControllerType(ControllerType.SELECT);
		selectBefore();
		onPrepare();
		manageButtons(Operation.PREPARE);
		if (isBack()) {
			setEntitySelect((ENTITY) getSessionAttribute(getSelectFormKey()));
			setEntities((List<ENTITY>) getSessionAttribute(getSelectTableKey()));
			setPaging((Paging<ENTITY>) getSessionAttribute(getSelectPagingKey()));
			if (getPaging() != null) {
				getPaging().setList(getEntities());
			}
		}
		controlResultForward();
		if (isBack()) {
			selectAfter();
			return read();
		} else {
			getSession().removeAttribute(getSelectFormKey());
			getSession().removeAttribute(getSelectTableKey());
			getSession().removeAttribute(getSelectPagingKey());
		}
		if (getControllerConfig().getController().select().readOnShow()) {
			onRead();
		}
		selectAfter();
		return getResultName();
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
	public String report() {
		changeControllerType(ControllerType.REPORT);
		reportBefore();
		manageButtons(Operation.PREPARE);
		final String read = read();
		reportAfter();
		changeControllerType(ControllerType.SELECT);
		return read;
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
	public String tabular() {
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
		return read();
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
	public String upload() {
		uploadBefore();
		onUpload();
		uploadAfter();
		return Forward.UPLOAD;
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
	public String download() {
		downloadBefore();
		onDownload();

		downloadAfter();
		return Forward.DOWNLOAD;
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
					final VulpeEntity<ID> value = (VulpeEntity<ID>) PropertyUtils.getProperty(entity, field.getName());
					if (VulpeValidationUtil.isNotEmpty(value) && !Modifier.isTransient(field.getModifiers())
							&& value.getClass().isAnnotationPresent(CachedClass.class)) {
						final List<ENTITY> cachedList = getCachedClasses().getSelf(value.getClass().getSimpleName());
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
		final CachedClass cachedClass = getControllerConfig().getEntityClass().getAnnotation(CachedClass.class);
		if (cachedClass != null) {
			if (cachedClass.parameters().length > 0) {
				for (final QueryParameter queryParameter : cachedClass.parameters()) {
					if (StringUtils.isNotBlank(queryParameter.equals().name())
							&& StringUtils.isNotBlank(queryParameter.equals().value())) {
						final Object value = VulpeReflectUtil.getFieldValue(entity, queryParameter.equals().name());
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

}