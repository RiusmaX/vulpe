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
package org.vulpe.common.factory;

import org.vulpe.common.cache.VulpeCacheHelper;
import org.vulpe.exception.VulpeSystemException;

/**
 * Utility class to manager Factories intances
 *
 * @author <a href="mailto:fabio.viana@activethread.com.br">Fábio Viana</a>
 */
public class VulpeFactoryLocator {
	/**
	 * Returns VulpeFactoryLocator instance
	 */
	public static VulpeFactoryLocator getInstance() {
		final VulpeCacheHelper cache = VulpeCacheHelper.getInstance();
		if (!cache.contains(VulpeFactoryLocator.class)) {
			cache.put(VulpeFactoryLocator.class, new VulpeFactoryLocator());
		}
		return cache.get(VulpeFactoryLocator.class);
	}

	protected VulpeFactoryLocator() {
		// default constructor
	}

	/**
	 * Returns Factory<T> instance
	 */
	@SuppressWarnings("unchecked")
	public <T extends Factory<?>> T getFactory(final Class<T> classFactory) {
		try {
			final VulpeCacheHelper cache = VulpeCacheHelper.getInstance();
			if (!cache.contains(classFactory)) {
				cache.put(classFactory, classFactory.newInstance());
			}
			return (T) cache.get(classFactory);
		} catch (Exception e) {
			throw new VulpeSystemException(e);
		}
	}
}