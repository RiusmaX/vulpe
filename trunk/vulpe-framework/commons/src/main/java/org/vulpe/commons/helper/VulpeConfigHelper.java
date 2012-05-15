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
package org.vulpe.commons.helper;

import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.vulpe.commons.VulpeConstants;
import org.vulpe.config.annotations.VulpeApplication;
import org.vulpe.config.annotations.VulpeDomains;
import org.vulpe.config.annotations.VulpeProject;

/**
 * Framework configuration helper.
 * 
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 * @since 1.0
 */
@SuppressWarnings( { "unchecked", "deprecation" })
public final class VulpeConfigHelper {

	private static final Logger LOG = Logger.getLogger(VulpeConfigHelper.class);
	private static final String DOMAINS_CONFIG_CLASS = "org.vulpe.config.domains.package-info";
	private static final String DOMAINS_CONFIG_BASE_CLASS = "org.vulpe.config.base.domains.package-info";
	private static final String CONFIG_CLASS = "org.vulpe.config.package-info";
	private static final String CONFIG_BASE_CLASS = "org.vulpe.config.base.package-info";
	private static ResourceBundle VULPE = null;
	private static ResourceBundle VULPE_APPLICATION = null;

	static {
		try {
			VULPE = ResourceBundle.getBundle(VulpeConstants.VULPE, new Locale(""));
			VULPE_APPLICATION = ResourceBundle
					.getBundle(VulpeConstants.APPLICATION, new Locale(""));
		} catch (Exception e) {
			LOG.error(e);
		}
	}

	/**
	 * 
	 * @param classPath
	 */
	private static void forceClassloader(final String classPath) {
		try {
			final Class config = Class.forName(classPath);
			@SuppressWarnings("unused")
			final VulpeApplication application = (VulpeApplication) config
					.getAnnotation(VulpeApplication.class);
			@SuppressWarnings("unused")
			final VulpeProject project = (VulpeProject) config.getAnnotation(VulpeProject.class);
		} catch (ClassNotFoundException e) {
			LOG.error(e);
		}
	}

	/**
	 * Method return true for audit enabled e false to audit disabled.
	 * 
	 * @since 1.0
	 * @return Enabled (true|false).
	 */
	public static boolean isDebugEnabled() {
		boolean enabled = false;
		final VulpeProject project = getProjectConfiguration();
		if (project != null) {
			enabled = project.debug();
		} else {
			if (VULPE_APPLICATION != null && VULPE_APPLICATION.containsKey("debug")) {
				if ("true".equals(VULPE_APPLICATION.getString("debug"))) {
					enabled = true;
				}
			} else if (VULPE.containsKey("debug")) {
				if ("true".equals(VULPE.getString("debug"))) {
					enabled = true;
				}
			}
		}
		return enabled;
	}

	/**
	 * Method return true for audit enabled e false to audit disabled.
	 * 
	 * @since 1.0
	 * @return Enabled (true|false).
	 */
	public static boolean isAuditEnabled() {
		boolean enabled = false;
		final VulpeProject project = getProjectConfiguration();
		if (project != null) {
			enabled = project.audit();
		} else {
			if (VULPE_APPLICATION != null && VULPE_APPLICATION.containsKey("audit")) {
				if ("true".equals(VULPE_APPLICATION.getString("audit"))) {
					enabled = true;
				}
			} else if (VULPE.containsKey("audit")) {
				if ("true".equals(VULPE.getString("audit"))) {
					enabled = true;
				}
			}
		}
		return enabled;
	}

	/**
	 * Method return date pattern.
	 * 
	 * @since 1.0
	 * @return Date Pattern. Example: MM/dd/yyyy.
	 */
	public static String getDatePattern() {
		String pattern = "MM/dd/yyyy";
		if (VULPE_APPLICATION != null && VULPE_APPLICATION.containsKey("datePattern")) {
			pattern = VULPE_APPLICATION.getString("datePattern");
		} else if (VULPE.containsKey("datePattern")) {
			pattern = VULPE.getString("datePattern");
		}
		return pattern;
	}
	
	/**
	 * Method return date time pattern.
	 * 
	 * @since 1.0
	 * @return Date Pattern. Example: MM/dd/yyyy HH:mm:ss.
	 */
	public static String getDateTimePattern() {
		String pattern = "MM/dd/yyyy HH:mm:ss";
		if (VULPE_APPLICATION != null && VULPE_APPLICATION.containsKey("dateTimePattern")) {
			pattern = VULPE_APPLICATION.getString("dateTimePattern");
		} else if (VULPE.containsKey("dateTimePattern")) {
			pattern = VULPE.getString("dateTimePattern");
		}
		return pattern;
	}

	/**
	 * Method return true for security enabled e false to audit disabled.
	 * 
	 * @since 1.0
	 * @return Enabled (true|false).
	 */
	public static boolean isSecurityEnabled() {
		boolean enabled = false;
		final VulpeProject project = getProjectConfiguration();
		if (project != null) {
			enabled = project.security();
		} else {
			if (VULPE_APPLICATION != null && VULPE_APPLICATION.containsKey("security")) {
				if ("true".equals(VULPE_APPLICATION.getString("security"))) {
					enabled = true;
				}
			} else if (VULPE.containsKey("security")) {
				if ("true".equals(VULPE.getString("security"))) {
					enabled = true;
				}
			}
		}
		return enabled;
	}

