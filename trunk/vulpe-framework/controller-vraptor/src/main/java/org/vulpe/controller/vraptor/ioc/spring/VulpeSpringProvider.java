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
package org.vulpe.controller.vraptor.ioc.spring;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequestEvent;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.RequestContextListener;
import org.vulpe.controller.vraptor.http.route.VulpePathAnnotationRoutesParser;
import org.vulpe.controller.vraptor.view.VulpePathResolver;

import br.com.caelum.vraptor.ComponentRegistry;
import br.com.caelum.vraptor.config.BasicConfiguration;
import br.com.caelum.vraptor.core.Execution;
import br.com.caelum.vraptor.core.RequestInfo;
import br.com.caelum.vraptor.http.route.RoutesParser;
import br.com.caelum.vraptor.ioc.ContainerProvider;
import br.com.caelum.vraptor.ioc.spring.VRaptorRequestHolder;
import br.com.caelum.vraptor.ioc.spring.VulpeSpringBasedContainer;
import br.com.caelum.vraptor.view.PathResolver;

public class VulpeSpringProvider implements ContainerProvider {

	protected ApplicationContext getParentApplicationContext(ServletContext context) {
		return new ClassPathXmlApplicationContext("classpath*:beanRefContext.xml");
	}

	protected void registerCustomComponents(ComponentRegistry registry) {
		registry.register(PathResolver.class, VulpePathResolver.class);
		registry.register(RoutesParser.class, VulpePathAnnotationRoutesParser.class);
	}

	private final RequestContextListener requestListener = new RequestContextListener();
	private VulpeSpringBasedContainer container;

	/**
	 * Provides request scope support for Spring IoC Container when
	 * org.springframework.web.context.request.RequestContextListener has not
	 * been called.
	 */
	public <T> T provideForRequest(RequestInfo request, Execution<T> execution) {
		if (springListenerAlreadyCalled()) {
			return execution.insideRequest(getContainer());
		}
		VRaptorRequestHolder.setRequestForCurrentThread(request);
		T result;
		try {
			ServletContext context = request.getServletContext();
			HttpServletRequest webRequest = request.getRequest();
			requestListener.requestInitialized(new ServletRequestEvent(context, webRequest));
			try {
				result = execution.insideRequest(getContainer());
			} finally {
				requestListener.requestDestroyed(new ServletRequestEvent(context, webRequest));
			}
		} finally {
			VRaptorRequestHolder.resetRequestForCurrentThread();
		}
		return result;
	}

	protected VulpeSpringBasedContainer getContainer() {
		return container;
	}

	public void stop() {
		container.stop();
	}

	/**
	 * You can override this method to start some components, remember to call
	 * super before.
	 */
	public void start(ServletContext context) {
		BasicConfiguration config = new BasicConfiguration(context);
		container = new VulpeSpringBasedContainer(getParentApplicationContext(context), config);

		registerCustomComponents(container);
		container.start(context);
	}

	private boolean springListenerAlreadyCalled() {
		return RequestContextHolder.getRequestAttributes() != null;
	}
}
