package br.com.activethread.gmn.ministerio.controller;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Controller.ControllerType;

import br.com.activethread.gmn.ministerio.model.entity.Relatorio;
import br.com.activethread.gmn.ministerio.model.services.MinisterioServices;
import br.com.activethread.gmn.controller.ApplicationBaseController;


/**
 * Controller implementation of Relatorio
 */
@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component("ministerio.RelatorioCRUD")
@Controller(controllerType = ControllerType.CRUD, serviceClass = MinisterioServices.class)
public class RelatorioCRUDController extends ApplicationBaseController<Relatorio, java.lang.Long> {

}
