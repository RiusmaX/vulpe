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
package org.vulpe.commons;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.vulpe.commons.cache.VulpeCacheHelper;

public class VulpeConvertUtil {

	public static VulpeConvertUtil getInstance() {
		final VulpeCacheHelper cache = VulpeCacheHelper.getInstance();
		if (!cache.contains(VulpeConvertUtil.class)) {
			cache.put(VulpeConvertUtil.class, new VulpeConvertUtil());
		}
		return cache.get(VulpeConvertUtil.class);
	}

	protected VulpeConvertUtil() {
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
			return org.apache.xerces.jaxp.datatype.DatatypeFactoryImpl.newInstance()
					.newXMLGregorianCalendar(calendar);
		} catch (javax.xml.datatype.DatatypeConfigurationException e) {
			return null;
		}
	}

	public Date convertToDate(final XMLGregorianCalendar date) {
		return (date == null || date.toGregorianCalendar() == null) ? null : date
				.toGregorianCalendar().getTime();
	}
}