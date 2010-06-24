package br.com.activethread.gmn.core.controller;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import org.vulpe.commons.annotations.DetailConfig;

import org.vulpe.controller.annotations.Controller;

import br.com.activethread.gmn.core.model.entity.Grupo;
import br.com.activethread.gmn.core.model.services.CoreServices;
import br.com.activethread.gmn.controller.ApplicationBaseController;


/**
 * Controller implementation of Grupo
 */
@Component("core.GrupoController")
@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller(serviceClass = CoreServices.class, detailsConfig = { @DetailConfig(name = "publicadores", propertyName = "entity.publicadores", despiseFields = "nome", startNewDetails = 10, newDetails = 1) }, pageSize = 5, tabularStartNewDetails = 5, tabularNewDetails = 1, tabularDespiseFields = { "nome" })
public class GrupoController extends ApplicationBaseController<Grupo, java.lang.Long> {

}
