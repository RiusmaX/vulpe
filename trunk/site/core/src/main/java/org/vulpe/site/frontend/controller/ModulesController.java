package org.vulpe.site.frontend.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.commons.VulpeControllerConfig.ControllerType;
import org.vulpe.controller.struts.AbstractVulpeStrutsSimpleController;

@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component("frontend.ModulesController")
@Controller(controllerType = ControllerType.FRONTEND)
public class ModulesController extends AbstractVulpeStrutsSimpleController {

	Logger log = Logger.getLogger(ModulesController.class);

}
