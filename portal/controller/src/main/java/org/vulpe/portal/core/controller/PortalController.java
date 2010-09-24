package org.vulpe.portal.core.controller;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Select;
import org.vulpe.portal.controller.ApplicationBaseController;
import org.vulpe.portal.core.model.entity.Portal;
import org.vulpe.portal.core.model.services.CoreService;

/**
 * Controller implementation of Portal
 */
@Component("core.PortalController")
@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller(serviceClass = CoreService.class, select = @Select(pageSize = 1))
public class PortalController extends ApplicationBaseController<Portal, java.lang.Long> {

	@Override
	protected Portal prepareEntity(Operation operation) {
		final Portal portal = super.prepareEntity(operation);
		//portal.setStatus(Status.ACTIVE);
		return portal;
	}

}
