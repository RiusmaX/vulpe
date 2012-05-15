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
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Transient;

import org.apache.commons.lang.IllegalClassException;
import org.apache.commons.lang.NullArgumentException;
import org.apache.log4j.Logger;
import org.hibernate.mapping.Collection;
import org.vulpe.commons.helper.VulpeConfigHelper;
import org.vulpe.model.annotations.SkipCompare;
import org.vulpe.model.entity.VulpeEntity;

import com.sun.jmx.snmp.Timestamp;

/**
 * Utility class to compare beans.
 * 
 */
public class VulpeBeanComparatorUtil {

	private static final Logger LOG = Logger.getLogger(VulpeBeanComparatorUtil.class);

	/**
	 * Compare beans and return map with differences by field.
	 * 
	 * @param bean1
	 * @param bean2
	 * @return Map with differences by field.
	 */
	public static Map<String, Object[]> compare(final Object bean1, final Object bean2,
			boolean skipCollections, boolean skipTransient) {
		if (VulpeValidationUtil.isEmpty(bean1) || VulpeValidationUtil.isEmpty(bean2)) {
			throw new NullArgumentException("bean1(" + bean1 + ") bean2(" + bean2 + ")");
		}
		if (!bean1.getClass().equals(bean2.getClass()) || VulpeValidationUtil.isEmpty(bean1)
				|| VulpeValidationUtil.isEmpty(bean2)) {
			throw new IllegalClassException(bean1.getClass(), bean2.getClass());
		}
		final Map<String, Object[]> diffMap = new HashMap<String, Object[]>();
		final List<Field> fields = VulpeReflectUtil.getFields(bean1.getClass());
		for (final Field field : fields) {
			if (VulpeReflectUtil.isAnnotationInField(SkipCompare.class, bean1.getClass(), field)
					|| (skipCollections && Collection.class.isAssignableFrom(field.getType()))
					|| (skipTransient && (Modifier.isTransient(field.getModifiers()) || field
							.isAnnotationPresent(Transient.class)))) {
				continue;
			}
			try {
				Object value1 = VulpeReflectUtil.getFieldValue(bean1, field.getName());
				Object value2 = VulpeReflectUtil.getFieldValue(bean2, field.getName());
				if (VulpeValidationUtil.isNull(value1, value2)) {
					continue;
				}
				boolean diff = false;
				if ((VulpeValidationUtil.isEmpty(value1) && VulpeValidationUtil.isNotEmpty(value2))
						|| (VulpeValidationUtil.isNotEmpty(value1) && VulpeValidationUtil
								.isEmpty(value2))) {
					diff = true;
				} else {
					if (Date.class.isAssignableFrom(field.getType())) {
						if (((Date) value1).getTime() != ((Date) value2).getTime()) {
							if (value1 instanceof Timestamp || value2 instanceof Timestamp) {
								value1 = VulpeDateUtil.getDate((Date) value1, VulpeConfigHelper
										.getDateTimePattern());
								value2 = VulpeDateUtil.getDate((Date) value2, VulpeConfigHelper
										.getDateTimePattern());
							} else {
								value1 = VulpeDateUtil.getDate((Date) value1, VulpeConfigHelper
										.getDatePattern());
								value2 = VulpeDateUtil.getDate((Date) value2, VulpeConfigHelper
										.getDatePattern());
							}
							diff = true;
						}
					} else if (VulpeEntity.class.isAssignableFrom(field.getType())) {
						if (!((VulpeEntity<?>) value1).getId().equals(
								((VulpeEntity<?>) value2).getId())) {
							value1 = ((VulpeEntity<?>) value1).getId();
							value2 = ((VulpeEntity<?>) value2).getId();
							diff = true;
						}
					} else if (!value1.equals(value2)) {
						diff = true;
					}
				}
				if (diff) {
					diffMap.put(field.getName(), new Object[] { value1, value2 });
				}
			} catch (Exception e) {
				LOG.error(e);
			}
		}
		return diffMap;
	}

	public static boolean isDifferent(final Object bean1, final Object bean2) {
		return !compare(bean1, bean2, false, true).isEmpty();
	}

}