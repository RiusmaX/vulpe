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

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.commons.VulpeConstants.Controller.Button;
import org.vulpe.commons.annotations.DetailConfig;
import org.vulpe.commons.annotations.Quantity;
import org.vulpe.commons.annotations.Quantity.QuantityType;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Select;
import org.vulpe.controller.struts.VulpeStrutsController;
import org.vulpe.security.model.entity.User;
import org.vulpe.security.model.services.SecurityService;

@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component("security.UserController")
@Controller(serviceClass = SecurityService.class, select = @Select(requireOneFilter = true), detailsConfig = { @DetailConfig(name = "userRoles", propertyName = "entity.userRoles", despiseFields = "role", startNewDetails = 1, newDetails = 1, quantity = @Quantity(type = QuantityType.ONE_OR_MORE)) })
@SuppressWarnings("serial")
public class UserController extends VulpeStrutsController<User, Long> {

	public static final String PASSWORD = "vulpeUserPassword";

	@Override
	public void createPost() {
		setPassword(getEntity().getPassword());
		super.createPost();
	}

	@Override
	public boolean validateEntity() {
		boolean valid = super.validateEntity();
		if (vulpe.controller().operation().equals(Operation.CREATE_POST)
				&& StringUtils.isBlank(getEntity().getPassword())) {
			addActionError("{vulpe.security.user.error.empty.password}");
			return false;
		}
		if ((StringUtils.isNotBlank(getEntity().getPassword()) && StringUtils
				.isNotBlank(getEntity().getPasswordConfirm()))
				&& (!getEntity().getPassword().equals(getEntity().getPasswordConfirm()))) {
			addActionError("{vulpe.security.user.error.password.not.match}");
			valid = false;
		}
		return valid;
	}

	@Override
	protected void onUpdate() {
		super.onUpdate();
		setPassword(getEntity().getPassword());
	}

	@Override
	protected boolean onUpdatePost() {
		if (StringUtils.isBlank(getEntity().getPassword())
				&& StringUtils.isBlank(getEntity().getPasswordConfirm())) {
			getEntity().setPasswordEncrypted(getPassword());
		} else {
			setPassword(getEntity().getPassword());
		}
		return super.onUpdatePost();
	}

	public String getPassword() {
		return ever.<String> getSelf(PASSWORD);
	}

	public void setPassword(final String password) {
		ever.putWeakRef(PASSWORD, password);
	}

	@Override
	public void manageButtons(Operation operation) {
		super.manageButtons(operation);
		vulpe.view().notRenderDetailButtons("userRoles", Button.ADD_DETAIL);
	}
}
