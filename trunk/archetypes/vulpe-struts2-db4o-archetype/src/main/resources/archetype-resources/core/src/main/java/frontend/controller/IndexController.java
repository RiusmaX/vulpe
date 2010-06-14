#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.frontend.controller;

import org.apache.log4j.Logger;

import ${package}.controller.action.ApplicationBaseSimpleController;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Controller.ControllerType;

@SuppressWarnings("serial")
@Controller(controllerType = ControllerType.FRONTEND)
public class IndexController extends ApplicationBaseSimpleController {

	protected final Logger LOG = Logger.getLogger(IndexController.class);

}
