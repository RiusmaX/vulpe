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
package org.vulpe.security.vraptor.controller;

import org.vulpe.commons.VulpeConstants;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.commons.VulpeControllerConfig.ControllerType;
import org.vulpe.controller.vraptor.AbstractVulpeVRaptorSimpleController;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;

@Resource
@Path("/authenticator")
@SuppressWarnings("serial")
@Controller(controllerType = ControllerType.OTHER)
public class VulpeLoginController extends AbstractVulpeVRaptorSimpleController {

	private Integer loginError;

	private boolean accessDenied;

	/**
	 *
	 * @return
	 */
	public String define() {
		if (accessDenied) {
			return VulpeConstants.Action.Forward.ACCESS_DENIED;
		}
		if (loginError != null && loginError == 1) {
			return VulpeConstants.Action.Forward.ERRORS;
		}
		return VulpeConstants.Action.Forward.SUCCESS;
	}

	public Integer getLoginError() {
		return loginError;
	}

	public void setLoginError(final Integer loginError) {
		this.loginError = loginError;
	}

	public boolean isAccessDenied() {
		return accessDenied;
	}

	public void setAccessDenied(final boolean accessDenied) {
		this.accessDenied = accessDenied;
	}

}
