package br.gov.caixa.sirci.backend;

import org.apache.log4j.Logger;

import br.gov.caixa.sirci.controller.ApplicationBaseSimpleController;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.commons.VulpeControllerConfig.ControllerType;

@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component("backend.IndexController")
@Controller(controllerType = ControllerType.BACKEND)
public class IndexController extends ApplicationBaseSimpleController {

	protected static final Logger LOG = Logger.getLogger(IndexController.class);

}
