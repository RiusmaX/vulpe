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
package org.vulpe.controller.struts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import ognl.Ognl;
import ognl.OgnlException;
import ognl.OgnlRuntime;
import ognl.PropertyAccessor;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.vulpe.commons.VulpeConstants;
import org.vulpe.commons.VulpeConstants.Controller;
import org.vulpe.commons.VulpeConstants.View.Layout;
import org.vulpe.commons.beans.DownloadInfo;
import org.vulpe.commons.beans.Paging;
import org.vulpe.commons.helper.VulpeConfigHelper;
import org.vulpe.commons.util.VulpeFileUtil;
import org.vulpe.commons.util.VulpeValidationUtil;
import org.vulpe.config.annotations.VulpeView;
import org.vulpe.controller.AbstractVulpeBaseController;
import org.vulpe.controller.VulpeController;
import org.vulpe.controller.annotations.ResetSession;
import org.vulpe.controller.commons.VulpeBaseDetailConfig;
import org.vulpe.controller.commons.VulpeControllerConfig.ControllerType;
import org.vulpe.controller.struts.util.StrutsReportUtil;
import org.vulpe.exception.VulpeSystemException;
import org.vulpe.exception.VulpeValidationException;
import org.vulpe.model.entity.VulpeEntity;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.LocaleProvider;
import com.opensymphony.xwork2.Validateable;
import com.opensymphony.xwork2.ValidationAware;
import com.opensymphony.xwork2.ValidationAwareSupport;
import com.opensymphony.xwork2.util.OgnlContextState;
import com.opensymphony.xwork2.util.OgnlUtil;

