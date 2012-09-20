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
import java.util.Arrays;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Vulpe Collection Utility class.
 * 
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 * @version 1.0
 * @since 1.0
 */
public class VulpeCollectionUtil {

	protected static final Logger LOG = LoggerFactory.getLogger(VulpeCollectionUtil.class);

	public static <C extends Collection<?>> boolean exists(final C collection,
			final String property, final Object value) {
		for (final Object object : collection) {
			final Object objectValue = VulpeReflectUtil.getFieldValue(object, property);
			if (objectValue.equals(value)) {
				return true;
			}
		}
		return false;
	}

	public static <C extends Collection<?>> Collection<?> check(final C one, final C two,
			final String property) {
		final Collection<Object> collection = new ArrayList<Object>();
		for (final Object cOne : one) {
			final Object valueOne = VulpeReflectUtil.getFieldValue(cOne, property);
			if (!exists(two, property, valueOne)) {
				collection.add(cOne);
			}
		}
		return collection;
	}

	public static Collection<?> asCollection(final String value) {
		if (value.startsWith("[") && value.endsWith("]")) {
			String newValue = value.substring(1, value.length() - 1);
			final String[] parts = newValue.split(",");
			return Arrays.asList(parts);
		}
		return null;
	}
}