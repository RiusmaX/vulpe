package org.vulpe.controller.struts.interceptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.vulpe.common.Constants;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ValidationAware;
import com.opensymphony.xwork2.util.CompoundRoot;
import com.opensymphony.xwork2.util.OgnlUtil;
import com.opensymphony.xwork2.util.ValueStack;

@SuppressWarnings( { "serial", "unchecked" })
public class VulpeChainingInterceptor extends
		com.opensymphony.xwork2.interceptor.ChainingInterceptor {

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
		if (Boolean.TRUE.equals(ActionContext.getContext().get(
				Constants.CLEAR_PARAMS))) {
			if (invocation.getAction() instanceof ValidationAware) {
				final ValueStack stack = invocation.getStack();
				final CompoundRoot root = stack.getRoot();
				if (root.size() > 1) {
					final List list = new ArrayList(root);
					list.remove(0);
					Collections.reverse(list);
					final Map ctxMap = invocation.getInvocationContext()
							.getContextMap();
					final Iterator iterator = list.iterator();
					while (iterator.hasNext()) {
						final Object obj = iterator.next();
						if (obj instanceof ValidationAware) {
							new OgnlUtil().copy(obj, invocation.getAction(),
									ctxMap, null, Arrays.asList(new String[] {
											"actionErrors", "actionMessages",
											"fieldErrors" }));
						}
					}
				}
			}
			return invocation.invoke();
		}
		return super.intercept(invocation);
	}
}