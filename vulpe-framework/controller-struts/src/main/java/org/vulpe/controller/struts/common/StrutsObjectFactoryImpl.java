package org.vulpe.controller.struts.common;

import org.apache.struts2.impl.StrutsObjectFactory;
import org.vulpe.controller.util.ControllerUtil;

/**
 * Classe criada para corrigir o nome da classe quando esta tem algum parametro
 * pré-configurado.
 *
 * @author <a href="mailto:fabio.viana@activethread.com.br">Fábio Viana</a>
 */
@SuppressWarnings("serial")
public class StrutsObjectFactoryImpl extends StrutsObjectFactory {

	@Override
	@SuppressWarnings("unchecked")
	public Class getClassInstance(final String className)
			throws ClassNotFoundException {
		if (ControllerUtil.getInstance().getServletContext() == null) {
			return super.getClassInstance(className);
		}

		String expression = null;
		String value = null;
		String newClassName = className;
		while (newClassName.contains("{") && newClassName.contains("}")) {
			expression = className.substring(className.indexOf("{".charAt(0)) + 1,
					className.indexOf("}".charAt(0)));
			value = ControllerUtil.getInstance().getServletContext()
					.getInitParameter(expression);
			newClassName = className.replace("{".concat(expression)
					.concat("}"), value == null ? "" : value);
		}
		return super.getClassInstance(newClassName);
	}
}