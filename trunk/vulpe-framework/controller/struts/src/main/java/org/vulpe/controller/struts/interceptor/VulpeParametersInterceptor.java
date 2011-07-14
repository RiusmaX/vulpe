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

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.vulpe.commons.VulpeConstants;
import org.vulpe.commons.helper.VulpeCacheHelper;
import org.vulpe.controller.AbstractVulpeBaseController;
import org.vulpe.controller.VulpeController;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.ParametersInterceptor;
import com.opensymphony.xwork2.util.ValueStack;

@SuppressWarnings( { "serial", "unchecked" })
public class VulpeParametersInterceptor extends ParametersInterceptor {

	private static final Logger LOG = Logger.getLogger(VulpeParametersInterceptor.class);

	private ActionInvocation invocation;

	@Override
	protected void setParameters(final Object action, final ValueStack stack, final Map parameters) {
		super.setParameters(action, stack, parameters);
		if (action instanceof VulpeController) {
			final Map<String, String> mapControllerMethods = VulpeCacheHelper.getInstance().get(
					VulpeConstants.CONTROLLER_METHODS);
			final AbstractVulpeBaseController baseController = (AbstractVulpeBaseController) invocation
					.getAction();
			if (!mapControllerMethods.containsKey(invocation.getProxy().getMethod())) {
				if (StringUtils.isEmpty(baseController.vulpe.controller().resultForward())) {
					baseController.controlResultForward();
					baseController.manageButtons(baseController.vulpe.controller().operation());
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

}