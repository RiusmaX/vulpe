package br.com.activethread.gmn.anuncios.controller;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.commons.annotations.DetailConfig;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Controller.ControllerType;

import br.com.activethread.gmn.anuncios.model.entity.ReuniaoServico;
import br.com.activethread.gmn.anuncios.model.services.AnunciosServices;
import br.com.activethread.gmn.controller.ApplicationBaseController;


/**
 * Controller implementation of ReuniaoServico
 */
@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component("anuncios.ReuniaoServicoCRUD")
@Controller(controllerType = ControllerType.CRUD, serviceClass = AnunciosServices.class, detailsConfig = { @DetailConfig(name = "discursos", propertyName = "entity.discursos", despiseFields = "tema", startNewDetails = 1, newDetails = 3) })
public class ReuniaoServicoCRUDController extends ApplicationBaseController<ReuniaoServico, java.lang.Long> {

}
