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

import java.util.Collection;

import org.apache.commons.lang.ArrayUtils;
import org.vulpe.model.entity.VulpeEntity;

/**
 * Utility class to validation.
 * 
 */
@SuppressWarnings("unchecked")
public class VulpeValidationUtil {

	/**
	 * Validate if value is not empty
	 * 
	 * @param value
	 * @return returns true if is not empty
	 */
	public static boolean isNotEmpty(final Object value) {
		if (value == null) {
			return false;
		}

		if (value instanceof String) {
			return !value.toString().trim().equals("");
		}

		if (value instanceof Collection) {
			return !((Collection) value).isEmpty();
		}

		if (value instanceof VulpeEntity<?>) {
			return ((VulpeEntity<?>) value).getId() != null;
		}

		if (value.getClass().isArray()) {
			return ArrayUtils.isNotEmpty((Object[]) value);
		}

		return true;
	}

	public static boolean isNotEmpty(final Object... values) {
		boolean notEmpty = true;
		for (final Object value : values) {
			if (isEmpty(value)) {
				notEmpty = false;
				break;
			}
		}
		return notEmpty;
	}

	/**
	 * Validate if value is empty.
	 * 
	 * @param value
	 * @return returns true if is empty
	 */
	public static boolean isEmpty(final Object value) {
		return !isNotEmpty(value);
	}

	public static boolean isEmpty(final Object... values) {
		boolean empty = true;
		for (final Object value : values) {
			if (isNotEmpty(value)) {
				empty = false;
				break;
			}
		}
		return empty;
	}

	public static boolean isNull(final Object... objects) {
		boolean nullable = true;
		for (final Object object : objects) {
			if (object != null) {
				nullable = false;
				break;
			}
		}
		return nullable;

	}

	public static boolean isNotNull(final Object... objects) {
		boolean nullable = false;
		for (final Object object : objects) {
			if (object == null) {
				nullable = true;
				break;
			}
		}
		return !nullable;

	}
}