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

import java.io.File;

import ognl.TypeConverter;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.opensymphony.xwork2.util.TypeConversionException;

public class ByteArrayConverter extends AbstractVulpeBaseTypeConverter implements
		TypeConverter {
	private static final Logger LOG = Logger
			.getLogger(ByteArrayConverter.class);

	private transient final org.apache.commons.beanutils.converters.ByteArrayConverter converter = new org.apache.commons.beanutils.converters.ByteArrayConverter();

	@SuppressWarnings("unchecked")
	public Object convert(final Class type, final Object value) {
		if (value == null) {
			return null;
		}

		try {
			if (type == byte[].class) {
				if (value instanceof File) {
					return FileUtils.readFileToByteArray((File) value);
				} else if (value instanceof byte[]) {
					return value;
				}
			}
		} catch (Exception e) {
			LOG.error("Erro ao converter byte: " + value);
			throw new TypeConversionException("Error byte convert: " + value, e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("Not possible convert to byte: " + value + " - type: "
					+ type);
		}

		return converter.convert(type, value);
	}

}
