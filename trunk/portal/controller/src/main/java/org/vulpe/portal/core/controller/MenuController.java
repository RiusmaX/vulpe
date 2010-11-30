package org.vulpe.portal.core.controller;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Select;
import org.vulpe.portal.core.model.entity.Menu;
import org.vulpe.portal.core.model.services.CoreService;


/**
 * Controller implementation of Menu
 */
@Component("core.MenuController")
@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller(serviceClass = CoreService.class, select = @Select(pageSize = 5, readOnShow = true))
public class MenuController extends BasePortalController<Menu> {

}
