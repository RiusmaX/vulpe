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

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.jsp.PageContext;

import org.apache.log4j.Logger;
import org.vulpe.commons.VulpeConstants;
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
			final VulpeSecurityContext vulpeSecurityContext = AbstractVulpeBeanFactory
					.getInstance().getBean(VulpeSecurityContext.class.getSimpleName());
			if (vulpeSecurityContext != null) {
				vulpeSecurityContext.initialize();
			}
		}

		// sets scopes as attributes to use in tags and JSPs
		evt.getServletContext().setAttribute(VulpeConstants.Context.APPLICATION_SCOPE,
				Integer.valueOf(PageContext.APPLICATION_SCOPE));
		evt.getServletContext().setAttribute(VulpeConstants.Context.PAGE_SCOPE,
				Integer.valueOf(PageContext.PAGE_SCOPE));
		evt.getServletContext().setAttribute(VulpeConstants.Context.REQUEST_SCOPE,
				Integer.valueOf(PageContext.REQUEST_SCOPE));
		evt.getServletContext().setAttribute(VulpeConstants.Context.SESSION_SCOPE,
				Integer.valueOf(PageContext.SESSION_SCOPE));

		// sets attributes to configure application
		final VulpeProject vulpeProject = VulpeConfigHelper.get(VulpeProject.class);
		evt.getServletContext().setAttribute(VulpeConstants.Context.I18N_MANAGER,
				vulpeProject.i18nManager());
		evt.getServletContext().setAttribute(VulpeConstants.Context.THEME,
				VulpeConfigHelper.getTheme());
		evt.getServletContext().setAttribute(VulpeConstants.Context.AUDIT_ENABLED,
				VulpeConfigHelper.isAuditEnabled());
		evt.getServletContext().setAttribute(VulpeConstants.Context.SECURITY_ENABLED,
				VulpeConfigHelper.isSecurityEnabled());
		evt.getServletContext().setAttribute(VulpeConstants.Context.FRONTEND_MENU_TYPE,
				vulpeProject.frontendMenuType());
		evt.getServletContext().setAttribute(VulpeConstants.Context.BACKEND_MENU_TYPE,
				vulpeProject.backendMenuType());

		if (vulpeProject.view() != null) {
			evt.getServletContext().setAttribute(
					VulpeConstants.Context.View.BACKEND_CENTERED_LAYOUT,
					vulpeProject.view().backendCenteredLayout());
			evt.getServletContext().setAttribute(
					VulpeConstants.Context.View.FRONTEND_CENTERED_LAYOUT,
					vulpeProject.view().frontendCenteredLayout());
			evt.getServletContext().setAttribute(VulpeConstants.Context.View.SHOW_BUTTON_AS_IMAGE,
					vulpeProject.view().showButtonAsImage());
			evt.getServletContext().setAttribute(VulpeConstants.Context.View.SHOW_BUTTON_ICON,
					vulpeProject.view().showButtonIcon());
			evt.getServletContext().setAttribute(VulpeConstants.Context.View.SHOW_BUTTON_TEXT,
					vulpeProject.view().showButtonText());
			evt.getServletContext().setAttribute(VulpeConstants.Context.View.WIDTH_BUTTON_ICON,
					vulpeProject.view().widthButtonIcon());
			evt.getServletContext().setAttribute(
					VulpeConstants.Context.View.WIDTH_MOBILE_BUTTON_ICON,
					vulpeProject.view().widthMobileButtonIcon());
			evt.getServletContext().setAttribute(VulpeConstants.Context.View.HEIGHT_BUTTON_ICON,
					vulpeProject.view().heightButtonIcon());
			evt.getServletContext().setAttribute(
					VulpeConstants.Context.View.HEIGHT_MOBILE_BUTTON_ICON,
					vulpeProject.view().heightMobileButtonIcon());
			evt.getServletContext().setAttribute(VulpeConstants.Context.View.MESSAGE_SLIDE_UP,
					vulpeProject.view().messageSlideUp());
			evt.getServletContext().setAttribute(VulpeConstants.Context.View.MESSAGE_SLIDE_UP_TIME,
					vulpeProject.view().messageSlideUpTime());
			evt.getServletContext().setAttribute(VulpeConstants.Context.View.BREAK_LABEL,
					vulpeProject.view().breakLabel());
			evt.getServletContext().setAttribute(VulpeConstants.Context.View.SHOW_COPYRIGHT,
					vulpeProject.view().showCopyright());
			evt.getServletContext().setAttribute(VulpeConstants.Context.View.SHOW_POWERED_BY,
					vulpeProject.view().showPoweredBy());
			evt.getServletContext().setAttribute(VulpeConstants.Context.View.PAGING_STYLE,
					vulpeProject.view().pagingStyle());
		}
		if (vulpeProject.mobileEnabled()) {
			evt.getServletContext().setAttribute(VulpeConstants.Context.SHOW_AS_MOBILE, true);
			evt.getServletContext().setAttribute(VulpeConstants.Context.Mobile.VIEWPORT_WIDHT,
					vulpeProject.mobile().viewportWidth());
			evt.getServletContext().setAttribute(VulpeConstants.Context.Mobile.VIEWPORT_HEIGHT,
					vulpeProject.mobile().viewportHeight());
			evt.getServletContext().setAttribute(
					VulpeConstants.Context.Mobile.VIEWPORT_USER_SCALABLE,
					vulpeProject.mobile().viewportUserScalable());
			evt.getServletContext().setAttribute(
					VulpeConstants.Context.Mobile.VIEWPORT_INITIAL_SCALE,
					vulpeProject.mobile().viewportInitialScale());
			evt.getServletContext().setAttribute(
					VulpeConstants.Context.Mobile.VIEWPORT_MAXIMUM_SCALE,
					vulpeProject.mobile().viewportMaximumScale());
			evt.getServletContext().setAttribute(
					VulpeConstants.Context.Mobile.VIEWPORT_MINIMUM_SCALE,
					vulpeProject.mobile().viewportMinimumScale());
		}
		evt.getServletContext().setAttribute(VulpeConstants.Context.VULPE_USE_DB4O,
				VulpeConfigHelper.get(VulpeDomains.class).useDB4O());

		CachedObjectsHelper.putAnnotedObjectsInCache(evt.getServletContext());
	}

}