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

import java.util.Date;

import ognl.TypeConverter;

import org.apache.log4j.Logger;
import org.vulpe.commons.VulpeDateUtil;

import com.opensymphony.xwork2.util.TypeConversionException;

@SuppressWarnings("rawtypes")
public class DateConverter extends AbstractVulpeBaseTypeConverter implements TypeConverter {

	private static final Logger LOG = Logger.getLogger(DateConverter.class);

	public Object convert(final Class type, final Object value) {
		try {
			if (value instanceof String) {
				if (!value.toString().equals("")) {
					return VulpeDateUtil.convertStringToDate(value.toString());
				}
			} else if (value instanceof Date && String.class.equals(type)) {
				return VulpeDateUtil.convertDateToString((Date) value);
			} else if (value instanceof Date) {
				return (Date) value;
			}
		} catch (Exception e) {
			LOG.error("Error on convert date: " + value);
			throw new TypeConversionException("Erroe on convert date: " + value, e);
		}
		return null;
	}

}
