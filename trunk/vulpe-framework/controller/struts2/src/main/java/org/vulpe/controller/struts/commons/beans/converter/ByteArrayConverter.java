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

import java.io.File;

import ognl.TypeConverter;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.opensymphony.xwork2.conversion.TypeConversionException;

@SuppressWarnings({ "rawtypes" })
public class ByteArrayConverter extends AbstractVulpeBaseTypeConverter implements TypeConverter {
	private static final Logger LOG = Logger.getLogger(ByteArrayConverter.class);

	private transient final org.apache.commons.beanutils.converters.ByteArrayConverter converter = new org.apache.commons.beanutils.converters.ByteArrayConverter();

	public Object convert(final Class type, final Object value) {
		if (value == null) {
			return null;
		}

		try {
			if (type == byte[].class) {
				if (value instanceof File) {
					return FileUtils.readFileToByteArray((File) value);
				} else if (value instanceof byte[]) {
					return value;
				}
			}
		} catch (Exception e) {
			LOG.error("Error on convert byte: " + value);
			throw new TypeConversionException("Error byte convert: " + value, e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("Not possible convert to byte: " + value + " - type: " + type);
		}

		return converter.convert(type, value);
	}

}
