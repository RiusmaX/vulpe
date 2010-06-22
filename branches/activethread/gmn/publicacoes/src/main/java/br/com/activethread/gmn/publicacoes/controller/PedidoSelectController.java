package br.com.activethread.gmn.publicacoes.controller;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.controller.struts.VulpeStrutsController;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Controller.ControllerType;

import br.com.activethread.gmn.publicacoes.model.services.PublicacoesServices;
import br.com.activethread.gmn.publicacoes.model.entity.Pedido;

@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component("publicacoes.PedidoSelect")
@Controller(controllerType = ControllerType.SELECT, serviceClass = PublicacoesServices.class, pageSize = 5, showReport = true)
public class PedidoSelectController extends VulpeStrutsController<Pedido, Long> {

}
