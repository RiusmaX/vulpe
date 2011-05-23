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
package org.vulpe.controller.struts.commons.beans.converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ognl.TypeConverter;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.conversion.TypeConversionException;

@SuppressWarnings("unchecked")
public class EnumConverter extends AbstractVulpeBaseTypeConverter implements TypeConverter {

	private static final Logger LOG = Logger.getLogger(EnumConverter.class);

	public Object convert(final Class type, final Object value) {
		try {
			if (value instanceof String) {
				if (!value.toString().equals("")) {
					if (Collection.class.isAssignableFrom(type)) {
						final List list = new ArrayList();
						list.add(value);
						return list;
					} else {
						return Enum.valueOf(type, value.toString());
					}
				}
			} else if (value instanceof String[]) {
				if (value != null) {
					final String[] values = (String[]) value;
					final List list = new ArrayList();
					for (String string : values) {
						if (Collection.class.isAssignableFrom(type)) {
							list.add(string);
						} else {
							list.add(Enum.valueOf(type, string));
						}
					}
					return list;
				}
			} else if (value instanceof Enum) {
				if (String.class.equals(type)) {
					return value.toString();
				} else {
					return value;
				}
			} else if (value instanceof Collection) {
				final List list = (List) value;
				final String[] values = new String[list.size()];
				int count = 0;
				for (Object object : list) {
					values[count] = object.toString();
					count++;
				}
				return values;
			}
		} catch (Exception e) {
			LOG.error("Error on convert enum: " + value);
			throw new TypeConversionException("Error on convert enum: " + value, e);
		}
		return null;
	}

}