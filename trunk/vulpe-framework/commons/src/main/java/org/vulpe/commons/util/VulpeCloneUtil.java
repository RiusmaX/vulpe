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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.mapping.Collection;
import org.vulpe.model.entity.VulpeEntity;

/**
 * Utility class to clone beans.
 * 
 */
@SuppressWarnings("unchecked")
public class VulpeCloneUtil {

	private static final Logger LOG = Logger.getLogger(VulpeCloneUtil.class);

	public static  VulpeEntity<?> clone(final VulpeEntity<?> entity, final String parent) {
		LOG.debug("Initiating clone");
		final VulpeEntity<?> clone = (VulpeEntity<?>) entity.clone();
		final List<Field> fields = VulpeReflectUtil.getFields(clone.getClass());
		for (final Field field : fields) {
			if (VulpeValidationUtil.isNotEmpty(parent) && parent.equals(field.getName())) {
				continue;
			}
			if (Collection.class.isAssignableFrom(field.getType())) {
				final List<?> value1 = VulpeReflectUtil.getFieldValue(clone, field.getName());
				if (VulpeValidationUtil.isNotEmpty(value1)) {
					final List<?> value2 = ((List<?>) ((ArrayList<?>) value1).clone());
					for (final VulpeEntity<?> childEntity : (List<VulpeEntity<?>>) value2) {
						if (VulpeValidationUtil.isNotEmpty(childEntity)) {
							clone((VulpeEntity<?>) childEntity.clone(), VulpeStringUtil
									.getAttributeName(clone.getClass().getSimpleName()));
						}
					}
					VulpeReflectUtil.setFieldValue(clone, field.getName(), value2);
				}
			} else if (VulpeEntity.class.isAssignableFrom(field.getType())) {
				final VulpeEntity<?> value = VulpeReflectUtil.getFieldValue(clone, field.getName());
				if (VulpeValidationUtil.isNotEmpty(value)) {
					VulpeReflectUtil.setFieldValue(clone, field.getName(), clone(
							(VulpeEntity<?>) value.clone(), VulpeStringUtil.getAttributeName(clone
									.getClass().getSimpleName())));
				}
			}
		}
		LOG.debug("Ending clone");
		return clone;
	}

}