	/**
	 * 
	 * @since 1.0
	 * @return Name of theme.
	 */
	public static <T> T get(final Class<T> annotation) {
		try {
			Class config = null;
			if (VulpeDomains.class.isAssignableFrom(annotation)) {
				config = getDomainsConfig();
			} else {
				config = getConfig();
			}
			if (config != null) {
				return (T) config.getAnnotation(annotation);
			}
		} catch (Exception e) {
			LOG.error(e);
		}
		return null;
	}

	/**
	 * Method returns name of theme to application use.
	 * 
	 * @since 1.0
	 * @return Name of theme.
	 */
	public static String getTheme() {
		String themeName = "default";
		final VulpeProject project = getProjectConfiguration();
		if (project != null) {
			themeName = project.theme();
		} else {
			if (VULPE_APPLICATION != null && VULPE_APPLICATION.containsKey("theme")) {
				themeName = (String) VULPE_APPLICATION.getString("theme");
			} else if (VULPE.containsKey("theme")) {
				themeName = (String) VULPE.getString("theme");
			}
		}
		return themeName;
	}

	/**
	 * 
	 * @return
	 */
	public static VulpeApplication getApplicationConfiguration() {
		try {
			final Class config = getConfig();
			if (config != null) {
				final VulpeApplication application = (VulpeApplication) config
						.getAnnotation(VulpeApplication.class);
				return application;
			}
		} catch (Exception e) {
			LOG.error(e);
		}
		return null;
	}

	/**
	 * 
	 * @return
	 */
	public static VulpeProject getProjectConfiguration() {
		try {
			final Class config = getConfig();
			if (config != null) {
				final VulpeProject project = (VulpeProject) config
						.getAnnotation(VulpeProject.class);
				return project;
			}
		} catch (Exception e) {
			LOG.error(e);
		}
		return null;
	}

	/**
	 * 
	 * @return
	 */
	public static String getApplicationName() {
		String applicationName = "";
		final VulpeProject project = getProjectConfiguration();
		if (project != null) {
			applicationName = project.name();
		} else {
			final VulpeApplication application = getApplicationConfiguration();
			if (application != null) {
				applicationName = application.name();
			}
		}
		return applicationName;
	}

	/**
	 * 
	 * @return
	 */
	public static String getI18n() {
		String i18n = "";
		final VulpeProject project = getProjectConfiguration();
		if (project != null) {
			i18n = project.i18n().toString();
		} else {
			final VulpeApplication application = getApplicationConfiguration();
			if (application != null) {
				i18n = application.i18n().toString();
			}
		}
		return i18n;
	}

	/**
	 * 
	 * @return
	 */
	public static String getI18nManager() {
		String i18nManager = "";
		final VulpeProject project = getProjectConfiguration();
		if (project != null) {
			i18nManager = project.i18nManager();
		} else {
			final VulpeApplication application = getApplicationConfiguration();
			if (application != null) {
				i18nManager = application.i18nManager();
			}
		}
		return i18nManager;
	}

	/**
	 * 
	 * @return
	 */
	public static String getApplicationPackage() {
		String applicationPackage = "";
		final VulpeProject project = getProjectConfiguration();
		if (project != null) {
			applicationPackage = project.projectPackage();
		} else {
			final VulpeApplication application = getApplicationConfiguration();
			if (application != null) {
				applicationPackage = application.applicationPackage();
			}
		}
		return applicationPackage;
	}

	/**
	 * 
	 * @return
	 */
	private static Class getConfig() {
		forceClassloader(CONFIG_CLASS);
		Class config = null;
		try {
			forceClassloader(CONFIG_CLASS);
			config = Class.forName(CONFIG_CLASS);
		} catch (Exception e) {
			LOG.error("Error in load config application class.");
		}
		if (config == null) {
			try {
				forceClassloader(CONFIG_BASE_CLASS);
				config = Class.forName(CONFIG_BASE_CLASS);
			} catch (Exception e) {
				LOG.error("Error in load config base framework class.");
			}
		}
		return config;
	}

	/**
	 * 
	 * @return
	 */
	private static Class getDomainsConfig() {
		forceClassloader(DOMAINS_CONFIG_CLASS);
		Class domainsConfig = null;
		try {
			forceClassloader(DOMAINS_CONFIG_CLASS);
			domainsConfig = Class.forName(DOMAINS_CONFIG_CLASS);
		} catch (Exception e) {
			LOG.error("Error in load config application class.");
		}
		if (domainsConfig == null) {
			try {
				forceClassloader(DOMAINS_CONFIG_BASE_CLASS);
				domainsConfig = Class.forName(DOMAINS_CONFIG_BASE_CLASS);
			} catch (Exception e) {
				LOG.error("Error in load domain config base framework class.");
			}
		}
		return domainsConfig;
	}

	/**
	 * 
	 * @return
	 */
	public static Locale getLocale() {
		Locale locale = new Locale("en", "US");
		final VulpeApplication application = getApplicationConfiguration();
		if (application != null) {
			String[] localeParts = application.localeCode().split("\\_");
			return new Locale(localeParts[0], localeParts[1]);
		}
		return locale;
	}

}
