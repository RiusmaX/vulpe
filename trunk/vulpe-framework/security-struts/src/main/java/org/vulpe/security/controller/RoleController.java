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
package org.vulpe.security.controller;

import static org.vulpe.controller.struts.VulpeStrutsController.BaseActionButtons.CREATE;
import static org.vulpe.controller.struts.VulpeStrutsController.BaseActionButtons.DELETE;
import static org.vulpe.controller.struts.VulpeStrutsController.BaseActionButtons.UPDATE;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.struts.VulpeStrutsController;
import org.vulpe.security.model.entity.Role;
import org.vulpe.security.model.services.SecurityServices;

@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component("security.RoleController")
@Controller(serviceClass = SecurityServices.class, pageSize = 5, tabularNewDetails = 4, tabularDespiseFields = { "name" })
@SuppressWarnings("serial")
public class RoleController extends VulpeStrutsController<Role, Long> {

	@Override
	protected void showButtons(final String method) {
		super.showButtons(method);
		hideButton(new BaseActionButtons[] { CREATE, UPDATE, DELETE });
	}

}