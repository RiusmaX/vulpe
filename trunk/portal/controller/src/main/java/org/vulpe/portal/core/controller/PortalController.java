package org.vulpe.portal.core.controller;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Select;
import org.vulpe.portal.commons.ApplicationConstants.Core;
import org.vulpe.portal.commons.model.entity.Status;
import org.vulpe.portal.core.model.entity.Portal;
import org.vulpe.portal.core.model.services.CoreService;

/**
 * Controller implementation of Portal
 */
@Component("core.PortalController")
@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller(serviceClass = CoreService.class, select = @Select(pageSize = 1))
public class PortalController extends BasePortalController<Portal> {

	@Override
	public String update() {
		final Portal portal = getSessionAttribute(Core.VULPE_PORTAL);
		if (portal != null) {
			setId(portal.getId());
			setEntity(portal);
		}
		return super.update();
	}

	@Override
	protected void updatePostAfter() {
		super.updatePostAfter();
		if (getEntity().getStatus().equals(Status.ACTIVE)) {
			setSessionAttribute(Core.VULPE_PORTAL, getEntity());
		}
	}

}
