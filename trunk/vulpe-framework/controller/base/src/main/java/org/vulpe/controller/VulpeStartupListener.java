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
import org.vulpe.commons.factory.AbstractVulpeBeanFactory;
import org.vulpe.commons.helper.VulpeCacheHelper;
import org.vulpe.commons.helper.VulpeConfigHelper;
import org.vulpe.commons.util.VulpeDB4OUtil;
import org.vulpe.commons.util.VulpeHashMap;
import org.vulpe.config.annotations.VulpeDomains;
import org.vulpe.config.annotations.VulpeHotKey;
import org.vulpe.config.annotations.VulpeHotKeys;
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
		global.put(Global.PROJECT_DEBUG, vulpeProject.debug());
		global.put(Global.PROJECT_I18N_MANAGER, vulpeProject.i18nManager());
		global.put(Global.PROJECT_AUDIT, VulpeConfigHelper.isAuditEnabled());
		global.put(Global.PROJECT_SECURITY, VulpeConfigHelper.isSecurityEnabled());
		global.put(Global.PROJECT_USE_DB4O, VulpeConfigHelper.get(VulpeDomains.class).useDB4O());
		global.put(Global.PROJECT_THEME, VulpeConfigHelper.getTheme());
		global.put(Global.PROJECT_DATE_PATTERN, vulpeProject.datePattern());
		global.put(Global.PROJECT_DATE_TIME_PATTERN, vulpeProject.dateTimePattern());
		global.put(Global.PROJECT_LOCALE_CODE, vulpeProject.localeCode());
		global.put(Global.PROJECT_HOT_KEYS, mountHotKeys(vulpeProject.hotKeys()));
		if (vulpeProject.view() != null) {
			global.put(Global.PROJECT_VIEW_FRONTEND_MENU_TYPE, vulpeProject.view().layout()
					.frontendMenuType());
			global.put(Global.PROJECT_VIEW_BACKEND_MENU_TYPE, vulpeProject.view().layout()
					.backendMenuType());
			global.put(Global.PROJECT_VIEW_BREAK_LABEL, vulpeProject.view().layout().breakLabel());
			global.put(Global.PROJECT_VIEW_DATE_MASK, vulpeProject.view().dateMask());
			global.put(Global.PROJECT_VIEW_FOCUS_FIRST, vulpeProject.view().focusFirst());
			global.put(Global.PROJECT_VIEW_ICON_HEIGHT, vulpeProject.view().layout().iconHeight());
			global.put(Global.PROJECT_VIEW_JQUERYUI, vulpeProject.view().layout().jQueryUI());
			global.put(Global.PROJECT_VIEW_MESSAGE_SLIDE_UP, vulpeProject.view().messages()
					.slideUp());
			global.put(Global.PROJECT_VIEW_MESSAGE_SLIDE_UP_TIME, vulpeProject.view().messages()
					.slideUpTime());
			global.put(Global.PROJECT_VIEW_PAGING_STYLE, vulpeProject.view().paging().style());
			global.put(Global.PROJECT_VIEW_PAGING_BUTTON_STYLE, vulpeProject.view().paging()
					.buttonStyle());
			global.put(Global.PROJECT_VIEW_PAGING_SHOW_BUTTON_AS_LINK, vulpeProject.view().paging()
					.showButtonAsLink());
			global.put(Global.PROJECT_VIEW_SHOW_BUTTONS_AS_IMAGE, vulpeProject.view().layout()
					.showButtonsAsImage());
			global.put(Global.PROJECT_VIEW_SHOW_ICON_OF_BUTTON, vulpeProject.view().layout()
					.showIconOfButton());
			global.put(Global.PROJECT_VIEW_SHOW_TEXT_OF_BUTTON, vulpeProject.view().layout()
					.showTextOfButton());
			global.put(Global.PROJECT_VIEW_SHOW_BUTTON_DELETE_THIS, vulpeProject.view().layout()
					.showButtonDeleteThis());
			global.put(Global.PROJECT_VIEW_SHOW_BUTTON_UPDATE, vulpeProject.view().layout()
					.showButtonUpdate());
			global.put(Global.PROJECT_VIEW_SHOW_BUTTONS_DELETE, vulpeProject.view().layout()
					.showButtonsDelete());
			global.put(Global.PROJECT_VIEW_SHOW_ROWS, vulpeProject.view().layout().showRows());
			global.put(Global.PROJECT_VIEW_SHOW_COPYRIGHT, vulpeProject.view().layout()
					.showCopyright());
			global.put(Global.PROJECT_VIEW_SHOW_MODIFICATION_WARNING, vulpeProject.view()
					.messages().showModificationWarning());
			global.put(Global.PROJECT_VIEW_SHOW_REPORT_IN_NEW_WINDOW, vulpeProject.view().layout()
					.showReportInNewWindow());
			global.put(Global.PROJECT_VIEW_SHOW_POWERED_BY, vulpeProject.view().layout()
					.showPoweredBy());
			global.put(Global.PROJECT_VIEW_SHOW_WARNING_BEFORE_CLEAR, vulpeProject.view()
					.messages().showWarningBeforeClear());
			global.put(Global.PROJECT_VIEW_SHOW_WARNING_BEFORE_DELETE, vulpeProject.view()
					.messages().showWarningBeforeDelete());
			global.put(Global.PROJECT_VIEW_SHOW_WARNING_BEFORE_UPDATE_POST, vulpeProject.view()
					.messages().showWarningBeforeUpdatePost());
			global.put(Global.PROJECT_VIEW_SORT_TYPE, vulpeProject.view().sortType());
			global.put(Global.PROJECT_VIEW_ICON_WIDTH, vulpeProject.view().layout().iconWidth());
			global.put(Global.PROJECT_VIEW_USE_BACKEND_LAYER, vulpeProject.view().layout()
					.useBackendLayer());
			global.put(Global.PROJECT_VIEW_USE_FRONTEND_LAYER, vulpeProject.view().layout()
					.useFrontendLayer());
			global.put(Global.PROJECT_VIEW_LAYOUT_SHOW_SLIDER_PANEL, vulpeProject.view().layout()
					.showSliderPanel());
			global.put(Global.PROJECT_VIEW_LAYOUT_SHOW_SLIDER_PANEL_ONLY_IF_AUTHENTICATED,
					vulpeProject.view().layout().showSliderPanelOnlyIfAuthenticated());
			global.put(Global.PROJECT_VIEW_LAYOUT_SHOW_LOADING_AS_MODAL, vulpeProject.view()
					.layout().showLoadingAsModal());
			global.put(Global.PROJECT_VIEW_LAYOUT_SHOW_DISPLAY_SPECIFIC_MESSAGES_WHEN_LOADING,
					vulpeProject.view().layout().displaySpecificMessagesWhenLoading());
			global.put(Global.PROJECT_VIEW_SESSION_IDLE_TIME, vulpeProject.view().session()
					.idleTime());
			global.put(Global.PROJECT_VIEW_SESSION_KEEP_ALIVE_URL, vulpeProject.view().session()
					.keepAliveURL());
			global.put(Global.PROJECT_VIEW_SESSION_REDIRECT_AFTER, vulpeProject.view().session()
					.redirectAfter());
			global.put(Global.PROJECT_VIEW_SESSION_REDIRECT_TO, vulpeProject.view().session()
					.redirectTo());
		}

		if (vulpeProject.mobile().enabled()) {
			global.put(Global.PROJECT_MOBILE_ENABLED, vulpeProject.mobile().enabled());
			global.put(Global.PROJECT_MOBILE_VIEWPORT_WIDHT, vulpeProject.mobile().viewportWidth());
			global.put(Global.PROJECT_MOBILE_VIEWPORT_HEIGHT, vulpeProject.mobile()
					.viewportHeight());
			global.put(Global.PROJECT_MOBILE_VIEWPORT_USER_SCALABLE, vulpeProject.mobile()
					.viewportUserScalable());
			global.put(Global.PROJECT_MOBILE_VIEWPORT_INITIAL_SCALE, vulpeProject.mobile()
					.viewportInitialScale());
			global.put(Global.PROJECT_MOBILE_VIEWPORT_MAXIMUM_SCALE, vulpeProject.mobile()
					.viewportMaximumScale());
			global.put(Global.PROJECT_MOBILE_VIEWPORT_MINIMUM_SCALE, vulpeProject.mobile()
					.viewportMinimumScale());
			global.put(Global.PROJECT_MOBILE_ICON_HEIGHT, vulpeProject.mobile().iconHeight());
			global.put(Global.PROJECT_MOBILE_ICON_WIDTH, vulpeProject.mobile().iconWidth());
		}
		evt.getServletContext().setAttribute(Context.GLOBAL, global);
		VulpeCachedObjectsHelper.putAnnotedObjectsInCache(evt.getServletContext());
		VulpeJobSchedulerHelper.schedulerAnnotedJobs(evt.getServletContext());
		loadControllerMethods();
	}

	/**
	 * 
	 */
	private void loadControllerMethods() {
		final Map<String, String> mapControllerMethods = new HashMap<String, String>();
		for (Method method : VulpeController.class.getMethods()) {
			mapControllerMethods.put(method.getName(), method.getName());
		}
		VulpeCacheHelper.getInstance().put(VulpeConstants.CONTROLLER_METHODS, mapControllerMethods);
	}

	/**
	 * 
	 * @param vulpeHotKeys
	 * @return
	 */
	private String mountHotKeys(final VulpeHotKeys vulpeHotKeys) {
		final StringBuilder hotKeys = new StringBuilder("[");
		final VulpeHashMap<String, String> mapKeys = new VulpeHashMap<String, String>();
		for (final VulpeHotKey hotKey : vulpeHotKeys.defaultKeys()) {
			final StringBuilder hotKeyValue = new StringBuilder("[");
			final StringBuilder keys = new StringBuilder();
			for (final String key : hotKey.keys()) {
				if (keys.length() > 0) {
					keys.append(",");
				}
				keys.append(key);
			}
			final StringBuilder additionals = new StringBuilder();
			if (hotKey.dontFireOnText()) {
				additionals.append("dontFireOnText");
			}
			if (hotKey.returnKeyDontFireInText()) {
				if (additionals.length() > 0) {
					additionals.append(",");
				}
				additionals.append("returnKeyDontFireInText");
			}
			if (hotKey.putSameOnReturnKey()) {
				if (additionals.length() > 0) {
					additionals.append(",");
				}
				additionals.append("putSameOnReturnKey");
			}
			hotKeyValue.append("'").append(hotKey.name()).append("','").append(keys.toString())
					.append("',").append("'").append(additionals.toString()).append("'");
			hotKeyValue.append("]");
			mapKeys.put(hotKey.name(), hotKeyValue.toString());
		}
		for (final VulpeHotKey hotKey : vulpeHotKeys.value()) {
			final StringBuilder hotKeyValue = new StringBuilder("[");
			final StringBuilder keys = new StringBuilder();
			for (final String key : hotKey.keys()) {
				if (keys.length() > 0) {
					keys.append(",");
				}
				keys.append(key);
			}
			final StringBuilder additionals = new StringBuilder();
			if (hotKey.dontFireOnText()) {
				additionals.append("dontFireOnText");
			}
			if (hotKey.returnKeyDontFireInText()) {
				if (additionals.length() > 0) {
					additionals.append(",");
				}
				additionals.append("returnKeyDontFireInText");
			}
			if (hotKey.putSameOnReturnKey()) {
				if (additionals.length() > 0) {
					additionals.append(",");
				}
				additionals.append("putSameOnReturnKey");
			}
			hotKeyValue.append("'").append(hotKey.name()).append("','").append(keys.toString())
					.append("',").append("'").append(additionals.toString()).append("'");
			hotKeyValue.append("]");
			mapKeys.put(hotKey.name(), hotKeyValue.toString());
		}
		for (final String hotKey : mapKeys.values()) {
			if (hotKeys.length() > 1) {
				hotKeys.append(",");
			}
			hotKeys.append(hotKey);
		}
		hotKeys.append("];");
		return hotKeys.toString();
	}

}