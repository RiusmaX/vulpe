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
package org.vulpe.security.commons;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.vulpe.commons.beans.AbstractVulpeBeanFactory;
import org.vulpe.commons.model.services.VulpeServiceLocator;
import org.vulpe.model.services.Services;

/**
 * Vulpe Security Callback utility class.
 * 
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings("unchecked")
public class VulpeSecurityUtil {

	/**
	 * Method find specific service returns POJO or EJB implementation.
	 * 
	 * @param serviceClass
	 * @return Service Implementation.
	 * @since 1.0
	 * @see Services
	 */
	public <T extends Services> T getService(final Class<T> serviceClass) {
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

	/**
	 * Retrieves Spring Security Authentication.
	 * 
	 * @return Authentication Interface
	 * @since 1.0
	 */
	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
}