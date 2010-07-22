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
package org.vulpe.fox.view;

import java.net.URL;
import java.util.Collection;

import net.sf.jelly.apt.freemarker.FreemarkerProcessor;
import net.sf.jelly.apt.freemarker.FreemarkerProcessorFactory;
import net.sf.jelly.apt.freemarker.FreemarkerTransform;

import com.sun.mirror.apt.AnnotationProcessor;

public class ViewProcessorFactory extends FreemarkerProcessorFactory {

	protected AnnotationProcessor newProcessor(final URL url) {
		return new FreemarkerProcessor(url) {
			@SuppressWarnings("unchecked")
			@Override
			protected Collection<FreemarkerTransform> getTransforms() {
				final Collection<FreemarkerTransform> list = super
						.getTransforms();
				if (list != null && !list.isEmpty()) {
					final FreemarkerTransform transform = (FreemarkerTransform) list
							.toArray()[0];
					list.add(new ForAllViewTransform(transform
							.getTransformNamespace()));
				}
				return list;
			}
		};
	}
}