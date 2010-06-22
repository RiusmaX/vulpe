package br.com.activethread.gmn.publicacoes.controller;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Controller.ControllerType;

import br.com.activethread.gmn.publicacoes.model.entity.TipoPublicacao;
import br.com.activethread.gmn.publicacoes.model.services.PublicacoesServices;
import br.com.activethread.gmn.controller.ApplicationBaseController;


/**
 * Controller implementation of TipoPublicacao
 */
@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component("publicacoes.TipoPublicacaoTabular")
@Controller(controllerType = ControllerType.TABULAR, serviceClass = PublicacoesServices.class, tabularStartNewDetails = 5, tabularNewDetails = 1, tabularDespiseFields = { "descricao" })
public class TipoPublicacaoTabularController extends ApplicationBaseController<TipoPublicacao, java.lang.Long> {

}
