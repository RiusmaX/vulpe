package br.com.caelum.vraptor.ioc.spring;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;

import br.com.caelum.vraptor.config.BasicConfiguration;
import br.com.caelum.vraptor.ioc.AbstractComponentRegistry;
import br.com.caelum.vraptor.ioc.Container;

public class VulpeSpringBasedContainer extends AbstractComponentRegistry implements Container {

	private VulpeVRaptorApplicationContext applicationContext;

	private final List<Class<?>> toRegister = new ArrayList<Class<?>>();

	public VulpeSpringBasedContainer(ApplicationContext parentContext, BasicConfiguration config) {
		applicationContext = new VulpeVRaptorApplicationContext(this, config);
		applicationContext.setParent(parentContext);
	}

	public void register(Class<?> requiredType, Class<?> componentType) {
		if (applicationContext.isActive()) {
			applicationContext.register(componentType);
		} else {
			toRegister.add(componentType);
		}
	}

	public List<Class<?>> getToRegister() {
		return toRegister;
	}

	public <T> T instanceFor(Class<T> type) {
		return applicationContext.getBean(type);
	}

	public <T> boolean canProvide(Class<T> type) {
		return BeanFactoryUtils.beanNamesForTypeIncludingAncestors(applicationContext, type).length > 0;
	}

	public void start(ServletContext context) {
		applicationContext.setServletContext(context);
		applicationContext.refresh();
		applicationContext.start();
	}

	public void stop() {
		applicationContext.stop();
		applicationContext.destroy();
		applicationContext = null;
	}

}
