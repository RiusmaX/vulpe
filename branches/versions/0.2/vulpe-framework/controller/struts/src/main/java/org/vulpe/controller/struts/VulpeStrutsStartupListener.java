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
package org.vulpe.controller.struts;

import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.ServletContextEvent;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.log4j.Logger;
import org.vulpe.commons.helper.VulpeConfigHelper;
import org.vulpe.controller.VulpeStartupListener;
import org.vulpe.controller.struts.commons.beans.converter.BigDecimalConverter;
import org.vulpe.controller.struts.commons.beans.converter.DateConverter;
import org.vulpe.controller.struts.commons.beans.converter.DecimalConverter;
import org.vulpe.controller.struts.commons.beans.converter.EnumConverter;
import org.vulpe.controller.struts.commons.beans.converter.SqlDateConverter;

import com.opensymphony.xwork2.util.LocalizedTextUtil;

/**
 * Class to manager startup with struts of application.
 * 
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
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
		LocalizedTextUtil.addDefaultResourceBundle(VulpeConfigHelper.getI18nManager());

		// register converters to struts
		ConvertUtils.register(new DateConverter(), Date.class);
		ConvertUtils.register(new SqlDateConverter(), java.sql.Date.class);
		ConvertUtils.register(new BigDecimalConverter(), BigDecimal.class);
		ConvertUtils.register(new DecimalConverter(), Double.class);
		ConvertUtils.register(new EnumConverter(), Enum.class);
	}

}