package org.vulpe.controller.struts.listener;

import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.ServletContextEvent;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.log4j.Logger;
import org.vulpe.common.helper.VulpeConfigHelper;
import org.vulpe.controller.listeners.VulpeStartupListener;
import org.vulpe.controller.struts.common.beans.converter.BigDecimalConverter;
import org.vulpe.controller.struts.common.beans.converter.DateConverter;
import org.vulpe.controller.struts.common.beans.converter.DecimalConverter;
import org.vulpe.controller.struts.common.beans.converter.EnumConverter;
import org.vulpe.controller.struts.common.beans.converter.SqlDateConverter;

import com.opensymphony.xwork2.util.LocalizedTextUtil;

/**
 * Class to manager startup with struts of application.
 * 
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 */
public class VulpeStrutsStartupListener extends VulpeStartupListener {

	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(VulpeStrutsStartupListener.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.ServletContextListener#contextInitialized(javax.servlet
	 * .ServletContextEvent)
	 */
	public void contextInitialized(final ServletContextEvent evt) {
		super.contextInitialized(evt);
		// configuration bundle
		LocalizedTextUtil.addDefaultResourceBundle(VulpeConfigHelper.getI18n().toString());

		// register converters to struts
		ConvertUtils.register(new DateConverter(), Date.class);
		ConvertUtils.register(new SqlDateConverter(), java.sql.Date.class);
		ConvertUtils.register(new BigDecimalConverter(), BigDecimal.class);
		ConvertUtils.register(new DecimalConverter(), Double.class);
		ConvertUtils.register(new EnumConverter(), Enum.class);
	}

}