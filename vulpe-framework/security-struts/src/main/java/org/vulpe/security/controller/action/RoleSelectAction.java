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