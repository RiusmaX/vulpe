package br.com.activethread.gmn.core.controller;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.commons.VulpeControllerConfig.ControllerType;

import br.com.activethread.gmn.controller.ApplicationBaseController;
import br.com.activethread.gmn.core.model.entity.Publicador;
import br.com.activethread.gmn.core.model.services.CoreService;

@Component("core.PublicadorController")
@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller(controllerType = ControllerType.CRUD, serviceClass = CoreService.class)
public class PublicadorController extends ApplicationBaseController<Publicador, Long> {

}
