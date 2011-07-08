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
package org.vulpe.commons.helper;

import java.util.Locale;

import org.apache.log4j.Logger;
import org.vulpe.config.annotations.VulpeDomains;
import org.vulpe.config.annotations.VulpeProject;

/**
 * Framework configuration helper.
 *
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 * @since 1.0
 */
@SuppressWarnings( { "unchecked" })
public final class VulpeConfigHelper {

	private VulpeConfigHelper() {
	}

	private static final Logger LOG = Logger.getLogger(VulpeConfigHelper.class);
	private static final String DOMAINS_CONFIG_CLASS = "org.vulpe.config.domains.package-info";
	private static final String DOMAINS_CONFIG_BASE_CLASS = "org.vulpe.config.base.domains.package-info";
	private static final String CONFIG_CLASS = "org.vulpe.config.package-info";
	private static final String CONFIG_BASE_CLASS = "org.vulpe.config.base.package-info";

	/**
	 *
	 * @param classPath
	 */
	private static void forceClassloader(final String classPath) {
		try {
			final Class config = Class.forName(classPath);
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
	public static boolean isAuditEnabled() {
		boolean enabled = true;
		try {
			final Class config = getConfig();
			if (config != null) {
				final VulpeProject project = get(VulpeProject.class);
				if (project != null) {
					enabled = project.audit();
				}
			}
		} catch (Exception e) {
			LOG.error(e);
		}
		return enabled;
	}

	/**
	 * Method return true for security enabled e false to audit disabled.
	 *
	 * @since 1.0
	 * @return Enabled (true|false).
	 */
	public static boolean isSecurityEnabled() {
		boolean enabled = true;
		try {
			final Class config = getConfig();
			if (config != null) {
				final VulpeProject project = get(VulpeProject.class);
				if (project != null) {
					enabled = project.security();
				}
			}
		} catch (Exception e) {
			LOG.error(e);
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
		try {
			final Class config = getConfig();
			if (config != null) {
				final VulpeProject project = get(VulpeProject.class);
				if (project != null) {
					themeName = project.theme();
				}
			}
		} catch (Exception e) {
			LOG.error(e);
		}
		return themeName;
	}

	/**
	 *
	 * @return
	 */
	public static VulpeProject getProjectConfiguration() {
		try {
			final Class config = getConfig();
			if (config != null) {
				VulpeProject project = (VulpeProject) config.getAnnotation(VulpeProject.class);
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
	public static String getProjectName() {
		try {
			final Class config = getConfig();
			if (config != null) {
				VulpeProject project = (VulpeProject) config.getAnnotation(VulpeProject.class);
				if (project != null) {
					return project.name();
				}
			}
		} catch (Exception e) {
			LOG.error(e);
		}
		return "";
	}

	/**
	 *
	 * @return
	 */
	public static String getI18n() {
		try {
			final Class config = getConfig();
			if (config != null) {
				VulpeProject project = (VulpeProject) config.getAnnotation(VulpeProject.class);
				if (project != null) {
					return project.i18n().toString();
				}
			}
		} catch (Exception e) {
			LOG.error(e);
		}
		return "";
	}

	/**
	 *
	 * @return
	 */
	public static String getI18nManager() {
		try {
			final Class config = getConfig();
			if (config != null) {
				VulpeProject project = (VulpeProject) config.getAnnotation(VulpeProject.class);
				if (project != null) {
					return project.i18nManager();
				}
			}
		} catch (Exception e) {
			LOG.error(e);
		}
		return "";
	}

	/**
	 *
	 * @return
	 */
	public static String getProjectPackage() {
		try {
			final Class config = getConfig();
			if (config != null) {
				VulpeProject project = (VulpeProject) config.getAnnotation(VulpeProject.class);
				if (project != null) {
					return project.projectPackage();
				}
			}
		} catch (Exception e) {
			LOG.error(e);
		}
		return "";
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
		try {
			final Class config = getConfig();
			if (config != null) {
				VulpeProject project = (VulpeProject) config.getAnnotation(VulpeProject.class);
				if (project != null) {
					String[] locale = project.localeCode().split("\\_");
					return new Locale(locale[0], locale[1]);
				}
			}
		} catch (Exception e) {
			LOG.error(e);
		}
		return new Locale("en", "US");
	}

}
