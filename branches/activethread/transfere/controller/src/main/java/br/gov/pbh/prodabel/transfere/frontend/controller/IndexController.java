package br.gov.pbh.prodabel.transfere.frontend.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.commons.VulpeControllerConfig.ControllerType;

import br.gov.pbh.prodabel.transfere.controller.ApplicationBaseSimpleController;

@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component("frontend.IndexController")
@Controller(controllerType = ControllerType.FRONTEND)
public class IndexController extends ApplicationBaseSimpleController {

	protected final Logger LOG = Logger.getLogger(IndexController.class);

}
