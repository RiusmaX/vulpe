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

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.ServletRedirectResult;
import org.apache.struts2.dispatcher.mapper.DefaultActionMapper;
import org.vulpe.commons.VulpeConstants;
import org.vulpe.commons.VulpeConstants.Configuration.Ever;
import org.vulpe.commons.VulpeConstants.Controller.Forward;
import org.vulpe.commons.helper.VulpeCacheHelper;
import org.vulpe.commons.util.VulpeReflectUtil;
import org.vulpe.commons.util.VulpeValidationUtil;
import org.vulpe.controller.AbstractVulpeBaseController;
import org.vulpe.controller.VulpeController;
import org.vulpe.controller.annotations.ExecuteAlways;
import org.vulpe.controller.annotations.ExecuteOnce;
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
			if (!mapControllerMethods.containsKey(invocation.getProxy().getMethod())) {
				final VulpeController controller = (VulpeController) action;
				if (StringUtils.isEmpty(controller.getResultForward())) {
					controller.controlResultForward();
					controller.manageButtons(controller.getOperation());
				}
			}
			final AbstractVulpeBaseController controller = (AbstractVulpeBaseController) invocation.getAction();
			if (controller.ever != null) {
				final String currentControllerKey = controller.ever.getSelf(Ever.CURRENT_CONTROLLER_KEY);
				final String controllerKey = new ControllerUtil().getCurrentControllerKey(controller);
				boolean autocomplete = false;
				if (controller.getEntitySelect() != null
						&& StringUtils.isNotEmpty(controller.getEntitySelect().getAutocomplete())) {
					autocomplete = true;
				}
				if (StringUtils.isEmpty(currentControllerKey)) {
					controller.ever.put(Ever.CURRENT_CONTROLLER_KEY, controllerKey);
					executeOnce(action);
				} else if (!currentControllerKey.equals(controllerKey) && StringUtils.isEmpty(controller.getPopupKey())
						&& !autocomplete) {
					controller.ever.removeWeakRef();
					controller.ever.put(Ever.CURRENT_CONTROLLER_KEY, controllerKey);
					executeOnce(action);
				}
			}
			ServletActionContext.getRequest().getSession().setAttribute(VulpeConstants.Session.EVER, controller.ever);
			ServletActionContext.getRequest().setAttribute(VulpeConstants.Request.NOW, controller.now);
			key = new ControllerUtil().getCurrentControllerKey(controller).concat(VulpeConstants.PARAMS_SESSION_KEY);
		}
		
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
		executeAlways(action);
	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		LOG.debug("Init intercept");
		this.invocation = invocation;
		String result = super.intercept(invocation);
		if (invocation.getAction() instanceof VulpeController && !result.equals(Forward.REDIRECT)) {
			final VulpeController controller = (VulpeController) invocation.getAction();
			if (StringUtils.isNotBlank(controller.getUrlRedirect())) {
				result = Forward.REDIRECT;
				VulpeReflectUtil.setFieldValue(invocation, "executed", false);
				final ServletRedirectResult srr = new ServletRedirectResult("${urlRedirect}");
				srr.setPrependServletContext(true);
				srr.setActionMapper(new DefaultActionMapper());
				VulpeReflectUtil.setFieldValue(srr, "lastFinalLocation", controller.getUrlRedirect());
				VulpeReflectUtil.setFieldValue(invocation, "result", srr);
				invocation.setResultCode(result);
			}
		}
		return result;
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

	private void executeAlways(final Object action) {
		if (action instanceof VulpeController) {
			final VulpeController controller = (VulpeController) action;
			final List<Method> methods = VulpeReflectUtil.getMethods(controller.getClass());
			for (final Method method : methods) {
				if (method.isAnnotationPresent(ExecuteAlways.class)) {
					try {
						method.invoke(controller, new Object[] {});
					} catch (Exception e) {
						LOG.error(e);
					}
				}
			}
		}
	}

	private void executeOnce(final Object action) {
		if (action instanceof VulpeController) {
			final VulpeController controller = (VulpeController) action;
			final List<Method> methods = VulpeReflectUtil.getMethods(controller.getClass());
			for (final Method method : methods) {
				if (method.isAnnotationPresent(ExecuteOnce.class)) {
					try {
						method.invoke(controller, new Object[] {});
					} catch (Exception e) {
						LOG.error(e);
					}
				}
			}
		}
	}

}