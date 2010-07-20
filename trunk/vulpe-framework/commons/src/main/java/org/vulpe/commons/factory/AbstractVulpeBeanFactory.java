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
package org.vulpe.commons.factory;

import org.vulpe.commons.factory.AbstractVulpeBeanFactory;
import org.vulpe.commons.helper.VulpeCacheHelper;
import org.vulpe.exception.VulpeSystemException;

public abstract class AbstractVulpeBeanFactory {

	/**
	 * Returns AbstractVulpeBeanFactory instance
	 */
	@SuppressWarnings("unchecked")
	public static AbstractVulpeBeanFactory getInstance() {
		final VulpeCacheHelper cache = VulpeCacheHelper.getInstance();
		if (!cache.contains(AbstractVulpeBeanFactory.class)) {
			try {
				final Class<AbstractVulpeBeanFactory> beanFactory = (Class<AbstractVulpeBeanFactory>) Class
						.forName("org.vulpe.commons.beans.SpringBeanFactory");
				cache.put(AbstractVulpeBeanFactory.class, beanFactory.newInstance());
			} catch (Exception e) {
				throw new VulpeSystemException(e);
			}
		}
		return cache.get(AbstractVulpeBeanFactory.class);
	}

	/**
	 * Returns bean by name
	 */
	public abstract <T> T getBean(String beanName);
}