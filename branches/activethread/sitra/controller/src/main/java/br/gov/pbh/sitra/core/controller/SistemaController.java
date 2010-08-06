package br.gov.pbh.sitra.core.controller;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import org.vulpe.commons.annotations.DetailConfig;

import org.vulpe.controller.annotations.Controller;

import br.gov.pbh.sitra.core.model.entity.Sistema;
import br.gov.pbh.sitra.core.model.services.CoreService;
import br.gov.pbh.sitra.controller.ApplicationBaseController;


/**
 * Controller implementation of Sistema
 */
@Component("core.SistemaController")
@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller(serviceClass = CoreService.class, detailsConfig = { @DetailConfig(name = "usuarios", propertyName = "entity.usuarios", despiseFields = "usuario", startNewDetails = 5, newDetails = 1) }, pageSize = 5)
public class SistemaController extends ApplicationBaseController<Sistema, java.lang.Long> {

}
