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
package org.vulpe.controller.vraptor.ioc;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.vulpe.controller.vraptor.http.route.VulpePathAnnotationRoutesParser;
import org.vulpe.controller.vraptor.view.VulpePathResolver;

import br.com.caelum.vraptor.ComponentRegistry;
import br.com.caelum.vraptor.http.route.RoutesParser;
import br.com.caelum.vraptor.ioc.spring.SpringProvider;
import br.com.caelum.vraptor.view.PathResolver;

public class VulpeSpringProvider extends SpringProvider {

	@Override
	protected ApplicationContext getParentApplicationContext(ServletContext context) {
		return new ClassPathXmlApplicationContext("classpath*:beanRefContext.xml");
	}

	@Override
	protected void registerCustomComponents(ComponentRegistry registry) {
		super.registerCustomComponents(registry);
		registry.register(PathResolver.class, VulpePathResolver.class);
		registry.register(RoutesParser.class, VulpePathAnnotationRoutesParser.class);
	}
}
