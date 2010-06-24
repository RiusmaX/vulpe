package br.com.activethread.gmn.ministerio.controller;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


import org.vulpe.controller.annotations.Controller;

import br.com.activethread.gmn.ministerio.model.entity.Relatorio;
import br.com.activethread.gmn.ministerio.model.services.MinisterioServices;
import br.com.activethread.gmn.controller.ApplicationBaseController;


/**
 * Controller implementation of Relatorio
 */
@Component("ministerio.RelatorioController")
@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller(serviceClass = MinisterioServices.class, tabularStartNewDetails = 1, tabularNewDetails = 1)
public class RelatorioController extends ApplicationBaseController<Relatorio, java.lang.Long> {

}
