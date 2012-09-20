/**
 * Vulpe Framework - Quick and Smart ;)
 * Copyright (C) 2011 Active Thread
 *
 * Este programa é software livre; você pode redistribuí-lo e/ou
 * modificá-lo sob os termos da Licença Pública Geral GNU, conforme
 * publicada pela Free Software Foundation; tanto a versão 2 da
 * Licença como (a seu critério) qualquer versão mais nova.
 *
 * Este programa é distribuído na expectativa de ser útil, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia implícita de
 * COMERCIALIZAÇÃO ou de ADEQUAÇÃO A QUALQUER PROPÓSITO EM
 * PARTICULAR. Consulte a Licença Pública Geral GNU para obter mais
 * detalhes.
 *
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU
 * junto com este programa; se não, escreva para a Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307, USA.
 */
/**
 * Vulpe Framework - Quick and Smart ;)
 * Copyright (C) 2011 Active Thread
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.vulpe.controller.listener;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;
import javax.servlet.jsp.PageContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoaderListener;
import org.vulpe.commons.VulpeConstants;
import org.vulpe.commons.VulpeConstants.Configuration.Global;
import org.vulpe.commons.VulpeConstants.Context;
import org.vulpe.commons.factory.AbstractVulpeBeanFactory;
import org.vulpe.commons.helper.VulpeCacheHelper;
import org.vulpe.commons.helper.VulpeConfigHelper;
import org.vulpe.commons.util.VulpeDB4OUtil;
import org.vulpe.commons.util.VulpeHashMap;
import org.vulpe.config.annotations.VulpeApplication;
import org.vulpe.config.annotations.VulpeDomains;
import org.vulpe.config.annotations.VulpeHotKey;
import org.vulpe.config.annotations.VulpeHotKeys;
import org.vulpe.controller.VulpeController;
import org.vulpe.controller.helper.VulpeCachedObjectsHelper;
import org.vulpe.controller.helper.VulpeJobSchedulerHelper;
import org.vulpe.security.context.VulpeSecurityContext;

/**
 * Class to manager startup of application.
 *
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 */
@WebListener
public class VulpeStartupListener extends ContextLoaderListener {

	private static final Logger LOG = LoggerFactory.getLogger(VulpeStartupListener.class);

