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
package org.vulpe.controller.struts.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Map;

import org.vulpe.commons.VulpeReflectUtil;
import org.vulpe.commons.VulpeReflectUtil.DeclaredType;

import com.opensymphony.xwork2.util.DefaultObjectTypeDeterminer;

/**
 * Classe utilizada para corrigir problemas ao determinar a classe de tipos
 * genericos.
 * 
 * @author <a href="mailto:fabio.viana@activethread.com.br">Fábio Viana</a>
 */
@SuppressWarnings("unchecked")
public class GenericsObjectTypeDeterminer extends DefaultObjectTypeDeterminer {

	@Override
	public Class getElementClass(final Class parentClass, final String property, final Object key) {
		Class clazz = super.getElementClass(parentClass, property, key);
		if (clazz == null) {
			final Field field = VulpeReflectUtil.getInstance().getField(parentClass, property);
			if (field.getGenericType() instanceof ParameterizedType) {
				final ParameterizedType type = (ParameterizedType) field.getGenericType();
				final int index = (Map.class.isAssignableFrom(VulpeReflectUtil.getInstance()
						.getDeclaredType(clazz, type.getRawType()).getType()) ? 1 : 0);
				final DeclaredType declaredType = VulpeReflectUtil.getInstance().getDeclaredType(
						parentClass, type);
				clazz = declaredType.getItems().get(index).getType();
			}
		}
		return clazz;
	}

}