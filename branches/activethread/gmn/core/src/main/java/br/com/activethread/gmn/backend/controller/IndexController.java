package br.com.activethread.gmn.backend.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.commons.VulpeControllerConfig.ControllerType;

import br.com.activethread.gmn.controller.ApplicationBaseSimpleController;

@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component("backend.Index")
@SuppressWarnings("serial")
@Controller(controllerType = ControllerType.BACKEND)
public class IndexController extends ApplicationBaseSimpleController {

	protected static final Logger LOG = Logger.getLogger(IndexController.class);

}
