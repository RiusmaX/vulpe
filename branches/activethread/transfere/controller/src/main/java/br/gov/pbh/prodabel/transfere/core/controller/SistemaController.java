package br.gov.pbh.prodabel.transfere.core.controller;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import org.vulpe.commons.annotations.DetailConfig;

import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Select;

import br.gov.pbh.prodabel.transfere.controller.ApplicationBaseController;
import br.gov.pbh.prodabel.transfere.core.model.entity.Sistema;
import br.gov.pbh.prodabel.transfere.core.model.services.CoreService;

/**
 * Controller implementation of Sistema
 */
@Component("core.SistemaController")
@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller(serviceClass = CoreService.class, detailsConfig = { @DetailConfig(name = "usuarios", propertyName = "entity.usuarios", despiseFields = "usuario", startNewDetails = 5, newDetails = 1) }, select = @Select(pageSize = 5))
public class SistemaController extends ApplicationBaseController<Sistema, java.lang.Long> {

}
