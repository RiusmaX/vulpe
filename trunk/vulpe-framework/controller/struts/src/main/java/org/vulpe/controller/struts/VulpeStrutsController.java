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
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ognl.Ognl;
import ognl.OgnlException;
import ognl.OgnlRuntime;
import ognl.PropertyAccessor;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.vulpe.commons.VulpeConstants.Controller;
import org.vulpe.commons.VulpeConstants.View.Layout;
import org.vulpe.commons.beans.DownloadInfo;
import org.vulpe.commons.beans.Paging;
import org.vulpe.commons.util.VulpeFileUtil;
import org.vulpe.commons.util.VulpeReflectUtil;
import org.vulpe.commons.util.VulpeValidationUtil;
import org.vulpe.controller.AbstractVulpeBaseController;
import org.vulpe.controller.VulpeController;
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
import com.opensymphony.xwork2.ognl.OgnlUtil;
import com.opensymphony.xwork2.util.reflection.ReflectionContextState;

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
	public void create() {
		super.create();
	}

	@SkipValidation
	public void update() {
		super.update();
	}

	/**
	 * Make visualization read only.
	 * 
	 * @since 1.0
	 * @return
	 */
	@SkipValidation
	public void view() {
		super.view();
	}

	@SkipValidation
	public void delete() {
		super.delete();
	}

	@SkipValidation
	public void deleteDetail() {
		super.deleteDetail();
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
			final List<ENTITY> details = new ArrayList<ENTITY>((List<ENTITY>) Ognl.getValue(
					vulpe.controller().detail(), context, this));
			final List<ENTITY> removedDetails = new ArrayList<ENTITY>();
			final int size = details.size();
			int removed = 0;
			if (vulpe.controller().detailIndex() == null) {
				for (final Iterator<ENTITY> iterator = details.iterator(); iterator.hasNext();) {
					final ENTITY detail = iterator.next();
					if (detail.isSelected()) {
						if (detail.getId() != null) {
							removedDetails.add(detail);
						}
						iterator.remove();
						++removed;
					}
				}
			} else {
				final ENTITY detail = details.get(vulpe.controller().detailIndex().intValue());
				if (detail.getId() != null) {
					removedDetails.add(detail);
				}
				details.remove(vulpe.controller().detailIndex().intValue());
				++removed;
			}
			final VulpeBaseDetailConfig detailConfig = vulpe.controller().config().getDetailConfig(
					vulpe.controller().detail());
			if (detailConfig != null) {
				final Paging paging = ever.getAuto(detailConfig.getName() + Controller.DETAIL_PAGING_LIST);
				final List<ENTITY> realList = new ArrayList<ENTITY>();
				if (paging != null) {
					realList.addAll(paging.getRealList());
					for (final ENTITY detail : (List<ENTITY>) removedDetails) {
						for (final Iterator<ENTITY> iterator = paging.getRealList().iterator(); iterator.hasNext();) {
							final ENTITY realDetail = iterator.next();
							if (realDetail.getId().equals(detail.getId())) {
								iterator.remove();
								break;
							}
						}
					}
					mountDetailPaging(detailConfig, paging);
				}
				if (validateQuantity(paging != null ? paging.getRealList() : details, detailConfig)) {
					boolean save = false;
					for (final VulpeEntity<?> baseEntity : removedDetails) {
						if (baseEntity.getId() != null && !baseEntity.isFakeId()) {
							save = true;
							break;
						}
					}
					if (save) {
						if (vulpe.controller().type().equals(ControllerType.TABULAR)) {
							invokeServices(vulpe.serviceMethodName(Operation.DELETE), new Class[] { List.class },
									new Object[] { removedDetails });
							if (vulpe.controller().config().getTabularPageSize() > 0) {
								vulpe.controller()
										.tabularSize(vulpe.controller().tabularSize() - removedDetails.size());
							}
						} else {
							if (entity.getId() != null && size > details.size()) {
								invokeServices(vulpe.serviceMethodName(Operation.DELETE), new Class[] { List.class },
										new Object[] { removedDetails });
							}
						}
					}
					Ognl.setValue(vulpe.controller().detail(), context, this, details);
					final String parentName = vulpe.controller().config().getParentName(vulpe.controller().detail());
					final Object parent = Ognl.getValue(parentName, context, this);
					if (vulpe.controller().detailConfig().getParentDetailConfig() != null) {
						vulpe.requestAttribute(vulpe.controller().detailConfig().getParentDetailConfig().getBaseName()
								.concat(Layout.DETAIL_ITEM), parent);
					}
				} else {
					removed = 0;
					if (paging != null) {
						paging.setRealList(realList);
						mountDetailPaging(detailConfig, paging);
					}
				}
			}
			vulpe.controller().executed(true);
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
	public void addDetail() {
		super.addDetail();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeController#prepare()
	 */
	@SkipValidation
	public void prepare() {
		super.prepare();
	}

	@SkipValidation
	public void twice() {
		super.twice();
	}

	@SkipValidation
	public void select() {
		super.select();
	}

	@SkipValidation
	public void report() {
		super.report();
	}

	@SkipValidation
	public void tabular() {
		super.tabular();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.AbstractVulpeBaseController#doReportLoad()
	 */
	protected DownloadInfo doReportLoad() {
		try {
			Collection<?> collection = vulpe.controller().reportCollection();
			if (collection == null) {
				collection = VulpeReflectUtil
						.getFieldValue(this, vulpe.controller().config().getReportDataSourceName());
			}
			return StrutsReportUtil.getInstance().getDownloadInfo(collection, vulpe.controller().reportParameters(),
					vulpe.controller().config().getReportFile(), vulpe.controller().config().getSubReports(),
					vulpe.controller().reportFormat(), vulpe.controller().config().getReportName(),
					vulpe.controller().config().isReportDownload());
		} catch (Exception e) {
			throw new VulpeSystemException(e);
		}
	}

	@SkipValidation
	@Override
	public void backend() {
		super.backend();
	}

	@SkipValidation
	@Override
	public void frontend() {
		super.frontend();
	}

	@SkipValidation
	@Override
	public void upload() {
		super.upload();
	}

	@SkipValidation
	@Override
	public void download() {
		super.download();
	}

	/**
	 * Extension point to prepare download.
	 * 
	 * @since 1.0
	 */
	protected DownloadInfo prepareDownloadInfo() {
		try {
			Object value = ever.get(vulpe.controller().downloadKey());
			if (value == null) {
				value = ognlUtil.getValue(vulpe.controller().downloadKey(), ActionContext.getContext().getContextMap(),
						this);
			}
			final DownloadInfo downloadInfo = VulpeFileUtil.getDownloadInfo(value, vulpe.controller()
					.downloadContentType(), vulpe.controller().downloadContentDisposition());
			if (downloadInfo != null) {
				downloadInfo.setKey(vulpe.controller().downloadKey());
			}
			return downloadInfo;
		} catch (OgnlException e) {
			throw new VulpeSystemException(e);
		}
	}

	@Override
	public void validate() {
		if (vulpe.controller().back() && !vulpe.controller().executed()) {
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
			final String message = vulpe.controller().text(anErrorMessage.substring(1, anErrorMessage.length() - 1));
			validationAware.addActionError(message);
		} else {
			validationAware.addActionError(anErrorMessage);
		}
	}

	public void addActionMessage(final String aMessage) {
		if (aMessage.startsWith("{") && aMessage.endsWith("}")) {
			final String message = vulpe.controller().text(aMessage.substring(1, aMessage.length() - 1));
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
		for (final VulpeBaseDetailConfig detailConfig : vulpe.controller().config().getDetails()) {
			if (detailConfig.getParentDetailConfig() == null) {
				despiseDetail(this, entity, detailConfig);
				try {
					final List<ENTITY> beans = (List<ENTITY>) Ognl.getValue(detailConfig.getPropertyName(), context,
							this);
					if (!validateQuantity(beans, detailConfig)) {
						valid = false;
					}
					if (duplicatedDetail(this, entity, detailConfig)) {
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
			if (!ReflectionContextState.isCreatingNullObjects(context)) {
				ReflectionContextState.setCreatingNullObjects(context, true);
				createNullObjects = true;
			}
			int newDetails = 1;
			final VulpeBaseDetailConfig detailConfig = vulpe.controller().config().getDetailConfig(
					vulpe.controller().detail());
			if (detailConfig != null) {
				newDetails = start ? detailConfig.getStartNewDetails() : detailConfig.getNewDetails();
			}
			final Collection collection = (Collection) Ognl.getValue(vulpe.controller().detail(), context, this);
			for (int i = 0; i < newDetails; i++) {
				doAddDetail(collection);
			}
			if (detailConfig != null) {
				final Paging<ENTITY> paging = ever.getAuto(detailConfig.getName() + Controller.DETAIL_PAGING_LIST);
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
				final String parentName = vulpe.controller().config().getParentName(vulpe.controller().detail());
				final Object parent = Ognl.getValue(parentName, context, this);
				if (detailConfig.getParentDetailConfig() != null) {
					vulpe.requestAttribute(detailConfig.getParentDetailConfig().getBaseName()
							.concat(Layout.DETAIL_ITEM), parent);
				}
				// if
				// (VulpeValidationUtil.isNotEmpty(detailConfig.getSubDetails())
				// && !start) {
				// for (final VulpeBaseDetailConfig detail :
				// detailConfig.getSubDetails()) {
				// try {
				// final Collection collectionx = (Collection)
				// Ognl.getValue(vulpe.controller().detail(), context, this);
				// for (int i = 0; i < collectionx.size(); i++) {
				// vulpe.controller().detail(detail.getParentDetailConfig().getPropertyName()
				// +
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

			if (vulpe.controller().type().equals(ControllerType.TABULAR)) {
				tabularPagingMount(true);
			}
			return detailConfig;
		} catch (Exception e) {
			throw new VulpeSystemException(e);
		} finally {
			if (createNullObjects) {
				ReflectionContextState.setCreatingNullObjects(context, false);
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
		if (((vulpe.controller().type().equals(ControllerType.TABULAR) && vulpe.controller().config()
				.getTabularConfig().isAddNewDetailsOnTop()) || (vulpe.controller().type().equals(ControllerType.MAIN) && vulpe
				.controller().detailConfig().isAddNewDetailsOnTop()))
				&& VulpeValidationUtil.isNotEmpty(collection)) {
			final Object value = accessor.getProperty(context, collection, 0);
			try {
				final ENTITY detail = (ENTITY) value.getClass().newInstance();
				vulpe.updateAuditInformation(detail);
				((ArrayList<ENTITY>) collection).add(0, prepareDetail(detail));
			} catch (InstantiationException e) {
				LOG.error(e);
			} catch (IllegalAccessException e) {
				LOG.error(e);
			}
		} else {
			final ENTITY detail = (ENTITY) accessor.getProperty(context, collection, index);
			vulpe.updateAuditInformation(detail);
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
					final Collection collection = (Collection) Ognl
							.getValue(vulpe.controller().detail(), context, this);
					for (int i = 0; i < collection.size(); i++) {
						vulpe.controller().detail(
								detail.getParentDetailConfig().getPropertyName() + "[" + i + "]."
										+ detail.getPropertyName());
						onAddDetail(true);
					}
				} catch (OgnlException e) {
					LOG.error(e);
				}
			} else if (detail.getParentDetailConfig() == null) {
				vulpe.controller().detail(detail.getPropertyName());
				onAddDetail(true);
			}
			if (VulpeValidationUtil.isNotEmpty(detail.getSubDetails())) {
				createDetails(detail.getSubDetails(), true);
			}
		}
	}

	@Override
	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	@Override
	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	@Override
	public HttpSession getSession() {
		return ServletActionContext.getRequest().getSession();
	}

}