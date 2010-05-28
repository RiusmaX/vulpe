#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.backend.controller.action;

import org.apache.log4j.Logger;

import ${package}.controller.action.ApplicationBaseSimpleAction;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Controller.ControllerType;

@SuppressWarnings("serial")
@Controller(controllerType = ControllerType.BACKEND)
public class IndexAction extends ApplicationBaseSimpleAction {

	protected static final Logger LOG = Logger.getLogger(IndexAction.class);

}
