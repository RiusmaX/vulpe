package br.com.activethread.gmn.frontend.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.commons.VulpeConstants.Action.Forward;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Controller.ControllerType;

import br.com.activethread.gmn.controller.ApplicationBaseSimpleController;

@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component("frontend.Index")
@SuppressWarnings("serial")
@Controller(controllerType = ControllerType.FRONTEND)
public class IndexController extends ApplicationBaseSimpleController {

	protected static final Logger LOG = Logger.getLogger(IndexController.class);

	public String teste() {
		controlResultForward();
		return Forward.SUCCESS;
	}
}
