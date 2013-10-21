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

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ognl.NoSuchPropertyException;
import ognl.OgnlException;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ObjectFactory;
import com.opensymphony.xwork2.XWorkException;
import com.opensymphony.xwork2.conversion.ObjectTypeDeterminer;
import com.opensymphony.xwork2.conversion.impl.XWorkConverter;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.ognl.accessor.XWorkCollectionPropertyAccessor;
import com.opensymphony.xwork2.util.reflection.ReflectionContextState;

/**
 * Utility class to fix bugs on Set data binding.
 *
 * @author <a href="mailto:fabio.viana@vulpe.org">Fábio Viana</a>
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class XWorkSetPropertyAccessor extends XWorkCollectionPropertyAccessor {

	private ObjectTypeDeterminer objectTypeDeterminer;

	@Inject
	@Override
	public void setObjectTypeDeterminer(ObjectTypeDeterminer ot) {
		objectTypeDeterminer = ot;
	}

	@Override
	public Object getProperty(final Map context, final Object target, final Object name)
			throws OgnlException {
		if (target instanceof Set && name instanceof Number) {
			final Set set = (Set) target;
			final List list = getList(set);
			final int index = ((Number) name).intValue();
			ReflectionContextState.updateCurrentPropertyPath(context, name);
			final Class lastClass = (Class) context.get(XWorkConverter.LAST_BEAN_CLASS_ACCESSED);
			final String lastProperty = (String) context
					.get(XWorkConverter.LAST_BEAN_PROPERTY_ACCESSED);

			if (lastClass == null || lastProperty == null) {
				return super.getProperty(context, target, name);
			}

			Object result = null;
			final Class beanClass = objectTypeDeterminer.getElementClass(lastClass, lastProperty,
					name);
			if (ReflectionContextState.isCreatingNullObjects(context)
					&& objectTypeDeterminer.shouldCreateIfNew(lastClass, lastProperty, target,
							null, true)) {
				boolean setted = false;
				if (list.size() <= index) {
					for (int i = list.size(); i < index; i++) {
						list.add(null);
					}
					try {
						final boolean added = set.add(result = ActionContext.getContext()
								.getContainer().getInstance(ObjectFactory.class)
								.buildBean(beanClass, context));
						if (added) {
							list.add(index, result);
							setted = true;
						}
					} catch (Exception exc) {
						throw new XWorkException(exc);
					}
				} else if (list.get(index) == null) {
					try {
						list.set(index, (result = ActionContext.getContext().getContainer()
								.getInstance(ObjectFactory.class).buildBean(beanClass, context)));
						setted = true;
					} catch (Exception exc) {
						throw new XWorkException(exc);
					}
				} else {
					result = list.get(index);
				}
				if (setted) {
					set.clear();
					for (Object object : list) {
						if (object != null) {
							set.add(object);
						}
					}
				}
			} else {
				if (index < list.size()) {
					result = list.get(index);
				}
			}

			return result;
		}
		return super.getProperty(context, target, name);
	}

	private final transient List<WeakReference<Object[]>> cache = Collections
			.synchronizedList(new ArrayList<WeakReference<Object[]>>());

	private List getList(final Set set) {
		final List<WeakReference<Object[]>> excluds = new ArrayList<WeakReference<Object[]>>();
		try {
			List list = null;
			for (int i = 0; i < cache.size(); i++) {
				final Object[] objs = cache.get(i).get();
				if (objs != null && objs[0] == set) {
					list = (List) objs[1];
					break;
				}
				excluds.add(cache.get(i));
			}
			if (list == null) {
				list = new ArrayList(set);
				cache.add(new WeakReference<Object[]>(new Object[] { set, list }));
			}
			return list;
		} finally {
			if (!excluds.isEmpty()) {
				cache.removeAll(excluds);
			}
		}
	}

	@Override
	public void setProperty(final Map context, final Object target, final Object name,
			final Object value) throws OgnlException {
		if (target instanceof Set && name instanceof Number) {
			final Set set = (Set) target;
			final List list = getList(set);
			final int index = ((Number) name).intValue();
			if (index > list.size()) {
				throw new NoSuchPropertyException(target, name);
			} else {
				if (index == list.size()) {
					list.add(value);
				} else {
					list.set(index, value);
				}
				set.clear();
				set.addAll(list);
				return;
			}
		}
		super.setProperty(context, target, name, value);
	}
}