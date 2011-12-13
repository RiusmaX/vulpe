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

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings( { "serial", "unchecked" })
public class VulpeHashMap<KEY extends Object, VALUE extends Object> extends HashMap<KEY, VALUE> {

	/**
	 * Retrieves the value referenced by key making automatic conversion.
	 * 
	 * @param key
	 * @return
	 */
	public <T> T getAuto(final KEY key) {
		return (T) get(key);
	}

	public <T> T getEnum(final KEY key, final Class type) {
		final Object object = getAuto(key);
		T value = null;
		if (object instanceof String) {
			final String string = (String) object;
			if (StringUtils.isNotBlank(string)) {
				value = (T) Enum.valueOf(type, string);
			}
		} else {
			value = (T) object;
		}
		return value;
	}

	public <T> T getEnum(final KEY key, final Class type, final T defaultValue) {
		final T object = (T) getEnum(key, type);
		return object == null ? defaultValue : object;
	}

	public boolean getBoolean(final KEY key) {
		Boolean value = Boolean.FALSE;
		Object object = get(key);
		if (object != null) {
			if (object instanceof Boolean) {
				value = (Boolean) object;
			} else if (object instanceof String) {
				String string = (String) object;
				if (string.equals("true")) {
					value = Boolean.TRUE;
				} else {
					value = Boolean.FALSE;
				}
			}
		}
		return value;
	}

	public <T> T getAuto(final KEY key, final T defaultValue) {
		return containsKey(key) && VulpeValidationUtil.isNotEmpty(get(key)) ? (T) getAuto(key)
				: defaultValue;
	}

	/**
	 * Remove and returns the value referenced by key making automatic
	 * conversion.
	 * 
	 * @param key
	 * @return
	 */
	public <T> T removeAuto(final KEY key) {
		return (T) remove(key);
	}

	/**
	 * Add object on the map and returns himself making automatic conversion.
	 * 
	 * @param <T>
	 * @param key
	 * @param value
	 * @return
	 */
	public <T> T putAuto(final KEY key, final VALUE value) {
		return (T) super.put(key, value);
	}

}
