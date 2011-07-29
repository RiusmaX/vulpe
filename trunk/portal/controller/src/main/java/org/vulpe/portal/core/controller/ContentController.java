package org.vulpe.portal.core.controller;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Select;
import org.vulpe.portal.controller.PortalBaseController;
import org.vulpe.portal.core.model.entity.Content;
import org.vulpe.portal.core.model.services.CoreService;

/**
 * Controller implementation of Content
 */
@Component("core.ContentController")
@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller(serviceClass = CoreService.class, select = @Select(pageSize = 5))
public class ContentController extends PortalBaseController<Content, Long> {

	@Override
	protected void createAfter() {
		super.createAfter();
		getEntity().setViews(0L);
	}
}
