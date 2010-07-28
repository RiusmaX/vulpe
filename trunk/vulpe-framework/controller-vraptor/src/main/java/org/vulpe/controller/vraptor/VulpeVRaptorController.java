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
package org.vulpe.controller.vraptor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ognl.Ognl;
import ognl.OgnlException;
import ognl.OgnlRuntime;
import ognl.PropertyAccessor;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.vulpe.commons.VulpeConstants.Action;
import org.vulpe.commons.VulpeConstants.Error;
import org.vulpe.commons.VulpeConstants.Action.Button;
import org.vulpe.commons.VulpeConstants.Action.Forward;
import org.vulpe.commons.VulpeConstants.Action.Validate.Cardinality;
import org.vulpe.commons.VulpeConstants.View.Layout;
import org.vulpe.commons.beans.DownloadInfo;
import org.vulpe.commons.beans.Paging;
import org.vulpe.controller.VulpeController;
import org.vulpe.controller.annotations.ResetSession;
import org.vulpe.controller.commons.VulpeControllerConfig.ControllerType;
import org.vulpe.controller.commons.DuplicatedBean;
import org.vulpe.controller.commons.VulpeBaseControllerConfig;
import org.vulpe.controller.commons.VulpeBaseDetailConfig;
import org.vulpe.controller.validator.EntityValidator;
import org.vulpe.exception.VulpeSystemException;
import org.vulpe.model.annotations.CachedClass;
import org.vulpe.model.entity.VulpeEntity;


