package template.vulpe_struts2_db4o.frontend.controller.action;

import org.apache.log4j.Logger;

import org.vulpe.controller.struts.action.AbstractVulpeBaseSimpleAction;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Controller.ControllerType;

@SuppressWarnings("serial")
@Controller(controllerType = ControllerType.FRONTEND)
public class IndexAction extends AbstractVulpeBaseSimpleAction {

	Logger log = Logger.getLogger(IndexAction.class);

}
