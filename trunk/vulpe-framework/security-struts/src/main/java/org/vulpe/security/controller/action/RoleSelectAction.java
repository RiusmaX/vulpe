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
package org.vulpe.security.controller.action;

import static org.vulpe.controller.struts.action.VulpeBaseAction.BaseActionButtons.CREATE;
import static org.vulpe.controller.struts.action.VulpeBaseAction.BaseActionButtons.DELETE;
import static org.vulpe.controller.struts.action.VulpeBaseAction.BaseActionButtons.UPDATE;

import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Controller.ControllerType;
import org.vulpe.controller.struts.action.VulpeBaseAction;
import org.vulpe.security.model.entity.Role;
import org.vulpe.security.model.services.SecurityServices;

@Controller(controllerType = ControllerType.SELECT, serviceClass = SecurityServices.class, pageSize = 5)
@SuppressWarnings("serial")
public class RoleSelectAction extends VulpeBaseAction<Role, Long> {

	@Override
	protected void showButtons(final String method) {
		super.showButtons(method);
		hideButton(new BaseActionButtons[] { CREATE, UPDATE, DELETE });
	}

}