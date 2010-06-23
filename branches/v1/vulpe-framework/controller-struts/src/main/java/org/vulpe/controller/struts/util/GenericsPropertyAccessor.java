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

import java.beans.IntrospectionException;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Map;

import org.vulpe.commons.VulpeReflectUtil;
import org.vulpe.commons.VulpeReflectUtil.DeclaredType;

import ognl.NoSuchPropertyException;
import ognl.OgnlContext;
import ognl.OgnlException;
import ognl.OgnlRuntime;

import com.opensymphony.xwork2.util.OgnlValueStack.ObjectAccessor;

/**
 * Classe utilizada para corrigir problemas ao setar valores em tipos genericos.
 *
 * @author <a href="mailto:fabio.viana@activethread.com.br">Fábio Viana</a>
 */
public class GenericsPropertyAccessor extends ObjectAccessor {

	@Override
	@SuppressWarnings("unchecked")
	public void setProperty(final Map context, final Object target, final Object oname,
			final Object value) throws OgnlException {
		Object newValue = value;
		if (oname instanceof String && target != null) {
			final OgnlContext ognlContext = (OgnlContext) context;

			Method method = null;
			try {
				method = OgnlRuntime.getSetMethod(ognlContext, target
						.getClass(), oname.toString());
			} catch (IntrospectionException e) {
				throw new OgnlException(oname.toString(), e);
			}
			// verifica se o parametro do metodo set é do tipo Generics
			if (method != null
					&& OgnlRuntime.isMethodAccessible(ognlContext, target,
							method, oname.toString())
					&& !method.getParameterTypes()[0].equals(method
							.getGenericParameterTypes()[0])) {
				newValue = getValue(ognlContext, target, oname.toString(),
						method, method.getGenericParameterTypes()[0], newValue);
				OgnlRuntime.callAppropriateMethod(ognlContext, target, target,
						method.getName(), oname.toString(), Collections
								.nCopies(1, method), new Object[] { newValue });
				return;
			}

			final Field field = OgnlRuntime.getField(target.getClass(), oname
					.toString());
			if (field != null
					&& (!field.getType().equals(field.getGenericType()) || (method != null && !method
							.getGenericParameterTypes()[0].equals(field
							.getGenericType())))) {
				boolean fieldAccess = true;
				synchronized (field) {
					if (!field.isAccessible()) {
						field.setAccessible(true);
						fieldAccess = false;
					}
					try {
						newValue = getValue(ognlContext, target, oname
								.toString(), field, field.getGenericType(),
								newValue);
						try {
							field.set(target, newValue);
							return;
						} catch (Exception e) {
							throw new NoSuchPropertyException(target, oname
									.toString(), e);
						}
					} finally {
						if (!fieldAccess) {
							field.setAccessible(false);
						}
					}
				}
			}
		}

		super.setProperty(context, target, oname, newValue);
	}

	protected Object getValue(final OgnlContext ognlContext,
			final Object target, final String name, final Member member,
			final Type type, final Object value) {
		final DeclaredType declaredType = VulpeReflectUtil.getInstance()
				.getDeclaredType(target.getClass(), type);
		return OgnlRuntime.getConvertedType(ognlContext, target, member, name,
				value, declaredType.getType());
	}
}