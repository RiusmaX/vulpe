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

import org.vulpe.commons.VulpeConstants;
import org.vulpe.controller.AbstractVulpeBaseController;
import org.vulpe.controller.VulpeController;
import org.vulpe.controller.annotations.ResetSession;
import org.vulpe.exception.VulpeSystemException;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ValidationAware;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

@SuppressWarnings( { "serial", "unchecked" })
public class VulpeResetParametersInterceptor extends MethodFilterInterceptor {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.opensymphony.xwork2.interceptor.MethodFilterInterceptor#doIntercept
	 * (com.opensymphony.xwork2.ActionInvocation)
	 */
	@Override
	protected String doIntercept(final ActionInvocation invocation) throws Exception {
		if (invocation.getAction() instanceof VulpeController) {
			final AbstractVulpeBaseController controller = (AbstractVulpeBaseController) invocation
					.getAction();
			final String key = controller.getCurrentControllerKey().concat(
					VulpeConstants.PARAMS_SESSION_KEY);
			if (controller.ever.containsKey(key) && isMethodReset(invocation)) {
				controller.ever.remove(key);
			}
		}
		return invocation.invoke();
	}

	/**
	 * 
	 * @param action
	 * @return
	 */
	protected boolean isMethodReset(final ActionInvocation invocation) {
		try {
			boolean reset = false;
			if (invocation.getAction() instanceof ValidationAware) {
				final ValidationAware validationAware = (ValidationAware) invocation.getAction();
				reset = (validationAware.hasActionErrors() || validationAware.hasFieldErrors() ? false
						: validationAware.getClass().getMethod(invocation.getProxy().getMethod())
								.isAnnotationPresent(ResetSession.class));
			}
			return reset;
		} catch (Exception e) {
			throw new VulpeSystemException(e);
		}
	}

}