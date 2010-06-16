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

import org.apache.struts2.ServletActionContext;
import org.vulpe.commons.VulpeConstants;
import org.vulpe.controller.annotations.ResetSession;
import org.vulpe.controller.util.ControllerUtil;
import org.vulpe.exception.VulpeSystemException;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ValidationAware;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

@SuppressWarnings("serial")
public class ResetSessionParametersInterceptor extends MethodFilterInterceptor {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.opensymphony.xwork2.interceptor.MethodFilterInterceptor#doIntercept
	 * (com.opensymphony.xwork2.ActionInvocation)
	 */
	@Override
	protected String doIntercept(final ActionInvocation invocation) throws Exception {
		final Object action = invocation.getAction();
		final String key = ControllerUtil.getInstance(ServletActionContext.getRequest())
				.getCurrentControllerName().replace("/", ".").concat(
						VulpeConstants.PARAMS_SESSION_KEY);
		if (ActionContext.getContext().getSession().containsKey(key) && isMethodReset(action)) {
			ActionContext.getContext().getSession().remove(key);
		}
		return invocation.invoke();
	}

	/**
	 * 
	 * @param action
	 * @return
	 */
	protected boolean isMethodReset(final Object action) {
		try {
			return action instanceof ValidationAware
					&& (((ValidationAware) action).hasActionErrors() || ((ValidationAware) action)
							.hasFieldErrors()) ? false : action.getClass().getMethod(
					ControllerUtil.getInstance(ServletActionContext.getRequest())
							.getCurrentMethod()).isAnnotationPresent(ResetSession.class);
		} catch (Exception e) {
			throw new VulpeSystemException(e);
		}
	}
}