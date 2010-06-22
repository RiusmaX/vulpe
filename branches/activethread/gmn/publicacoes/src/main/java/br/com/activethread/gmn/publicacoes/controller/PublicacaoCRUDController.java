package br.com.activethread.gmn.publicacoes.controller;

import br.com.activethread.gmn.publicacoes.model.services.PublicacoesServices;
import br.com.activethread.gmn.publicacoes.model.entity.Publicacao;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.controller.struts.VulpeStrutsController;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Controller.ControllerType;

@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component("publicacoes.PublicacaoCRUD")
@Controller(controllerType = ControllerType.CRUD, serviceClass = PublicacoesServices.class)
public class PublicacaoCRUDController extends VulpeStrutsController<Publicacao, Long> {

}
