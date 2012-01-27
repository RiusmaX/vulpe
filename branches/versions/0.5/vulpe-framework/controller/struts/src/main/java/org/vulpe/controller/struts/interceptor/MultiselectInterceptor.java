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
package org.vulpe.controller.struts.interceptor;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.vulpe.commons.util.VulpeReflectUtil;
import org.vulpe.controller.struts.VulpeStrutsController;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * Just as the CheckboxInterceptor checks that if only the hidden field is
 * present, so too does this interceptor. If the "__multiselect_" request
 * parameter is present and its visible counterpart is not, set a new request
 * parameter to an empty Sting.
 */
@SuppressWarnings("unchecked")
public class MultiselectInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = 1L;

	/**
	 * Just as the CheckboxInterceptor checks that if only the hidden field is
	 * present, so too does this interceptor. If the "__multiselect_" request
	 * parameter is present and its visible counterpart is not, set a new
	 * request parameter to an empty Sting.
	 * 
	 * @param actionInvocation
	 *            ActionInvocation
	 * @return the result of the action
	 * @throws Exception
	 *             if error
	 * @see com.opensymphony.xwork2.interceptor.Interceptor#intercept(com.opensymphony.xwork2.ActionInvocation)
	 */
	@Override
	public String intercept(final ActionInvocation actionInvocation) throws Exception {
		final Map parameters = actionInvocation.getInvocationContext().getParameters();
		final Map<String, Object> newParams = new HashMap<String, Object>();
		final Set<String> keys = parameters.keySet();

		for (final Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
			final String key = iterator.next();

			if (key.startsWith("__multiselect_")) {
				final String name = key.substring("__multiselect_".length());

				iterator.remove();

				// is this multi-select box submitted?
				if (parameters.containsKey(name)) {
					final Object[] values = (Object[]) parameters.get(name);
					if (values != null) {
						final VulpeStrutsController baseAction = VulpeReflectUtil.getFieldValue(
								actionInvocation, "action");
						final Object entity = baseAction.vulpe.controller().config()
								.getEntityClass().newInstance();
						final String attributeName = name.contains("entities") ? name
								.substring(name.indexOf("].") + 2) : name.substring("entity."
								.length());
						if (String[].class.isAssignableFrom(values.getClass())) {
							final Field field = VulpeReflectUtil.getField(entity.getClass(),
									attributeName);
							if (List.class.isAssignableFrom(field.getType())) {
								final Type[] fieldListType = VulpeReflectUtil.getFieldValue(field
										.getGenericType(), "actualTypeArguments");
								final Object[] enumConstants = VulpeReflectUtil.getFieldValue(
										fieldListType[0], "enumConstants");
								if (!ArrayUtils.isEmpty(enumConstants)) {
									final List list = new ArrayList();
									for (Object eConstant : enumConstants) {
										for (Object value : values) {
											if (eConstant.toString().equals(value.toString())) {
												list.add(eConstant);
											}
										}
									}
									newParams.put(name, list);
								}
							}
						}
					}
				} else {
					// if not, let's be sure to default the value to an empty
					// string array
					newParams.put(name, new String[0]);
				}
			}
		}

		parameters.putAll(newParams);

		return actionInvocation.invoke();
	}
}