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
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.struts.VulpeStrutsController;
import org.vulpe.security.model.entity.User;
import org.vulpe.security.model.services.SecurityService;

@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component("security.UserPasswordController")
@Controller(serviceClass = SecurityService.class)
@SuppressWarnings("serial")
public class UserPasswordController extends VulpeStrutsController<User, Long> {

	public static final String PASSWORD = "vulpeUserPassword";

	@Override
	public void update() {
		setEntity((User) vulpe.securityContext().getUser().clone());
		getEntity().setPassword(null);
		super.update();
	}

	@Override
	protected void onUpdate() {

	}

	@Override
	public boolean validateEntity() {
		final User user = vulpe.securityContext().getUser();
		boolean valid = super.validateEntity();
		if (StringUtils.isBlank(getEntity().getCurrentPassword())) {
			addActionError("{vulpe.security.user.error.empty.current.password}");
			valid = false;
		} else {
			if (user.getPassword().equals(getEntity().getCurrentPassword())) {
				if (StringUtils.isBlank(getEntity().getPassword())) {
					addActionError("{vulpe.security.user.error.empty.password}");
					setEntity(user);
					return false;
				}
				if ((StringUtils.isNotBlank(getEntity().getPassword()) && StringUtils
						.isNotBlank(getEntity().getPasswordConfirm()))
						&& (!getEntity().getPassword().equals(getEntity().getPasswordConfirm()))) {
					addActionError("{vulpe.security.user.error.new.password.not.match}");
					valid = false;
				}
				if (getEntity().getPassword().equals(getEntity().getCurrentPassword())) {
					addActionError("{vulpe.security.user.error.must.be.different.new.password.and.old.password}");
					setEntity(user);
					return false;
				}
			} else {
				addActionError("{vulpe.security.user.error.empty.current.password.not.match}");
				valid = false;
			}
		}
		if (!valid) {
			setEntity(user);
		}
		return valid;
	}

	@Override
	protected boolean onUpdatePost() {
		final User user = vulpe.securityContext().getUser();
		user.setPasswordEncrypted(getEntity().getPassword());
		user.setPasswordConfirmEncrypted(getEntity().getPasswordConfirm());
		setEntity(user);
		defaultMessage.put(Operation.UPDATE_POST, "{vulpe.security.msg.user.password.changed}");
		vulpe.controller().redirectTo("/j_spring_security_logout", false);
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
		vulpe.view().hideButtons(Button.CLEAR, Button.CREATE, Button.DELETE, Button.BACK,
				Button.CLONE);
	}
}
