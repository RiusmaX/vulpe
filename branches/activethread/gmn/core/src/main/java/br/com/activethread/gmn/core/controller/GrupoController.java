package br.com.activethread.gmn.core.controller;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.commons.annotations.DetailConfig;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Controller.ControllerType;

import br.com.activethread.gmn.controller.ApplicationBaseController;
import br.com.activethread.gmn.core.model.entity.Grupo;
import br.com.activethread.gmn.core.model.services.CoreServices;

@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component("core.GrupoController")
@Controller(controllerType = ControllerType.CRUD, serviceClass = CoreServices.class, detailsConfig = { @DetailConfig(name = "publicadores", propertyName = "entity.publicadores", despiseFields = "nome", startNewDetails = 10, newDetails = 1) }, pageSize = 5)
@SuppressWarnings("serial")
public class GrupoController extends ApplicationBaseController<Grupo, Long> {

	
}
