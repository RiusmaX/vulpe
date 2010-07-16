/*
 * $Id: BackgroundProcess.java 651946 2008-04-27 13:41:38Z apetrelli $
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
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
import org.vulpe.commons.VulpeReflectUtil;
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
		final Map parameters = actionInvocation.getInvocationContext()
				.getParameters();
		final Map<String, Object> newParams = new HashMap<String, Object>();
		final Set<String> keys = parameters.keySet();

		for (final Iterator<String> iterator = keys.iterator(); iterator
				.hasNext();) {
			final String key = iterator.next();

			if (key.startsWith("__multiselect_")) {
				final String name = key.substring("__multiselect_".length());

				iterator.remove();

				// is this multi-select box submitted?
				if (parameters.containsKey(name)) {
					final Object[] values = (Object[]) parameters.get(name);
					if (values != null) {
						final VulpeStrutsController baseAction = VulpeReflectUtil.getInstance()
								.getFieldValue(actionInvocation, "action");
						final Object entity = baseAction.getControllerConfig()
								.getEntityClass().newInstance();
						final String attributeName = name.contains("entities") ? name
								.substring(name.indexOf("].") + 2)
								: name.substring("entity.".length());
						if (String[].class.isAssignableFrom(values.getClass())) {
							final Field field = VulpeReflectUtil.getInstance()
									.getField(entity.getClass(), attributeName);
							if (List.class.isAssignableFrom(field.getType())) {
								final Type[] fieldListType = VulpeReflectUtil
										.getInstance().getFieldValue(
												field.getGenericType(),
												"actualTypeArguments");
								final Object[] enumConstants = VulpeReflectUtil
										.getInstance().getFieldValue(
												fieldListType[0],
												"enumConstants");
								if (!ArrayUtils.isEmpty(enumConstants)) {
									final List list = new ArrayList();
									for (Object eConstant : enumConstants) {
										for (Object value : values) {
											if (eConstant.toString().equals(
													value.toString())) {
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