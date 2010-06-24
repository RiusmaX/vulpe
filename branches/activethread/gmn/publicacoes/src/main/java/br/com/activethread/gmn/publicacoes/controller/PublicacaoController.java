package br.com.activethread.gmn.publicacoes.controller;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.struts.VulpeStrutsController;

import br.com.activethread.gmn.publicacoes.model.entity.Publicacao;
import br.com.activethread.gmn.publicacoes.model.services.PublicacoesServices;

@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component("publicacoes.PublicacaoController")
@Controller(serviceClass = PublicacoesServices.class)
public class PublicacaoController extends VulpeStrutsController<Publicacao, Long> {

}
