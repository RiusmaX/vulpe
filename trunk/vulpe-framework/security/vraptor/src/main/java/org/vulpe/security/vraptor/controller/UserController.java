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

import org.apache.commons.lang.StringUtils;
import org.vulpe.commons.annotations.DetailConfig;
import org.vulpe.commons.annotations.Quantity;
import org.vulpe.commons.annotations.Quantity.QuantityType;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.vraptor.VulpeVRaptorController;
import org.vulpe.security.model.entity.User;
import org.vulpe.security.model.services.SecurityService;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;

@Resource
@Path("/security/User")
@Controller(serviceClass = SecurityService.class, detailsConfig = { @DetailConfig(name = "userRoles", propertyName = "entity.userRoles", despiseFields = "role", startNewDetails = 1, newDetails = 1, quantity = @Quantity(type = QuantityType.ONE_OR_MORE)) })
@SuppressWarnings("serial")
public class UserController extends VulpeVRaptorController<User, Long> {

	@Override
	public void createPost() {
		if ((StringUtils.isNotBlank(entity.getPassword()) && StringUtils
				.isNotBlank(entity.getPasswordConfirm()))
				&& (!entity.getPassword().equals(entity.getPasswordConfirm()))) {
			showError("{vulpe.security.user.password.not.match}");
		}
		setPassword(entity.getPassword());
		super.createPost();
	}

	@Override
	protected void onUpdate() {
		super.onUpdate();
		setPassword(entity.getPassword());
	}

	@Override
	protected boolean onUpdatePost() {
		if (StringUtils.isBlank(entity.getPassword())
				&& StringUtils.isBlank(entity.getPasswordConfirm())) {
			entity.setPassword(getPassword());
		} else {
			setPassword(entity.getPassword());
		}
		if ((StringUtils.isNotBlank(entity.getPassword()) && StringUtils
				.isNotBlank(entity.getPasswordConfirm()))
				&& (!entity.getPassword().equals(entity.getPasswordConfirm()))) {
			addActionError("{vulpe.security.user.password.not.match}");
			return false;
		}
		return super.onUpdatePost();
	}

	public String getPassword() {
		return (String) getRequest().getAttribute("password");
	}

	public void setPassword(final String password) {
		getRequest().setAttribute("password", password);
	}
}
