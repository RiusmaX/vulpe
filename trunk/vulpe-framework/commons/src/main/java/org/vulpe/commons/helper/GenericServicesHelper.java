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

import org.apache.log4j.Logger;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.vulpe.commons.factory.SpringBeanFactory;
import org.vulpe.commons.model.services.VulpeServiceLocator;
import org.vulpe.model.services.GenericService;
import org.vulpe.model.services.VulpeService;


public final class GenericServicesHelper {

	private GenericServicesHelper() {
	}

	private static final Logger LOG = Logger.getLogger(GenericServicesHelper.class);

	/**
	 * Method find generic services and returns POJO or EJB implementation.
	 *
	 * @return Generic VulpeService Implementation.
	 * @since 1.0
	 * @see VulpeService
	 */
	public static GenericService getService() {
		GenericService service = null;
		try {
			service = (GenericService) SpringBeanFactory.getInstance().getBean(
					GenericService.class.getSimpleName());
		} catch (NoSuchBeanDefinitionException e) {
			if (service == null) {
				service = VulpeServiceLocator.getInstance().getEJB(
						GenericService.class);
			}
			LOG.error(e);
		}
		return service;
	}

	/**
	 * Method find specific service and returns POJO or EJB implementation.
	 *
	 * @param serviceClass
	 * @return Service Implementation.
	 * @since 1.0
	 * @see VulpeService
	 */
	@SuppressWarnings("unchecked")
	public static <T extends VulpeService> T getService(final Class<T> serviceClass) {
		T service = null;
		try {
			service = (T) SpringBeanFactory.getInstance().getBean(
					serviceClass.getSimpleName());
		} catch (NoSuchBeanDefinitionException e) {
			if (service == null) {
				service = VulpeServiceLocator.getInstance().getEJB(serviceClass);
			}
			LOG.error(e);
		}
		return service;
	}
}
