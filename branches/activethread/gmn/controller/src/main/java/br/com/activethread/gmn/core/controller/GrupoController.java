package br.com.activethread.gmn.core.controller;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.commons.annotations.DetailConfig;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Select;
import org.vulpe.controller.annotations.Tabular;

import br.com.activethread.gmn.controller.ApplicationBaseController;
import br.com.activethread.gmn.core.model.entity.Grupo;
import br.com.activethread.gmn.core.model.services.CoreService;

/**
 * Controller implementation of Grupo
 */
@Component("core.GrupoController")
@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller(serviceClass = CoreService.class, detailsConfig = { @DetailConfig(name = "publicadores", propertyName = "entity.publicadores", despiseFields = "nome", startNewDetails = 10, newDetails = 1) }, select = @Select(pageSize = 5), tabular = @Tabular(startNewRecords = 5, newRecords = 1, despiseFields = { "nome" }))
public class GrupoController extends ApplicationBaseController<Grupo, java.lang.Long> {

}
