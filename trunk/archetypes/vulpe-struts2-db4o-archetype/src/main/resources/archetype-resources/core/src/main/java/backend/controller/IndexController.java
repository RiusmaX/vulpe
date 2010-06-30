#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.backend.controller;

import org.apache.log4j.Logger;

import ${package}.controller.action.ApplicationBaseSimpleController;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.commons.VulpeControllerConfig.ControllerType;

@SuppressWarnings("serial")
@Controller(controllerType = ControllerType.BACKEND)
public class IndexController extends ApplicationBaseSimpleController {

	protected static final Logger LOG = Logger.getLogger(IndexController.class);

}
