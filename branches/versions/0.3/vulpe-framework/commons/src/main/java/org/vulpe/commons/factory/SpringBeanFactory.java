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
package org.vulpe.commons.factory;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.access.BeanFactoryReference;
import org.springframework.context.ApplicationContext;
import org.springframework.context.access.ContextSingletonBeanFactoryLocator;

/**
 *
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
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