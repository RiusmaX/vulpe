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
package org.vulpe.controller.helper;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.scannotation.AnnotationDB;
import org.scannotation.WarUrlFinder;
import org.vulpe.commons.VulpeConstants;
import org.vulpe.commons.beans.ValueBean;
import org.vulpe.commons.cache.VulpeCacheHelper;
import org.vulpe.commons.helper.GenericServicesHelper;
import org.vulpe.commons.helper.VulpeConfigHelper;
import org.vulpe.config.annotations.VulpeDomains;
import org.vulpe.model.entity.VulpeBaseEntity;

/**
 * Class to control Cached Objects.
 *
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 *
 */
@SuppressWarnings("unchecked")
public final class CachedObjectsHelper {

	private CachedObjectsHelper() {
	}

	private static final Logger LOG = Logger.getLogger(CachedObjectsHelper.class);

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
				org.vulpe.model.annotations.CachedClass.class.getName());
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
				org.vulpe.model.annotations.CachedEnum.class.getName());
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
			for (URL url : urlsWebInfLib) {
				final String jarName = url.getFile().substring(url.getFile().lastIndexOf("/") + 1);
				if (jarName.contains(VulpeConstants.FRAMEWORK)
						|| jarName.contains(VulpeConstants.DOMAINS)) {
					urls.add(url);
				}
			}
			final URL[] urlsFrameworkApplication = new URL[urls.size()];
			int count = 0;
			for (URL url : urls) {
				urlsFrameworkApplication[count] = url;
				count++;
			}
			annotationDB = new AnnotationDB();
			try {
				annotationDB.scanArchives(urlWebInfClasses);
				annotationDB.scanArchives(urlsFrameworkApplication);
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
		final Map<String, Object> mapCachedClass = new HashMap<String, Object>();
		if (cachedClasses != null && !cachedClasses.isEmpty()) {
			for (String cachedClass : cachedClasses) {
				try {
					final Class classicClass = Class.forName(cachedClass);
					if (VulpeBaseEntity.class.isAssignableFrom(classicClass)) {
						final Class<? extends VulpeBaseEntity<?>> clazz = (Class<? extends VulpeBaseEntity<?>>) classicClass;
						mapCachedClass.put(clazz.getSimpleName(), GenericServicesHelper
								.getService().getList(clazz.newInstance()));
					}
				} catch (Exception e) {
					LOG.error(e);
				}
			}
		}
		VulpeCacheHelper.getInstance().put(VulpeConstants.CACHED_CLASS, mapCachedClass);
		final Set<String> cachedEnums = loadCachedEnums(servletContext);
		if (cachedEnums != null && !cachedEnums.isEmpty()) {
			final Map<String, Object> mapCachedEnum = new HashMap<String, Object>();
			final Map<String, String> mapCachedEnumArray = new HashMap<String, String>();
			for (String cachedEnum : cachedEnums) {
				try {
					final Class classicClass = Class.forName(cachedEnum);
					if (classicClass.isEnum()) {
						try {
							final String enumName = classicClass.getSimpleName();
							final Object[] values = classicClass.getEnumConstants();
							final List<ValueBean> list = new ArrayList<ValueBean>();
							final StringBuilder valuesDescricption = new StringBuilder();
							final StringBuilder array = new StringBuilder("#{");
							for (Object object : values) {
								if (valuesDescricption.length() > 0) {
									valuesDescricption.append(", ");
									array.append(", ");
								}
								valuesDescricption.append(object);
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
									.concat(valuesDescricption.toString()).concat("]"));
							mapCachedEnum.put(enumName, list);
						} catch (Exception e) {
							LOG.error(e);
						}
					}
				} catch (Exception e) {
					LOG.error(e);
				}
			}
			VulpeCacheHelper.getInstance().put(VulpeConstants.CACHED_ENUM, mapCachedEnum);
			VulpeCacheHelper.getInstance()
					.put(VulpeConstants.CACHED_ENUM_ARRAY, mapCachedEnumArray);
		}
	}

	/**
	 * Puts domains objects configured by @VulpeDomains annotation in cache.
	 *
	 * @param servletContext
	 */
	public static void putObjectsInCache(final ServletContext servletContext) {
		final Class<? extends VulpeBaseEntity<?>>[] cachedClass = VulpeConfigHelper.get(
				VulpeDomains.class).cachedClass();
		final Map<String, Object> mapCachedClass = new HashMap<String, Object>();
		if (cachedClass != null) {
			for (Class<? extends VulpeBaseEntity<?>> clazz : cachedClass) {
				try {
					mapCachedClass.put(clazz.getSimpleName(), GenericServicesHelper.getService()
							.getList(clazz.newInstance()));
				} catch (Exception e) {
					LOG.error(e);
				}
			}
		}
		VulpeCacheHelper.getInstance().put(VulpeConstants.CACHED_CLASS, mapCachedClass);
		final Class[] cachedEnum = VulpeConfigHelper.get(VulpeDomains.class).cachedEnum();
		if (cachedEnum != null) {
			final String projectName = VulpeConfigHelper.getProjectName();
			final Map<String, Object> mapCachedEnum = new HashMap<String, Object>();
			final Map<String, String> mapCachedEnumArray = new HashMap<String, String>();
			for (Class clazz : cachedEnum) {
				try {
					final String enumName = clazz.getSimpleName();
					final Object[] values = clazz.getEnumConstants();
					final List<ValueBean> list = new ArrayList<ValueBean>();
					final StringBuilder valuesDescricption = new StringBuilder();
					final StringBuilder array = new StringBuilder("#{");
					for (Object object : values) {
						if (valuesDescricption.length() > 0) {
							valuesDescricption.append(", ");
							array.append(", ");
						}
						valuesDescricption.append(object);
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
							.concat(valuesDescricption.toString()).concat("]"));
					mapCachedEnum.put(enumName, list);
				} catch (Exception e) {
					LOG.error(e);
				}
			}
			VulpeCacheHelper.getInstance().put(VulpeConstants.CACHED_ENUM, mapCachedEnum);
			VulpeCacheHelper.getInstance()
					.put(VulpeConstants.CACHED_ENUM_ARRAY, mapCachedEnumArray);
		}
	}
}
