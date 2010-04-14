package org.vulpe.security.controller.action;

import org.vulpe.common.Constants;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Controller.ControllerType;
import org.vulpe.controller.struts.action.AbstractVulpeBaseSimpleAction;

@SuppressWarnings("serial")
@Controller(controllerType = ControllerType.OTHER)
public class VulpeLoginAction extends AbstractVulpeBaseSimpleAction {

	private Integer loginError;

	private boolean accessDenied;

	/**
	 *
	 * @return
	 */
	public String define() {
		if (accessDenied) {
			return Constants.Action.Forward.ACCESS_DENIED;
		}
		if (loginError != null && loginError == 1) {
			return Constants.Action.Forward.ERRORS;
		}
		return Constants.Action.Forward.SUCCESS;
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
