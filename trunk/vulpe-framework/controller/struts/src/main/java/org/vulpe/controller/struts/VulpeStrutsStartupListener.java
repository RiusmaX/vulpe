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