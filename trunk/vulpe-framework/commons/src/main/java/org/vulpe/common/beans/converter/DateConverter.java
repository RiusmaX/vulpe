package org.vulpe.common.beans.converter;

import java.util.Date;

import ognl.TypeConverter;

import org.apache.log4j.Logger;
import org.vulpe.common.DateUtil;

import com.opensymphony.xwork2.util.TypeConversionException;

public class DateConverter extends AbstractVulpeBaseTypeConverter implements TypeConverter {

	private static final Logger LOG = Logger.getLogger(DateConverter.class);

	@SuppressWarnings("unchecked")
	public Object convert(final Class type, final Object value) {
		try {
			if (value instanceof String) {
				if (!value.toString().equals("")) {
					return DateUtil.convertStringToDate(value.toString());
				}
			} else if (value instanceof Date && String.class.equals(type)) {
				return DateUtil.convertDateToString((Date) value);
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
