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