	private static final VulpeStartupExtend STARTUP_EXTEND = AbstractVulpeBeanFactory.getInstance()
			.getBean(VulpeConstants.Context.STARTUP_EXTEND);
	private static final VulpeStartupExtend FW_STARTUP_EXTEND = AbstractVulpeBeanFactory.getInstance()
			.getBean(VulpeConstants.Context.FW_STARTUP_EXTEND);

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
	public void contextDestroyed(final ServletContextEvent event) {
		super.contextDestroyed(event);
		LOG.debug("Entering in Context Detroyed");
		if (VulpeConfigHelper.get(VulpeDomains.class).useDB4O()) {
			VulpeDB4OUtil.getInstance().shutdown();
		}
		if (FW_STARTUP_EXTEND != null) {
			FW_STARTUP_EXTEND.contextDestroyed(event);
		}
		if (STARTUP_EXTEND != null) {
			STARTUP_EXTEND.contextDestroyed(event);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * javax.servlet.ServletContextListener#contextInitialized(javax.servlet
	 * .ServletContextEvent)
	 */
	public void contextInitialized(final ServletContextEvent event) {
		super.contextInitialized(event);
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
		event.getServletContext().setAttribute(Context.APPLICATION_SCOPE,
				Integer.valueOf(PageContext.APPLICATION_SCOPE));
		event.getServletContext().setAttribute(Context.PAGE_SCOPE,
				Integer.valueOf(PageContext.PAGE_SCOPE));
		event.getServletContext().setAttribute(Context.REQUEST_SCOPE,
				Integer.valueOf(PageContext.REQUEST_SCOPE));
		event.getServletContext().setAttribute(Context.SESSION_SCOPE,
				Integer.valueOf(PageContext.SESSION_SCOPE));

		// sets attributes to configure application
		global.put(Global.APPLICATION_DEBUG, VulpeConfigHelper.isDebugEnabled());
		global.put(Global.APPLICATION_AUDIT, VulpeConfigHelper.isAuditEnabled());
		global.put(Global.APPLICATION_SECURITY, VulpeConfigHelper.isSecurityEnabled());
		global.put(Global.APPLICATION_USE_DB4O, VulpeConfigHelper.get(VulpeDomains.class).useDB4O());
		global.put(Global.APPLICATION_THEME, VulpeConfigHelper.getTheme());
		final VulpeApplication vulpeApplication = VulpeConfigHelper.getApplicationConfiguration();
		global.put(Global.APPLICATION_I18N_MANAGER, vulpeApplication.i18nManager());
		global.put(Global.APPLICATION_DATE_PATTERN, vulpeApplication.datePattern());
		global.put(Global.APPLICATION_DATE_TIME_PATTERN, vulpeApplication.dateTimePattern());
		global.put(Global.APPLICATION_LOCALE_CODE, vulpeApplication.localeCode());
		global.put(Global.APPLICATION_HOT_KEYS, mountHotKeys(vulpeApplication.hotKeys()));
		if (vulpeApplication.view() != null) {
			global.put(Global.APPLICATION_VIEW_FRONTEND_MENU_TYPE, vulpeApplication.view().layout()
					.frontendMenuType());
			global.put(Global.APPLICATION_VIEW_BACKEND_MENU_TYPE, vulpeApplication.view().layout()
					.backendMenuType());
			global.put(Global.APPLICATION_VIEW_BREAK_LABEL, vulpeApplication.view().layout()
					.breakLabel());
			global.put(Global.APPLICATION_VIEW_DATE_MASK, vulpeApplication.view().dateMask());
			global.put(Global.APPLICATION_VIEW_FOCUS_FIRST, vulpeApplication.view().focusFirst());
			global.put(Global.APPLICATION_VIEW_ICON_HEIGHT, vulpeApplication.view().layout()
					.iconHeight());
			global.put(Global.APPLICATION_VIEW_JQUERYUI, vulpeApplication.view().layout()
					.jQueryUI());
			global.put(Global.APPLICATION_VIEW_MESSAGE_SLIDE_UP, vulpeApplication.view().messages()
					.slideUp());
			global.put(Global.APPLICATION_VIEW_MESSAGE_SLIDE_UP_TIME, vulpeApplication.view()
					.messages().slideUpTime());
			global.put(Global.APPLICATION_VIEW_PAGING_STYLE, vulpeApplication.view().paging()
					.style());
			global.put(Global.APPLICATION_VIEW_PAGING_BUTTON_STYLE, vulpeApplication.view()
					.paging().buttonStyle());
			global.put(Global.APPLICATION_VIEW_PAGING_SHOW_BUTTON_AS_LINK, vulpeApplication.view()
					.paging().showButtonAsLink());
			global.put(Global.APPLICATION_VIEW_SHOW_BUTTONS_AS_IMAGE, vulpeApplication.view()
					.layout().showButtonsAsImage());
			global.put(Global.APPLICATION_VIEW_SHOW_ICON_OF_BUTTON, vulpeApplication.view()
					.layout().showIconOfButton());
			global.put(Global.APPLICATION_VIEW_SHOW_TEXT_OF_BUTTON, vulpeApplication.view()
					.layout().showTextOfButton());
			global.put(Global.APPLICATION_VIEW_SHOW_BUTTON_DELETE_THIS, vulpeApplication.view()
					.layout().showButtonDeleteThis());
			global.put(Global.APPLICATION_VIEW_SHOW_BUTTON_UPDATE, vulpeApplication.view().layout()
					.showButtonUpdate());
			global.put(Global.APPLICATION_VIEW_SHOW_BUTTONS_DELETE, vulpeApplication.view()
					.layout().showButtonsDelete());
			global.put(Global.APPLICATION_VIEW_SHOW_ROWS, vulpeApplication.view().layout()
					.showRows());
			global.put(Global.APPLICATION_VIEW_SHOW_COPYRIGHT, vulpeApplication.view().layout()
					.showCopyright());
			global.put(Global.APPLICATION_VIEW_SHOW_MODIFICATION_WARNING, vulpeApplication.view()
					.messages().showModificationWarning());
			global.put(Global.APPLICATION_VIEW_SHOW_REPORT_IN_NEW_WINDOW, vulpeApplication.view()
					.layout().showReportInNewWindow());
			global.put(Global.APPLICATION_VIEW_SHOW_POWERED_BY, vulpeApplication.view().layout()
					.showPoweredBy());
			global.put(Global.APPLICATION_VIEW_SHOW_WARNING_BEFORE_CLEAR, vulpeApplication.view()
					.messages().showWarningBeforeClear());
			global.put(Global.APPLICATION_VIEW_SHOW_WARNING_BEFORE_DELETE, vulpeApplication.view()
					.messages().showWarningBeforeDelete());
			global.put(Global.APPLICATION_VIEW_SHOW_WARNING_BEFORE_UPDATE_POST, vulpeApplication
					.view().messages().showWarningBeforeUpdatePost());
			global.put(Global.APPLICATION_VIEW_SORT_TYPE, vulpeApplication.view().sortType());
			global.put(Global.APPLICATION_VIEW_ICON_WIDTH, vulpeApplication.view().layout()
					.iconWidth());
			global.put(Global.APPLICATION_VIEW_USE_BACKEND_LAYER, vulpeApplication.view().layout()
					.useBackendLayer());
			global.put(Global.APPLICATION_VIEW_USE_FRONTEND_LAYER, vulpeApplication.view().layout()
					.useFrontendLayer());
			global.put(Global.APPLICATION_VIEW_LAYOUT_SHOW_SLIDER_PANEL, vulpeApplication.view()
					.layout().showSliderPanel());
			global.put(Global.APPLICATION_VIEW_LAYOUT_SHOW_SLIDER_PANEL_ONLY_IF_AUTHENTICATED,
					vulpeApplication.view().layout().showSliderPanelOnlyIfAuthenticated());
			global.put(Global.APPLICATION_VIEW_LAYOUT_SHOW_LOADING_AS_MODAL, vulpeApplication
					.view().layout().showLoadingAsModal());
			global.put(Global.APPLICATION_VIEW_LAYOUT_SHOW_DISPLAY_SPECIFIC_MESSAGES_WHEN_LOADING,
					vulpeApplication.view().layout().displaySpecificMessagesWhenLoading());
			global.put(Global.APPLICATION_VIEW_SESSION_IDLE_TIME, vulpeApplication.view().session()
					.idleTime());
			global.put(Global.APPLICATION_VIEW_SESSION_KEEP_ALIVE_URL, vulpeApplication.view()
					.session().keepAliveURL());
			global.put(Global.APPLICATION_VIEW_SESSION_REDIRECT_AFTER, vulpeApplication.view()
					.session().redirectAfter());
			global.put(Global.APPLICATION_VIEW_SESSION_REDIRECT_TO, vulpeApplication.view()
					.session().redirectTo());
		}

		if (vulpeApplication.mobile().enabled()) {
			global.put(Global.APPLICATION_MOBILE_ENABLED, vulpeApplication.mobile().enabled());
			global.put(Global.APPLICATION_MOBILE_VIEWPORT_WIDHT, vulpeApplication.mobile()
					.viewportWidth());
			global.put(Global.APPLICATION_MOBILE_VIEWPORT_HEIGHT, vulpeApplication.mobile()
					.viewportHeight());
			global.put(Global.APPLICATION_MOBILE_VIEWPORT_USER_SCALABLE, vulpeApplication.mobile()
					.viewportUserScalable());
			global.put(Global.APPLICATION_MOBILE_VIEWPORT_INITIAL_SCALE, vulpeApplication.mobile()
					.viewportInitialScale());
			global.put(Global.APPLICATION_MOBILE_VIEWPORT_MAXIMUM_SCALE, vulpeApplication.mobile()
					.viewportMaximumScale());
			global.put(Global.APPLICATION_MOBILE_VIEWPORT_MINIMUM_SCALE, vulpeApplication.mobile()
					.viewportMinimumScale());
			global.put(Global.APPLICATION_MOBILE_ICON_HEIGHT, vulpeApplication.mobile()
					.iconHeight());
			global.put(Global.APPLICATION_MOBILE_ICON_WIDTH, vulpeApplication.mobile().iconWidth());
		}
		event.getServletContext().setAttribute(Context.GLOBAL, global);
		VulpeCachedObjectsHelper.putAnnotedObjectsInCache(event.getServletContext());
		VulpeJobSchedulerHelper.schedulerAnnotedJobs(event.getServletContext());
		loadControllerMethods();
		if (FW_STARTUP_EXTEND != null) {
			FW_STARTUP_EXTEND.contextInitialized(event);
		}
		if (STARTUP_EXTEND != null) {
			STARTUP_EXTEND.contextInitialized(event);
		}
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