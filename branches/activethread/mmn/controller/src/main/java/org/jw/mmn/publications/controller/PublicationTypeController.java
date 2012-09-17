package org.jw.mmn.publications.controller;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Select;
import org.vulpe.controller.annotations.Tabular;

import org.jw.mmn.publications.model.services.PublicationsService;
import org.jw.mmn.publications.model.entity.PublicationType;
import org.jw.mmn.controller.ApplicationBaseController;

/**
 * Controller implementation of PublicationType
 */
@Component("publications.PublicationTypeController")
@SuppressWarnings({ "serial", "unchecked" })
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller(serviceClass = PublicationsService.class, select = @Select(pageSize = 5), tabular = @Tabular(startNewRecords = 5, newRecords = 1, despiseFields = { "description" }))
public class PublicationTypeController extends
		ApplicationBaseController<PublicationType, java.lang.Long> {

}
