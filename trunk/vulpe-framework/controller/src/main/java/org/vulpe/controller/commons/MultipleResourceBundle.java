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
package org.vulpe.controller.commons;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Vector;

import org.apache.commons.beanutils.locale.LocaleBeanUtils;
import org.apache.log4j.Logger;
import org.vulpe.commons.VulpeConstants;
import org.vulpe.commons.cache.VulpeCacheHelper;
import org.vulpe.commons.helper.VulpeConfigHelper;
import org.vulpe.config.annotations.VulpeProject;

/**
 * Class to provide multiple Resource Bundle in application.
 * 
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 * @version 1.0
 * @since 1.0
 */
public class MultipleResourceBundle extends ResourceBundle {

	private static final Logger LOG = Logger.getLogger(MultipleResourceBundle.class);

	private final static String BUNDLES_KEY = MultipleResourceBundle.class.getName().concat(
			".bundles");

	private static final MultipleResourceBundle INSTANCE = new MultipleResourceBundle();

	private Locale locale;

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
		final VulpeCacheHelper cache = VulpeCacheHelper.getInstance();
		final Locale requestLocale = cache.get(VulpeConstants.View.APPLICATION_LOCALE);
		final boolean checkLocale = (locale == null || (requestLocale != null && !locale
				.getLanguage().equals(requestLocale.getLanguage())));
		if (checkLocale) {
			locale = requestLocale;
		}
		List<ResourceBundle> list = cache.get(BUNDLES_KEY);
		if (list == null || checkLocale) {
			VulpeProject project = VulpeConfigHelper.get(VulpeProject.class);
			final String modules[] = project.i18n();
			list = new ArrayList<ResourceBundle>(modules.length);
			for (String module : modules) {
				final ResourceBundle resourceBundle = ResourceBundle.getBundle(module, locale);
				list.add(resourceBundle);
			}
			Collections.reverse(list);
			cache.put(BUNDLES_KEY, list);
		}
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
			for (ResourceBundle resourceBundle : list) {
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
			for (ResourceBundle resourceBundle : list) {
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
					LOG.debug(e);
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
		setLocale(LocaleBeanUtils.getDefaultLocale());
		return getObject(key);
	}

	/**
	 * 
	 * @param locale
	 */
	public void setLocale(final Locale locale) {
		this.locale = locale;
	}

}