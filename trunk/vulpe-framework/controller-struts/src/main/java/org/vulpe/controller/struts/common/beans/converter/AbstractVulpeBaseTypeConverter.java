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
package org.vulpe.controller.struts.common.beans.converter;

import java.util.Map;

import ognl.DefaultTypeConverter;

import org.apache.commons.beanutils.Converter;

@SuppressWarnings("unchecked")
public abstract class AbstractVulpeBaseTypeConverter extends DefaultTypeConverter implements
		Converter {

	public Object convertValue(final Map context, final Object value,
			final Class toClass) {
		if (value != null && value.getClass().isArray()) {
			final Object values[] = (Object[]) value;
			if (values.length == 1) {
				return convert(toClass, values[0]);
			}
		}
		return convert(toClass, value);
	}

}