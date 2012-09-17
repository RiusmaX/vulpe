package org.jw.mmn.backend.controller;

import org.jw.mmn.controller.IndexBaseController;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.commons.VulpeControllerConfig.ControllerType;

@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component("backend.IndexController")
@SuppressWarnings( { "serial" })
@Controller(type = ControllerType.BACKEND)
public class BackendController extends IndexBaseController {

}
