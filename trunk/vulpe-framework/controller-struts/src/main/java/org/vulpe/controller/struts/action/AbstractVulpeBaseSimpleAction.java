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
package org.vulpe.controller.struts.action;

import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ognl.OgnlException;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.vulpe.common.Constants;
import org.vulpe.common.Constants.Security;
import org.vulpe.common.Constants.Action.Forward;
import org.vulpe.common.Constants.Action.URI;
import org.vulpe.common.Constants.View.Layout;
import org.vulpe.common.beans.AbstractVulpeBeanFactory;
import org.vulpe.common.beans.DownloadInfo;
import org.vulpe.common.cache.VulpeCacheHelper;
import org.vulpe.common.file.FileUtil;
import org.vulpe.common.model.services.VulpeServiceLocator;
import org.vulpe.controller.VulpeBaseSimpleController;
import org.vulpe.controller.annotations.ResetSession;
import org.vulpe.controller.annotations.Controller.ControllerType;
import org.vulpe.controller.common.VulpeBaseSimpleActionConfig;
import org.vulpe.controller.struts.util.StrutsControllerUtil;
import org.vulpe.controller.struts.util.StrutsReportUtil;
import org.vulpe.exception.VulpeSystemException;
import org.vulpe.exception.VulpeValidationException;
import org.vulpe.model.entity.VulpeBaseEntity;
import org.vulpe.model.services.Services;
import org.vulpe.security.UserAuthenticated;
import org.vulpe.security.UserAuthentication;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.OgnlUtil;

