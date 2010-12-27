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
import org.apache.struts2.ServletActionContext;
import org.vulpe.commons.VulpeConstants;
import org.vulpe.commons.VulpeConstants.Configuration.Ever;
import org.vulpe.commons.helper.VulpeCacheHelper;
import org.vulpe.commons.util.VulpeValidationUtil;
import org.vulpe.controller.AbstractVulpeBaseController;
import org.vulpe.controller.AbstractVulpeBaseSimpleController;
import org.vulpe.controller.VulpeController;
import org.vulpe.controller.annotations.ResetSession;
import org.vulpe.controller.util.ControllerUtil;
import org.vulpe.exception.VulpeSystemException;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ValidationAware;
import com.opensymphony.xwork2.interceptor.ParametersInterceptor;
import com.opensymphony.xwork2.util.OgnlContextState;
import com.opensymphony.xwork2.util.ValueStack;

@SuppressWarnings( { "serial", "unchecked" })
public class SessionParametersInterceptor extends ParametersInterceptor {

	private ActionInvocation invocation;

	@Override
	protected void setParameters(final Object action, final ValueStack stack, final Map parameters) {
		super.setParameters(action, stack, parameters);
		if (invocation.getAction() instanceof VulpeController) {
			final Map<String, String> mapControllerMethods = VulpeCacheHelper.getInstance().get(
					VulpeConstants.CONTROLLER_METHODS);
			if (!mapControllerMethods.containsKey(invocation.getProxy().getMethod())) {
				final VulpeController controller = (VulpeController) invocation.getAction();
				if (StringUtils.isEmpty(controller.getResultForward())) {
					controller.controlResultForward();
					controller.manageButtons(controller.getOperation());
				}
			}
			final AbstractVulpeBaseSimpleController simpleController = (AbstractVulpeBaseSimpleController) invocation
					.getAction();
			if (simpleController.ever != null) {
				final String currentControllerKey = simpleController.ever.getSelf(Ever.CURRENT_CONTROLLER_KEY);
				final String controllerKey = simpleController.getControllerUtil().getCurrentControllerKey();
				boolean autocomplete = false;
				if (simpleController instanceof AbstractVulpeBaseController) {
					final AbstractVulpeBaseController controller = (AbstractVulpeBaseController) simpleController;
					if (controller.getEntitySelect() != null
							&& StringUtils.isNotEmpty(controller.getEntitySelect().getAutocomplete())) {
						autocomplete = true;
					}
				}
				if (StringUtils.isEmpty(currentControllerKey)) {
					simpleController.ever.put(Ever.CURRENT_CONTROLLER_KEY, controllerKey);
				} else if (!currentControllerKey.equals(controllerKey)
						&& StringUtils.isEmpty(simpleController.getPopupKey()) && !autocomplete) {
					simpleController.ever.removeWeakRef();
					simpleController.ever.put(Ever.CURRENT_CONTROLLER_KEY, controllerKey);
				}
			}
			ServletActionContext.getRequest().getSession().setAttribute(
					VulpeConstants.Configuration.Ever.class.getName(), simpleController.ever);
		}
		final String key = ControllerUtil.getInstance().getCurrentControllerKey().concat(
				VulpeConstants.PARAMS_SESSION_KEY);
		if (isMethodReset(this.invocation)) {
			ActionContext.getContext().getSession().remove(key);
		} else {
			final Map params = (Map) ActionContext.getContext().getSession().get(key);
			if (params != null) {
				final boolean createNullObjects = OgnlContextState.isCreatingNullObjects(stack.getContext());
				try {
					for (final Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
						final String name = (String) iterator.next();
						final Object[] value = (Object[]) params.get(name);
						OgnlContextState.setCreatingNullObjects(stack.getContext(), false);
						if (VulpeValidationUtil.isEmpty(stack.findValue(name))) {
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

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
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
						: validationAware.getClass().getMethod(invocation.getProxy().getMethod()).isAnnotationPresent(
								ResetSession.class));
			}
			return reset;
		} catch (Exception e) {
			throw new VulpeSystemException(e);
		}
	}
}