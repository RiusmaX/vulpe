package org.vulpe.fox.view;

import net.sf.jelly.apt.freemarker.FreemarkerTransform;

public class ForAllViewTransform extends
		FreemarkerTransform<ForAllViewTemplateStrategy> {

	public ForAllViewTransform(final String namespace) {
		super(namespace);
	}

	public ForAllViewTemplateStrategy newStrategy() {
		return new ForAllViewTemplateStrategy();
	}

	@Override
	public String getTransformName() {
		return "forAllView";
	}
}