/**
 * Action base for Struts2
 * 
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings( { "unchecked", "serial" })
public abstract class AbstractVulpeBaseSimpleAction extends ActionSupport implements
		VulpeBaseSimpleController {

	/**
	 *
	 */
	private final OgnlUtil ognlUtil = new OgnlUtil();

	/**
	 * Calendar
	 */
	private final Calendar calendar = Calendar.getInstance();

	/**
	 * Show Title
	 */
	private boolean showTitle = true;

	/**
	 * Method returns current date time of system.
	 * 
	 * @since 1.0
	 * @return VulpeDate.
	 */
	public Date getSystemDate() {
		return calendar.getTime();
	}

	/**
	 * Method returns current year from date of system.
	 * 
	 * @since 1.0
	 * @return Year.
	 */
	public int getCurrentYear() {
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * Method returns current month from date of system.
	 * 
	 * @since 1.0
	 * @return Month.
	 */
	public int getCurrentMonth() {
		return calendar.get(Calendar.MONTH);
	}

	/**
	 * Method returns current day from date of system.
	 * 
	 * @since 1.0
	 * @return Day.
	 */
	public int getCurrentDay() {
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * Return current action configuration.
	 * 
	 * @since 1.0
	 * @return ActionConfig object for current action.
	 */
	public VulpeBaseSimpleActionConfig getActionConfig() {
		return StrutsControllerUtil.getInstance().getActionConfig(this);
	}

	/**
	 * Extension point to read report.
	 * 
	 * @since 1.0
	 */
	protected DownloadInfo doReadReportLoad() {
		try {
			List<VulpeBaseEntity<?>> list = (List<VulpeBaseEntity<?>>) PropertyUtils.getProperty(
					this, getActionConfig().getReportDataSource());
			return StringUtils.isNotBlank(getActionConfig().getReportName()) ? StrutsReportUtil
					.getInstance()
					.getDownloadInfo(list, getActionConfig().getReportFile(),
							getActionConfig().getSubReports(), getActionConfig().getReportFormat(),
							getActionConfig().getReportName(), getActionConfig().isReportDownload())
					: StrutsReportUtil.getInstance().getDownloadInfo(list,
							getActionConfig().getReportFile(), getActionConfig().getSubReports(),
							getActionConfig().getReportFormat());
		} catch (Exception e) {
			throw new VulpeSystemException(e);
		}
	}

	/**
	 * Method to prepare show.
	 * 
	 * @since 1.0
	 * @return Navigation
	 */
	@SkipValidation
	@ResetSession(before = true)
	public String frontend() {
		frontendBefore();
		onFrontend();

		String forward = Forward.SUCCESS;
		if (getActionConfig().getType().equals(ControllerType.FRONTEND)) {
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

	/**
	 * Method to upload file.
	 * 
	 * @since 1.0
	 * @return Navigation.
	 */
	@SkipValidation
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

	/**
	 * Method to download file.
	 * 
	 * @since 1.0
	 * @return Navigation.
	 */
	@SkipValidation
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
	@SuppressWarnings("static-access")
	protected DownloadInfo prepareDownloadInfo() {
		try {
			Object value = null;
			if (getFormParams() != null && getFormParams().containsKey(getDownloadKey())) {
				final Object[] array = (Object[]) getFormParams().get(getDownloadKey());
				value = array[1];
			}
			if (value == null) {
				value = ognlUtil.getValue(getDownloadKey(), ActionContext.getContext()
						.getContextMap(), this);
			}
			final DownloadInfo downloadInfo = FileUtil.getInstance().getDownloadInfo(value,
					getDownloadContentType(), getDownloadContentDisposition());
			if (downloadInfo != null) {
				downloadInfo.setKey(getDownloadKey());
			}
			return downloadInfo;
		} catch (OgnlException e) {
			throw new VulpeSystemException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.ActionSupport#validate()
	 */
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
	 * Method to invoke services.
	 * 
	 * @param eventName
	 *            Name of event
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

	/**
	 * Returns current action service configured.
	 * 
	 * @since 1.0
	 * @return Service Implementation.
	 * @see Services
	 */
	public Services getService() {
		return getService(getActionConfig().getServiceClass());
	}

	/**
	 * Method find specific service returns POJO or EJB implementation.
	 * 
	 * @param serviceClass
	 * @return Service Implementation.
	 * @since 1.0
	 * @see Services
	 */
	public <T extends Services> T getService(final Class<T> serviceClass) {
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
	public void addActionError(final String key, final String... args) {
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
	public void addActionMessage(final String key, final String... args) {
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
		return getText(key, new String[] { getText(arg) });
	}

	public String getTextArg(final String key, final String arg1, final String arg2) {
		return getText(key, new String[] { getText(arg1), getText(arg2) });
	}

	public String getTextArg(final String key, final String arg1, final String arg2,
			final String arg3) {
		return getText(key, new String[] { getText(arg1), getText(arg2), getText(arg3) });
	}

	public String getTextArg(final String key, final String arg1, final String arg2,
			final String arg3, final String arg4) {
		return getText(key, new String[] { getText(arg1), getText(arg2), getText(arg3),
				getText(arg4) });
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

	public boolean isAjax() {
		return ajax;
	}

	public void setAjax(final boolean ajax) {
		this.ajax = ajax;
	}

	public String getResultName() {
		return resultName;
	}

	public void setResultName(final String resultName) {
		this.resultName = resultName;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(final String operation) {
		this.operation = operation;
	}

	public boolean isUploaded() {
		return uploaded;
	}

	public void setUploaded(final boolean uploaded) {
		this.uploaded = uploaded;
	}

	public String getOnHideMessages() {
		return onHideMessages;
	}

	public void setOnHideMessages(final String onHideMessages) {
		this.onHideMessages = onHideMessages;
	}

	public boolean isBack() {
		return back;
	}

	public void setBack(final boolean back) {
		this.back = back;
	}

	public boolean isExecuted() {
		return executed;
	}

	public void setExecuted(final boolean executed) {
		this.executed = executed;
	}

	public String getDownloadKey() {
		return downloadKey;
	}

	public void setDownloadKey(final String downloadKey) {
		this.downloadKey = downloadKey;
	}

	public String getDownloadContentType() {
		return downloadContentType;
	}

	public void setDownloadContentType(final String downloadContentType) {
		this.downloadContentType = downloadContentType;
	}

	public String getDownloadContentDisposition() {
		return downloadContentDisposition;
	}

	public void setDownloadContentDisposition(final String downloadContentDisposition) {
		this.downloadContentDisposition = downloadContentDisposition;
	}

	/**
	 * 
	 * @return
	 */
	public String getPopupKey() {
		return popupKey;
	}

	/**
	 * 
	 * @param popupKey
	 */
	public void setPopupKey(final String popupKey) {
		this.popupKey = popupKey;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isPopup() {
		return StringUtils.isNotEmpty(getPopupKey());
	}

	/**
	 * Returns map of cached class
	 * 
	 * @return Map with cached classes
	 */
	public Map<String, Object> getCachedClass() {
		return (Map<String, Object>) VulpeCacheHelper.getInstance().get(Constants.CACHED_CLASS);
	}

	/**
	 * Returns map of cached enumeration
	 * 
	 * @return Map with cached enumerations
	 */
	public Map<String, Object> getCachedEnum() {
		return (Map<String, Object>) VulpeCacheHelper.getInstance().get(Constants.CACHED_ENUM);
	}

	/**
	 * Returns map of cached enumeration in array to checkbox list
	 * 
	 * @return Map with cached enumerations array
	 */
	public Map<String, String> getCachedEnumArray() {
		return (Map<String, String>) VulpeCacheHelper.getInstance()
				.get(Constants.CACHED_ENUM_ARRAY);
	}

	/**
	 * Returns form parameters
	 * 
	 * @return Map with form parameters
	 */
	public Map getFormParams() {
		final String keyForm = StrutsControllerUtil.getInstance().getCurrentActionName().concat(
				Constants.PARAMS_SESSION_KEY);
		Map formParams = (Map) ServletActionContext.getRequest().getSession().getAttribute(keyForm);
		if (formParams == null) {
			formParams = new HashMap();
			ServletActionContext.getRequest().getSession().setAttribute(keyForm, formParams);
		}
		return formParams;
	}

	/**
	 * Returns current HTTP Session.
	 * 
	 * @return Http Session
	 */
	public HttpSession getSession() {
		return ServletActionContext.getRequest().getSession();
	}

	/**
	 * Returns current HTTP Request.
	 * 
	 * @return Http Servlet Request
	 */
	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	/**
	 * Returns current HTTP Response.
	 * 
	 * @return Http Servlet Reponse
	 */
	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	public void setUrlBack(final String urlBack) {
		getSession().setAttribute(Constants.View.URL_BACK, urlBack);
	}

	public void setLayerUrlBack(final String layerUrlBack) {
		getSession().setAttribute(Constants.View.LAYER_URL_BACK, layerUrlBack);
	}

	/**
	 * Returns controller type
	 * 
	 * @return Controller Type
	 */
	protected ControllerType getControllerType() {
		return getActionConfig().getType();
	}

	/**
	 * Define Result Forward to render normal or AJAX request
	 */
	protected void controlResultForward() {
		setResultForward(Layout.PROTECTED_JSP_COMMON.concat(Layout.UC_JSP));
	}

	public boolean isShowTitle() {
		return showTitle;
	}

	public void setShowTitle(boolean showTitle) {
		this.showTitle = showTitle;
	}

	/**
	 * Verified if user is authenticated.
	 * 
	 * @return true if authenticated
	 */
	public boolean isAuthenticated() {
		final UserAuthentication userAuthentication = AbstractVulpeBeanFactory.getInstance()
				.getBean(UserAuthentication.class.getSimpleName());
		if (userAuthentication != null) {
			return userAuthentication.isAuthenticated();
		}
		return false;
	}

	/**
	 * Returns current authenticated user.
	 * 
	 * @return
	 */
	public UserAuthenticated getUserAuthenticated() {
		UserAuthenticated userAuthenticated = (UserAuthenticated) getSession().getAttribute(
				Security.VULPE_USER_AUTHENTICATED);
		if (userAuthenticated == null) {
			userAuthenticated = AbstractVulpeBeanFactory.getInstance().getBean(
					UserAuthenticated.class.getSimpleName());
			userAuthenticated.getUsername();
			getSession().setAttribute(Security.VULPE_USER_AUTHENTICATED, userAuthenticated);
		}
		return userAuthenticated;
	}

	/**
	 * Returns user name.
	 * 
	 * @return User Name
	 */
	public String getUserName() {
		if (getUserAuthenticated() != null) {
			return getUserAuthenticated().getUserName();
		}
		return "";
	}

}