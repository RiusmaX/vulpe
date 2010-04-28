package org.vulpe.controller.struts.common.beans.converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ognl.TypeConverter;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.util.TypeConversionException;

public class EnumConverter extends AbstractVulpeBaseTypeConverter implements TypeConverter {

	private static final Logger LOG = Logger.getLogger(EnumConverter.class);

	@SuppressWarnings("unchecked")
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
			LOG.error("Erro ao converter enumeração: " + value);
			throw new TypeConversionException("Erro ao converter enumeração: "
					+ value, e);
		}
		return null;
	}

}