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

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.vulpe.commons.VulpeConstants;
import org.vulpe.commons.helper.VulpeCacheHelper;
import org.vulpe.commons.util.VulpeValidationUtil;
import org.vulpe.controller.AbstractVulpeBaseController;
import org.vulpe.controller.VulpeController;
import org.vulpe.controller.annotations.ResetSession;
import org.vulpe.exception.VulpeSystemException;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ValidationAware;
import com.opensymphony.xwork2.interceptor.ParametersInterceptor;
import com.opensymphony.xwork2.util.ValueStack;
import com.opensymphony.xwork2.util.reflection.ReflectionContextState;

@SuppressWarnings( { "serial", "unchecked" })
public class VulpeParametersInterceptor extends ParametersInterceptor {

	private static final Logger LOG = Logger.getLogger(VulpeParametersInterceptor.class);

	private ActionInvocation invocation;

	@Override
	protected void setParameters(final Object action, final ValueStack stack, final Map parameters) {
		super.setParameters(action, stack, parameters);
		String key = "";
		if (action instanceof VulpeController) {
			final Map<String, String> mapControllerMethods = VulpeCacheHelper.getInstance().get(
					VulpeConstants.CONTROLLER_METHODS);
			final AbstractVulpeBaseController controller = (AbstractVulpeBaseController) invocation
					.getAction();
			if (!mapControllerMethods.containsKey(invocation.getProxy().getMethod())) {
				if (StringUtils.isEmpty(controller.getResultForward())) {
					controller.controlResultForward();
					controller.manageButtons(controller.getOperation());
				}
			}
			key = controller.getCurrentControllerKey().concat(VulpeConstants.PARAMS_SESSION_KEY);
		}

		if (isMethodReset(this.invocation)) {
			if (action instanceof VulpeController) {
				final AbstractVulpeBaseController controller = (AbstractVulpeBaseController) invocation
						.getAction();
				controller.ever.remove(key);
			}
		} else {
			final Map params = (Map) ActionContext.getContext().getSession().get(key);
			if (params != null) {
				final boolean createNullObjects = ReflectionContextState
						.isCreatingNullObjects(stack.getContext());
				try {
					for (final Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
						final String name = (String) iterator.next();
						final Object[] value = (Object[]) params.get(name);
						ReflectionContextState.setCreatingNullObjects(stack.getContext(), false);
						if (VulpeValidationUtil.isEmpty(stack.findValue(name))) {
							ReflectionContextState.setCreatingNullObjects(stack.getContext(), true);
							stack.setValue(name, value[1]);
						}
						if (Boolean.TRUE.equals(value[0])) {
							params.remove(name);
						}
					}
				} finally {
					ReflectionContextState.setCreatingNullObjects(stack.getContext(),
							createNullObjects);
				}
			}
		}
	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		LOG.debug("Init intercept");
		this.invocation = invocation;
		return super.intercept(invocation);
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