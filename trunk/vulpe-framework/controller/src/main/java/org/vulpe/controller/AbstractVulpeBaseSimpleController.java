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

import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.vulpe.commons.VulpeConstants;
import org.vulpe.commons.VulpeContext;
import org.vulpe.commons.VulpeServiceLocator;
import org.vulpe.commons.VulpeConstants.Action;
import org.vulpe.commons.VulpeConstants.Action.Forward;
import org.vulpe.commons.VulpeConstants.Configuration.Now;
import org.vulpe.commons.VulpeConstants.View.Layout;
import org.vulpe.commons.beans.DownloadInfo;
import org.vulpe.commons.beans.Tab;
import org.vulpe.commons.factory.AbstractVulpeBeanFactory;
import org.vulpe.commons.helper.VulpeCacheHelper;
import org.vulpe.controller.commons.I18NService;
import org.vulpe.controller.commons.VulpeControllerConfig;
import org.vulpe.controller.commons.VulpeControllerConfig.ControllerType;
import org.vulpe.controller.util.ControllerUtil;
import org.vulpe.exception.VulpeSystemException;
import org.vulpe.model.services.VulpeService;
import org.vulpe.security.context.VulpeSecurityContext;

/**
 * Simple Base Controller implementation.
 *
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings( { "unchecked", "serial" })
public abstract class AbstractVulpeBaseSimpleController implements VulpeSimpleController {

	protected static final Logger LOG = Logger.getLogger(AbstractVulpeBaseSimpleController.class);

	@Autowired
	protected I18NService i18nService;

	@Autowired
	protected VulpeContext vulpeContext;

	/**
	 * Global attributes map
	 */
	public static Map<String, Object> ever = new HashMap<String, Object>();

	/**
	 * Temporal attributes map
	 */
	public Map<String, Object> now = new HashMap<String, Object>();

	/**
	 * Calendar
	 */
	private final Calendar calendar = Calendar.getInstance();

	{
		now.put(Now.SHOW_CONTENT_TITLE, true);
		now.put(Now.SYSTEM_DATE, calendar.getTime());
		now.put(Now.CURRENT_DAY, calendar.get(Calendar.DAY_OF_MONTH));
		now.put(Now.CURRENT_MONTH, calendar.get(Calendar.MONTH));
		now.put(Now.CURRENT_YEAR, calendar.get(Calendar.YEAR));
	}

	@PostConstruct
	protected void loadNow() {
		now.put(Now.CONTROLLER_TYPE, getControllerType());
		now.put(Now.TITLE_KEY, getControllerConfig().getTitleKey());
		now.put(Now.MASTER_TITLE_KEY, getControllerConfig().getMasterTitleKey());
		now.put(Now.FORM_NAME, getControllerConfig().getFormName());
		// now.put(VulpeConstants.SECURITY_CONTEXT, getSecurityContext());
	}

	/**
	 * Extension point to read report.
	 *
	 * @since 1.0
	 */
	protected abstract DownloadInfo doReadReportLoad();

	/*
	 * (non-Javadoc)
	 *
	 * @see org.vulpe.controller.VulpeSimpleController#backend()
	 */
	public String backend() {
		getControllerConfig().setControllerType(ControllerType.BACKEND);
		backendBefore();
		onBackend();

		setResultName(Forward.SUCCESS);
		controlResultForward();

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
	 * @see org.vulpe.controller.VulpeSimpleController#frontend()
	 */
	public String frontend() {
		getControllerConfig().setControllerType(ControllerType.FRONTEND);
		frontendBefore();
		onFrontend();

		setResultName(Forward.SUCCESS);
		controlResultForward();

		frontendAfter();
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

	/*
	 * (non-Javadoc)
	 *
	 * @see org.vulpe.controller.VulpeSimpleController#invokeServices(java.lang
	 * .String, java.lang.String, java.lang.Class<?>[], java.lang.Object[])
	 */
	public Object invokeServices(final String eventName, final String serviceName,
			final Class<?>[] argsType, final Object[] argsValues) {
		final VulpeService service = getService();
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

	private boolean uploaded;
	/**
	 * URL Redirect.
	 */
	private String urlRedirect;

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

	/*
	 * (non-Javadoc)
	 *
	 * @see org.vulpe.controller.VulpeSimpleController#getOperation()
	 */
	public String getOperation() {
		return operation;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.vulpe.controller.VulpeSimpleController#setOperation(java.lang
	 * .String)
	 */
	public void setOperation(final String operation) {
		this.operation = operation;
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

	/*
	 * (non-Javadoc)
	 *
	 * @see org.vulpe.controller.VulpeSimpleController#getCachedClass()
	 */
	public Map<String, Object> getCachedClass() {
		return (Map<String, Object>) VulpeCacheHelper.getInstance()
				.get(VulpeConstants.CACHED_CLASS);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.vulpe.controller.VulpeSimpleController#getCachedEnum()
	 */
	public Map<String, Object> getCachedEnum() {
		return (Map<String, Object>) VulpeCacheHelper.getInstance().get(VulpeConstants.CACHED_ENUM);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.vulpe.controller.VulpeSimpleController#getCachedEnumArray()
	 */
	public Map<String, String> getCachedEnumArray() {
		return (Map<String, String>) VulpeCacheHelper.getInstance().get(
				VulpeConstants.CACHED_ENUM_ARRAY);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.vulpe.controller.VulpeSimpleController#getSession()
	 */
	public HttpSession getSession() {
		return getRequest().getSession();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.vulpe.controller.VulpeSimpleController#getRequest()
	 */
	public HttpServletRequest getRequest() {
		return vulpeContext.getRequest();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.vulpe.controller.VulpeSimpleController#getResponse()
	 */
	public HttpServletResponse getResponse() {
		return vulpeContext.getResponse();
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

	/*
	 * (non-Javadoc)
	 *
	 * @see org.vulpe.controller.VulpeSimpleController#getControllerConfig()
	 */
	public VulpeControllerConfig getControllerConfig() {
		return getControllerUtil().getControllerConfig(this);
	}

	/**
	 *
	 * @return
	 */
	public ControllerUtil getControllerUtil() {
		return ControllerUtil.getInstance(getRequest());
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
		VulpeSecurityContext securityContext = getSessionAttribute(VulpeConstants.SECURITY_CONTEXT);
		if (securityContext == null) {
			securityContext = getBean(VulpeSecurityContext.class);
			setSessionAttribute(VulpeConstants.SECURITY_CONTEXT, securityContext);
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
		return i18nService.getText(key);
	}

	public String getText(final String key, final Object... args) {
		return i18nService.getText(key, args);
	}

	public abstract void addActionMessage(final String message);

	public abstract void addActionError(final String message);

	public Map<String, Tab> getTabs() {
		if (now.containsKey("tabs")) {
			return (Map<String, Tab>) now.get("tabs");
		}
		final Map<String, Tab> tabs = new HashMap<String, Tab>();
		now.put("tabs", tabs);
		return tabs;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.vulpe.controller.VulpeSimpleController#getSelectFormKey()
	 */
	public String getSelectFormKey() {
		return getControllerKey() + Action.SELECT_FORM;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.vulpe.controller.VulpeSimpleController#getSelectTableKey()
	 */
	public String getSelectTableKey() {
		return getControllerKey() + Action.SELECT_TABLE;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.vulpe.controller.VulpeSimpleController#getSelectPagingKey()
	 */
	public String getSelectPagingKey() {
		return getControllerKey() + Action.SELECT_PAGING;
	}

	private String getControllerKey() {
		String key = getControllerUtil().getCurrentControllerKey();
		if (StringUtils.isNotEmpty(getControllerConfig().getViewBaseName())) {
			key = key.substring(0, key.lastIndexOf(".") + 1)
					+ getControllerConfig().getViewBaseName();
		}
		return key;
	}

	public static Map<String, Object> getEver() {
		return ever;
	}

	protected void changeControllerType(final ControllerType controllerType) {
		getControllerConfig().setControllerType(controllerType);
		loadNow();
	}

	public void setUrlRedirect(String urlRedirect) {
		this.urlRedirect = urlRedirect;
	}

	public String getUrlRedirect() {
		return urlRedirect;
	}

	protected String redirectTo(final String url, final boolean ajax) {
		setUrlRedirect(url + (ajax ? "/ajax" : ""));
		return Forward.REDIRECT;
	}

	protected String redirectTo(final String url) {
		return redirectTo(url, isAjax());
	}

}