#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.backend.controller.action;

import org.apache.log4j.Logger;

import org.vulpe.controller.struts.action.AbstractVulpeBaseSimpleAction;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Controller.ControllerType;

@SuppressWarnings("serial")
@Controller(controllerType = ControllerType.BACKEND)
public class IndexAction extends AbstractVulpeBaseSimpleAction {

	Logger log = Logger.getLogger(IndexAction.class);

}
