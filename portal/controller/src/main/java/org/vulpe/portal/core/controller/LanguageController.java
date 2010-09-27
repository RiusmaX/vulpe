package org.vulpe.portal.core.controller;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Tabular;

import org.vulpe.portal.core.model.entity.Language;
import org.vulpe.portal.core.model.services.CoreService;
import org.vulpe.portal.controller.ApplicationBaseController;


/**
 * Controller implementation of Language
 */
@Component("core.LanguageController")
@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller(serviceClass = CoreService.class, tabular = @Tabular(despiseFields = { "localeCode" }, startNewRecords = 3, newRecords = 1))
public class LanguageController extends ApplicationBaseController<Language, java.lang.Long> {

}