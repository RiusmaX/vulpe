package org.vulpe.controller.struts.common;

import org.apache.struts2.impl.StrutsObjectFactory;
import org.vulpe.common.Constants.Expression;
import org.vulpe.common.helper.VulpeConfigHelper;
import org.vulpe.controller.struts.util.StrutsControllerUtil;

/**
 * Created to fix name of class with wildcards
 * 
 * @author <a href="mailto:fabio.viana@activethread.com.br">Fábio Viana</a>
 */
@SuppressWarnings("serial")
public class StrutsObjectFactoryImpl extends StrutsObjectFactory {

	@Override
	@SuppressWarnings("unchecked")
	public Class getClassInstance(final String className) throws ClassNotFoundException {
		if (StrutsControllerUtil.getServletContext() == null) {
			return super.getClassInstance(className);
		}

		String expression = null;
		String value = null;
		String newClassName = className;
		while (newClassName.contains("{") && newClassName.contains("}")) {
			expression = className.substring(className.indexOf("{".charAt(0)) + 1, className
					.indexOf("}".charAt(0)));
			if (Expression.PROJECT_PACKAGE.equals(expression)) {
				value = VulpeConfigHelper.getProjectPackage();
			} else {
				value = StrutsControllerUtil.getServletContext().getInitParameter(
						expression);
			}
			newClassName = className.replace("{".concat(expression).concat("}"), value == null ? ""
					: value);
		}
		return super.getClassInstance(newClassName);
	}
}