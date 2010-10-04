package br.gov.pbh.prodabel.transfere.core.controller;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Select;

import br.gov.pbh.prodabel.transfere.core.model.entity.Agenda;
import br.gov.pbh.prodabel.transfere.core.model.services.CoreService;
import br.gov.pbh.prodabel.transfere.controller.ApplicationBaseController;


/**
 * Controller implementation of Agenda
 */
@Component("core.AgendaController")
@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller(serviceClass = CoreService.class, select = @Select(pageSize = 5))
public class AgendaController extends ApplicationBaseController<Agenda, java.lang.Long> {

}
