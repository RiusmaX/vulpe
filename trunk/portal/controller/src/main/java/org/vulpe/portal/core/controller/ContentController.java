package org.vulpe.portal.core.controller;

import java.util.Date;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Select;
import org.vulpe.portal.controller.ApplicationBaseController;
import org.vulpe.portal.core.model.entity.Content;
import org.vulpe.portal.core.model.services.CoreService;

/**
 * Controller implementation of Content
 */
@Component("core.ContentController")
@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller(serviceClass = CoreService.class, select = @Select(pageSize = 5))
public class ContentController extends ApplicationBaseController<Content, java.lang.Long> {

	@Override
	protected Content onCreatePost() {
		getEntity().setDate(new Date());
		return super.onCreatePost();
	}

	@Override
	protected boolean onUpdatePost() {
		if (getEntity().getDate() == null) {
			getEntity().setDate(new Date());
		}
		return super.onUpdatePost();
	}
}
