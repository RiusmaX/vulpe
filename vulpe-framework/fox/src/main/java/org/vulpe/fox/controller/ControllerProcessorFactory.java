package org.vulpe.fox.controller;

import java.net.URL;
import java.util.Collection;

import net.sf.jelly.apt.freemarker.FreemarkerProcessor;
import net.sf.jelly.apt.freemarker.FreemarkerProcessorFactory;
import net.sf.jelly.apt.freemarker.FreemarkerTransform;

import com.sun.mirror.apt.AnnotationProcessor;

public class ControllerProcessorFactory extends FreemarkerProcessorFactory {

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
					list.add(new ForAllControllerTransform(transform
							.getTransformNamespace()));
				}
				return list;
			}
		};
	}
}