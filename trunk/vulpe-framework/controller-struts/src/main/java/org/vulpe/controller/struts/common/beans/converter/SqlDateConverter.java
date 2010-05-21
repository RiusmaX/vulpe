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

import java.sql.Date;

import ognl.TypeConverter;

import org.apache.log4j.Logger;
import org.vulpe.common.DateUtil;

import com.opensymphony.xwork2.util.TypeConversionException;

public class SqlDateConverter extends AbstractVulpeBaseTypeConverter implements
		TypeConverter {

	private static final Logger LOG = Logger.getLogger(SqlDateConverter.class);

	@SuppressWarnings("unchecked")
	public Object convert(final Class type, final Object value) {
		try {
			if (value instanceof String) {
				if (!value.toString().equals("")) {
					final java.util.Date date = DateUtil.convertStringToDate(value
							.toString());
					return new Date(date.getTime());
				}
			} else if (value instanceof Date && String.class.equals(type)) {
				return DateUtil.convertDateTimeToString((Date) value);
			} else if (value instanceof Date) {
				return (Date) value;
			}
		} catch (Exception e) {
			LOG.error("Erro ao converter data: " + value);
			throw new TypeConversionException("Erro ao converter data: "
					+ value, e);
		}
		return null;
	}

}
