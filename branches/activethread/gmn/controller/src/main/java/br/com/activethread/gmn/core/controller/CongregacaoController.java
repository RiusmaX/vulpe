package br.com.activethread.gmn.core.controller;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import org.vulpe.commons.annotations.DetailConfig;

import org.vulpe.controller.annotations.Controller;

import br.com.activethread.gmn.core.model.entity.Congregacao;
import br.com.activethread.gmn.core.model.services.CoreService;
import br.com.activethread.gmn.controller.ApplicationBaseController;


/**
 * Controller implementation of Congregacao
 */
@Component("core.CongregacaoController")
@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller(serviceClass = CoreService.class, detailsConfig = { @DetailConfig(name = "grupos", propertyName = "entity.grupos", despiseFields = "nome", startNewDetails = 3, newDetails = 1) }, pageSize = 5, tabularStartNewDetails = 1, tabularNewDetails = 1)
public class CongregacaoController extends ApplicationBaseController<Congregacao, java.lang.Long> {

}
