package org.vulpe.common.beans;

import org.vulpe.common.cache.VulpeCacheHelper;
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
						.forName("org.vulpe.common.beans.SpringBeanFactory");
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