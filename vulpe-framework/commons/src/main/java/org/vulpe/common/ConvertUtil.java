package org.vulpe.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.vulpe.common.cache.VulpeCacheHelper;

public class ConvertUtil {

	public static ConvertUtil getInstance() {
		final VulpeCacheHelper cache = VulpeCacheHelper.getInstance();
		if (!cache.contains(ConvertUtil.class)) {
			cache.put(ConvertUtil.class, new ConvertUtil());
		}
		return cache.get(ConvertUtil.class);
	}

	protected ConvertUtil() {
		// default constructor
	}

	public List<XMLGregorianCalendar> convertToXMLDate(final List<Date> dates) {
		if (dates == null) {
			return null;
		}

		final List<XMLGregorianCalendar> list = new ArrayList<XMLGregorianCalendar>();
		for (Date date : dates) {
			list.add(convertToXMLDate(date));
		}
		return list;
	}

	public List<Date> convertToDate(final List<XMLGregorianCalendar> dates) {
		if (dates == null) {
			return null;
		}

		final List<Date> list = new ArrayList<Date>();
		for (XMLGregorianCalendar date : dates) {
			list.add(convertToDate(date));
		}
		return list;
	}

	public XMLGregorianCalendar convertToXMLDate(final Date dates) {
		if (dates == null) {
			return null;
		}

		final java.util.GregorianCalendar calendar = new java.util.GregorianCalendar();
		calendar.setTime(dates);
		try {
			return org.apache.xerces.jaxp.datatype.DatatypeFactoryImpl
					.newInstance().newXMLGregorianCalendar(calendar);
		} catch (javax.xml.datatype.DatatypeConfigurationException e) {
			return null;
		}
	}

	public Date convertToDate(final XMLGregorianCalendar date) {
		return (date == null || date.toGregorianCalendar() == null) ? null : date
				.toGregorianCalendar().getTime();
	}
}