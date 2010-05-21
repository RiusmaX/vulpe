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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

public class XEnumConverter extends StrutsTypeConverter {

	@SuppressWarnings("unchecked")
	@Override
	public Object convertFromString(final Map context, final String[] values, final Class toClass) {
		final List<Enum> result = new ArrayList<Enum>();
		for (int i = 0; i < values.length; i++) {
			final Enum enumaration = Enum.valueOf(toClass, values[i]);
			if (enumaration != null) {
				result.add(enumaration);
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String convertToString(final Map context, final Object obj) {
		final List list = (List) obj;
		final StringBuilder result = new StringBuilder("<");
		for (final Iterator i = list.iterator(); i.hasNext();) {
			result.append("[" + i.next() + "]");
		}
		result.append(">");
		return result.toString();
	}

}
