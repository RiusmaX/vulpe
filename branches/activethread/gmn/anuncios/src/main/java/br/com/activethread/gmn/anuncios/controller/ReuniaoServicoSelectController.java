package br.com.activethread.gmn.anuncios.controller;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Controller.ControllerType;

import br.com.activethread.gmn.anuncios.model.entity.ReuniaoServico;
import br.com.activethread.gmn.anuncios.model.services.AnunciosServices;
import br.com.activethread.gmn.controller.ApplicationBaseController;


/**
 * Controller implementation of ReuniaoServico
 */
@Component("anuncios.ReuniaoServicoSelect")
@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller(controllerType = ControllerType.SELECT, serviceClass = AnunciosServices.class)
public class ReuniaoServicoSelectController extends ApplicationBaseController<ReuniaoServico, java.lang.Long> {

}
