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
package org.vulpe.view.struts.result;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.vulpe.commons.VulpeConstants;
import org.vulpe.controller.struts.VulpeStrutsController;

import com.opensymphony.xwork2.ActionChainResult;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;

@SuppressWarnings( { "serial", "unchecked" })
public class SessionActionChainResult extends ActionChainResult {
	private boolean saveParams;
	private boolean sendParams;
	private boolean clearParams;
	private String noClearParamNames;
	private String name;

	public String getNoClearParamNames() {
		return noClearParamNames;
	}

	public void setNoClearParamNames(final String noClearParamNames) {
		this.noClearParamNames = noClearParamNames;
	}

	public boolean isSaveParams() {
		return saveParams;
	}

	public boolean isClearParams() {
		return clearParams;
	}

	public void setClearParams(final boolean clearParams) {
		this.clearParams = clearParams;
	}

	public void setSaveParams(final boolean saveParams) {
		this.saveParams = saveParams;
	}

	public boolean isSendParams() {
		return sendParams;
	}

	public void setSendParams(final boolean sendParams) {
		this.sendParams = sendParams;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getName(final ActionInvocation invocation, final boolean owner) {
		if (StringUtils.isEmpty(name)) {
			name = ActionContext.getContext().getName();
			if (invocation.getAction() instanceof VulpeStrutsController) {
				final VulpeStrutsController<?, ?> action = (VulpeStrutsController<?, ?>) invocation
						.getAction();
				name = owner ? action.getControllerConfig().getOwnerAction()
						: action.getControllerConfig().getActionName();
			}
		}
		return name;
	}

	@Override
	public void execute(final ActionInvocation invocation) throws Exception {
		final Map requestParams = invocation.getInvocationContext()
				.getParameters();
		final Map sessionParams = invocation.getInvocationContext()
				.getSession();
		String name;
		if (isClearParams()) {
			ActionContext.getContext()
					.put(VulpeConstants.CLEAR_PARAMS, Boolean.TRUE);
		}

		Map params = null;
		if (isClearParams()) {
			if (StringUtils.isNotEmpty(getNoClearParamNames())) {
				params = new HashMap();
				final String paramNames[] = getNoClearParamNames().split(",");
				for (int i = 0; i < paramNames.length; i++) {
					if (paramNames[i].contains("=")) {
						final String paramName[] = paramNames[i].split("=");
						final Object value = requestParams.get(paramName[0]);
						params.put(paramName[0],
								(value == null ? new String[] { paramName[1] }
										: value));
					} else {
						params.put(paramNames[i], requestParams
								.get(paramNames[i]));
					}
				}
			}
			requestParams.clear();
		}

		if (isSaveParams()) {
			name = getName(invocation, false);
			sessionParams.put(name, requestParams);
		}

		if (isSendParams()) {
			name = getName(invocation, true);
			final Map paramsInSession = (Map) sessionParams.get(name);
			if (paramsInSession != null) {
				requestParams.putAll(paramsInSession);
				sessionParams.remove(name);
			}
		}

		if (params != null) {
			requestParams.putAll(params);
		}

		super.execute(invocation);
	}
}