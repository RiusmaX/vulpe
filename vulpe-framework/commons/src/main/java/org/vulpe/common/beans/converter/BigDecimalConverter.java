package org.vulpe.common.beans.converter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import ognl.TypeConverter;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.opensymphony.xwork2.util.TypeConversionException;

public class BigDecimalConverter extends AbstractVulpeBaseTypeConverter implements
		TypeConverter {
	private static final Logger LOG = Logger
			.getLogger(BigDecimalConverter.class);

	@SuppressWarnings("unchecked")
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
			LOG.error("Erro ao converter valor decimal: " + value);
			throw new TypeConversionException(
					"Erro ao converter valor decimal: " + value, e);
		}
		return null;
	}

}