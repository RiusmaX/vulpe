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
package org.vulpe.controller.helper;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.scannotation.AnnotationDB;
import org.scannotation.WarUrlFinder;
import org.vulpe.commons.VulpeConstants;
import org.vulpe.commons.beans.ValueBean;
import org.vulpe.commons.helper.GenericServicesHelper;
import org.vulpe.commons.helper.VulpeCacheHelper;
import org.vulpe.commons.helper.VulpeConfigHelper;
import org.vulpe.commons.util.VulpeHashMap;
import org.vulpe.config.annotations.VulpeDomains;
import org.vulpe.model.annotations.CachedClass;
import org.vulpe.model.annotations.CachedEnum;
import org.vulpe.model.entity.VulpeEntity;

/**
 * Class to control Cached Objects.
 *
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 *
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public final class VulpeCachedObjectsHelper {

	private VulpeCachedObjectsHelper() {
	}

	private static final Logger LOG = LoggerFactory.getLogger(VulpeCachedObjectsHelper.class);

	private static AnnotationDB annotationDB;

	/**
	 * Load list of classes noted with @CachedClass
	 *
	 * @param servletContext
	 * @return
	 */
	private static Set<String> loadCachedClasses(final ServletContext servletContext) {
		scannotation(servletContext);
		final Set<String> cachedClasses = annotationDB.getAnnotationIndex().get(
				CachedClass.class.getName());
		return cachedClasses;
	}

	/**
	 * Load list of classes noted with @CachedEnum
	 *
	 * @param servletContext
	 * @return
	 */
	private static Set<String> loadCachedEnums(final ServletContext servletContext) {
		scannotation(servletContext);
		final Set<String> cachedEnums = annotationDB.getAnnotationIndex().get(
				CachedEnum.class.getName());
		return cachedEnums;
	}

	/**
	 * Scanning libs of application to find noted classes.
	 *
	 * @param servletContext
	 * @return
	 * @return
	 */
	private static void scannotation(final ServletContext servletContext) {
		if (annotationDB == null) {
			final URL urlWebInfClasses = WarUrlFinder.findWebInfClassesPath(servletContext);
			final URL[] urlsWebInfLib = WarUrlFinder.findWebInfLibClasspaths(servletContext);
			final List<URL> urls = new ArrayList<URL>();
			for (final URL url : urlsWebInfLib) {
				final String jarName = url.getFile().substring(url.getFile().lastIndexOf("/") + 1);
				if (!VulpeConfigHelper.isSecurityEnabled()
						&& jarName.contains(VulpeConstants.VULPE_SECURITY)) {
					continue;
				}
				if (jarName.contains(VulpeConstants.VULPE)
						|| jarName.contains(VulpeConstants.DOMAINS)) {
					urls.add(url);
				}
			}
			final URL[] urlsFrameworkApplication = new URL[urls.size()];
			int count = 0;
			for (final URL url : urls) {
				urlsFrameworkApplication[count] = url;
				++count;
			}
			annotationDB = new AnnotationDB();
			try {
				if (urlWebInfClasses != null) {
					annotationDB.scanArchives(urlWebInfClasses);
				}
				if (urlsFrameworkApplication != null && urlsFrameworkApplication.length > 0) {
					annotationDB.scanArchives(urlsFrameworkApplication);
				}
			} catch (IOException e) {
				LOG.error(e.getMessage());
			}
		}
	}

	/**
	 * Puts domains objects with annotations (@CachedClass and @CachedEnum) in
	 * cache.
	 *
	 * @param servletContext
	 */
	public static void putAnnotedObjectsInCache(final ServletContext servletContext) {
		final Set<String> cachedClasses = loadCachedClasses(servletContext);
		final VulpeHashMap<String, Object> mapCachedClass = new VulpeHashMap<String, Object>();
		if (cachedClasses != null && !cachedClasses.isEmpty()) {
			for (final String cachedClass : cachedClasses) {
				try {
					final Class classicClass = Class.forName(cachedClass);
					if (VulpeEntity.class.isAssignableFrom(classicClass)) {
						final Class<? extends VulpeEntity<?>> clazz = (Class<? extends VulpeEntity<?>>) classicClass;
						final VulpeEntity<?> entity = clazz.newInstance();
						final CachedClass cachedClassAnnotation = clazz
								.getAnnotation(CachedClass.class);
						if (cachedClassAnnotation != null) {
							entity.setQueryConfigurationName(cachedClassAnnotation
									.queryConfigurationName());
							mapCachedClass.put(clazz.getSimpleName(), GenericServicesHelper
									.getService().getList(entity));
						}
					}
				} catch (Exception e) {
					LOG.error(e.getMessage());
				}
			}
		}
		VulpeCacheHelper.getInstance().put(VulpeConstants.CACHED_CLASSES, mapCachedClass);
		final Set<String> cachedEnums = loadCachedEnums(servletContext);
		if (cachedEnums != null && !cachedEnums.isEmpty()) {
			final VulpeHashMap<String, Object> mapCachedEnum = new VulpeHashMap<String, Object>();
			final VulpeHashMap<String, String> mapCachedEnumArray = new VulpeHashMap<String, String>();
			for (final String cachedEnum : cachedEnums) {
				try {
					final Class classicClass = Class.forName(cachedEnum);
					if (classicClass.isEnum()) {
						try {
							final String enumName = classicClass.getSimpleName();
							final Object[] values = classicClass.getEnumConstants();
							final List<ValueBean> list = new ArrayList<ValueBean>();
							final StringBuilder valuesDescription = new StringBuilder();
							final StringBuilder array = new StringBuilder("#{");
							for (Object object : values) {
								if (valuesDescription.length() > 0) {
									valuesDescription.append(", ");
									array.append(", ");
								}
								valuesDescription.append(object);
								final ValueBean value = new ValueBean(object.toString(),
										classicClass.getName().concat(".")
												.concat(object.toString()));
								array.append("'");
								array.append(value.getId());
								array.append("':'");
								array.append(value.getValue()).append("'");
								list.add(value);
							}
							array.append("}");
							mapCachedEnumArray.put(enumName, array.toString());
							LOG.debug("Reading object: ".concat(enumName).concat(" [")
									.concat(valuesDescription.toString()).concat("]"));
							mapCachedEnum.put(enumName, list);
						} catch (Exception e) {
							LOG.error(e.getMessage());
						}
					}
				} catch (Exception e) {
					LOG.error(e.getMessage());
				}
			}
			VulpeCacheHelper.getInstance().put(VulpeConstants.CACHED_ENUMS, mapCachedEnum);
			VulpeCacheHelper.getInstance().put(VulpeConstants.CACHED_ENUMS_ARRAY,
					mapCachedEnumArray);
		}
	}

	/**
	 * Puts domains objects configured by @VulpeDomains annotation in cache.
	 *
	 * @param servletContext
	 */
	public static void putObjectsInCache(final ServletContext servletContext) {
		final Class<? extends VulpeEntity<?>>[] cachedClass = VulpeConfigHelper.get(
				VulpeDomains.class).cachedClass();
		final VulpeHashMap<String, Object> mapCachedClass = new VulpeHashMap<String, Object>();
		if (cachedClass != null) {
			for (Class<? extends VulpeEntity<?>> clazz : cachedClass) {
				try {
					final VulpeEntity<?> entity = clazz.newInstance();
					final CachedClass cachedClassAnnotation = clazz
							.getAnnotation(CachedClass.class);
					entity.setQueryConfigurationName(cachedClassAnnotation.queryConfigurationName());
					mapCachedClass.put(clazz.getSimpleName(), GenericServicesHelper.getService()
							.getList(entity));
				} catch (Exception e) {
					LOG.error(e.getMessage());
				}
			}
		}
		VulpeCacheHelper.getInstance().put(VulpeConstants.CACHED_CLASSES, mapCachedClass);
		final Class[] cachedEnum = VulpeConfigHelper.get(VulpeDomains.class).cachedEnum();
		if (cachedEnum != null) {
			final String projectName = VulpeConfigHelper.getApplicationName();
			final VulpeHashMap<String, Object> mapCachedEnum = new VulpeHashMap<String, Object>();
			final VulpeHashMap<String, String> mapCachedEnumArray = new VulpeHashMap<String, String>();
			for (Class clazz : cachedEnum) {
				try {
					final String enumName = clazz.getSimpleName();
					final Object[] values = clazz.getEnumConstants();
					final List<ValueBean> list = new ArrayList<ValueBean>();
					final StringBuilder valuesDescription = new StringBuilder();
					final StringBuilder array = new StringBuilder("#{");
					for (final Object object : values) {
						if (valuesDescription.length() > 0) {
							valuesDescription.append(", ");
							array.append(", ");
						}
						valuesDescription.append(object);
						final ValueBean value = new ValueBean(object.toString(),
								VulpeConstants.View.LABEL.concat(projectName)
										.concat(VulpeConstants.View.ENUM).concat(enumName)
										.concat(".").concat(object.toString()));
						array.append("'");
						array.append(value.getId());
						array.append("':'");
						array.append(value.getValue()).append("'");
						list.add(value);
					}
					array.append("}");
					mapCachedEnumArray.put(enumName, array.toString());
					LOG.debug("Reading object: ".concat(enumName).concat(" [")
							.concat(valuesDescription.toString()).concat("]"));
					mapCachedEnum.put(enumName, list);
				} catch (Exception e) {
					LOG.error(e.getMessage());
				}
			}
			VulpeCacheHelper.getInstance().put(VulpeConstants.CACHED_ENUMS, mapCachedEnum);
			VulpeCacheHelper.getInstance().put(VulpeConstants.CACHED_ENUMS_ARRAY,
					mapCachedEnumArray);
		}
	}
}
