package org.vulpe.portal.core.controller;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.portal.controller.PortalBaseController;
import org.vulpe.portal.core.model.entity.Link;
import org.vulpe.portal.core.model.services.CoreService;


/**
 * Controller implementation of Link
 */
@Component("core.LinkController")
@SuppressWarnings({ "serial", "unchecked" })
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller(serviceClass = CoreService.class)
public class LinkController extends PortalBaseController<Link, java.lang.Long> {

	@Override
	protected void createAfter() {
		super.createAfter();
		entity.setClicks(0L);
	}

}
