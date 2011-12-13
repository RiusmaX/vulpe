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
package org.vulpe.commons;

import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.vulpe.commons.annotations.FactoryClass;
import org.vulpe.commons.factory.AbstractVulpeBeanFactory;
import org.vulpe.commons.factory.Factory;
import org.vulpe.commons.factory.VulpeFactoryLocator;
import org.vulpe.commons.helper.VulpeCacheHelper;
import org.vulpe.commons.helper.VulpeConfigHelper;
import org.vulpe.commons.util.VulpeReflectUtil;
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