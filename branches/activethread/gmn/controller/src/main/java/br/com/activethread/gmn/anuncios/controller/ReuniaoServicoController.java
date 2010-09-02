package br.com.activethread.gmn.anuncios.controller;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import org.vulpe.commons.annotations.DetailConfig;

import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Select;

import br.com.activethread.gmn.anuncios.model.entity.Reuniao;
import br.com.activethread.gmn.anuncios.model.services.AnunciosService;
import br.com.activethread.gmn.controller.ApplicationBaseController;


/**
 * Controller implementation of Reuniao
 */
@Component("anuncios.ReuniaoServicoController")
@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller(serviceClass = AnunciosService.class, detailsConfig = { @DetailConfig(name = "discursos", propertyName = "entity.discursos", despiseFields = "tema", newDetails = 3) }, select = @Select(pageSize = 5))
public class ReuniaoServicoController extends ApplicationBaseController<Reuniao, java.lang.Long> {

}
