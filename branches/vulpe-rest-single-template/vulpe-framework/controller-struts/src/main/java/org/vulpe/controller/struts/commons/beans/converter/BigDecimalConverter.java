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

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import ognl.TypeConverter;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.opensymphony.xwork2.util.TypeConversionException;

@SuppressWarnings("rawtypes")
public class BigDecimalConverter extends AbstractVulpeBaseTypeConverter implements TypeConverter {
	private static final Logger LOG = Logger.getLogger(BigDecimalConverter.class);

	public Object convert(final Class type, final Object value) {
		try {
			final DecimalFormat valueFormat = new DecimalFormat("#,##0.00",
					new DecimalFormatSymbols(new Locale("pt", "BR")));
			if (value instanceof String) {
				if (!value.toString().equals("")) {
					Object newValue = value;
					newValue = StringUtils.replace(value.toString(), ".", "");
					newValue = StringUtils.replace(value.toString(), ",", ".");
					return new BigDecimal(newValue.toString());
				}
			} else if (value instanceof BigDecimal && String.class.equals(type)) {
				return valueFormat.format(value);
			} else if (value instanceof BigDecimal) {
				return value;
			}
		} catch (Exception e) {
			LOG.error("Error on convert decimal value: " + value);
			throw new TypeConversionException("Error on convert decimal value: " + value, e);
		}
		return null;
	}

}