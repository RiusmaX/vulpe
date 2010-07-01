package br.gov.caixa.sirci.core.controller;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.commons.VulpeControllerConfig.ControllerType;

import br.gov.caixa.sirci.core.model.entity.TipoApontamento;
import br.gov.caixa.sirci.core.model.services.CoreServices;
import br.gov.caixa.sirci.controller.ApplicationBaseController;


/**
 * Controller implementation of TipoApontamento
 */
@Component("core.TipoApontamentoController")
@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller(controllerType = ControllerType.CRUD_SELECT, serviceClass = CoreServices.class, pageSize = 5, tabularStartNewDetails = 1, tabularNewDetails = 1)
public class TipoApontamentoController extends ApplicationBaseController<TipoApontamento, java.lang.Long> {

}
