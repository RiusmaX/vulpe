package org.vulpe.controller.listeners;

import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.jsp.PageContext;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.log4j.Logger;
import org.vulpe.common.Constants;
import org.vulpe.common.beans.converter.BigDecimalConverter;
import org.vulpe.common.beans.converter.DateConverter;
import org.vulpe.common.beans.converter.DecimalConverter;
import org.vulpe.common.beans.converter.EnumConverter;
import org.vulpe.common.beans.converter.SqlDateConverter;
import org.vulpe.common.db4o.DB4OUtil;
import org.vulpe.common.helper.VulpeConfigHelper;
import org.vulpe.config.annotations.VulpeDomains;
import org.vulpe.config.annotations.VulpeProject;
import org.vulpe.controller.helper.CachedObjectsHelper;

import com.opensymphony.xwork2.util.LocalizedTextUtil;

/**
 * Class to manager startup of application.
 * 
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 */
public class VulpeStartupListener implements ServletContextListener {

	private static final Logger LOG = Logger
			.getLogger(VulpeStartupListener.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @seejavax.servlet.ServletContextListener#contextDestroyed(javax.servlet.
	 * ServletContextEvent)
	 */
	public void contextDestroyed(final ServletContextEvent evt) {
		LOG.debug("Entering in Context Detroyed");
		if (VulpeConfigHelper.get(VulpeDomains.class).useDB4O()) {
			DB4OUtil.getInstance().shutdown();
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
			DB4OUtil.getInstance().getObjectServer();
		}

		// register converters to struts
		ConvertUtils.register(new DateConverter(), Date.class);
		ConvertUtils.register(new SqlDateConverter(), java.sql.Date.class);
		ConvertUtils.register(new BigDecimalConverter(), BigDecimal.class);
		ConvertUtils.register(new DecimalConverter(), Double.class);
		ConvertUtils.register(new EnumConverter(), Enum.class);

		// configuration bundle
		LocalizedTextUtil.addDefaultResourceBundle(evt.getServletContext()
				.getInitParameter(Constants.InitParameter.PROJECT_BUNDLE));

		// sets scopes as attributes to use in tags and JSPs
		evt.getServletContext().setAttribute(
				Constants.Context.APPLICATION_SCOPE,
				Integer.valueOf(PageContext.APPLICATION_SCOPE));
		evt.getServletContext().setAttribute(Constants.Context.PAGE_SCOPE,
				Integer.valueOf(PageContext.PAGE_SCOPE));
		evt.getServletContext().setAttribute(Constants.Context.REQUEST_SCOPE,
				Integer.valueOf(PageContext.REQUEST_SCOPE));
		evt.getServletContext().setAttribute(Constants.Context.SESSION_SCOPE,
				Integer.valueOf(PageContext.SESSION_SCOPE));

		// sets attributes to configure application
		final VulpeProject vulpeProject = VulpeConfigHelper
				.get(VulpeProject.class);
		evt.getServletContext().setAttribute(Constants.Context.THEME,
				VulpeConfigHelper.getTheme());
		evt.getServletContext().setAttribute(Constants.Context.AUDIT_ENABLED,
				VulpeConfigHelper.isAuditEnabled());
		evt.getServletContext().setAttribute(
				Constants.Context.SECURITY_ENABLED,
				VulpeConfigHelper.isSecurityEnabled());
		evt.getServletContext().setAttribute(
				Constants.Context.FRONTEND_MENU_TYPE,
				vulpeProject.frontendMenuType());
		evt.getServletContext().setAttribute(
				Constants.Context.BACKEND_MENU_TYPE,
				vulpeProject.backendMenuType());

		if (vulpeProject.view() != null) {
			evt.getServletContext().setAttribute(
					Constants.Context.View.BACKEND_CENTERED_LAYOUT,
					vulpeProject.view().backendCenteredLayout());
			evt.getServletContext().setAttribute(
					Constants.Context.View.FRONTEND_CENTERED_LAYOUT,
					vulpeProject.view().frontendCenteredLayout());
			evt.getServletContext().setAttribute(
					Constants.Context.View.SHOW_BUTTON_AS_IMAGE,
					vulpeProject.view().showButtonAsImage());
			evt.getServletContext().setAttribute(
					Constants.Context.View.SHOW_BUTTON_ICON,
					vulpeProject.view().showButtonIcon());
			evt.getServletContext().setAttribute(
					Constants.Context.View.SHOW_BUTTON_TEXT,
					vulpeProject.view().showButtonText());
			evt.getServletContext().setAttribute(
					Constants.Context.View.WIDTH_BUTTON_ICON,
					vulpeProject.view().widthButtonIcon());
			evt.getServletContext().setAttribute(
					Constants.Context.View.WIDTH_MOBILE_BUTTON_ICON,
					vulpeProject.view().widthMobileButtonIcon());
			evt.getServletContext().setAttribute(
					Constants.Context.View.HEIGHT_BUTTON_ICON,
					vulpeProject.view().heightButtonIcon());
			evt.getServletContext().setAttribute(
					Constants.Context.View.HEIGHT_MOBILE_BUTTON_ICON,
					vulpeProject.view().heightMobileButtonIcon());
			evt.getServletContext().setAttribute(
					Constants.Context.View.MESSAGE_SLIDE_UP_TIME,
					vulpeProject.view().messageSlideUpTime());
		}
		if (vulpeProject.mobileEnabled()) {
			evt.getServletContext().setAttribute(
					Constants.Context.SHOW_AS_MOBILE, true);
			evt.getServletContext().setAttribute(
					Constants.Context.Mobile.VIEWPORT_WIDHT,
					vulpeProject.mobile().viewportWidth());
			evt.getServletContext().setAttribute(
					Constants.Context.Mobile.VIEWPORT_HEIGHT,
					vulpeProject.mobile().viewportHeight());
			evt.getServletContext().setAttribute(
					Constants.Context.Mobile.VIEWPORT_USER_SCALABLE,
					vulpeProject.mobile().viewportUserScalable());
			evt.getServletContext().setAttribute(
					Constants.Context.Mobile.VIEWPORT_INITIAL_SCALE,
					vulpeProject.mobile().viewportInitialScale());
			evt.getServletContext().setAttribute(
					Constants.Context.Mobile.VIEWPORT_MAXIMUM_SCALE,
					vulpeProject.mobile().viewportMaximumScale());
			evt.getServletContext().setAttribute(
					Constants.Context.Mobile.VIEWPORT_MINIMUM_SCALE,
					vulpeProject.mobile().viewportMinimumScale());
		}
		evt.getServletContext().setAttribute(Constants.Context.VULPE_USE_DB4O,
				VulpeConfigHelper.get(VulpeDomains.class).useDB4O());

		CachedObjectsHelper.putAnnotedObjectsInCache(evt.getServletContext());
	}

}