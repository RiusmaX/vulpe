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
package org.vulpe.commons.xml;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.vulpe.commons.xml.XMLDateConversor;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

@SuppressWarnings("unchecked")
public class XMLDateConversor implements Converter {

	private static final Logger LOG = Logger.getLogger(XMLDateConversor.class);

	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	public XMLDateConversor() {
		super();
	}

	public boolean canConvert(final Class clazz) {
		return (clazz == Date.class || clazz == java.sql.Date.class || clazz == java.sql.Timestamp.class);
	}

	public void marshal(final Object value, final HierarchicalStreamWriter writer,
			final MarshallingContext context) {
		writer.setValue(formatter.format((Date) value));
	}

	public Object unmarshal(final HierarchicalStreamReader reader,
			final UnmarshallingContext context) {
		try {
			return formatter.parse(reader.getValue());
		} catch (ParseException e) {
			LOG.error(e);
		}
		return null;
	}

}