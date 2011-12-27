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
package org.vulpe.controller.commons;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.vulpe.commons.VulpeConstants;
import org.vulpe.commons.VulpeContext;
import org.vulpe.commons.factory.AbstractVulpeBeanFactory;
import org.vulpe.commons.helper.VulpeConfigHelper;
import org.vulpe.config.annotations.VulpeProject;

/**
 * Class to provide multiple Resource Bundle in application.
 * 
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 * @version 1.0
 * @since 1.0
 */
public class MultipleResourceBundle extends ResourceBundle {

	private static final Logger LOG = Logger.getLogger(MultipleResourceBundle.class);

	private static final MultipleResourceBundle INSTANCE = new MultipleResourceBundle();

	/**
	 * 
	 * @return Instance of MultipleResourceBundle
	 */
	public static MultipleResourceBundle getInstance() {
		return INSTANCE;
	}

	/**
	 * Gets all bundles in application
	 * 
	 * @return list of bundles in application
	 */
	protected List<ResourceBundle> getBundles() {
		final VulpeContext vulpeContext = AbstractVulpeBeanFactory.getInstance().getBean(
				VulpeConstants.CONTEXT);
		final VulpeProject project = VulpeConfigHelper.get(VulpeProject.class);
		final String modules[] = project.i18n();
		final List<ResourceBundle> list = new ArrayList<ResourceBundle>(modules.length);
		for (final String module : modules) {
			ResourceBundle resourceBundle = ResourceBundle.getBundle(module, vulpeContext
					.getLocale());
			if (!resourceBundle.getLocale().equals(vulpeContext.getLocale())) {
				resourceBundle = ResourceBundle.getBundle(module, new Locale(""));
			}
			list.add(resourceBundle);
		}
		Collections.reverse(list);
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.ResourceBundle#getKeys()
	 */
	@Override
	public Enumeration<String> getKeys() {
		final List<ResourceBundle> list = getBundles();
		if (list != null) {
			final Vector<String> listKeys = new Vector<String>();
			for (final ResourceBundle resourceBundle : list) {
				final Enumeration<String> enume = resourceBundle.getKeys();
				while (enume.hasMoreElements()) {
					listKeys.add(enume.nextElement());
				}
			}
			return listKeys.elements();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.ResourceBundle#handleGetObject(java.lang.String)
	 */
	@Override
	protected Object handleGetObject(final String key) {
		final List<ResourceBundle> list = getBundles();
		if (list != null) {
			for (final ResourceBundle resourceBundle : list) {
				try {
					final Object value = resourceBundle.getObject(key);
					if (value != null) {
						if (value instanceof String) {
							if (!value.toString().startsWith("???")
									&& !value.toString().endsWith("???")) {
								return value;
							}
						}
						return value;
					}
				} catch (MissingResourceException e) {
					LOG.debug(resourceBundle.getLocale().getDisplayName() + " - missing key: "
							+ key);
				}
			}
		}
		return key;
	}

	/**
	 * Method to get key description.
	 * 
	 * @param servletContext
	 * @param key
	 * @return
	 */
	public Object getKeyDescription(final String key) {
		return getObject(key);
	}

	public String getI18NEnum(Enum<?> value) {
		return value == null ? "" : getString(value.getClass().getName() + "." + value.name());
	}

}