package br.gov.caixa.sirci.core.controller;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


import org.vulpe.controller.annotations.Controller;

import br.gov.caixa.sirci.core.model.entity.Apontamento;
import br.gov.caixa.sirci.core.model.services.CoreServices;
import br.gov.caixa.sirci.controller.ApplicationBaseController;


/**
 * Controller implementation of Apontamento
 */
@Component("core.ApontamentoController")
@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller(serviceClass = CoreServices.class, pageSize = 5, tabularStartNewDetails = 1, tabularNewDetails = 1)
public class ApontamentoController extends ApplicationBaseController<Apontamento, java.lang.Long> {

}
