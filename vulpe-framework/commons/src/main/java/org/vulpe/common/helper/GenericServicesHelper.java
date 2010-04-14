package org.vulpe.common.helper;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.vulpe.common.beans.AbstractVulpeBeanFactory;
import org.vulpe.common.model.services.VulpeServiceLocator;
import org.vulpe.model.services.GenericServices;
import org.vulpe.model.services.Services;


public final class GenericServicesHelper {

	private GenericServicesHelper() {
	}

	private static final Logger LOG = Logger.getLogger(GenericServicesHelper.class);

	/**
	 * Method find generic services and returns POJO or EJB implementation.
	 *
	 * @return Generic Services Implementation.
	 * @since 1.0
	 * @see Services
	 */
	public static GenericServices getService() {
		GenericServices service = null;
		try {
			service = (GenericServices) AbstractVulpeBeanFactory.getInstance().getBean(
					GenericServices.class.getSimpleName());
		} catch (NoSuchBeanDefinitionException e) {
			if (service == null) {
				service = VulpeServiceLocator.getInstance().getEJB(
						GenericServices.class);
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
	 * @see Services
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Services> T getService(final Class<T> serviceClass) {
		T service = null;
		try {
			service = (T) AbstractVulpeBeanFactory.getInstance().getBean(
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
