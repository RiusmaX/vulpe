#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.controller.action;

import org.apache.log4j.Logger;
import org.vulpe.controller.struts.AbstractVulpeBaseSimpleAction;

@SuppressWarnings("serial")
public class ApplicationBaseSimpleAction extends AbstractVulpeBaseSimpleAction {

	protected static final Logger LOG = Logger.getLogger(ApplicationBaseSimpleAction.class);
	
}
