package org.vulpe.portal.backend.controller;

import org.apache.log4j.Logger;

import org.vulpe.portal.controller.ApplicationBaseSimpleController;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.commons.VulpeControllerConfig.ControllerType;

@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component("backend.IndexController")
@Controller(type = ControllerType.BACKEND)
public class IndexController extends ApplicationBaseSimpleController {

	protected static final Logger LOG = Logger.getLogger(IndexController.class);

}
