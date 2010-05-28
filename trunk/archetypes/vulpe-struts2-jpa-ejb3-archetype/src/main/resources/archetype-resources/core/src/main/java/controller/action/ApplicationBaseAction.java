#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.controller.action;

import java.io.Serializable;

import org.vulpe.controller.struts.action.VulpeBaseAction;
import org.vulpe.model.entity.VulpeBaseEntity;

import ${package}.commons.ApplicationConstants.Core;

@SuppressWarnings( { "serial", "unchecked" })
public class ApplicationBaseAction<ENTITY extends VulpeBaseEntity<ID>, ID extends Serializable & Comparable>
		extends VulpeBaseAction<ENTITY, ID> {
	
}
