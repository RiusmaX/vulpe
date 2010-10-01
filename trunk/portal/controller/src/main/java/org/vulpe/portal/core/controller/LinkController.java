package org.vulpe.portal.core.controller;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Select;

import org.vulpe.portal.core.model.entity.Link;
import org.vulpe.portal.core.model.services.CoreService;
import org.vulpe.portal.controller.ApplicationBaseController;


/**
 * Controller implementation of Link
 */
@Component("core.LinkController")
@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller(serviceClass = CoreService.class, select = @Select(pageSize = 5))
public class LinkController extends ApplicationBaseController<Link, java.lang.Long> {

}
