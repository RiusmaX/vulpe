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

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Map;

import org.vulpe.commons.VulpeConstants.View.Struts;
import org.vulpe.commons.helper.VulpeCacheHelper;
import org.vulpe.commons.util.VulpeReflectUtil;
import org.vulpe.commons.util.VulpeReflectUtil.DeclaredType;

import com.opensymphony.xwork2.conversion.impl.DefaultObjectTypeDeterminer;
import com.opensymphony.xwork2.conversion.impl.XWorkConverter;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.util.reflection.ReflectionProvider;

/**
 * Utility class to solve problems on determine generic types.
 * 
 * @author <a href="mailto:fabio.viana@vulpe.org">Fábio Viana</a>
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 */
@SuppressWarnings("unchecked")
public class GenericsObjectTypeDeterminer extends DefaultObjectTypeDeterminer {

	@Inject
	public GenericsObjectTypeDeterminer(XWorkConverter conv, ReflectionProvider prov) {
		super(conv, prov);
		VulpeCacheHelper.getInstance().put(Struts.XWORK_CONVERTER, conv);
	}

	@Override
	public Class getElementClass(final Class parentClass, final String property, final Object key) {
		Class clazz = super.getElementClass(parentClass, property, key);
		if (clazz == null) {
			final Field field = VulpeReflectUtil.getField(parentClass, property);
			if (field.getGenericType() instanceof ParameterizedType) {
				final ParameterizedType type = (ParameterizedType) field.getGenericType();
				final int index = (Map.class.isAssignableFrom(VulpeReflectUtil.getDeclaredType(
						clazz, type.getRawType()).getType()) ? 1 : 0);
				final DeclaredType declaredType = VulpeReflectUtil.getDeclaredType(parentClass,
						type);
				clazz = declaredType.getItems().get(index).getType();
			}
		}
		return clazz;
	}

}