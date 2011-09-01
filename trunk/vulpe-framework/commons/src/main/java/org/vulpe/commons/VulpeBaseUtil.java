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
package org.vulpe.commons;

import org.apache.log4j.Logger;
import org.vulpe.commons.factory.AbstractVulpeBeanFactory;
import org.vulpe.commons.helper.VulpeCacheHelper;
import org.vulpe.commons.util.VulpeHashMap;
import org.vulpe.model.services.VulpeService;

/**
 *
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 */
@SuppressWarnings("unchecked")
public class VulpeBaseUtil {

	public static final Logger LOG = Logger.getLogger(VulpeBaseUtil.class);
	
	/**
	 * Method find specific service returns POJO or EJB implementation.
	 *
	 * @param serviceClass
	 * @return Service Implementation.
	 * @since 1.0
	 * @see VulpeService
	 */
	public <T extends VulpeService> T getService(final Class<T> serviceClass) {
		return VulpeServiceLocator.getInstance().getService(serviceClass);
	}

	/**
	 * Retrieves a Spring Bean by name.
	 *
	 * @param <T>
	 *            Class type to return
	 * @param beanName
	 *            Name of Component/Service/Repository
	 * @return Bean converted to Class type
	 * @since 1.0
	 */
	public <T> T getBean(final String beanName) {
		return (T) AbstractVulpeBeanFactory.getInstance().getBean(beanName);
	}

	/**
	 * Retrieves a Spring Bean by class.
	 *
	 * @param <T>
	 *            Class type to return
	 * @param clazz
	 *            Component/Service/Repository class
	 * @return Bean converted to Class type
	 * @since 1.0
	 */
	public <T> T getBean(final Class<T> clazz) {
		return (T) getBean(clazz.getSimpleName());
	}

	public VulpeHashMap<String, Object> getCachedClass() {
		return VulpeCacheHelper.getInstance().get(VulpeConstants.CACHED_CLASSES);
	}

	public VulpeHashMap<String, Object> getCachedEnum() {
		return VulpeCacheHelper.getInstance().get(VulpeConstants.CACHED_ENUMS);
	}

	public VulpeHashMap<String, Object> getCachedEnumsArray() {
		return VulpeCacheHelper.getInstance().get(VulpeConstants.CACHED_ENUMS_ARRAY);
	}
}
