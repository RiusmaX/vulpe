package br.com.activethread.gmn.core.controller;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Controller.ControllerType;

import br.com.activethread.gmn.core.model.entity.Congregacao;
import br.com.activethread.gmn.core.model.services.CoreServices;
import br.com.activethread.gmn.controller.ApplicationBaseController;


/**
 * Controller implementation of Congregacao
 */
@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component("core.CongregacaoTabular")
@Controller(controllerType = ControllerType.TABULAR, serviceClass = CoreServices.class, tabularStartNewDetails = 5, tabularNewDetails = 1, tabularDespiseFields = { "nome" })
public class CongregacaoTabularController extends ApplicationBaseController<Congregacao, java.lang.Long> {

}