/**
 * Vulpe Base Controller to Struts2
 *
 * @param <ENTITY>
 *            Entity
 * @param <ID>
 *            Identifier
 * @author <a href="mailto:fabio.viana@vulpe.org">Fábio Viana</a>
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings( { "unchecked", "serial" })
public class VulpeStrutsController<ENTITY extends VulpeEntity<ID>, ID extends Serializable & Comparable> extends
		AbstractVulpeBaseController<ENTITY, ID> implements VulpeController, com.opensymphony.xwork2.Action,
		Validateable, ValidationAware, LocaleProvider {

	/**
	 *
	 */
	private final OgnlUtil ognlUtil = new OgnlUtil();

	@SkipValidation
	@ResetSession(before = true)
	public String create() {
		return super.create();
	}

	@ResetSession
	public String createPost() {
		return super.createPost();
	}

	@SkipValidation
	@ResetSession(before = true)
	public String update() {
		return super.update();
	}

	/**
	 * Make visualization read only.
	 *
	 * @since 1.0
	 * @return
	 */
	@SkipValidation
	@ResetSession(before = true)
	public String view() {
		return super.view();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.vulpe.controller.AbstractVulpeBaseController#updatePost()
	 */
	@ResetSession
	public String updatePost() {
		return super.updatePost();
	}

	@SkipValidation
	public String delete() {
		return super.delete();
	}

	@SkipValidation
	public String deleteDetail() {
		return super.deleteDetail();
	}

	/**
	 * Extension point to delete detail items.
	 *
	 * @since 1.0
	 * @return number of items affected
	 */
	protected int onDeleteDetail() {
		final ENTITY entity = prepareEntity(Operation.DELETE);
		final Map context = ActionContext.getContext().getContextMap();
		try {
			final List<VulpeEntity<ID>> details = (List<VulpeEntity<ID>>) Ognl.getValue(getDetail(), context, this);
			final List<VulpeEntity<ID>> removedDetails = new ArrayList<VulpeEntity<ID>>();
			final int size = details.size();
			int removed = 0;
			if (getDetailIndex() == null) {
				for (final Iterator<VulpeEntity<ID>> iterator = details.iterator(); iterator.hasNext();) {
					final VulpeEntity<ID> detail = iterator.next();
					if (detail.isSelected()) {
						if (detail.getId() != null) {
							removedDetails.add(detail);
						}
						iterator.remove();
						removed++;
					}
				}
			} else {
				final VulpeEntity<ID> detail = details.get(getDetailIndex().intValue());
				if (detail.getId() != null) {
					removedDetails.add(detail);
				}
				details.remove(getDetailIndex().intValue());
				removed++;
			}
			final VulpeBaseDetailConfig detailConfig = getControllerConfig().getDetailConfig(getDetail());
			if (detailConfig != null) {
				final Paging paging = ever.getSelf(detailConfig.getName() + Controller.DETAIL_PAGING_LIST);
				if (paging != null) {
					for (final ENTITY detail : (List<ENTITY>) removedDetails) {
						for (final Iterator<ENTITY> iterator = paging.getRealList().iterator(); iterator.hasNext();) {
							final ENTITY realDetail = iterator.next();
							if (realDetail.getId().equals(detail.getId())) {
								iterator.remove();
								break;
							}
						}
					}
					paging.processPage();
					final List<ENTITY> list = new ArrayList<ENTITY>();
					int count = 0;
					for (final ENTITY detail : (List<ENTITY>) paging.getRealList()) {
						if (count == detailConfig.getPageSize()) {
							break;
						}
						list.add(detail);
						++count;
					}
					paging.setList(list);
				}
			}
			boolean save = false;
			for (VulpeEntity<ID> baseEntity : removedDetails) {
				if (baseEntity.getId() != null && !baseEntity.isFakeId()) {
					save = true;
					break;
				}
			}
			if (save) {
				if (getControllerType().equals(ControllerType.TABULAR)) {
					invokeServices(Operation.DELETE.getValue().concat(
							getControllerConfig().getEntityClass().getSimpleName()), new Class[] { List.class },
							new Object[] { removedDetails });
					if (getControllerConfig().getTabularPageSize() > 0) {
						setTabularSize(getTabularSize() - removedDetails.size());
					}
				} else {
					if (entity.getId() != null && size > details.size()) {
						invokeServices(Operation.UPDATE.getValue().concat(
								getControllerConfig().getEntityClass().getSimpleName()),
								new Class[] { getControllerConfig().getEntityClass() }, new Object[] { entity });
						invokeServices(Operation.DELETE.getValue().concat(
								getControllerConfig().getEntityClass().getSimpleName()), new Class[] { List.class },
								new Object[] { removedDetails });
					}
				}
			}
			Ognl.setValue(getDetail(), context, this, details);
			if (!getControllerType().equals(ControllerType.TABULAR)) {
				configureDetail();
			}
			final String parentName = getControllerConfig().getParentName(getDetail());
			final Object parent = Ognl.getValue(parentName, context, this);
			if (getDetailConfig().getParentDetailConfig() != null) {
				setRequestAttribute(getDetailConfig().getParentDetailConfig().getBaseName().concat(Layout.DETAIL_ITEM),
						parent);
			}
			setExecuted(true);
			return removed;
		} catch (OgnlException e) {
			throw new VulpeSystemException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.vulpe.controller.VulpeController#addDetail()
	 */
	@SkipValidation
	public String addDetail() {
		return super.addDetail();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.vulpe.controller.VulpeController#prepare()
	 */
	@SkipValidation
	@ResetSession(before = true)
	public String prepare() {
		return super.prepare();
	}

	@SkipValidation
	@ResetSession(before = true)
	public String twice() {
		return super.twice();
	}

	@SkipValidation
	@ResetSession(before = true)
	public String select() {
		return super.select();
	}

	@SkipValidation
	@ResetSession(before = true)
	public String report() {
		return super.report();
	}

	@SkipValidation
	@ResetSession(before = true)
	public String tabular() {
		return super.tabular();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.vulpe.controller.AbstractVulpeBaseController#doReportLoad()
	 */
	protected DownloadInfo doReportLoad() {
		try {
			Collection<?> collection = getReportCollection();
			if (collection == null) {
				collection = (Collection<?>) PropertyUtils.getProperty(this, getControllerConfig()
						.getReportDataSourceName());
			}
			return StringUtils.isNotBlank(getControllerConfig().getReportName()) ? StrutsReportUtil.getInstance()
					.getDownloadInfo(collection, getReportParameters(), getControllerConfig().getReportFile(),
							getControllerConfig().getSubReports(), getControllerConfig().getReportFormat(),
							getControllerConfig().getReportName(), getControllerConfig().isReportDownload())
					: StrutsReportUtil.getInstance().getDownloadInfo(collection, getReportParameters(),
							getControllerConfig().getReportFile(), getControllerConfig().getSubReports(),
							getControllerConfig().getReportFormat());
		} catch (Exception e) {
			throw new VulpeSystemException(e);
		}
	}

	@SkipValidation
	@ResetSession(before = true)
	@Override
	public String backend() {
		return super.backend();
	}

	@SkipValidation
	@ResetSession(before = true)
	@Override
	public String frontend() {
		return super.frontend();
	}

	@SkipValidation
	@Override
	public String upload() {
		return super.upload();
	}

	@SkipValidation
	@Override
	public String download() {
		return super.download();
	}

	/**
	 * Extension point to prepare download.
	 *
	 * @since 1.0
	 */
	@SuppressWarnings("static-access")
	protected DownloadInfo prepareDownloadInfo() {
		try {
			Object value = null;
			if (getFormParams() != null && getFormParams().containsKey(getDownloadKey())) {
				final Object[] array = (Object[]) getFormParams().get(getDownloadKey());
				value = array[1];
			}
			if (value == null) {
				value = ognlUtil.getValue(getDownloadKey(), ActionContext.getContext().getContextMap(), this);
			}
			final DownloadInfo downloadInfo = VulpeFileUtil.getDownloadInfo(value, getDownloadContentType(),
					getDownloadContentDisposition());
			if (downloadInfo != null) {
				downloadInfo.setKey(getDownloadKey());
			}
			return downloadInfo;
		} catch (OgnlException e) {
			throw new VulpeSystemException(e);
		}
	}

	@Override
	public void validate() {
		if (isBack() && !isExecuted()) {
			final Collection messages = getActionMessages();
			clearErrorsAndMessages();
			for (Object object : messages) {
				addActionMessage(object.toString());
			}
		}
		if (hasActionErrors() || hasFieldErrors()) {
			throw new VulpeValidationException();
		}
	}

	/**
	 * Retrieves form parameters
	 *
	 * @return Map with form parameters
	 */
	public Map getFormParams() {
		final String keyForm = getCurrentControllerKey().concat(VulpeConstants.PARAMS_SESSION_KEY);
		Map formParams = (Map) ServletActionContext.getRequest().getSession().getAttribute(keyForm);
		if (formParams == null) {
			formParams = new HashMap();
			ServletActionContext.getRequest().getSession().setAttribute(keyForm, formParams);
		}
		return formParams;
	}

	private final ValidationAwareSupport validationAware = new ValidationAwareSupport();

	public void setActionErrors(Collection errorMessages) {
		validationAware.setActionErrors(errorMessages);
	}

	public Collection getActionErrors() {
		return validationAware.getActionErrors();
	}

	public void setActionMessages(Collection messages) {
		validationAware.setActionMessages(messages);
	}

	public Collection getActionMessages() {
		return validationAware.getActionMessages();
	}

	/**
	 * @deprecated Use {@link #getActionErrors()}.
	 */
	public Collection getErrorMessages() {
		return getActionErrors();
	}

	/**
	 * @deprecated Use {@link #getFieldErrors()}.
	 */
	public Map getErrors() {
		return getFieldErrors();
	}

	public void setFieldErrors(final Map errorMap) {
		validationAware.setFieldErrors(errorMap);
	}

	public Map getFieldErrors() {
		return validationAware.getFieldErrors();
	}

	public Locale getLocale() {
		return ActionContext.getContext().getLocale();
	}

	public void addActionError(final String anErrorMessage) {
		if (anErrorMessage.startsWith("{") && anErrorMessage.endsWith("}")) {
			final String message = getText(anErrorMessage.substring(1, anErrorMessage.length() - 1));
			validationAware.addActionError(message);
		} else {
			validationAware.addActionError(anErrorMessage);
		}
	}

	public void addActionMessage(final String aMessage) {
		if (aMessage.startsWith("{") && aMessage.endsWith("}")) {
			final String message = getText(aMessage.substring(1, aMessage.length() - 1));
			validationAware.addActionMessage(message);
		} else {
			validationAware.addActionMessage(aMessage);
		}
	}

	public void addFieldError(final String fieldName, final String errorMessage) {
		validationAware.addFieldError(fieldName, errorMessage);
	}

	public String input() throws Exception {
		return INPUT;
	}

	public String doDefault() throws Exception {
		return SUCCESS;
	}

	/**
	 * A default implementation that does nothing an returns "success".
	 * <p/>
	 * Subclasses should override this method to provide their business logic.
	 * <p/>
	 * See also {@link com.opensymphony.xwork2.Action#execute()}.
	 *
	 * @return returns {@link #SUCCESS}
	 * @throws Exception
	 *             can be thrown by subclasses.
	 */
	public String execute() throws Exception {
		return SUCCESS;
	}

	public boolean hasActionErrors() {
		return validationAware.hasActionErrors();
	}

	public boolean hasActionMessages() {
		return validationAware.hasActionMessages();
	}

	public boolean hasErrors() {
		return validationAware.hasErrors();
	}

	public boolean hasFieldErrors() {
		return validationAware.hasFieldErrors();
	}

	/**
	 * Clears all errors and messages. Useful for Continuations and other
	 * situations where you might want to clear parts of the state on the same
	 * action.
	 */
	public void clearErrorsAndMessages() {
		validationAware.clearErrorsAndMessages();
	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	/**
	 * <!-- START SNIPPET: pause-method --> Stops the action invocation
	 * immediately (by throwing a PauseException) and causes the action
	 * invocation to return the specified result, such as {@link #SUCCESS},
	 * {@link #INPUT}, etc.
	 * <p/>
	 *
	 * The next time this action is invoked (and using the same continuation
	 * ID), the method will resume immediately after where this method was
	 * called, with the entire call stack in the execute method restored.
	 * <p/>
	 *
	 * Note: this method can <b>only</b> be called within the {@link #execute()}
	 * method. <!-- END SNIPPET: pause-method -->
	 *
	 * @param result
	 *            the result to return - the same type of return value in the
	 *            {@link #execute()} method.
	 */
	public void pause(String result) {
		// pause
	}

	/**
	 * Method to validate detail.
	 *
	 * @since 1.0
	 */
	protected boolean validateDetails() {
		final Map context = ActionContext.getContext().getContextMap();
		boolean valid = true;
		for (VulpeBaseDetailConfig detailConfig : getControllerConfig().getDetails()) {
			if (detailConfig.getParentDetailConfig() == null) {
				despiseDetail(this, getEntity(), detailConfig);
				try {
					final Collection<VulpeEntity<?>> beans = (Collection) Ognl.getValue(detailConfig.getPropertyName(),
							context, this);
					if (!validateQuantity(beans, detailConfig)) {
						valid = false;
					}
					if (duplicatedDetail(this, getEntity(), detailConfig)) {
						valid = false;
					}
				} catch (OgnlException e) {
					throw new VulpeSystemException(e);
				}
			}
		}
		return valid;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.vulpe.controller.AbstractVulpeBaseController#despiseDetail(java.lang
	 * .Object, org.vulpe.model.entity.VulpeEntity,
	 * org.vulpe.controller.commons.VulpeBaseDetailConfig)
	 */
	@Override
	protected void despiseDetail(final Object parent, final ENTITY baseEntity, final VulpeBaseDetailConfig detailConfig) {
		final Map context = ActionContext.getContext().getContextMap();
		try {
			final Collection<VulpeEntity<?>> beans = (Collection) Ognl.getValue(detailConfig.getPropertyName(),
					context, parent);
			final List<VulpeEntity<?>> deleted = despiseDetailItens(beans, detailConfig);
			if (VulpeValidationUtil.isNotEmpty(deleted) && VulpeValidationUtil.isNotEmpty(baseEntity)) {
				baseEntity.getDeletedDetails().addAll(deleted);
			}
			if (beans != null && VulpeValidationUtil.isNotEmpty(detailConfig.getSubDetails())) {
				for (final VulpeEntity<?> bean : beans) {
					for (final VulpeBaseDetailConfig subDetailConfig : detailConfig.getSubDetails()) {
						despiseDetail(bean, baseEntity, subDetailConfig);
					}
				}
			}
		} catch (OgnlException e) {
			throw new VulpeSystemException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.vulpe.controller.AbstractVulpeBaseController#duplicatedDetail(java
	 * .lang.Object, org.vulpe.model.entity.VulpeEntity,
	 * org.vulpe.controller.commons.VulpeBaseDetailConfig)
	 */
	@Override
	protected boolean duplicatedDetail(final Object parent, final ENTITY baseEntity,
			final VulpeBaseDetailConfig detailConfig) {
		boolean has = false;
		final Map context = ActionContext.getContext().getContextMap();
		try {
			final Collection<VulpeEntity<?>> beans = (Collection) Ognl.getValue(detailConfig.getPropertyName(),
					context, parent);
			if (beans != null && beans.size() > 1) {
				has = duplicatedDetailItens(beans, detailConfig);
			}
			if (!has) {
				if (beans != null && VulpeValidationUtil.isNotEmpty(detailConfig.getSubDetails())) {
					for (final VulpeEntity<?> bean : beans) {
						for (final VulpeBaseDetailConfig subDetailConfig : detailConfig.getSubDetails()) {
							has = duplicatedDetail(bean, baseEntity, subDetailConfig);
						}
					}
				}
			}
		} catch (OgnlException e) {
			throw new VulpeSystemException(e);
		}
		return has;
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
		final Map context = ActionContext.getContext().getContextMap();
		try {
			if (!OgnlContextState.isCreatingNullObjects(context)) {
				OgnlContextState.setCreatingNullObjects(context, true);
				createNullObjects = true;
			}
			int newDetails = 1;
			final VulpeBaseDetailConfig detailConfig = getControllerConfig().getDetailConfig(getDetail());
			if (detailConfig != null) {
				newDetails = start ? detailConfig.getStartNewDetails() : detailConfig.getNewDetails();
			}
			final Collection collection = (Collection) Ognl.getValue(getDetail(), context, this);
			for (int i = 0; i < newDetails; i++) {
				doAddDetail(collection);
			}
			if (detailConfig != null) {
				final Paging<ENTITY> paging = ever.getSelf(detailConfig.getName() + Controller.DETAIL_PAGING_LIST);
				if (paging != null) {
					int id = paging.getRealList().size();
					for (final ENTITY entity : (List<ENTITY>) collection) {
						if (entity.getId() == null) {
							++id;
							entity.setId((ID) new Long(id));
							entity.setFakeId(true);
							paging.getRealList().add(0, entity);
						}
					}
					repairDetailPaging((List<ENTITY>) collection, paging);
					paging.processPage();
					final List<ENTITY> list = new ArrayList<ENTITY>();
					int count = 0;
					for (final ENTITY entity : (List<ENTITY>) paging.getRealList()) {
						if (count == detailConfig.getPageSize()) {
							break;
						}
						list.add(entity);
						++count;
					}
					paging.setList(list);
				}
				newDetails = detailConfig.getNewDetails();
				final String parentName = getControllerConfig().getParentName(getDetail());
				final Object parent = Ognl.getValue(parentName, context, this);
				configureDetail();
				if (detailConfig.getParentDetailConfig() != null) {
					setRequestAttribute(detailConfig.getParentDetailConfig().getBaseName().concat(Layout.DETAIL_ITEM),
							parent);
				}
				// if
				// (VulpeValidationUtil.isNotEmpty(detailConfig.getSubDetails())
				// && !start) {
				// for (final VulpeBaseDetailConfig detail :
				// detailConfig.getSubDetails()) {
				// try {
				// final Collection collectionx = (Collection)
				// Ognl.getValue(getDetail(), context, this);
				// for (int i = 0; i < collectionx.size(); i++) {
				// setDetail(detail.getParentDetailConfig().getPropertyName() +
				// "[" + i + "]."
				// + detail.getPropertyName());
				// onAddDetail(false);
				// }
				// } catch (OgnlException e) {
				// LOG.error(e);
				// }
				// }
				// }
			}

			if (getControllerType().equals(ControllerType.TABULAR)) {
				tabularPagingMount(true);
			}
			return detailConfig;
		} catch (Exception e) {
			throw new VulpeSystemException(e);
		} finally {
			if (createNullObjects) {
				OgnlContextState.setCreatingNullObjects(context, false);
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
		final Map context = ActionContext.getContext().getContextMap();
		final PropertyAccessor accessor = OgnlRuntime.getPropertyAccessor(collection.getClass());
		final Integer index = Integer.valueOf(collection.size());
		final VulpeView view = VulpeConfigHelper.getProjectConfiguration().view();
		if (((getControllerType().equals(ControllerType.TABULAR) && (view.addNewDetailsOnTop() || getControllerConfig()
				.getTabularConfig().isAddNewDetailsOnTop())) || (getControllerType().equals(ControllerType.MAIN) && (view
				.addNewDetailsOnTop() || getDetailConfig().isAddNewDetailsOnTop())))
				&& VulpeValidationUtil.isNotEmpty(collection)) {
			final Object value = accessor.getProperty(context, collection, 0);
			try {
				final ENTITY detail = (ENTITY) value.getClass().newInstance();
				updateAuditInformation(detail);
				((ArrayList<ENTITY>) collection).add(0, prepareDetail(detail));
			} catch (InstantiationException e) {
				LOG.error(e);
			} catch (IllegalAccessException e) {
				LOG.error(e);
			}
		} else {
			final ENTITY detail = (ENTITY) accessor.getProperty(context, collection, index);
			updateAuditInformation(detail);
			final ENTITY preparedDetail = prepareDetail(detail);
			if (!preparedDetail.equals(detail)) {
				accessor.setProperty(context, collection, index, preparedDetail);
			}
		}
	}

	protected void createDetails(final List<VulpeBaseDetailConfig> details, final boolean subDetail) {
		for (final VulpeBaseDetailConfig detail : details) {
			if (subDetail) {
				final Map context = ActionContext.getContext().getContextMap();
				try {
					final Collection collection = (Collection) Ognl.getValue(getDetail(), context, this);
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
			if (VulpeValidationUtil.isNotEmpty(detail.getSubDetails())) {
				createDetails(detail.getSubDetails(), true);
			}
		}
	}

}