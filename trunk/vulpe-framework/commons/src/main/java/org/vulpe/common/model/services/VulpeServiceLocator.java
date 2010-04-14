package org.vulpe.common.model.services;

import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import org.vulpe.common.ReflectUtil;
import org.vulpe.common.annotations.FactoryClass;
import org.vulpe.common.cache.VulpeCacheHelper;
import org.vulpe.common.factory.Factory;
import org.vulpe.common.factory.VulpeFactoryLocator;
import org.vulpe.common.helper.VulpeConfigHelper;
import org.vulpe.exception.VulpeSystemException;
import org.vulpe.model.services.Services;

/**
 * Class to lookup Services
 *
 * @author <a href="mailto:fabio.viana@activethread.com.br">Fábio Viana</a>
 */
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
	public <T extends Services> T lookup(final Class<T> classe) {
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
	@SuppressWarnings("unchecked")
	public <T extends Services> T getEJB(final Class<T> clazz) {
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
	@SuppressWarnings( { "unchecked", "unused" })
	private String getEJBName(final Class classe) {
		return VulpeConfigHelper.getProjectName().concat("/").concat(
				classe.getSimpleName()).concat("/remote");
	}

	/**
	 * Método auxiliar para obter a instancia do factory
	 *
	 * @param <T>
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <T extends Services> Factory<T> getFactory(final Class<T> clazz) {
		try {
			if (!VulpeCacheHelper.getInstance().contains(
					clazz.getName().concat(".factory"))) {
				final FactoryClass factoryClass = ReflectUtil.getInstance()
						.getAnnotationInClass(FactoryClass.class, clazz);
				final Factory<?> factory = VulpeFactoryLocator.getInstance()
						.getFactory(factoryClass.value());
				VulpeCacheHelper.getInstance().put(
						clazz.getName().concat(".factory"), factory);
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
}