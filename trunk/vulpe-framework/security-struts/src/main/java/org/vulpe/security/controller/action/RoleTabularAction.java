package org.vulpe.security.controller.action;

import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Controller.ControllerType;
import org.vulpe.controller.struts.action.VulpeBaseAction;
import org.vulpe.security.model.entity.Role;
import org.vulpe.security.model.services.SecurityServices;


@Controller(controllerType = ControllerType.TABULAR, serviceClass = SecurityServices.class, tabularDetailNews = 4, tabularDespiseFields = { "name" })
@SuppressWarnings("serial")
public class RoleTabularAction extends VulpeBaseAction<Role, Long> {


}
