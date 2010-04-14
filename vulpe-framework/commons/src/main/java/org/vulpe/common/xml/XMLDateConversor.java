package org.vulpe.common.xml;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class XMLDateConversor implements Converter {

	private static final Logger LOG = Logger.getLogger(XMLDateConversor.class);

	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	public XMLDateConversor() {
		super();
	}

	@SuppressWarnings("unchecked")
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