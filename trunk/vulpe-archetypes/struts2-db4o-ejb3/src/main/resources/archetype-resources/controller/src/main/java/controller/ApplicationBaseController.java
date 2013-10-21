#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.controller;

import java.io.Serializable;

import org.vulpe.controller.struts.VulpeStrutsController;
import org.vulpe.model.entity.VulpeEntity;

@SuppressWarnings( { "serial", "unchecked" })
public class ApplicationBaseController<ENTITY extends VulpeEntity<ID>, ID extends Serializable & Comparable<?>>
		extends VulpeStrutsController<ENTITY, ID> {

}
