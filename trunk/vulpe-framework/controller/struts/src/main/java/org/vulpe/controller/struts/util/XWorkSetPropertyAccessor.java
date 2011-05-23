/**
 * Vulpe Framework - Copyright (c) Active Thread
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
@SuppressWarnings("unchecked")
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
				boolean seted = false;
				if (list.size() <= index) {
					for (int i = list.size(); i < index; i++) {
						list.add(null);
					}
					try {
						final boolean added = set.add(result = ActionContext.getContext()
								.getContainer().getInstance(ObjectFactory.class).buildBean(
										beanClass, context));
						if (added) {
							list.add(index, result);
							seted = true;
						}
					} catch (Exception exc) {
						throw new XWorkException(exc);
					}
				} else if (list.get(index) == null) {
					try {
						list.set(index, (result = ActionContext.getContext().getContainer()
								.getInstance(ObjectFactory.class).buildBean(beanClass, context)));
						seted = true;
					} catch (Exception exc) {
						throw new XWorkException(exc);
					}
				} else {
					result = list.get(index);
				}
				if (seted) {
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

	private transient final List<WeakReference<Object[]>> cache = Collections
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