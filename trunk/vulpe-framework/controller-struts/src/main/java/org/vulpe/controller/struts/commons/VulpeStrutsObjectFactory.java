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
package org.vulpe.controller.struts.commons;

import org.apache.struts2.impl.StrutsObjectFactory;
import org.vulpe.commons.VulpeConstants.Expression;
import org.vulpe.commons.helper.VulpeConfigHelper;
import org.vulpe.controller.struts.util.StrutsControllerUtil;

/**
 * Created to fix name of class with wildcards
 * 
 * @author <a href="mailto:fabio.viana@activethread.com.br">Fábio Viana</a>
 */
@SuppressWarnings("serial")
public class VulpeStrutsObjectFactory extends StrutsObjectFactory {

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