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
package org.vulpe.controller.vraptor.action;

import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.vulpe.commons.VulpeConstants;
import org.vulpe.commons.VulpeConstants.Security;
import org.vulpe.commons.VulpeConstants.Action.Forward;
import org.vulpe.commons.VulpeConstants.Action.URI;
import org.vulpe.commons.VulpeConstants.View.Layout;
import org.vulpe.commons.beans.AbstractVulpeBeanFactory;
import org.vulpe.commons.beans.DownloadInfo;
import org.vulpe.commons.cache.VulpeCacheHelper;
import org.vulpe.commons.file.FileUtil;
import org.vulpe.commons.model.services.VulpeServiceLocator;
import org.vulpe.controller.VulpeBaseSimpleController;
import org.vulpe.controller.annotations.Controller.ControllerType;
import org.vulpe.controller.commons.I18NService;
import org.vulpe.controller.commons.VulpeActionConfig;
import org.vulpe.controller.util.ControllerUtil;
import org.vulpe.controller.vraptor.util.VRaptorControllerUtil;
import org.vulpe.exception.VulpeSystemException;
import org.vulpe.model.services.Services;
import org.vulpe.security.authentication.callback.UserAuthenticatedCallback;
import org.vulpe.security.authentication.callback.UserAuthenticationCallback;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.core.RequestInfo;

