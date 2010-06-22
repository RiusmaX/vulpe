package br.com.activethread.gmn.core.controller;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.commons.annotations.DetailConfig;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Controller.ControllerType;

import br.com.activethread.gmn.core.model.entity.Grupo;
import br.com.activethread.gmn.core.model.services.CoreServices;
import br.com.activethread.gmn.controller.ApplicationBaseController;


/**
 * Controller implementation of Grupo
 */
@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component("core.GrupoCRUD")
@Controller(controllerType = ControllerType.CRUD, serviceClass = CoreServices.class, detailsConfig = { @DetailConfig(name = "publicadores", propertyName = "entity.publicadores", despiseFields = "nome", startNewDetails = 10, newDetails = 1) })
public class GrupoCRUDController extends ApplicationBaseController<Grupo, java.lang.Long> {

}
