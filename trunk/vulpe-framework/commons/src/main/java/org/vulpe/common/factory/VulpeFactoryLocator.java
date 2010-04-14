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