/**
 * Action base for VRaptor
 * 
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings("unchecked")
public abstract class AbstractVulpeBaseSimpleAction implements VulpeBaseSimpleController {

	protected static final Logger LOG = Logger.getLogger(AbstractVulpeBaseSimpleAction.class);

	@Autowired
	protected RequestInfo requestInfo;
	@Autowired
	protected Result result;
	@Autowired
	protected Validator validator;
	protected I18NService i18nService = new I18NService();
	
	/**
	 * Calendar
	 */
	private final Calendar calendar = Calendar.getInstance();

	/**
	 * Show Title
	 */
	private boolean showTitle = true;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeBaseSimpleController#getSystemDate()
	 */
	public Date getSystemDate() {
		return calendar.getTime();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeBaseSimpleController#getCurrentYear()
	 */
	public int getCurrentYear() {
		return calendar.get(Calendar.YEAR);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeBaseSimpleController#getCurrentMonth()
	 */
	public int getCurrentMonth() {
		return calendar.get(Calendar.MONTH);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeBaseSimpleController#getCurrentDay()
	 */
	public int getCurrentDay() {
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeBaseSimpleController#getActionConfig()
	 */
	public VulpeActionConfig getActionConfig() {
		return getControllerUtil().getActionConfig(this);
	}

	public ControllerUtil getControllerUtil() {
		return VRaptorControllerUtil.getInstance(requestInfo);
	}

	/**
	 * Extension point to read report.
	 * 
	 * @since 1.0
	 */
	protected DownloadInfo doReadReportLoad() {
		try {
			// List<VulpeBaseEntity<?>> list = (List<VulpeBaseEntity<?>>)
			// PropertyUtils.getProperty(
			// this, getActionConfig().getReportDataSource());
			// return StringUtils.isNotBlank(getActionConfig().getReportName())
			// ? StrutsReportUtil
			// .getInstance()
			// .getDownloadInfo(list, getActionConfig().getReportFile(),
			// getActionConfig().getSubReports(),
			// getActionConfig().getReportFormat(),
			// getActionConfig().getReportName(),
			// getActionConfig().isReportDownload())
			// : StrutsReportUtil.getInstance().getDownloadInfo(list,
			// getActionConfig().getReportFile(),
			// getActionConfig().getSubReports(),
			// getActionConfig().getReportFormat());
			return null;
		} catch (Exception e) {
			throw new VulpeSystemException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeBaseSimpleController#backend()
	 */
	public String backend() {
		backendBefore();
		onBackend();

		String forward = Forward.SUCCESS;
		if (getControllerType().equals(ControllerType.BACKEND)) {
			if (isAjax()) {
				setResultForward(getActionConfig().getPrimitiveActionName().concat(URI.AJAX));
				forward = Forward.BACKEND;
			} else {
				controlResultForward();
			}
		} else {
			controlResultForward();
		}
		setResultName(forward);

		backendAfter();
		return getResultName();
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
	 * @see org.vulpe.controller.VulpeBaseSimpleController#frontend()
	 */
	public String frontend() {
		frontendBefore();
		onFrontend();

		String forward = Forward.SUCCESS;
		if (getControllerType().equals(ControllerType.FRONTEND)) {
			if (isAjax()) {
				setResultForward(getActionConfig().getPrimitiveActionName().concat(URI.AJAX));
				forward = Forward.FRONTEND;
			} else {
				controlResultForward();
			}
		} else {
			controlResultForward();
		}
		setResultName(forward);

		frontendAfter();
		result.include("actionConfig", getActionConfig());
		return getResultName();
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeBaseSimpleController#upload()
	 */
	public String upload() {
		uploadBefore();
		onUpload();

		setResultName(Forward.UPLOAD);

		uploadAfter();
		return getResultName();
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
	 * @see org.vulpe.controller.VulpeBaseSimpleController#download()
	 */
	public String download() {
		downloadBefore();
		onDownload();

		setResultName(Forward.DOWNLOAD);

		downloadAfter();
		return getResultName();
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
	 * Extension point to prepare download.
	 * 
	 * @since 1.0
	 */
	protected DownloadInfo prepareDownloadInfo() {
		try {
			Object value = null;
			// if (getFormParams() != null &&
			// getFormParams().containsKey(getDownloadKey())) {
			// final Object[] array = (Object[])
			// getFormParams().get(getDownloadKey());
			// value = array[1];
			// }
			if (value == null) {
				// value = ognlUtil.getValue(getDownloadKey(),
				// ActionContext.getContext()
				// .getContextMap(), this);
			}
			final DownloadInfo downloadInfo = FileUtil.getInstance().getDownloadInfo(value,
					getDownloadContentType(), getDownloadContentDisposition());
			if (downloadInfo != null) {
				downloadInfo.setKey(getDownloadKey());
			}
			return downloadInfo;
		} catch (Exception e) {
			throw new VulpeSystemException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.VulpeBaseSimpleController#invokeServices(java.lang
	 * .String, java.lang.String, java.lang.Class<?>[], java.lang.Object[])
	 */
	public Object invokeServices(final String eventName, final String serviceName,
			final Class<?>[] argsType, final Object[] argsValues) {
		final Services service = getService();
		try {
			final Method method = service.getClass().getMethod(serviceName, argsType);
			return method.invoke(service, argsValues);
		} catch (Exception e) {
			throw new VulpeSystemException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeBaseSimpleController#getService()
	 */
	public Services getService() {
		return getService(getActionConfig().getServiceClass());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.VulpeBaseSimpleController#getService(java.lang.Class
	 * )
	 */
	public <T extends Services> T getService(final Class<T> serviceClass) {
		return VulpeServiceLocator.getInstance().getService(serviceClass);
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

	private boolean uploaded;
	/**
	 * Result Forward.
	 */
	private String resultForward;
	/**
	 * Result Name.
	 */
	private String resultName;
	/**
	 * Operation
	 */
	private String operation;
	private boolean ajax = false;
	private boolean back = false;
	private boolean executed = false;
	/**
	 * Popup Key
	 */
	private String popupKey;
	/**
	 *
	 */
	private String onHideMessages;
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
	 * Method retrieve forward.
	 * 
	 * @since 1.0
	 * @return Result Forward.
	 */
	public String getResultForward() {
		return resultForward;
	}

	public void setResultForward(final String resultForward) {
		this.resultForward = resultForward;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeBaseSimpleController#isAjax()
	 */
	public boolean isAjax() {
		return ajax;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeBaseSimpleController#setAjax(boolean)
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeBaseSimpleController#getOperation()
	 */
	public String getOperation() {
		return operation;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.VulpeBaseSimpleController#setOperation(java.lang
	 * .String)
	 */
	public void setOperation(final String operation) {
		this.operation = operation;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeBaseSimpleController#isUploaded()
	 */
	public boolean isUploaded() {
		return uploaded;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeBaseSimpleController#setUploaded(boolean)
	 */
	public void setUploaded(final boolean uploaded) {
		this.uploaded = uploaded;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeBaseSimpleController#getOnHideMessages()
	 */
	public String getOnHideMessages() {
		return onHideMessages;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.VulpeBaseSimpleController#setOnHideMessages(java
	 * .lang.String)
	 */
	public void setOnHideMessages(final String onHideMessages) {
		this.onHideMessages = onHideMessages;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeBaseSimpleController#isBack()
	 */
	public boolean isBack() {
		return back;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeBaseSimpleController#setBack(boolean)
	 */
	public void setBack(final boolean back) {
		this.back = back;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeBaseSimpleController#isExecuted()
	 */
	public boolean isExecuted() {
		return executed;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeBaseSimpleController#setExecuted(boolean)
	 */
	public void setExecuted(final boolean executed) {
		this.executed = executed;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeBaseSimpleController#getDownloadKey()
	 */
	public String getDownloadKey() {
		return downloadKey;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.VulpeBaseSimpleController#setDownloadKey(java.lang
	 * .String)
	 */
	public void setDownloadKey(final String downloadKey) {
		this.downloadKey = downloadKey;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.VulpeBaseSimpleController#getDownloadContentType()
	 */
	public String getDownloadContentType() {
		return downloadContentType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.VulpeBaseSimpleController#setDownloadContentType
	 * (java.lang.String)
	 */
	public void setDownloadContentType(final String downloadContentType) {
		this.downloadContentType = downloadContentType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.VulpeBaseSimpleController#getDownloadContentDisposition
	 * ()
	 */
	public String getDownloadContentDisposition() {
		return downloadContentDisposition;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.VulpeBaseSimpleController#setDownloadContentDisposition
	 * (java.lang.String)
	 */
	public void setDownloadContentDisposition(final String downloadContentDisposition) {
		this.downloadContentDisposition = downloadContentDisposition;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeBaseSimpleController#getPopupKey()
	 */
	public String getPopupKey() {
		return popupKey;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.VulpeBaseSimpleController#setPopupKey(java.lang.
	 * String)
	 */
	public void setPopupKey(final String popupKey) {
		this.popupKey = popupKey;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeBaseSimpleController#isPopup()
	 */
	public boolean isPopup() {
		return StringUtils.isNotEmpty(getPopupKey());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeBaseSimpleController#getCachedClass()
	 */
	public Map<String, Object> getCachedClass() {
		return (Map<String, Object>) VulpeCacheHelper.getInstance()
				.get(VulpeConstants.CACHED_CLASS);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeBaseSimpleController#getCachedEnum()
	 */
	public Map<String, Object> getCachedEnum() {
		return (Map<String, Object>) VulpeCacheHelper.getInstance().get(VulpeConstants.CACHED_ENUM);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeBaseSimpleController#getCachedEnumArray()
	 */
	public Map<String, String> getCachedEnumArray() {
		return (Map<String, String>) VulpeCacheHelper.getInstance().get(
				VulpeConstants.CACHED_ENUM_ARRAY);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.VulpeBaseSimpleController#setUrlBack(java.lang.String
	 * )
	 */
	public void setUrlBack(final String urlBack) {
		// getSession().setAttribute(VulpeConstants.View.URL_BACK, urlBack);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.VulpeBaseSimpleController#setLayerUrlBack(java.lang
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
		return ControllerType.valueOf(getActionConfig().getType());
	}

	/**
	 * Define Result Forward to render normal or AJAX request
	 */
	protected void controlResultForward() {
		setResultForward(Layout.PROTECTED_JSP_COMMONS.concat(Layout.BODY_JSP));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeBaseSimpleController#isShowTitle()
	 */
	public boolean isShowTitle() {
		return showTitle;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeBaseSimpleController#setShowTitle(boolean)
	 */
	public void setShowTitle(boolean showTitle) {
		this.showTitle = showTitle;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeBaseSimpleController#isAuthenticated()
	 */
	public boolean isAuthenticated() {
		UserAuthenticationCallback userAuthenticationCallback = getSessionAttribute(Security.VULPE_USER_AUTHENTICATION);
		if (userAuthenticationCallback == null) {
			userAuthenticationCallback = getBean(UserAuthenticationCallback.class);
			if (userAuthenticationCallback != null) {
				final boolean authenticated = userAuthenticationCallback.isAuthenticated();
				if (authenticated) {
					getSession().setAttribute(Security.VULPE_USER_AUTHENTICATION,
							userAuthenticationCallback);
				} else {
					getSession().removeAttribute(Security.VULPE_USER_AUTHENTICATION);
				}
				return authenticated;
			}
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.VulpeBaseSimpleController#getUserAuthenticatedCallback
	 * ()
	 */
	public UserAuthenticatedCallback getUserAuthenticatedCallback() {
		UserAuthenticatedCallback userAuthenticatedCallback = getSessionAttribute(Security.VULPE_USER_AUTHENTICATED);
		if (userAuthenticatedCallback == null) {
			userAuthenticatedCallback = getBean(UserAuthenticatedCallback.class);
			userAuthenticatedCallback.execute();
			getSession().setAttribute(Security.VULPE_USER_AUTHENTICATED, userAuthenticatedCallback);
		}
		return userAuthenticatedCallback;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeBaseSimpleController#getUserName()
	 */
	public String getUserName() {
		if (getUserAuthenticatedCallback() != null) {
			return getUserAuthenticatedCallback().getName();
		}
		return "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.VulpeBaseSimpleController#getBean(java.lang.String)
	 */
	public <T> T getBean(final String beanName) {
		return (T) AbstractVulpeBeanFactory.getInstance().getBean(beanName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.VulpeBaseSimpleController#getBean(java.lang.Class)
	 */
	public <T> T getBean(final Class<T> clazz) {
		return (T) getBean(clazz.getSimpleName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.VulpeBaseSimpleController#getSessionAttribute(java
	 * .lang.String)
	 */
	public <T> T getSessionAttribute(final String attributeName) {
		return (T) getSession().getAttribute(attributeName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.VulpeBaseSimpleController#getRequestAttribute(java
	 * .lang.String)
	 */
	public <T> T getRequestAttribute(final String attributeName) {
		return (T) getRequest().getAttribute(attributeName);
	}

	/**
	 * Retrieves current HTTP Session.
	 * 
	 * @return Http Session
	 */
	public HttpSession getSession() {
		return requestInfo.getRequest().getSession();
	}

	/**
	 * Retrieves current HTTP Request.
	 * 
	 * @return Http Servlet Request
	 */
	public HttpServletRequest getRequest() {
		return requestInfo.getRequest();
	}

	/**
	 * Retrieves current HTTP Response.
	 * 
	 * @return Http Servlet Reponse
	 */
	public HttpServletResponse getResponse() {
		return requestInfo.getResponse();
	}
	
	public String getText(final String key) {
		return i18nService.getText(key);
	}

	public void addActionMessage(final String message) {
		result.include("notice", message);
	}

	public void addActionError(final String message) {
		result.include("notice", message);
	}
	
	public void addActionError(final String key, final Object...args) {
		result.include("notice", getText(key));
	}

}