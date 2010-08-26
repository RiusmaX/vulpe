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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.vulpe.commons.VulpeConstants.Action;
import org.vulpe.commons.VulpeConstants.Error;
import org.vulpe.commons.VulpeConstants.Action.Button;
import org.vulpe.commons.VulpeConstants.Action.Forward;
import org.vulpe.commons.VulpeConstants.View.Layout;
import org.vulpe.commons.annotations.Quantity.QuantityType;
import org.vulpe.commons.beans.DownloadInfo;
import org.vulpe.commons.beans.Paging;
import org.vulpe.commons.beans.Tab;
import org.vulpe.commons.beans.ValueBean;
import org.vulpe.commons.helper.VulpeConfigHelper;
import org.vulpe.commons.util.VulpeValidationUtil;
import org.vulpe.config.annotations.VulpeDomains;
import org.vulpe.controller.annotations.ResetSession;
import org.vulpe.controller.commons.DuplicatedBean;
import org.vulpe.controller.commons.VulpeBaseControllerConfig;
import org.vulpe.controller.commons.VulpeBaseDetailConfig;
import org.vulpe.controller.commons.VulpeControllerConfig.ControllerType;
import org.vulpe.controller.validator.EntityValidator;
import org.vulpe.exception.VulpeSystemException;
import org.vulpe.model.annotations.CachedClass;
import org.vulpe.model.annotations.NotExistEqual;
import org.vulpe.model.entity.VulpeEntity;
import org.vulpe.model.entity.impl.AbstractVulpeBaseAuditEntity;
import org.vulpe.model.services.GenericService;

