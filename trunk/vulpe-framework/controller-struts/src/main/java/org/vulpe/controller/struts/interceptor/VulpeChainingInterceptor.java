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
package org.vulpe.controller.struts.interceptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.vulpe.commons.VulpeConstants.Action;
import org.vulpe.controller.VulpeController;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ValidationAware;
import com.opensymphony.xwork2.interceptor.ChainingInterceptor;
import com.opensymphony.xwork2.util.CompoundRoot;
import com.opensymphony.xwork2.util.OgnlUtil;
import com.opensymphony.xwork2.util.ValueStack;

@SuppressWarnings({ "serial", "unchecked" })
public class VulpeChainingInterceptor extends ChainingInterceptor {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.opensymphony.xwork2.interceptor.ChainingInterceptor#intercept(com
	 * .opensymphony.xwork2.ActionInvocation)
	 */
	@SuppressWarnings("static-access")
	@Override
	public String intercept(final ActionInvocation invocation) throws Exception {
		if (invocation.getAction() != null && invocation.getAction() instanceof VulpeController) {
			if (Action.READ.equals(invocation.getProxy().getMethod())) {
				if (invocation.getAction() instanceof ValidationAware) {
					final ValueStack stack = invocation.getStack();
					final CompoundRoot root = stack.getRoot();
					if (root.size() > 1) {
						final List list = new ArrayList(root);
						list.remove(0);
						Collections.reverse(list);
						final Map ctxMap = invocation.getInvocationContext().getContextMap();
						final Iterator iterator = list.iterator();
						while (iterator.hasNext()) {
							final Object obj = iterator.next();
							if (obj instanceof ValidationAware) {
								new OgnlUtil().copy(
										obj,
										invocation.getAction(),
										ctxMap,
										null,
										Arrays.asList(new String[] { "actionErrors",
												"actionMessages", "fieldErrors" }));
							}
						}
					}
				}
				return invocation.invoke();
			}
		}
		return super.intercept(invocation);
	}
}