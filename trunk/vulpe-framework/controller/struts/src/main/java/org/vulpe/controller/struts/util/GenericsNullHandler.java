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
package org.vulpe.controller.struts.util;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import ognl.Ognl;

import org.apache.log4j.Logger;
import org.vulpe.commons.util.VulpeReflectUtil;
import org.vulpe.commons.util.VulpeReflectUtil.DeclaredType;
import org.vulpe.model.entity.VulpeEntity;

import com.opensymphony.xwork2.ObjectFactory;
import com.opensymphony.xwork2.conversion.impl.InstantiatingNullHandler;
import com.opensymphony.xwork2.ognl.OgnlUtil;
import com.opensymphony.xwork2.util.reflection.ReflectionContextState;

/**
 * Utility class to solve problems on instantiate generic types.
 *
 * @author <a href="mailto:fabio.viana@vulpe.org">Fábio Viana</a>
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class GenericsNullHandler<ENTITY extends VulpeEntity<ID>, ID extends Serializable & Comparable<?>>
		extends InstantiatingNullHandler {

	private static final Logger LOG = Logger.getLogger(GenericsNullHandler.class);

	/*
	 * (non-Javadoc)
	 *
	 * @seecom.opensymphony.xwork2.conversion.impl.InstantiatingNullHandler#
	 * nullPropertyValue(java.util.Map, java.lang.Object, java.lang.Object)
	 */
	@Override
	public Object nullPropertyValue(final Map context, final Object target, final Object property) {
		try {
			final boolean createNullObjects = ReflectionContextState.isCreatingNullObjects(context);
			if (property != null && createNullObjects) {
				final Object realTarget = new OgnlUtil().getRealTarget(property.toString(),
						context, target);
				if (realTarget != null) {
					Class clazz = null;
					final Field field = VulpeReflectUtil.getField(realTarget.getClass(),
							property.toString());
					if (field != null) {
						if (!field.getType().equals(field.getGenericType())) {
							final DeclaredType declaredType = VulpeReflectUtil.getDeclaredType(
									realTarget.getClass(), field.getGenericType());
							if (!Collection.class.isAssignableFrom(declaredType.getType())
									&& declaredType.getType() != Map.class) {
								clazz = declaredType.getType();
							}
						} else if (Set.class.isAssignableFrom(field.getType())) {
							clazz = Set.class;
						}
					}
					if (clazz != null) {
						final ObjectFactory objectFactory = new ObjectFactory();
						final Object param = objectFactory.buildBean(clazz, context);
						if (param != null) {
							Ognl.setValue(property.toString(), context, realTarget, param);
							return param;
						}
					}
				}
			}
		} catch (Exception e) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("Error on instantiate attributes declared as generic.", e);
			}
		}
		return super.nullPropertyValue(context, target, property);
	}
}