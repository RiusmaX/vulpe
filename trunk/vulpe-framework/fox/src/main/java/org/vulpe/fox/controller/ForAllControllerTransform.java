package org.vulpe.fox.controller;

import net.sf.jelly.apt.freemarker.FreemarkerTransform;

public class ForAllControllerTransform extends
		FreemarkerTransform<ForAllControllerTemplateStrategy> {

	public ForAllControllerTransform(final String namespace) {
		super(namespace);
	}

	public ForAllControllerTemplateStrategy newStrategy() {
		return new ForAllControllerTemplateStrategy();
	}

	@Override
	public String getTransformName() {
		return "forAllController";
	}
}