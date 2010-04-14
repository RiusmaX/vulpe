package org.vulpe.security.controller.action;

import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Controller.ControllerType;
import org.vulpe.controller.struts.action.VulpeBaseAction;
import org.vulpe.security.model.entity.SecureResource;
import org.vulpe.security.model.services.SecurityServices;


@Controller(controllerType = ControllerType.SELECT, serviceClass = SecurityServices.class, pageSize = 5)
@SuppressWarnings("serial")
public class SecureResourceSelectAction extends VulpeBaseAction<SecureResource, Long> {

}
