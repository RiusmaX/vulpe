package org.vulpe.common.beans.converter;

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