/**
 * Base Controller
 *
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
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
	 * Current CRUD Entity
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
	 * Selected tab index
	 */
	private int selectedTab;

	/**
	 * Tabular size to paging
	 */
	private int tabularSize;

	/**
	 * Detail
	 */
	private String detail;
	/**
	 * Detail index to delete
	 */
	private Integer detailIndex;

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

	private boolean uploaded;

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

	public void setSelectedTab(final int selectedTab) {
		this.selectedTab = selectedTab;
	}

	public int getSelectedTab() {
		return selectedTab;
	}

	public void setEntitySelect(final ENTITY entitySelect) {
		this.entitySelect = entitySelect;
	}

	public ENTITY getEntitySelect() {
		return entitySelect;
	}

	public void setTabularSize(final int tabularSize) {
		this.tabularSize = tabularSize;
	}

	public int getTabularSize() {
		return tabularSize;
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
	 * Extension point to read report.
	 *
	 * @since 1.0
	 */
	protected abstract DownloadInfo doReadReportLoad();

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
	protected boolean validateQuantity(final Collection<VulpeEntity<?>> beans,
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
	 * Method to validate duplicated details.
	 *
	 * @param beans
	 * @param detailConfig
	 * @return
	 */
	protected boolean validateDuplicatedDetailItens(final Collection<VulpeEntity<?>> beans,
			final VulpeBaseDetailConfig detailConfig) {
		final String[] despiseFields = detailConfig.getDespiseFields();
		final Collection<DuplicatedBean> duplicatedBeans = getControllerUtil().duplicatedItens(
				beans, despiseFields);
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
					lines.append(StringUtils.isBlank(lines.toString()) ? String
							.valueOf(duplicatedBean.getLine()) : ", " + duplicatedBean.getLine());
				}
				++count;
			}
			if (getControllerType().equals(ControllerType.TABULAR)) {
				addActionError("vulpe.error.tabular.duplicated", lines.toString());
			} else {
				final String tabName = getTabs().containsKey(detailConfig.getTitleKey()) ? ((Tab) getTabs()
						.get(detailConfig.getTitleKey())).getTitle()
						: getText(detailConfig.getTitleKey());
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
		return getControllerUtil().getControllerConfig(this);
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
	protected void despiseDetailItens(final Collection<VulpeEntity<?>> beans,
			final VulpeBaseDetailConfig detailConfig) {
		getControllerUtil().despiseItens(beans, detailConfig.getDespiseFields(),
				getControllerType().equals(ControllerType.TABULAR));
	}

	protected abstract void despiseDetail(final Object parent,
			final VulpeBaseDetailConfig detailConfig);

	/**
	 * Method to remove detail despised.
	 *
	 * @since 1.0
	 */
	protected void despiseDetails() {
		for (VulpeBaseDetailConfig detail : getControllerConfig().getDetails()) {
			if (detail.getParentDetailConfig() == null) {
				despiseDetail(this, detail);
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
	 * Method to manager button.
	 *
	 * @param button
	 *            Button
	 * @param show
	 *            Show (true|false)
	 * @since 1.0
	 */
	public void buttonControl(final String button, final boolean show) {
		if (getControllerType().equals(ControllerType.TABULAR)) {
			getButtons().put(
					Button.DELETE.concat(getControllerConfig().getTabularConfig().getBaseName()),
					(Boolean) show);
		}
		if (Button.ADD_DETAIL.equals(button)) {
			getButtons().put(
					Button.ADD_DETAIL
							.concat(getControllerConfig().getTabularConfig().getBaseName()),
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
		buttonControl(button, true);
	}

	/**
	 * Method to show buttons.
	 *
	 * @param buttons
	 *            Buttons.
	 * @since 1.0
	 */
	public void showButtons(final String... buttons) {
		for (String button : buttons) {
			showButton(button);
		}
	}

	public void showButtons(final ControllerType controllerType, final String... buttons) {
		for (String button : buttons) {
			showButton(controllerType + "_" + button);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.vulpe.controller.VulpeController#showButtons(java.lang.String)
	 */
	public void showButtons(final String method) {
		getButtons().clear();
		if (getControllerType().equals(ControllerType.CRUD)) {
			if (getControllerConfig().getDetails() != null) {
				for (VulpeBaseDetailConfig detail : getControllerConfig().getDetails()) {
					if (Action.VIEW.equals(method)) {
						addDetailHide(detail.getBaseName());
						deleteDetailHide(detail.getBaseName());
					} else {
						addDetailShow(detail.getBaseName());
						deleteDetailShow(detail.getBaseName());
					}
				}
			}
			if ((Action.CREATE.equals(method) || Action.DELETE.equals(method) || Action.PREPARE
					.equals(method))
					|| ((Action.CREATE.equals(getOperation()) || Action.CREATE_POST
							.equals(getOperation())) && Action.ADD_DETAIL.equals(method))) {
				showButtons(Button.PREPARE, Button.CREATE_POST, Button.CLEAR);
			} else if (Action.UPDATE.equals(method)
					|| ((Action.UPDATE.equals(getOperation()) || Action.UPDATE_POST
							.equals(getOperation())) && Action.ADD_DETAIL.equals(method))) {
				showButtons(Button.PREPARE, Button.CREATE, Button.UPDATE_POST, Button.DELETE,
						Button.CLEAR);
			} else if (Action.VIEW.equals(method)) {
				showButtons();
			}
		} else if (getControllerType().equals(ControllerType.SELECT)) {
			if (getControllerConfig().getController().select().showReport()) {
				showButtons(Button.READ, Button.REPORT, Button.PREPARE, Button.CREATE,
						Button.UPDATE, Button.DELETE);
			} else {
				showButtons(Button.READ, Button.PREPARE, Button.CREATE, Button.UPDATE,
						Button.DELETE);
			}
			if (isPopup()) {
				hideButtons(Button.CREATE, Button.UPDATE, Button.DELETE);
			}
		} else if (getControllerType().equals(ControllerType.REPORT)) {
			showButtons(Button.READ, Button.CLEAR);
		} else if (getControllerType().equals(ControllerType.TABULAR)) {
			showButtons(Button.TABULAR_RELOAD, Button.DELETE, Button.TABULAR_POST,
					Button.ADD_DETAIL);
			if (getControllerConfig().isTabularShowFilter()) {
				showButton(Button.TABULAR_FILTER);
			}
		} else if (getControllerType().equals(ControllerType.TWICE)) {
			if (Action.DELETE.equals(method) || Action.CREATE.equals(method)
					|| Action.TWICE.equals(method)) {
				showButtons(ControllerType.CRUD, Button.CREATE_POST, Button.CLEAR);
			} else if (Action.UPDATE.equals(method)) {
				showButtons(ControllerType.CRUD, Button.CREATE, Button.UPDATE_POST, Button.DELETE);
			} else if (Action.VIEW.equals(method)) {
				showButtons();
			}

			showButtons(ControllerType.SELECT, Button.READ, Button.PREPARE, Button.UPDATE,
					Button.DELETE);
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
		buttonControl(button, false);
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
		showButtons(getOperation());
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
		if (validateExists()) {
			final NotExistEqual notExistEqual = getEntity().getClass().getAnnotation(
					NotExistEqual.class);
			String message = "vulpe.error.entity.exists";
			if (StringUtils.isNotEmpty(notExistEqual.message())) {
				message = notExistEqual.message();
			}
			addActionError(getText(message));

			return false;
		}
		return EntityValidator.validate(getEntity());
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
						+ getControllerConfig().getController().tabular().newRecords());
			} else {
				setTabularSize(getEntities().size());
			}
			setPaging(new Paging<ENTITY>(getTabularSize(), getControllerConfig()
					.getTabularPageSize(), getPaging().getPage()));
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
		if (getEntities() == null || getEntities().isEmpty()) {
			final ENTITY entity = prepareEntity(Action.READ);
			final List<ENTITY> list = (List<ENTITY>) invokeServices(Action.READ, Action.READ
					.concat(getControllerConfig().getEntityClass().getSimpleName()),
					new Class[] { getControllerConfig().getEntityClass() }, new Object[] { entity
							.clone() });
			setEntities(list);
		}
		final List<ValueBean> values = new ArrayList<ValueBean>();
		if (VulpeConfigHelper.get(VulpeDomains.class).useDB4O()) {
			for (ENTITY entity : getEntities()) {
				String value = "";
				try {
					value = (String) PropertyUtils.getProperty(entity, getEntitySelect()
							.getAutoComplete());
				} catch (Exception e) {
					LOG.error(e);
				}
				values.add(new ValueBean(entity.getId().toString(), value));
			}
		} else {
			if (VulpeValidationUtil.isNotEmpty(getEntities())) {
				for (Iterator iterator = getEntities().iterator(); iterator.hasNext();) {
					Object[] type = (Object[]) iterator.next();
					values.add(new ValueBean(type[0].toString(), type[1].toString()));
				}
			}
		}
		final JSONArray jsonArray = new JSONArray(values);
		now.put("JSON", jsonArray.toString());
		return Forward.JSON;
	}

	/**
	 * Extension point to prepare entity.
	 *
	 * @since 1.0
	 */
	protected ENTITY prepareEntity(final String method) {
		ENTITY entity = Action.READ.equals(method) ? getEntitySelect() : getEntity();
		try {
			if (entity == null) {
				entity = getControllerConfig().getEntityClass().newInstance();
			}
			updateAuditInformation(entity);
			if (Action.READ.equals(method) && getEntitySelect() == null) {
				setEntitySelect(getControllerConfig().getEntityClass().newInstance());
				entity = getEntitySelect();
			} else if (Action.UPDATE.equals(method)
					|| (Action.DELETE.equals(method) && (getControllerType().equals(
							ControllerType.SELECT)
							|| getControllerType().equals(ControllerType.TABULAR) || getControllerType()
							.equals(ControllerType.TWICE)))) {
				entity.setId(getId());
			}
		} catch (Exception e) {
			throw new VulpeSystemException(e);
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

	/*
	 * (non-Javadoc)
	 *
	 * @see org.vulpe.controller.VulpeController#addDetail()
	 */
	public String addDetail() {
		addDetailBefore();
		final VulpeBaseDetailConfig detailConfig = onAddDetail(false);
		showButtons(Action.ADD_DETAIL);

		setResultName(Forward.SUCCESS);
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
		if (!getControllerType().equals(ControllerType.CRUD)
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
	 * @see org.vulpe.controller.VulpeController#create()
	 */
	@ResetSession(before = true)
	public String create() {
		if (getControllerType() == null || !getControllerType().equals(ControllerType.TWICE)) {
			changeControllerType(ControllerType.CRUD);
		}
		setOperation(Action.CREATE);
		createBefore();
		onCreate();
		setSelectedTab(0);
		showButtons(Action.CREATE);

		setResultName(Forward.SUCCESS);
		if (getControllerType().equals(ControllerType.TWICE)) {
			setBodyTwice(ControllerType.CRUD);
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
		if (getControllerType().equals(ControllerType.CRUD)
				|| getControllerType().equals(ControllerType.TWICE)) {
			try {
				setEntity(getControllerConfig().getEntityClass().newInstance());
				if (getControllerConfig().getDetails() != null
						&& !getControllerConfig().getDetails().isEmpty()) {
					createDetails(getControllerConfig().getDetails(), false);
					setDetail("");
				}
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
		if (!getControllerType().equals(ControllerType.CRUD)
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
	 * @see org.vulpe.controller.VulpeController#createPost()
	 */
	@ResetSession
	public String createPost() {
		if (getControllerType() == null || !getControllerType().equals(ControllerType.TWICE)) {
			changeControllerType(ControllerType.CRUD);
		}
		setOperation(Action.CREATE_POST);
		createPostBefore();
		controlResultForward();
		if (!validateEntity()) {
			showButtons(Action.CREATE);
			return Forward.SUCCESS;
		} else if (!validateDetails()) {
			showButtons(Action.CREATE);
			return Forward.SUCCESS;
		}
		showButtons(Action.UPDATE);
		final ENTITY entity = onCreatePost();
		addActionMessage(getText("vulpe.msg.create.post"));

		setResultName(Forward.SUCCESS);

		if (entity.getClass().isAnnotationPresent(CachedClass.class)) {
			final String entityName = entity.getClass().getSimpleName();
			List<ENTITY> list = (List<ENTITY>) getCachedClass().get(entityName);
			if (list == null) {
				list = new ArrayList<ENTITY>();
			}
			list.add(entity);
			getCachedClass().put(entityName, list);
		}
		createPostAfter(entity);
		if (getControllerType().equals(ControllerType.TWICE)) {
			onRead();
		}
		return getResultName();
	}

	/**
	 * Extension point to code in confirm create.
	 *
	 * @since 1.0
	 * @return Entity created.
	 */
	protected ENTITY onCreatePost() {
		setEntity((ENTITY) invokeServices(Action.CREATE_POST, Action.CREATE
				.concat(getControllerConfig().getEntityClass().getSimpleName()),
				new Class[] { getControllerConfig().getEntityClass() },
				new Object[] { prepareEntity(Action.CREATE_POST) }));
		setId(getEntity().getId());
		setExecuted(true);
		return getEntity();
	}

	/**
	 * Extension point to code before confirm create.
	 *
	 * @since 1.0
	 */
	protected void createPostBefore() {
		if (!getControllerType().equals(ControllerType.CRUD)
				&& !getControllerType().equals(ControllerType.TWICE)) {
			throw new VulpeSystemException(Error.CONTROLLER);
		}
	}

	/**
	 * Extension point to code after confirm create.
	 *
	 * @since 1.0
	 * @param entity
	 *            Entity to create.
	 */
	protected void createPostAfter(final ENTITY entity) {
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
	public String update() {
		if (getControllerType() == null || !getControllerType().equals(ControllerType.TWICE)) {
			changeControllerType(ControllerType.CRUD);
		}
		setOperation(Action.UPDATE);
		updateBefore();
		onUpdate();
		setSelectedTab(0);
		showButtons(Action.UPDATE);
		setResultName(Forward.SUCCESS);
		if (getControllerType().equals(ControllerType.TWICE)) {
			setBodyTwice(ControllerType.CRUD);
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
		showButtons(Action.VIEW);
		return update;
	}

	/**
	 * Extension point to prepare update.
	 *
	 * @since 1.0
	 */
	protected void onUpdate() {
		if (getControllerType().equals(ControllerType.CRUD)
				|| getControllerType().equals(ControllerType.TWICE)) {
			final ENTITY entity = prepareEntity(Action.UPDATE);
			final ENTITY persistentEntity = (ENTITY) invokeServices(Action.UPDATE, Action.FIND
					.concat(getControllerConfig().getEntityClass().getSimpleName()),
					new Class[] { getControllerConfig().getIdClass() }, new Object[] { entity
							.getId() });
			setEntity(persistentEntity);
			setExecuted(false);
		}
	}

	/**
	 * Extension point to code before update.
	 *
	 * @since 1.0
	 */
	protected void updateBefore() {
		if (!getControllerType().equals(ControllerType.CRUD)
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
	public String updatePost() {
		if (getControllerType() == null || !getControllerType().equals(ControllerType.TWICE)) {
			changeControllerType(ControllerType.CRUD);
		}
		setOperation(Action.UPDATE_POST);
		updatePostBefore();
		controlResultForward();
		showButtons(Action.UPDATE);
		if (!validateDetails()) {
			return Forward.SUCCESS;
		}
		if (onUpdatePost()) {
			addActionMessage(getText("vulpe.msg.update.post"));
		}
		setResultName(Forward.SUCCESS);
		if (getEntity().getClass().isAnnotationPresent(CachedClass.class)) {
			final String entityName = getEntity().getClass().getSimpleName();
			List<ENTITY> list = (List<ENTITY>) getCachedClass().get(entityName);
			if (list == null || list.isEmpty()) {
				list = new ArrayList<ENTITY>();
				list.add(getEntity());
			} else {
				int count = 0;
				for (ENTITY baseEntity : list) {
					if (baseEntity.getId().equals(getEntity().getId())) {
						list.set(count, getEntity());
						break;
					}
					++count;
				}
			}
			getCachedClass().put(entityName, list);
		}
		final List<ENTITY> entities = getSessionAttribute(getSelectTableKey());
		if (entities != null && !entities.isEmpty()) {
			final List<ENTITY> entitiesOld = new ArrayList<ENTITY>(entities);
			int index = 0;
			for (ENTITY entity : entitiesOld) {
				if (entity.getId().equals(getEntity().getId())) {
					entities.remove(index);
					entities.add(index, getEntity());
				}
				++index;
			}
			setSessionAttribute(getSelectTableKey(), entities);
		}
		updatePostAfter();
		if (getControllerType().equals(ControllerType.TWICE)) {
			onRead();
		}
		return getResultName();
	}

	/**
	 * Extension point prepare confirm update.
	 *
	 * @since 1.0
	 */
	protected boolean onUpdatePost() {
		final ENTITY entity = prepareEntity(Action.UPDATE_POST);
		invokeServices(Action.UPDATE_POST, Action.UPDATE.concat(getControllerConfig()
				.getEntityClass().getSimpleName()), new Class[] { getControllerConfig()
				.getEntityClass() }, new Object[] { entity });

		setExecuted(true);
		return true;
	}

	/**
	 * Extension point to code before confirm update.
	 *
	 * @since 1.0
	 */
	protected void updatePostBefore() {
		if (!getControllerType().equals(ControllerType.CRUD)
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
	public String delete() {
		setOperation(Action.DELETE);
		deleteBefore();
		showButtons(Action.DELETE);
		if (onDelete()) {
			addActionMessage(getText("vulpe.msg.delete"));
		}
		setResultName(Forward.SUCCESS);
		deleteAfter();
		if (getControllerType().equals(ControllerType.CRUD)) {
			setEntity(null);
			controlResultForward();
			return getResultName();
		} else if (getControllerType().equals(ControllerType.TWICE) && getEntity().getId() != null) {
			setEntity(null);
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
		final ENTITY entity = prepareEntity(Action.DELETE);
		final List<ENTITY> entities = new ArrayList<ENTITY>();
		if (getSelected() != null && !getSelected().isEmpty()) {
			for (ID id : getSelected()) {
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
		} else {
			setTabularSize(getTabularSize() - 1);
		}
		invokeServices(Action.DELETE, Action.DELETE.concat(getControllerConfig().getEntityClass()
				.getSimpleName()), new Class[] { entities.isEmpty() ? getControllerConfig()
				.getEntityClass() : List.class }, new Object[] { entities.isEmpty() ? entity
				: entities });

		setExecuted(true);
		return true;
	}

	/**
	 * Extension point to code before delete.
	 *
	 * @since
	 */
	protected void deleteBefore() {
		if (!getControllerType().equals(ControllerType.SELECT)
				&& !getControllerType().equals(ControllerType.CRUD)
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
		setOperation(Action.UPDATE_POST);
		deleteDetailBefore();
		showButtons(Action.UPDATE);
		final int size = onDeleteDetail();
		if (size > 0) {
			addActionMessage(getText(size > 1 ? "vulpe.msg.delete.details"
					: "vulpe.msg.delete.detail"));
		}
		setResultName(Forward.SUCCESS);
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
		if (!getControllerType().equals(ControllerType.SELECT)
				&& !getControllerType().equals(ControllerType.CRUD)
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
			setOperation(Action.READ);
		}
		readBefore();
		onRead();
		showButtons(Action.READ);
		setResultName(Forward.SUCCESS);
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
		if (getControllerConfig().requireOneOfFilters().length > 0
				&& isFiltersEmpty(getEntitySelect())) {
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
			addActionError(getText("vulpe.error.validate.require.one.of.filters", filters
					.toString()));
			return;
		}
		final ENTITY entity = prepareEntity(Action.READ);
		if (((getControllerType().equals(ControllerType.SELECT) || getControllerType().equals(
				ControllerType.TWICE)) && getControllerConfig().getPageSize() > 0)
				|| (getControllerType().equals(ControllerType.TABULAR) && getControllerConfig()
						.getTabularPageSize() > 0)) {
			final Integer page = getPaging() == null || getPaging().getPage() == null ? 1
					: getPaging().getPage();
			final Integer pageSize = getControllerType().equals(ControllerType.TABULAR) ? getControllerConfig()
					.getTabularPageSize()
					: getControllerConfig().getPageSize();
			final Paging<ENTITY> paging = (Paging<ENTITY>) invokeServices(Action.READ,
					Action.PAGING.concat(getControllerConfig().getEntityClass().getSimpleName()),
					new Class[] { getControllerConfig().getEntityClass(), Integer.class,
							Integer.class }, new Object[] { entity.clone(), pageSize, page });
			setPaging(paging);
			setEntities(paging.getList());
			setSessionAttribute(getSelectPagingKey(), paging);
			if (getControllerType().equals(ControllerType.TABULAR)) {
				setTabularSize(paging.getSize());
				if (paging.getList() == null || paging.getList().isEmpty()) {
					setDetail(Action.ENTITIES);
					onAddDetail(true);
				}
			}
		} else {
			final List<ENTITY> list = (List<ENTITY>) invokeServices(Action.READ, Action.READ
					.concat(getControllerConfig().getEntityClass().getSimpleName()),
					new Class[] { getControllerConfig().getEntityClass() }, new Object[] { entity
							.clone() });
			setEntities(list);

			if (getControllerType().equals(ControllerType.TABULAR)) {
				if (list == null || list.isEmpty()) {
					setDetail(Action.ENTITIES);
					onAddDetail(true);
				}
			}
			if (getControllerType().equals(ControllerType.REPORT)) {
				final DownloadInfo downloadInfo = doReadReportLoad();
				setDownloadInfo(downloadInfo);
			}
		}
		setSessionAttribute(getSelectFormKey(), entity.clone());
		if (getEntities() != null && !getEntities().isEmpty()) {
			setSessionAttribute(getSelectTableKey(), getEntities());
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
	public String tabularFilter() {
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
			setOperation(Action.TABULAR_POST);
			tabularPostBefore();
			controlResultForward();
			showButtons(Action.TABULAR_POST);
			if (!validateDetails()) {
				return Forward.SUCCESS;
			}
			onTabularPost();
			addActionMessage(getText("vulpe.msg.tabular.post"));
			if (!getEntities().isEmpty()) {
				final ENTITY entityTabular = getEntities().get(0);
				if (entityTabular.getClass().isAnnotationPresent(CachedClass.class)) {
					final String entityName = entityTabular.getClass().getSimpleName();
					getCachedClass().put(entityName, getEntities());
				}
			}
		}
		setResultName(Forward.SUCCESS);
		tabularPostAfter();
		return getResultName();
	}

	/**
	 * Extension point to logic tabulate.
	 *
	 * @since 1.0
	 */
	protected void onTabularPost() {
		final int size = getEntities().size();
		despiseDetails();
		final int sizeDespise = getEntities().size();
		if (getControllerConfig().getTabularPageSize() > 0) {
			setTabularSize(getTabularSize() - (size - sizeDespise));
		}
		for (ENTITY entity : getEntities()) {
			updateAuditInformation(entity);
		}
		final List<ENTITY> list = (List<ENTITY>) invokeServices(Action.TABULAR_POST, Action.PERSIST
				.concat(getControllerConfig().getEntityClass().getSimpleName()),
				new Class[] { List.class }, new Object[] { getEntities() });
		setEntities(list);

		tabularPagingMount(false);
		setExecuted(true);
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
		showButtons(Action.PREPARE);

		setResultName(Forward.SUCCESS);
		if (getControllerType().equals(ControllerType.SELECT)
				|| getControllerType().equals(ControllerType.REPORT)) {
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
		showButtons(Action.TWICE);

		setResultName(Forward.SUCCESS);
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
		prepareBefore();
		onPrepare();
		showButtons(Action.PREPARE);

		setResultName(Forward.SUCCESS);
		if (isBack()) {
			setEntitySelect((ENTITY) getSessionAttribute(getSelectFormKey()));
			setEntities((List<ENTITY>) getSessionAttribute(getSelectTableKey()));
			setPaging((Paging<ENTITY>) getSessionAttribute(getSelectPagingKey()));
			getPaging().setList(getEntities());
			return read();
		} else {
			getSession().removeAttribute(getSelectFormKey());
			getSession().removeAttribute(getSelectTableKey());
			getSession().removeAttribute(getSelectPagingKey());
		}
		controlResultForward();

		prepareAfter();
		return getResultName();
	}

	@ResetSession(before = true)
	public String report() {
		changeControllerType(ControllerType.REPORT);
		prepareBefore();
		onPrepare();
		showButtons(Action.PREPARE);

		setResultName(Forward.SUCCESS);
		if (isBack()) {
			setEntitySelect((ENTITY) getSessionAttribute(getSelectFormKey()));
			setEntities((List<ENTITY>) getSessionAttribute(getSelectTableKey()));
			return read();
		} else {
			getSession().removeAttribute(getSelectFormKey());
			getSession().removeAttribute(getSelectTableKey());
		}
		controlResultForward();

		prepareAfter();
		return getResultName();
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
		prepareBefore();
		onPrepare();
		showButtons(Action.TABULAR);
		prepareAfter();
		return read();
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
				setEntity(getControllerConfig().getEntityClass().newInstance());
			}
			setEntitySelect(getControllerConfig().getEntityClass().newInstance());
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
}