/**
 * Vulpe Framework - Copyright 2010 Active Thread
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.anonymous.AnonymousAuthenticationToken;
import org.springframework.security.userdetails.UserDetails;
import org.vulpe.common.Constants;
import org.vulpe.common.beans.AbstractVulpeBeanFactory;
import org.vulpe.common.beans.DownloadInfo;
import org.vulpe.common.cache.VulpeCacheHelper;
import org.vulpe.common.model.services.VulpeServiceLocator;
import org.vulpe.controller.annotations.Controller.ControllerType;
import org.vulpe.controller.common.VulpeBaseSimpleActionConfig;
import org.vulpe.controller.util.ControllerUtil;
import org.vulpe.exception.VulpeSystemException;
import org.vulpe.model.services.Services;

/**
 * Base Simple Controller
 *
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings( { "unchecked" })
public abstract class AbstractVulpeBaseSimpleController implements VulpeBaseSimpleController {

	/**
	 * Calendar
	 */
	private transient final Calendar calendar = Calendar.getInstance();

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
		return ControllerUtil.getInstance().getActionConfig(this);
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
	protected Object invokeServices(final String eventName,
			final String serviceName, final Class<?>[] argsType,
			final Object[] argsValues) {
		final Services service = getService();
		try {
			final Method method = service.getClass().getMethod(serviceName,
					argsType);
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
	protected Services getService() {
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
	protected <T extends Services> T getService(final Class<T> serviceClass) {
		T service = null;
		try {
			service = (T) AbstractVulpeBeanFactory.getInstance().getBean(
					serviceClass.getSimpleName());
		} catch (NoSuchBeanDefinitionException e) {
			if (service == null) {
				service = VulpeServiceLocator.getInstance().getEJB(serviceClass);
			}
		}
		return service;
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

	public boolean isAjax() {
		return ajax;
	}

	public void setAjax(final boolean ajax) {
		this.ajax = ajax;
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

	public void setDownloadContentDisposition(
			final String downloadContentDisposition) {
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
		return (Map<String, Object>) VulpeCacheHelper.getInstance().get(
				Constants.CACHED_CLASS);
	}

	/**
	 * Returns map of cached enumeration
	 *
	 * @return Map with cached enumerations
	 */
	public Map<String, Object> getCachedEnum() {
		return (Map<String, Object>) VulpeCacheHelper.getInstance().get(
				Constants.CACHED_ENUM);
	}

	/**
	 * Returns map of cached enumeration in array to checkbox list
	 *
	 * @return Map with cached enumerations array
	 */
	public Map<String, String> getCachedEnumArray() {
		return (Map<String, String>) VulpeCacheHelper.getInstance().get(
				Constants.CACHED_ENUM_ARRAY);
	}

	/**
	 * Returns form parameters
	 *
	 * @return Map with form parameters
	 */
	public Map getFormParams() {
		final String keyForm = ControllerUtil.getInstance()
				.getCurrentActionName().concat(Constants.PARAMS_SESSION_KEY);
		Map formParams = (Map) ServletActionContext.getRequest().getSession()
				.getAttribute(keyForm);
		if (formParams == null) {
			formParams = new HashMap();
			ServletActionContext.getRequest().getSession().setAttribute(
					keyForm, formParams);
		}
		return formParams;
	}

	/**
	 * Verified if user is authenticated.
	 *
	 * @return true if authenticated
	 */
	public boolean isAuthenticated() {
		final Authentication authentication = SecurityContextHolder
				.getContext().getAuthentication();
		return authentication.isAuthenticated()
				&& !(authentication instanceof AnonymousAuthenticationToken);
	}

	/**
	 * Returns user name.
	 *
	 * @return User Name
	 */
	public String getUserName() {
		final Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		return principal instanceof UserDetails ? ((UserDetails) principal)
				.getUsername() : principal.toString();
	}

	/**
	 * Returns controller type
	 *
	 * @return Controller Type
	 */
	protected ControllerType getControllerType() {
		return getActionConfig().getType();
	}

}