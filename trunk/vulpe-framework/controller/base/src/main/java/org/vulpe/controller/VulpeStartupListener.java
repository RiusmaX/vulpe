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
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.jsp.PageContext;

import org.apache.log4j.Logger;
import org.vulpe.commons.VulpeConstants;
import org.vulpe.commons.VulpeConstants.Context;
import org.vulpe.commons.VulpeConstants.Configuration.Global;
import org.vulpe.commons.VulpeConstants.Configuration.Global.Mobile;
import org.vulpe.commons.factory.AbstractVulpeBeanFactory;
import org.vulpe.commons.helper.VulpeCacheHelper;
import org.vulpe.commons.helper.VulpeConfigHelper;
import org.vulpe.commons.util.VulpeDB4OUtil;
import org.vulpe.config.annotations.VulpeDomains;
import org.vulpe.config.annotations.VulpeProject;
import org.vulpe.controller.helper.VulpeCachedObjectsHelper;
import org.vulpe.controller.helper.VulpeJobSchedulerHelper;
import org.vulpe.security.context.VulpeSecurityContext;

/**
 * Class to manager startup of application.
 *
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 */
public class VulpeStartupListener implements ServletContextListener {

	private static final Logger LOG = Logger.getLogger(VulpeStartupListener.class);

	/**
	 * Global map
	 */
	public Map<String, Object> global = new HashMap<String, Object>();

