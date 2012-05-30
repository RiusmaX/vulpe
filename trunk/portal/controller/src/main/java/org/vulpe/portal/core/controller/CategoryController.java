package org.vulpe.portal.core.controller;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Tabular;
import org.vulpe.portal.controller.PortalBaseController;
import org.vulpe.portal.core.model.entity.Category;
import org.vulpe.portal.core.model.services.CoreService;


/**
 * Controller implementation of Category
 */
@Component("core.CategoryController")
@SuppressWarnings({ "serial", "unchecked" })
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller(serviceClass = CoreService.class, tabular = @Tabular(despiseFields = { "name.languages.text" }, startNewRecords = 5, newRecords = 1))
public class CategoryController extends PortalBaseController<Category, Long> {

}
