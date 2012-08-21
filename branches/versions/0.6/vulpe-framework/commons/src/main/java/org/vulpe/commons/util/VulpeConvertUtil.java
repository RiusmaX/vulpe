/**
 * Vulpe Framework - Quick and Smart ;)
 * Copyright (C) 2011 Active Thread
 * 
 * Este programa é software livre; você pode redistribuí-lo e/ou
 * modificá-lo sob os termos da Licença Pública Geral GNU, conforme
 * publicada pela Free Software Foundation; tanto a versão 2 da
 * Licença como (a seu critério) qualquer versão mais nova.
 * 
 * Este programa é distribuído na expectativa de ser útil, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia implícita de
 * COMERCIALIZAÇÃO ou de ADEQUAÇÃO A QUALQUER PROPÓSITO EM
 * PARTICULAR. Consulte a Licença Pública Geral GNU para obter mais
 * detalhes.
 * 
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU
 * junto com este programa; se não, escreva para a Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307, USA.
 */
/**
 * Vulpe Framework - Quick and Smart ;)
 * Copyright (C) 2011 Active Thread
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.vulpe.commons.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

public class VulpeConvertUtil {

	protected VulpeConvertUtil() {
		// default constructor
	}

	public static List<XMLGregorianCalendar> convertToXMLDate(final List<Date> dates) {
		if (dates == null) {
			return null;
		}

		final List<XMLGregorianCalendar> list = new ArrayList<XMLGregorianCalendar>();
		for (Date date : dates) {
			list.add(convertToXMLDate(date));
		}
		return list;
	}

	public static List<Date> convertToDate(final List<XMLGregorianCalendar> dates) {
		if (dates == null) {
			return null;
		}

		final List<Date> list = new ArrayList<Date>();
		for (XMLGregorianCalendar date : dates) {
			list.add(convertToDate(date));
		}
		return list;
	}

	public static XMLGregorianCalendar convertToXMLDate(final Date dates) {
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

	public static Date convertToDate(final XMLGregorianCalendar date) {
		return (date == null || date.toGregorianCalendar() == null) ? null : date
				.toGregorianCalendar().getTime();
	}
}