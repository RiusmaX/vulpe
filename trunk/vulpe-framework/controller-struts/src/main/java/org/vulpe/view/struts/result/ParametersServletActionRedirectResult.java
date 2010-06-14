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

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.dispatcher.ServletActionRedirectResult;
import org.vulpe.controller.struts.VulpeStrutsController;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;

@SuppressWarnings("serial")
public class ParametersServletActionRedirectResult extends ServletActionRedirectResult {
	public ParametersServletActionRedirectResult() {
		super();
		sendParameters = false;
		sendParamsInSession = false;
		saveParamsInSession = false;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void execute(final ActionInvocation invocation) throws Exception {
		String name = ActionContext.getContext().getName().substring(0,
				StringUtils.lastIndexOf(ActionContext.getContext().getName(), '.'));
		if (saveParamsInSession) {
			invocation.getInvocationContext().getSession().put(name,
					invocation.getInvocationContext().getParameters());
		} else if (sendParamsInSession) {
			if (invocation.getAction() instanceof VulpeStrutsController) {
				final VulpeStrutsController action = (VulpeStrutsController) invocation.getAction();
				name = action.getControllerConfig().getOwnerController();
			}
			final Map params = (Map) invocation.getInvocationContext().getSession().get(name);
			if (params != null) {
				for (Object key : params.keySet()) {
					final String[] value = (String[]) params.get(key);
					addParameter((String) key, value[0]);
				}
				invocation.getInvocationContext().getSession().remove(name);
			}
		}

		if (sendParameters) {
			for (Object key : invocation.getInvocationContext().getParameters().keySet()) {
				final String[] val = (String[]) invocation.getInvocationContext().getParameters()
						.get(key);
				addParameter((String) key, val[0]);
			}
		}

		super.execute(invocation);
	}

	private boolean sendParameters;
	private boolean sendParamsInSession;
	private boolean saveParamsInSession;

	public boolean isSendParameters() {
		return sendParameters;
	}

	public void setSendParameters(final boolean sendParameters) {
		this.sendParameters = sendParameters;
	}

	public boolean isSendParamsInSession() {
		return sendParamsInSession;
	}

	public void setSendParamsInSession(final boolean sendParamsInSession) {
		this.sendParamsInSession = sendParamsInSession;
	}

	public boolean isSaveParamsInSession() {
		return saveParamsInSession;
	}

	public void setSaveParamsInSession(final boolean saveParamsInSession) {
		this.saveParamsInSession = saveParamsInSession;
	}
}