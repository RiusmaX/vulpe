package org.jw.mmn.core.controller;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.commons.annotations.DetailConfig;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Select;
import org.vulpe.controller.annotations.Tabular;

import org.jw.mmn.core.model.services.CoreService;
import org.jw.mmn.controller.ApplicationBaseController;
import org.jw.mmn.core.model.entity.Group;

/**
 * Controller implementation of Group
 */
@Component("core.GroupController")
@SuppressWarnings({ "serial", "unchecked" })
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller(serviceClass = CoreService.class, detailsConfig = { @DetailConfig(name = "members", propertyName = "entity.publicadores", despiseFields = "nome", startNewDetails = 10, newDetails = 1) }, select = @Select(pageSize = 5), tabular = @Tabular(startNewRecords = 5, newRecords = 1, despiseFields = { "nome" }))
public class GroupController extends ApplicationBaseController<Group, java.lang.Long> {

}
