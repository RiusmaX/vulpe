/**
 * Vulpe Framework - Quick and Smart ;)
 * Copyright (C) 2011 Active Thread
 *
 * Este programa é software livre; você pode redistribuí-lo e/ou
 * modificá-lo sob os termos da Licença Pública Geral GNU, conforme
 * publicada pela Free Software Foundation; tanto a versão 2 da
 * Licença como (a seu critério) qualquer versão mais nova.
 *
 * Este programa é distribuído na expectativa de ser útil, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia implícita de
 * COMERCIALIZAÇÃO ou de ADEQUAÇÃO A QUALQUER PROPÓSITO EM
 * PARTICULAR. Consulte a Licença Pública Geral GNU para obter mais
 * detalhes.
 *
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU
 * junto com este programa; se não, escreva para a Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307, USA.
 */
/**
 * Vulpe Framework - Quick and Smart ;)
 * Copyright (C) 2011 Active Thread
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.vulpe.controller.struts.util;

import java.beans.IntrospectionException;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Map;

import ognl.NoSuchPropertyException;
import ognl.OgnlContext;
import ognl.OgnlException;
import ognl.OgnlRuntime;

import org.vulpe.commons.util.VulpeReflectUtil;
import org.vulpe.commons.util.VulpeReflectUtil.DeclaredType;

import com.opensymphony.xwork2.ognl.accessor.ObjectAccessor;

/**
 * Utility class to solve problems on set generic types.
 *
 * @author <a href="mailto:fabio.viana@vulpe.org">Fábio Viana</a>
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 */
@SuppressWarnings("rawtypes")
public class GenericsPropertyAccessor extends ObjectAccessor {

	@Override
	public void setProperty(final Map context, final Object target, final Object oname,
			final Object value) throws OgnlException {
		Object newValue = value;
		if (oname instanceof String && target != null && !oname.equals("excludeParams")) {
			final OgnlContext ognlContext = (OgnlContext) context;

			Method method = null;
			try {
				method = OgnlRuntime.getSetMethod(ognlContext, target.getClass(), oname.toString());
			} catch (IntrospectionException e) {
				throw new OgnlException(oname.toString(), e);
			}
			if (method != null
					&& OgnlRuntime
							.isMethodAccessible(ognlContext, target, method, oname.toString())
					&& !method.getParameterTypes()[0].equals(method.getGenericParameterTypes()[0])) {
				newValue = getValue(ognlContext, target, oname.toString(), method,
						method.getGenericParameterTypes()[0], newValue);
				OgnlRuntime
						.callAppropriateMethod(ognlContext, target, target, method.getName(),
								oname.toString(), Collections.nCopies(1, method),
								new Object[] { newValue });
				return;
			}

			final Field field = OgnlRuntime.getField(target.getClass(), oname.toString());
			if (field != null
					&& (!field.getType().equals(field.getGenericType()) || (method != null && !method
							.getGenericParameterTypes()[0].equals(field.getGenericType())))) {
				boolean fieldAccess = true;
				synchronized (field) {
					if (!field.isAccessible()) {
						field.setAccessible(true);
						fieldAccess = false;
					}
					try {
						newValue = getValue(ognlContext, target, oname.toString(), field,
								field.getGenericType(), newValue);
						try {
							field.set(target, newValue);
							return;
						} catch (Exception e) {
							throw new NoSuchPropertyException(target, oname.toString(), e);
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

	protected Object getValue(final OgnlContext ognlContext, final Object target,
			final String name, final Member member, final Type type, final Object value) {
		final DeclaredType declaredType = VulpeReflectUtil.getDeclaredType(
				target.getClass(), type);
		return OgnlRuntime.getConvertedType(ognlContext, target, member, name, value,
				declaredType.getType());
	}
}