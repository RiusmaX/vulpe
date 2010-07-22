package br.gov.caixa.sirci.controller;

import java.io.Serializable;
import org.apache.log4j.Logger;

import org.vulpe.controller.struts.VulpeStrutsController;
import org.vulpe.model.entity.VulpeEntity;

@SuppressWarnings( { "serial", "unchecked" })
public class ApplicationBaseController<ENTITY extends VulpeEntity<ID>, ID extends Serializable & Comparable>
		extends VulpeStrutsController<ENTITY, ID> {
	
	protected static final Logger LOG = Logger.getLogger(ApplicationBaseController.class);
	
}
