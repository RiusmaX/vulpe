package org.vulpe.site.frontend.controller.action;

import org.apache.log4j.Logger;

import org.vulpe.controller.struts.action.AbstractVulpeBaseSimpleAction;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Controller.ControllerType;

@SuppressWarnings("serial")
@Controller(controllerType = ControllerType.FRONTEND)
public class LearnAction extends AbstractVulpeBaseSimpleAction {

	Logger log = Logger.getLogger(LearnAction.class);

	@Override
	public boolean isShowTitle() {
		return false;
	}
}
