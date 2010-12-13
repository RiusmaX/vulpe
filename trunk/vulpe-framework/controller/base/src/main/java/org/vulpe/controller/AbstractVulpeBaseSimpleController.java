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
import org.vulpe.commons.VulpeConstants.Controller;
import org.vulpe.commons.VulpeConstants.Security;
import org.vulpe.commons.VulpeConstants.Configuration.Ever;
import org.vulpe.commons.VulpeConstants.Configuration.Now;
import org.vulpe.commons.VulpeConstants.Controller.Forward;
import org.vulpe.commons.VulpeConstants.View.Layout;
import org.vulpe.commons.beans.Tab;
import org.vulpe.commons.factory.AbstractVulpeBeanFactory;
import org.vulpe.commons.helper.VulpeCacheHelper;
import org.vulpe.commons.util.EverParameter;
import org.vulpe.commons.util.VulpeHashMap;
import org.vulpe.controller.commons.I18NService;
import org.vulpe.controller.commons.VulpeControllerConfig;
import org.vulpe.controller.commons.VulpeControllerConfig.ControllerType;
import org.vulpe.controller.util.ControllerUtil;
import org.vulpe.exception.VulpeSystemException;
import org.vulpe.model.services.VulpeService;
import org.vulpe.security.context.VulpeSecurityContext;
import org.vulpe.view.annotations.View;

/**
 * Simple Base Controller implementation.
 *
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
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
	public EverParameter ever = null;

	/**
	 * Temporal attributes map
	 */
	public VulpeHashMap<String, Object> now = new VulpeHashMap<String, Object>();

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

	/**
	 * Used to set and/or initialize variables in the controller. On this point
	 * the components controlled by Spring are now available.
	 */
	@PostConstruct
	protected void postConstruct() {
		ever = getSessionAttribute(Ever.class.getName());
		if (ever == null) {
			ever = new EverParameter();
		}
		now.put(Now.CONTROLLER_TYPE, getControllerType());
		now.put(Now.TITLE_KEY, getControllerConfig().getTitleKey());
		now.put(Now.MASTER_TITLE_KEY, getControllerConfig().getMasterTitleKey());
		now.put(Now.FORM_NAME, getControllerConfig().getFormName());
		if (getControllerConfig().isRequireOneFilter()) {
			now.put(Now.REQUIRE_ONE_FILTER, true);
		}
		final View view = this.getClass().getAnnotation(View.class);
		if (view != null) {
			now.put(Now.FIELD_TO_FOCUS, view.fieldToFocus());
		}
		// now.put(VulpeConstants.SECURITY_CONTEXT, getSecurityContext());
	}

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
	public Object invokeServices(final String serviceName, final Class<?>[] argsType, final Object[] argsValues) {
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

	public String getTextArg(final String key, final String arg) {
		return getText(key, getText(arg));
	}

	public String getTextArg(final String key, final String arg1, final String arg2) {
		return getText(key, getText(arg1), getText(arg2));
	}

	public String getTextArg(final String key, final String arg1, final String arg2, final String arg3) {
		return getText(key, getText(arg1), getText(arg2), getText(arg3));
	}

	public String getTextArg(final String key, final String arg1, final String arg2, final String arg3,
			final String arg4) {
		return getText(key, getText(arg1), getText(arg2), getText(arg3), getText(arg4));
	}

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
	private String resultName = Forward.SUCCESS;
	/**
	 * Operation
	 */
	private Operation operation;
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

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(final Operation operation) {
		this.operation = operation;
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

	public VulpeHashMap<String, Object> getCachedClass() {
		return VulpeCacheHelper.getInstance().get(VulpeConstants.CACHED_CLASS);
	}

	public VulpeHashMap<String, Object> getCachedEnum() {
		return VulpeCacheHelper.getInstance().get(VulpeConstants.CACHED_ENUM);
	}

	public VulpeHashMap<String, Object> getCachedEnumArray() {
		return VulpeCacheHelper.getInstance().get(VulpeConstants.CACHED_ENUM_ARRAY);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.vulpe.controller.VulpeSimpleController#getSession()
	 */
	public HttpSession getSession() {
		return vulpeContext.getSession();
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

	public void setControllerType(ControllerType controllerType) {
		getControllerConfig().setControllerType(controllerType);
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
		return ControllerUtil.getInstance();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.vulpe.controller.VulpeSimpleController#controlResultForward()
	 */
	public void controlResultForward() {
		setResultForward(getControllerType().equals(ControllerType.TWICE) ? Layout.PROTECTED_JSP_COMMONS
				.concat(Layout.BODY_TWICE_JSP) : Layout.PROTECTED_JSP_COMMONS.concat(Layout.BODY_JSP));
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
		VulpeSecurityContext securityContext = getSessionAttribute(Security.SECURITY_CONTEXT);
		if (securityContext == null) {
			securityContext = getBean(VulpeSecurityContext.class);
			setSessionAttribute(Security.SECURITY_CONTEXT, securityContext);
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
		return getSecurityContext() == null ? false : getSecurityContext().getUserRoles().contains(roleName.toString());
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

	public VulpeHashMap<String, Tab> getTabs() {
		if (now.containsKey(Controller.TABS)) {
			return now.getSelf(Controller.TABS);
		}
		final VulpeHashMap<String, Tab> tabs = new VulpeHashMap<String, Tab>();
		now.put(Controller.TABS, tabs);
		return tabs;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.vulpe.controller.VulpeSimpleController#getSelectFormKey()
	 */
	public String getSelectFormKey() {
		return getControllerKey() + Controller.SELECT_FORM;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.vulpe.controller.VulpeSimpleController#getSelectTableKey()
	 */
	public String getSelectTableKey() {
		return getControllerKey() + Controller.SELECT_TABLE;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.vulpe.controller.VulpeSimpleController#getSelectPagingKey()
	 */
	public String getSelectPagingKey() {
		return getControllerKey() + Controller.SELECT_PAGING;
	}

	private String getControllerKey() {
		String key = getControllerUtil().getCurrentControllerKey();
		if (StringUtils.isNotEmpty(getControllerConfig().getViewBaseName())) {
			key = key.substring(0, key.lastIndexOf(".") + 1) + getControllerConfig().getViewBaseName();
		}
		return key;
	}

	protected void changeControllerType(final ControllerType controllerType) {
		getControllerConfig().setControllerType(controllerType);
		postConstruct();
	}

	public void setUrlRedirect(String urlRedirect) {
		this.urlRedirect = urlRedirect;
	}

	public String getUrlRedirect() {
		return urlRedirect;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.vulpe.controller.VulpeSimpleController#redirectTo(java.lang.String,
	 * boolean)
	 */
	public String redirectTo(final String url, final boolean ajax) {
		setUrlRedirect(url + (ajax ? "/ajax" : ""));
		setResultName(Forward.REDIRECT);
		return getResultName();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.vulpe.controller.VulpeSimpleController#redirectTo(java.lang.String)
	 */
	public String redirectTo(final String url) {
		return redirectTo(url, isAjax());
	}

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
			final String directory = getControllerConfig().getModuleName() + "/"
					+ getControllerConfig().getSimpleControllerName() + "/";
			path.append(directory);
			path.append(page).append(Layout.SUFFIX_JSP);
		} else {
			path.append(page);
		}
		setResultForward(path.toString());
	}

}