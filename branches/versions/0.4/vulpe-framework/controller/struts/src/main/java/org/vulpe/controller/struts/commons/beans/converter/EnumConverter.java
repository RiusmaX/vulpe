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
package org.vulpe.controller.struts.commons.beans.converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ognl.TypeConverter;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.vulpe.commons.util.VulpeCollectionUtil;

import com.opensymphony.xwork2.conversion.TypeConversionException;

@SuppressWarnings("unchecked")
public class EnumConverter extends AbstractVulpeBaseTypeConverter implements TypeConverter {

	private static final Logger LOG = Logger.getLogger(EnumConverter.class);

	public Object convert(final Class type, final Object value) {
		try {
			if (value instanceof String) {
				String enumValue = value.toString();
				if (StringUtils.isNotBlank(enumValue)) {
					if (enumValue.startsWith("[") && enumValue.endsWith("]")) {
						return VulpeCollectionUtil.asCollection(enumValue);
					} else if (Collection.class.isAssignableFrom(type)) {
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
					++count;
				}
				return values;
			}
		} catch (Exception e) {
			LOG.error("Error on convert enum: " + value);
			throw new TypeConversionException("Error on convert enum: " + value, e);
		}
		return null;
	}

}