package org.vulpe.security.controller.action;

import org.vulpe.common.annotations.DetailConfig;
import org.vulpe.common.annotations.DetailConfig.CardinalityType;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Controller.ControllerType;
import org.vulpe.controller.struts.action.VulpeBaseAction;
import org.vulpe.security.model.entity.SecureResource;
import org.vulpe.security.model.services.SecurityServices;

@Controller(controllerType = ControllerType.CRUD, serviceClass = SecurityServices.class, detailsConfig = { @DetailConfig(name = "secureResourceRoles", propertyName = "entity.secureResourceRoles", despiseFields = "role", detailNews = 1, cardinalityType = CardinalityType.ONE_OR_MORE) })
@SuppressWarnings("serial")
public class SecureResourceCRUDAction extends VulpeBaseAction<SecureResource, Long> {

}
