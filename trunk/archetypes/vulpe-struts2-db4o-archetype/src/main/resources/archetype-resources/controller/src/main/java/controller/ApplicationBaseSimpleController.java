#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.controller;

import org.apache.log4j.Logger;
import org.vulpe.controller.struts.AbstractVulpeStrutsSimpleController;

@SuppressWarnings("serial")
public class ApplicationBaseSimpleController extends AbstractVulpeStrutsSimpleController {

	protected static final Logger LOG = Logger.getLogger(ApplicationBaseSimpleController.class);

}
