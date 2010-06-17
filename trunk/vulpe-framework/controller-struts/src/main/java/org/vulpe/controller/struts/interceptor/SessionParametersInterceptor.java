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

import java.util.Iterator;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.vulpe.commons.VulpeConstants;
import org.vulpe.commons.VulpeValidationUtil;
import org.vulpe.controller.annotations.ResetSession;
import org.vulpe.controller.util.ControllerUtil;
import org.vulpe.exception.VulpeSystemException;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.OgnlContextState;
import com.opensymphony.xwork2.util.ValueStack;

@SuppressWarnings("serial")
public class SessionParametersInterceptor extends
		com.opensymphony.xwork2.interceptor.ParametersInterceptor {

	@Override
	@SuppressWarnings("unchecked")
	protected void setParameters(final Object action, final ValueStack stack, final Map parameters) {
		super.setParameters(action, stack, parameters);

		final String key = ControllerUtil.getInstance(ServletActionContext.getRequest())
				.getCurrentControllerKey().concat(VulpeConstants.PARAMS_SESSION_KEY);
		if (isMethodReset(action)) {
			ActionContext.getContext().getSession().remove(key);
		} else {
			final Map params = (Map) ActionContext.getContext().getSession().get(key);
			if (params != null) {
				final boolean createNullObjects = OgnlContextState.isCreatingNullObjects(stack
						.getContext());
				try {
					for (final Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
						final String name = (String) iterator.next();
						final Object[] value = (Object[]) params.get(name);
						OgnlContextState.setCreatingNullObjects(stack.getContext(), false);
						if (VulpeValidationUtil.getInstance().isEmpty(stack.findValue(name))) {
							OgnlContextState.setCreatingNullObjects(stack.getContext(), true);
							stack.setValue(name, value[1]);
						}
						if (Boolean.TRUE.equals(value[0])) {
							params.remove(name);
						}
					}
				} finally {
					OgnlContextState.setCreatingNullObjects(stack.getContext(), createNullObjects);
				}
			}
		}
	}

	protected boolean isMethodReset(final Object action) {
		try {
			final ResetSession resetSession = action.getClass().getMethod(
					ControllerUtil.getInstance(ServletActionContext.getRequest())
							.getCurrentMethod()).getAnnotation(ResetSession.class);
			return resetSession != null && resetSession.before();
		} catch (Exception e) {
			throw new VulpeSystemException(e);
		}
	}
}