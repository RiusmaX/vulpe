package br.com.activethread.gmn.anuncios.controller;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import org.vulpe.commons.annotations.DetailConfig;

import org.vulpe.controller.annotations.Controller;

import br.com.activethread.gmn.anuncios.model.entity.EscolaMinisterio;
import br.com.activethread.gmn.anuncios.model.services.AnunciosServices;
import br.com.activethread.gmn.controller.ApplicationBaseController;


/**
 * Controller implementation of EscolaMinisterio
 */
@Component("anuncios.EscolaMinisterioController")
@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller(serviceClass = AnunciosServices.class, detailsConfig = { @DetailConfig(name = "discursos", propertyName = "entity.discursos", despiseFields = "tema", startNewDetails = 1, newDetails = 4) }, tabularStartNewDetails = 1, tabularNewDetails = 1)
public class EscolaMinisterioController extends ApplicationBaseController<EscolaMinisterio, java.lang.Long> {

}
