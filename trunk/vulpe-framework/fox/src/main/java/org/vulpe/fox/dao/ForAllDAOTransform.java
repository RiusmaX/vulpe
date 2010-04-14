package org.vulpe.fox.dao;

import net.sf.jelly.apt.freemarker.FreemarkerTransform;

public class ForAllDAOTransform extends
		FreemarkerTransform<ForAllDAOTemplateStrategy> {

	public ForAllDAOTransform(final String namespace) {
		super(namespace);
	}

	public ForAllDAOTemplateStrategy newStrategy() {
		return new ForAllDAOTemplateStrategy();
	}

	@Override
	public String getTransformName() {
		return "forAllDAO";
	}
}