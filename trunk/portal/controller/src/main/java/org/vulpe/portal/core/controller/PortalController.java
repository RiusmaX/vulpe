package org.vulpe.portal.core.controller;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.commons.VulpeConstants.Controller.Button;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Select;
import org.vulpe.portal.commons.ApplicationConstants.Core;
import org.vulpe.portal.commons.model.entity.Status;
import org.vulpe.portal.controller.PortalBaseController;
import org.vulpe.portal.core.model.entity.Portal;
import org.vulpe.portal.core.model.services.CoreService;

/**
 * Controller implementation of Portal
 */
@Component("core.PortalController")
@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller(serviceClass = CoreService.class, select = @Select(pageSize = 1, readOnShow = true))
public class PortalController extends PortalBaseController<Portal, Long> {

	@Override
	public void update() {
		final Portal portal = ever.getSelf(Core.VULPE_PORTAL);
		if (portal != null) {
			setId(portal.getId());
			setEntity(portal);
		}
		super.update();
	}

	@Override
	protected void updatePostAfter() {
		super.updatePostAfter();
		if (getEntity().getStatus().equals(Status.ACTIVE)) {
			ever.put(Core.VULPE_PORTAL, getEntity());
		}
	}

	@Override
	public void manageButtons(Operation operation) {
		super.manageButtons(operation);
		vulpe.view().hideButtons(Button.CREATE, Button.DELETE, Button.BACK, Button.CLONE);
	}
}
