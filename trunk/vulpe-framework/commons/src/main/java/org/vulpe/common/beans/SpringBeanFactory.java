package org.vulpe.common.beans;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.access.BeanFactoryReference;
import org.springframework.context.ApplicationContext;
import org.springframework.context.access.ContextSingletonBeanFactoryLocator;

/**
 *
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 *
 */
public class SpringBeanFactory extends AbstractVulpeBeanFactory {

	private transient BeanFactory factory;

	public SpringBeanFactory() {
		final BeanFactoryReference reference = ContextSingletonBeanFactoryLocator
				.getInstance().useBeanFactory(null);
		factory = reference.getFactory();
		if (factory instanceof ApplicationContext) {
			factory = ((ApplicationContext) factory)
					.getAutowireCapableBeanFactory();
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T getBean(final String beanName) {
		return (T) factory.getBean(beanName);
	}
}