	/*
	 * (non-Javadoc)
	 *
	 * @seejavax.servlet.ServletContextListener#contextDestroyed(javax.servlet.
	 * ServletContextEvent)
	 */
	public void contextDestroyed(final ServletContextEvent evt) {
		LOG.debug("Entering in Context Detroyed");
		if (VulpeConfigHelper.get(VulpeDomains.class).useDB4O()) {
			VulpeDB4OUtil.getInstance().shutdown();
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * javax.servlet.ServletContextListener#contextInitialized(javax.servlet
	 * .ServletContextEvent)
	 */
	public void contextInitialized(final ServletContextEvent evt) {
		LOG.debug("Entering in Context Initialized");
		if (VulpeConfigHelper.get(VulpeDomains.class).useDB4O()) {
			VulpeDB4OUtil.getInstance().getObjectServer();
			if (VulpeConfigHelper.isSecurityEnabled()) {
				final VulpeSecurityContext vulpeSecurityContext = AbstractVulpeBeanFactory.getInstance().getBean(
						VulpeSecurityContext.class.getSimpleName());
				if (vulpeSecurityContext != null) {
					vulpeSecurityContext.initialize();
				}
			}
		}

		// sets scopes as attributes to use in tags and JSPs
		evt.getServletContext().setAttribute(Context.APPLICATION_SCOPE, Integer.valueOf(PageContext.APPLICATION_SCOPE));
		evt.getServletContext().setAttribute(Context.PAGE_SCOPE, Integer.valueOf(PageContext.PAGE_SCOPE));
		evt.getServletContext().setAttribute(Context.REQUEST_SCOPE, Integer.valueOf(PageContext.REQUEST_SCOPE));
		evt.getServletContext().setAttribute(Context.SESSION_SCOPE, Integer.valueOf(PageContext.SESSION_SCOPE));

		// sets attributes to configure application
		final VulpeProject vulpeProject = VulpeConfigHelper.get(VulpeProject.class);
		global.put(Global.DEBUG, vulpeProject.debug());
		global.put(Global.I18N_MANAGER, vulpeProject.i18nManager());
		global.put(Global.AUDIT_ENABLED, VulpeConfigHelper.isAuditEnabled());
		global.put(Global.SECURITY_ENABLED, VulpeConfigHelper.isSecurityEnabled());
		global.put(Global.USE_DB4O, VulpeConfigHelper.get(VulpeDomains.class).useDB4O());

		global.put(Global.THEME, VulpeConfigHelper.getTheme());
		global.put(Global.FRONTEND_MENU_TYPE, vulpeProject.frontendMenuType());
		global.put(Global.BACKEND_MENU_TYPE, vulpeProject.backendMenuType());

		if (vulpeProject.view() != null) {
			global.put(Global.BREAK_LABEL, vulpeProject.view().breakLabel());
			global.put(Global.FOCUS_FIRST, vulpeProject.view().focusFirst());
			global.put(Global.ICON_HEIGHT, vulpeProject.view().iconHeight());
			global.put(Global.JQUERYUI, vulpeProject.view().jQueryUI());
			global.put(Global.MOBILE_ICON_HEIGHT, vulpeProject.view().mobileIconHeight());
			global.put(Global.MESSAGE_SLIDE_UP, vulpeProject.view().messageSlideUp());
			global.put(Global.MESSAGE_SLIDE_UP_TIME, vulpeProject.view().messageSlideUpTime());
			global.put(Global.PAGING_STYLE, vulpeProject.view().pagingStyle());
			global.put(Global.PAGING_BUTTON_STYLE, vulpeProject.view().pagingButtonStyle());
			global.put(Global.SHOW_BUTTONS_AS_IMAGE, vulpeProject.view().showButtonsAsImage());
			global.put(Global.SHOW_ICON_OF_BUTTON, vulpeProject.view().showIconOfButton());
			global.put(Global.SHOW_TEXT_OF_BUTTON, vulpeProject.view().showTextOfButton());
			global.put(Global.SHOW_BUTTON_DELETE_THIS, vulpeProject.view().showButtonDeleteThis());
			global.put(Global.SHOW_BUTTON_UPDATE, vulpeProject.view().showButtonUpdate());
			global.put(Global.SHOW_BUTTONS_DELETE, vulpeProject.view().showButtonsDelete());
			global.put(Global.SHOW_LINE, vulpeProject.view().showLine());
			global.put(Global.SHOW_COPYRIGHT, vulpeProject.view().showCopyright());
			global.put(Global.SHOW_MODIFICATION_WARNING, vulpeProject.view().showModificationWarning());
			global.put(Global.SHOW_POWERED_BY, vulpeProject.view().showPoweredBy());
			global.put(Global.SHOW_WARNING_BEFORE_CLEAR, vulpeProject.view().showWarningBeforeClear());
			global.put(Global.SHOW_WARNING_BEFORE_DELETE, vulpeProject.view().showWarningBeforeDelete());
			global.put(Global.SHOW_WARNING_BEFORE_UPDATE_POST, vulpeProject.view().showWarningBeforeUpdatePost());
			global.put(Global.SORT_TYPE, vulpeProject.view().sortType());
			global.put(Global.ICON_WIDTH, vulpeProject.view().iconWidth());
			global.put(Global.MOBILE_ICON_WIDTH, vulpeProject.view().mobileIconWidth());
			global.put(Global.USE_BACKEND_LAYER, vulpeProject.view().useBackendLayer());
			global.put(Global.USE_FRONTEND_LAYER, vulpeProject.view().useFrontendLayer());
		}
		global.put(Global.SHOW_AS_MOBILE, vulpeProject.mobileEnabled());
		if (vulpeProject.mobileEnabled()) {
			global.put(Mobile.VIEWPORT_WIDHT, vulpeProject.mobile().viewportWidth());
			global.put(Mobile.VIEWPORT_HEIGHT, vulpeProject.mobile().viewportHeight());
			global.put(Mobile.VIEWPORT_USER_SCALABLE, vulpeProject.mobile().viewportUserScalable());
			global.put(Mobile.VIEWPORT_INITIAL_SCALE, vulpeProject.mobile().viewportInitialScale());
			global.put(Mobile.VIEWPORT_MAXIMUM_SCALE, vulpeProject.mobile().viewportMaximumScale());
			global.put(Mobile.VIEWPORT_MINIMUM_SCALE, vulpeProject.mobile().viewportMinimumScale());
		}
		global.put(Global.USE_DB4O, VulpeConfigHelper.get(VulpeDomains.class).useDB4O());
		evt.getServletContext().setAttribute(Context.GLOBAL, global);
		VulpeCachedObjectsHelper.putAnnotedObjectsInCache(evt.getServletContext());
		VulpeJobSchedulerHelper.schedulerAnnotedJobs(evt.getServletContext());
		loadControllerMethods();
	}

	private void loadControllerMethods() {
		final Map<String, String> mapControllerMethods = new HashMap<String, String>();
		for (Method method : VulpeController.class.getMethods()) {
			mapControllerMethods.put(method.getName(), method.getName());
		}
		VulpeCacheHelper.getInstance().put(VulpeConstants.CONTROLLER_METHODS, mapControllerMethods);
	}

}