/**
 * Vulpe Base Action to VRaptor
 *
 * @param <ENTITY>
 *            Entity
 * @param <ID>
 *            Identifier
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings({ "unchecked", "serial" })
public class VulpeVRaptorController<ENTITY extends VulpeEntity<ID>, ID extends Serializable & Comparable>
		extends AbstractVulpeVRaptorSimpleController implements VulpeController {

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

	/*
	 * (non-Javadoc)
	 *
	 * @see org.vulpe.controller.VulpeController#create()
	 */
	@ResetSession(before = true)
	public String create() {
		if (getControllerType() == null || !getControllerType().equals(ControllerType.TWICE)) {
			getControllerConfig().setControllerType(ControllerType.CRUD);
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

	private void createDetails(final List<VulpeBaseDetailConfig> details, final boolean subDetail) {
		for (VulpeBaseDetailConfig detail : details) {
			if (subDetail) {
				final Map context = null;//ActionContext.getContext().getContextMap();
				try {
					final Collection collection = (Collection) Ognl.getValue(getDetail(), context,
							this);
					for (int i = 0; i < collection.size(); i++) {
						setDetail(detail.getParentDetailConfig().getPropertyName() + "[" + i + "]."
								+ detail.getPropertyName());
						onAddDetail(true);
					}
				} catch (OgnlException e) {
					LOG.error(e);
				}
			} else if (detail.getParentDetailConfig() == null) {
				setDetail(detail.getPropertyName());
				onAddDetail(true);
			}
			if (detail.getSubDetails() != null && !detail.getSubDetails().isEmpty()) {
				createDetails(detail.getSubDetails(), true);
			}
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
			getControllerConfig().setControllerType(ControllerType.CRUD);
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
		despiseDetails();

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

	/*
	 * (non-Javadoc)
	 *
	 * @see org.vulpe.controller.VulpeController#update()
	 */
	@ResetSession(before = true)
	public String update() {
		if (getControllerType() == null || !getControllerType().equals(ControllerType.TWICE)) {
			getControllerConfig().setControllerType(ControllerType.CRUD);
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
			getControllerConfig().setControllerType(ControllerType.CRUD);
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
		if (entity.getClass().isAnnotationPresent(CachedClass.class)) {
			final String entityName = entity.getClass().getSimpleName();
			List<ENTITY> list = (List<ENTITY>) getCachedClass().get(entityName);
			if (list == null || list.isEmpty()) {
				list = new ArrayList<ENTITY>();
				list.add(entity);
			} else {
				int count = 0;
				for (ENTITY baseEntity : list) {
					if (baseEntity.getId().equals(entity.getId())) {
						list.set(count, entity);
						break;
					}
					++count;
				}
			}
			getCachedClass().put(entityName, list);
		}
		final String selectTableKey = getControllerUtil().getCurrentControllerKey()
				+ Action.SELECT_TABLE;
		final List<ENTITY> entities = getSessionAttribute(selectTableKey);
		if (entities != null && !entities.isEmpty()) {
			final List<ENTITY> entitiesOld = new ArrayList<ENTITY>(entities);
			int index = 0;
			for (ENTITY entity : entitiesOld) {
				if (entity.getId().equals(getEntity().getId())) {
					entities.remove(index);
					entities.add(index, getEntity());
					++index;
				}
			}
			setSessionAttribute(selectTableKey, entities);
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
		despiseDetails();

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
		if (getControllerType().equals(ControllerType.TWICE) && getEntity().getId() != null) {
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
	protected int onDeleteDetail() {
		final ENTITY entity = prepareEntity(Action.DELETE);
		final Map context = null;//ActionContext.getContext().getContextMap();
		try {
			final List<VulpeEntity<?>> details = (List<VulpeEntity<?>>) Ognl.getValue(
					getDetail(), context, this);
			final List<VulpeEntity<?>> removedDetails = new ArrayList<VulpeEntity<?>>();
			final int size = details.size();
			int removed = 0;
			if (getDetailIndex() == null) {
				for (final Iterator<VulpeEntity<?>> iterator = details.iterator(); iterator
						.hasNext();) {
					final VulpeEntity<?> detail = (VulpeEntity<?>) iterator.next();
					if (detail.isSelected()) {
						if (detail.getId() != null) {
							removedDetails.add(detail);
						}
						iterator.remove();
						removed++;
					}
				}
			} else {
				final VulpeEntity<?> detail = details.get(getDetailIndex().intValue());
				if (detail.getId() != null) {
					removedDetails.add(detail);
				}
				details.remove(getDetailIndex().intValue());
				removed++;
			}
			boolean save = false;
			for (VulpeEntity<?> baseEntity : removedDetails) {
				if (baseEntity.getId() != null) {
					save = true;
					break;
				}
			}
			if (save) {
				if (getControllerType().equals(ControllerType.TABULAR)) {
					invokeServices(Action.DELETE, Action.DELETE.concat(getControllerConfig()
							.getEntityClass().getSimpleName()), new Class[] { List.class },
							new Object[] { removedDetails });
					if (getControllerConfig().getTabularPageSize() > 0) {
						setTabularSize(getTabularSize() - removedDetails.size());
					}
				} else {
					if (entity.getId() != null && size > details.size()) {
						invokeServices(Action.UPDATE_POST, Action.UPDATE
								.concat(getControllerConfig().getEntityClass().getSimpleName()),
								new Class[] { getControllerConfig().getEntityClass() },
								new Object[] { entity });
						invokeServices(Action.DELETE, Action.DELETE.concat(getControllerConfig()
								.getEntityClass().getSimpleName()), new Class[] { List.class },
								new Object[] { removedDetails });
					}
				}
			}
			if (!getControllerType().equals(ControllerType.TABULAR)) {
				configureDetail();
			}
			setExecuted(true);
			return removed;
		} catch (OgnlException e) {
			throw new VulpeSystemException(e);
		}
	}

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
	 * Configure detail to view.
	 */
	protected void configureDetail() {
		setRequestAttribute(Layout.TARGET_CONFIG, getDetailConfig());
		setRequestAttribute(Layout.TARGET_CONFIG_PROPERTY_NAME, getDetail());
	}

	/**
	 *
	 * @return
	 */
	public String json() {
		if (getEntities() == null || getEntities().isEmpty()) {
			read();
		}
		JSONArray jsonArray = new JSONArray(getEntities());
		setRequestAttribute("JSON", jsonArray.toString());
		return Forward.JSON;
	}

	/**
	 *
	 * @return
	 */
	public String autocomplete() {
		if (getEntities() == null || getEntities().isEmpty()) {
			onRead();
		}
		JSONArray jsonArray = new JSONArray(getEntities());
		setRequestAttribute("JSON", jsonArray.toString());
		return Forward.JSON;
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
		final String selectFormKey = getControllerUtil().getCurrentControllerKey()
				+ Action.SELECT_FORM;
		if (getControllerType().equals(ControllerType.TWICE)) {
			if (getSessionAttribute(selectFormKey) != null && getEntitySelect() == null) {
				setEntitySelect((ENTITY) getSessionAttribute(selectFormKey));
			}
			if (getEntitySelect() == null) {
				setEntitySelect(getEntity());
			}
		}

		ENTITY entity = prepareEntity(Action.READ);
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
		setSessionAttribute(selectFormKey, entity.clone());
		if (getEntities() != null && !getEntities().isEmpty()) {
			final String selectTableKey = getControllerUtil().getCurrentControllerKey()
					+ Action.SELECT_TABLE;
			setSessionAttribute(selectTableKey, getEntities());
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
	 * Extension point to add detail.
	 *
	 * @since 1.0
	 * @param start
	 *            indicates if use <code>startNewDetails</code> or
	 *            <code>newDetails</code> parameter of Detail Config
	 * @return
	 */
	protected VulpeBaseDetailConfig onAddDetail(final boolean start) {
		boolean createNullObjects = false;
		final Map context = null;//ActionContext.getContext().getContextMap();
		try {
//			if (!OgnlContextState.isCreatingNullObjects(context)) {
//				OgnlContextState.setCreatingNullObjects(context, true);
//				createNullObjects = true;
//			}

			int newDetails = 1;
			final VulpeBaseDetailConfig detailConfig = getControllerConfig().getDetailConfig(
					getDetail());
			if (detailConfig != null) {
				newDetails = start ? detailConfig.getStartNewDetails() : detailConfig
						.getNewDetails();
			}
			final Collection collection = (Collection) Ognl.getValue(getDetail(), context, this);
			if (collection != null && getControllerType().equals(ControllerType.TABULAR)) {
				setTabularSize(collection.size());
			}
			for (int i = 0; i < newDetails; i++) {
				doAddDetail(collection);
			}

			if (detailConfig != null) {
				newDetails = detailConfig.getNewDetails();
				final String parentName = getControllerConfig().getParentName(getDetail());
				final Object parent = Ognl.getValue(parentName, context, this);
				configureDetail();
				if (detailConfig.getParentDetailConfig() != null) {
					setRequestAttribute(detailConfig.getParentDetailConfig().getBaseName().concat(
							Layout.DETAIL_ITEM), parent);
				}
			}

			if (getControllerType().equals(ControllerType.TABULAR)) {
				tabularPagingMount(true);
			}
			return detailConfig;
		} catch (Exception e) {
			throw new VulpeSystemException(e);
		} finally {
			if (createNullObjects) {
//				OgnlContextState.setCreatingNullObjects(context, false);
			}
		}
	}

	/**
	 * Method to add detail.
	 *
	 * @param collection
	 * @since 1.0
	 * @throws OgnlException
	 */
	protected void doAddDetail(final Collection collection) throws OgnlException {
		final Map context = null;//ActionContext.getContext().getContextMap();
		final PropertyAccessor accessor = OgnlRuntime.getPropertyAccessor(collection.getClass());
		final Integer index = Integer.valueOf(collection.size());
		final ENTITY detail = (ENTITY) accessor.getProperty(context, collection, index);
		updateAuditInformation(detail);
		final ENTITY preparedDetail = prepareDetail(detail);
		if (!preparedDetail.equals(detail)) {
			accessor.setProperty(context, collection, index, preparedDetail);
		}
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
			final String selectFormKey = getControllerUtil().getCurrentControllerKey()
					+ Action.SELECT_FORM;
			final String selectTableKey = getControllerUtil().getCurrentControllerKey()
					+ Action.SELECT_TABLE;
			if (isBack()) {
				setEntitySelect((ENTITY) getSessionAttribute(selectFormKey));
				setEntities((List<ENTITY>) getSessionAttribute(selectTableKey));
				return read();
			} else {
				getSession().removeAttribute(selectFormKey);
				getSession().removeAttribute(selectTableKey);
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
		getControllerConfig().setControllerType(ControllerType.TWICE);
		prepareBefore();
		onPrepare();
		showButtons(Action.TWICE);

		setResultName(Forward.SUCCESS);
		controlResultForward();

		prepareAfter();
		return getResultName();
	}

	@ResetSession(before = true)
	public String select() {
		getControllerConfig().setControllerType(ControllerType.SELECT);
		prepareBefore();
		onPrepare();
		showButtons(Action.PREPARE);

		setResultName(Forward.SUCCESS);
		final String selectFormKey = getControllerUtil().getCurrentControllerKey()
				+ Action.SELECT_FORM;
		final String selectTableKey = getControllerUtil().getCurrentControllerKey()
				+ Action.SELECT_TABLE;
		if (isBack()) {
			setEntitySelect((ENTITY) getSessionAttribute(selectFormKey));
			setEntities((List<ENTITY>) getSessionAttribute(selectTableKey));
			return read();
		} else {
			getSession().removeAttribute(selectFormKey);
			getSession().removeAttribute(selectTableKey);
		}
		controlResultForward();

		prepareAfter();
		return getResultName();
	}

	@ResetSession(before = true)
	public String report() {
		getControllerConfig().setControllerType(ControllerType.REPORT);
		prepareBefore();
		onPrepare();
		showButtons(Action.PREPARE);

		setResultName(Forward.SUCCESS);
		final String selectFormKey = getControllerUtil().getCurrentControllerKey()
				+ Action.SELECT_FORM;
		final String selectTableKey = getControllerUtil().getCurrentControllerKey()
				+ Action.SELECT_TABLE;
		if (isBack()) {
			setEntitySelect((ENTITY) getSessionAttribute(selectFormKey));
			setEntities((List<ENTITY>) getSessionAttribute(selectTableKey));
			return read();
		} else {
			getSession().removeAttribute(selectFormKey);
			getSession().removeAttribute(selectTableKey);
		}
		controlResultForward();

		prepareAfter();
		return getResultName();
	}

	@ResetSession(before = true)
	public String tabular() {
		getControllerConfig().setControllerType(ControllerType.TABULAR);
		if (getControllerConfig().getTabularFilter()) {
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
	 * Method to validate detail.
	 *
	 * @since 1.0
	 */
	protected boolean validateDetails() {
		final Map context = null;//ActionContext.getContext().getContextMap();
		for (VulpeBaseDetailConfig detailConfig : getControllerConfig().getDetails()) {
			if (detailConfig.getParentDetailConfig() == null) {
				try {
					final Collection<VulpeEntity<?>> beans = (Collection) Ognl.getValue(
							detailConfig.getPropertyName(), context, this);
					if (!validateCardinality(beans, detailConfig)) {
						return false;
					}
					if (beans != null && beans.size() > 1
							&& !validateDuplicatedDetailItens(beans, detailConfig)) {
						return false;
					}
				} catch (OgnlException e) {
					throw new VulpeSystemException(e);
				}
			}
		}
		return true;
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
	protected void despiseDetail(final Object parent, final VulpeBaseDetailConfig detailConfig) {
		final Map context = null;//ActionContext.getContext().getContextMap();
		try {
			final Collection<VulpeEntity<?>> beans = (Collection) Ognl.getValue(detailConfig
					.getPropertyName(), context, parent);
			despiseDetailItens(beans, detailConfig);
			if (beans != null && !detailConfig.getSubDetails().isEmpty()) {
				for (VulpeEntity<?> bean : beans) {
					for (VulpeBaseDetailConfig subDetailConfig : detailConfig.getSubDetails()) {
						despiseDetail(bean, subDetailConfig);
					}
				}
			}
		} catch (OgnlException e) {
			throw new VulpeSystemException(e);
		}
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
					lines.append(" " + getText("vulpe.label.and") + " " + duplicatedBean.getLine());
				} else {
					lines.append(StringUtils.isBlank(lines.toString()) ? String
							.valueOf(duplicatedBean.getLine()) : ", " + duplicatedBean.getLine());
				}
				++count;
			}
			if (getControllerType().equals(ControllerType.TABULAR)) {
				addActionError("vulpe.error.tabular.duplicated", lines.toString());
			} else {
				addActionError("vulpe.error.details.duplicated",
						getText(detailConfig.getTitleKey()), lines.toString());
			}
			return false;
		}
		return true;
	}

	/**
	 * Method to validate cardinality on details.
	 *
	 * @param beans
	 * @param detailConfig
	 * @return
	 */
	protected boolean validateCardinality(final Collection<VulpeEntity<?>> beans,
			final VulpeBaseDetailConfig detailConfig) {
		if (!Cardinality.ZERO.equals(detailConfig.getCardinalityType().getValue())) {
			if (Cardinality.ONE.equals(detailConfig.getCardinalityType().getValue())) {
				if (beans == null || beans.size() == 0) {
					addActionError("vulpe.error.details.cardinality.one.less", getText(detailConfig
							.getTitleKey()));
					return false;
				} else if (beans.size() > 1) {
					addActionError("vulpe.error.details.cardinality.one.only", getText(detailConfig
							.getTitleKey()));
				}
			} else if (Cardinality.ONE_OR_MORE.equals(detailConfig.getCardinalityType().getValue())) {
				if (beans == null || beans.size() == 0) {
					addActionError("vulpe.error.details.cardinality.one.more", getText(detailConfig
							.getTitleKey()));
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Method to show buttons and configure forward.
	 *
	 * @since 1.0
	 */
	protected void showButtons(final String method) {
		if (getControllerType().equals(ControllerType.CRUD)) {
			if (getControllerConfig().getDetails() != null) {
				for (VulpeBaseDetailConfig detail : getControllerConfig().getDetails()) {
					if (Action.VIEW.equals(method)) {
						addDetailHide(detail.getBaseName());
						deleteHide(detail.getBaseName());
					} else {
						addDetailShow(detail.getBaseName());
						deleteDetailShow(detail.getBaseName());
					}
				}
			}
			if ((Action.CREATE.equals(method) || Action.PREPARE.equals(method))
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
			if (getControllerConfig().getController().showReport()) {
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
			if (getControllerConfig().getTabularFilter()) {
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
	 * Detail
	 */
	private String detail;
	/**
	 * Detail index to delete
	 */
	private Integer detailIndex;

	public boolean isAddDetailShow() {
		return (Boolean) getRequestAttribute(Button.ADD_DETAIL.concat(getControllerConfig()
				.getTabularConfig().getBaseName()));
	}

	public boolean isAddDetailShow(final String detail) {
		return (Boolean) getRequestAttribute(Button.ADD_DETAIL.concat(detail));
	}

	public void addDetailShow(final String detail) {
		setRequestAttribute(Button.ADD_DETAIL.concat(detail), Boolean.TRUE);
	}

	public void addDetailHide(final String detail) {
		setRequestAttribute(Button.ADD_DETAIL.concat(detail), Boolean.FALSE);
	}

	public boolean isDeleteDetailShow(final String detail) {
		return (Boolean) getRequestAttribute(Button.DELETE.concat(detail));
	}

	public void deleteDetailShow(final String detail) {
		setRequestAttribute(Button.DELETE.concat(detail), Boolean.TRUE);
	}

	public void deleteHide(final String detail) {
		setRequestAttribute(Button.DELETE.concat(detail), Boolean.FALSE);
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(final String detail) {
		this.detail = detail;
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
			setRequestAttribute(Button.DELETE.concat(getControllerConfig().getTabularConfig()
					.getBaseName()), (Boolean) show);
		}
		if (Button.ADD_DETAIL.equals(button)) {
			setRequestAttribute(Button.ADD_DETAIL.concat(getControllerConfig().getTabularConfig()
					.getBaseName()), (Boolean) show);
		} else {
			setRequestAttribute(button, show);
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

	public void setSelectedTab(final int selectedTab) {
		this.selectedTab = selectedTab;
	}

	public int getSelectedTab() {
		return selectedTab;
	}

	public void setDetailIndex(final Integer detailIndex) {
		this.detailIndex = detailIndex;
	}

	public Integer getDetailIndex() {
		return detailIndex;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.vulpe.controller.VulpeSimpleController#validateEntity()
	 */
	public boolean validateEntity() {
		return EntityValidator.validate(getEntity());
	}

	public void setEntitySelect(ENTITY entitySelect) {
		this.entitySelect = entitySelect;
	}

	public ENTITY getEntitySelect() {
		return entitySelect;
	}

	protected void updateAuditInformation(final ENTITY entity) {
		entity.setUserOfLastUpdate(getUserAuthenticated());
		entity.setDateOfLastUpdate(Calendar.getInstance().getTime());
	}

	public void setTabularSize(int tabularSize) {
		this.tabularSize = tabularSize;
	}

	public int getTabularSize() {
		return tabularSize;
	}

	protected void tabularPagingMount(final boolean add) {
		if (getControllerType().equals(ControllerType.TABULAR)
				&& getControllerConfig().getTabularPageSize() > 0) {
			if (add) {
				setTabularSize(getTabularSize()
						+ getControllerConfig().getController().tabularNewDetails());
			} else {
				setTabularSize(getEntities().size());
			}
			setPaging(new Paging<ENTITY>(getTabularSize(), getControllerConfig()
					.getTabularPageSize(), getPaging().getPage()));
			getPaging().setList(getEntities());
		}
	}

}