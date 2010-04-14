package org.vulpe.fox.manager;

import net.sf.jelly.apt.freemarker.FreemarkerTransform;

public class ForAllManagerTransform extends
		FreemarkerTransform<ForAllManagerTemplateStrategy> {

	public ForAllManagerTransform(final String namespace) {
		super(namespace);
	}

	public ForAllManagerTemplateStrategy newStrategy() {
		return new ForAllManagerTemplateStrategy();
	}

	@Override
	public String getTransformName() {
		return "forAllManager";
	}
}