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

import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.vulpe.commons.VulpeServiceLocator;
import org.vulpe.commons.annotations.FactoryClass;
import org.vulpe.commons.factory.AbstractVulpeBeanFactory;
import org.vulpe.commons.factory.Factory;
import org.vulpe.commons.util.VulpeReflectUtil;
import org.vulpe.commons.factory.VulpeFactoryLocator;
import org.vulpe.commons.helper.VulpeCacheHelper;
import org.vulpe.commons.helper.VulpeConfigHelper;
import org.vulpe.exception.VulpeSystemException;
import org.vulpe.model.services.VulpeService;

/**
 * Class to lookup VulpeService
 *
 * @author <a href="mailto:fabio.viana@vulpe.org">Fábio Viana</a>
 */
@SuppressWarnings("unchecked")
public class VulpeServiceLocator {
	/**
	 * Returns VulpeServiceLocator instance
	 */
	public static VulpeServiceLocator getInstance() {
		final VulpeCacheHelper cache = VulpeCacheHelper.getInstance();
		if (!cache.contains(VulpeServiceLocator.class)) {
			cache.put(VulpeServiceLocator.class, new VulpeServiceLocator());
		}
		return cache.get(VulpeServiceLocator.class);
	}

	protected VulpeServiceLocator() {
		// default constructor
	}

	/**
	 * Returns instance of service created by Factory
	 *
	 * @param <T>
	 *            Service Interface Type
	 * @param classe
	 *            Service Interface class
	 * @return Instance of service
	 */
	public <T extends VulpeService> T lookup(final Class<T> classe) {
		try {
			final Factory<T> factory = getFactory(classe);
			return factory.instance();
		} catch (Exception e) {
			if (e instanceof VulpeSystemException) {
				throw (VulpeSystemException) e;
			} else {
				throw new VulpeSystemException(e);
			}
		}
	}

	/**
	 *
	 * @param clazz
	 * @return
	 */
	public <T extends VulpeService> T getEJB(final Class<T> clazz) {
		try {
			final InitialContext ctx = new InitialContext();
			final Object objref = ctx.lookup(clazz.getSimpleName());
			return (T) PortableRemoteObject.narrow(objref, clazz);
		} catch (Exception e) {
			if (e instanceof VulpeSystemException) {
				throw (VulpeSystemException) e;
			} else {
				throw new VulpeSystemException(e);
			}
		}
	}

	/**
	 *
	 * @param classe
	 * @return
	 */
	@SuppressWarnings("unused")
	private String getEJBName(final Class classe) {
		return VulpeConfigHelper.getProjectName().concat("/").concat(classe.getSimpleName())
				.concat("/remote");
	}

	/**
	 * M�todo auxiliar para obter a instancia do factory
	 *
	 * @param <T>
	 * @param clazz
	 * @return
	 */
	protected <T extends VulpeService> Factory<T> getFactory(final Class<T> clazz) {
		try {
			if (!VulpeCacheHelper.getInstance().contains(clazz.getName().concat(".factory"))) {
				final FactoryClass factoryClass = VulpeReflectUtil
						.getAnnotationInClass(FactoryClass.class, clazz);
				final Factory<?> factory = VulpeFactoryLocator.getInstance().getFactory(
						factoryClass.value());
				VulpeCacheHelper.getInstance().put(clazz.getName().concat(".factory"), factory);
			}
			return (Factory<T>) VulpeCacheHelper.getInstance().get(
					clazz.getName().concat(".factory"));
		} catch (Exception e) {
			if (e instanceof VulpeSystemException) {
				throw (VulpeSystemException) e;
			} else {
				throw new VulpeSystemException(e);
			}
		}
	}

	/**
	 * Method find specific service returns POJO or EJB implementation.
	 *
	 * @param serviceClass
	 * @return Service Implementation.
	 * @since 1.0
	 * @see VulpeService
	 */
	public <T extends VulpeService> T getService(final Class<T> serviceClass) {
		T service = null;
		try {
			service = (T) AbstractVulpeBeanFactory.getInstance().getBean(
					serviceClass.getSimpleName());
		} catch (NoSuchBeanDefinitionException e) {
			if (service == null) {
				service = getEJB(serviceClass);
			}
		}
		return service;
	}
}