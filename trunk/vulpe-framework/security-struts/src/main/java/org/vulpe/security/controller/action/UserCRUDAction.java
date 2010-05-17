package org.vulpe.security.controller.action;

import org.apache.commons.lang.StringUtils;
import org.vulpe.common.annotations.DetailConfig;
import org.vulpe.common.annotations.DetailConfig.CardinalityType;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Controller.ControllerType;
import org.vulpe.controller.struts.action.VulpeBaseAction;
import org.vulpe.security.model.entity.User;
import org.vulpe.security.model.services.SecurityServices;

@Controller(controllerType = ControllerType.CRUD, serviceClass = SecurityServices.class, detailsConfig = { @DetailConfig(name = "userRoles", propertyName = "entity.userRoles", despiseFields = "role", detailNews = 1, cardinalityType = CardinalityType.ONE_OR_MORE) })
@SuppressWarnings("serial")
public class UserCRUDAction extends VulpeBaseAction<User, Long> {

	@Override
	public String createPost() {
		if ((StringUtils.isNotBlank(getEntity().getPassword()) && StringUtils
				.isNotBlank(getEntity().getPasswordConfirm()))
				&& (!getEntity().getPassword().equals(getEntity().getPasswordConfirm()))) {
			return showError("vulpe.security.user.password.not.match");
		}
		setPassword(getEntity().getPassword());
		return super.createPost();
	}

	@Override
	protected void onUpdate() {
		super.onUpdate();
		setPassword(getEntity().getPassword());
	}

	@Override
	protected void onUpdatePost() {
		if (StringUtils.isBlank(getEntity().getPassword())
				&& StringUtils.isBlank(getEntity().getPasswordConfirm())) {
			getEntity().setPassword(getPassword());
		} else {
			setPassword(getEntity().getPassword());
		}
		super.onUpdatePost();
	}

	@Override
	public String updatePost() {
		if ((StringUtils.isNotBlank(getEntity().getPassword()) && StringUtils
				.isNotBlank(getEntity().getPasswordConfirm()))
				&& (!getEntity().getPassword().equals(getEntity().getPasswordConfirm()))) {
			return showError("vulpe.security.user.password.not.match");
		}
		return super.updatePost();
	}

	public String getPassword() {
		return (String) getRequest().getAttribute("password");
	}

	public void setPassword(final String password) {
		getRequest().setAttribute("password", password);
	}
}
