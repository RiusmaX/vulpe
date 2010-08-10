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

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.jsp.PageContext;

import org.apache.log4j.Logger;
import org.vulpe.commons.VulpeConstants.Context;
import org.vulpe.commons.VulpeConstants.Configuration.Global;
import org.vulpe.commons.VulpeConstants.Configuration.Global.Mobile;
import org.vulpe.commons.factory.AbstractVulpeBeanFactory;
import org.vulpe.commons.helper.VulpeConfigHelper;
import org.vulpe.commons.util.VulpeDB4OUtil;
import org.vulpe.config.annotations.VulpeDomains;
import org.vulpe.config.annotations.VulpeProject;
import org.vulpe.controller.helper.CachedObjectsHelper;
import org.vulpe.security.context.VulpeSecurityContext;

/**
 * Class to manager startup of application.
 *
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
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
				final VulpeSecurityContext vulpeSecurityContext = AbstractVulpeBeanFactory
						.getInstance().getBean(VulpeSecurityContext.class.getSimpleName());
				if (vulpeSecurityContext != null) {
					vulpeSecurityContext.initialize();
				}
			}
		}

		// sets scopes as attributes to use in tags and JSPs
		evt.getServletContext().setAttribute(Context.APPLICATION_SCOPE,
				Integer.valueOf(PageContext.APPLICATION_SCOPE));
		evt.getServletContext().setAttribute(Context.PAGE_SCOPE,
				Integer.valueOf(PageContext.PAGE_SCOPE));
		evt.getServletContext().setAttribute(Context.REQUEST_SCOPE,
				Integer.valueOf(PageContext.REQUEST_SCOPE));
		evt.getServletContext().setAttribute(Context.SESSION_SCOPE,
				Integer.valueOf(PageContext.SESSION_SCOPE));

		// sets attributes to configure application
		final VulpeProject vulpeProject = VulpeConfigHelper.get(VulpeProject.class);
		global.put(Global.I18N_MANAGER, vulpeProject.i18nManager());
		global.put(Global.AUDIT_ENABLED, VulpeConfigHelper.isAuditEnabled());
		global.put(Global.SECURITY_ENABLED, VulpeConfigHelper.isSecurityEnabled());
		global.put(Global.USE_DB4O, VulpeConfigHelper.get(VulpeDomains.class).useDB4O());

		global.put(Global.THEME, VulpeConfigHelper.getTheme());
		global.put(Global.FRONTEND_MENU_TYPE, vulpeProject.frontendMenuType());
		global.put(Global.BACKEND_MENU_TYPE, vulpeProject.backendMenuType());

		if (vulpeProject.view() != null) {
			global.put(Global.BACKEND_CENTERED_LAYOUT, vulpeProject.view().backendCenteredLayout());
			global.put(Global.FRONTEND_CENTERED_LAYOUT, vulpeProject.view()
					.frontendCenteredLayout());
			global.put(Global.SHOW_BUTTON_AS_IMAGE, vulpeProject.view().showButtonAsImage());
			global.put(Global.SHOW_BUTTON_ICON, vulpeProject.view().showButtonIcon());
			global.put(Global.SHOW_BUTTON_TEXT, vulpeProject.view().showButtonText());
			global.put(Global.WIDTH_BUTTON_ICON, vulpeProject.view().widthButtonIcon());
			global
					.put(Global.WIDTH_MOBILE_BUTTON_ICON, vulpeProject.view()
							.widthMobileButtonIcon());
			global.put(Global.HEIGHT_BUTTON_ICON, vulpeProject.view().heightButtonIcon());
			global.put(Global.HEIGHT_MOBILE_BUTTON_ICON, vulpeProject.view()
					.heightMobileButtonIcon());
			global.put(Global.MESSAGE_SLIDE_UP, vulpeProject.view().messageSlideUp());
			global.put(Global.MESSAGE_SLIDE_UP_TIME, vulpeProject.view().messageSlideUpTime());
			global.put(Global.BREAK_LABEL, vulpeProject.view().breakLabel());
			global.put(Global.SHOW_COPYRIGHT, vulpeProject.view().showCopyright());
			global.put(Global.SHOW_POWERED_BY, vulpeProject.view().showPoweredBy());
			global.put(Global.PAGING_STYLE, vulpeProject.view().pagingStyle());
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
		CachedObjectsHelper.putAnnotedObjectsInCache(evt.getServletContext());
	}

}