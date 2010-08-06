package br.gov.pbh.sitra.core.controller;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.commons.annotations.DetailConfig;
import org.vulpe.controller.annotations.Controller;

import br.gov.pbh.sitra.controller.ApplicationBaseController;
import br.gov.pbh.sitra.core.model.entity.Objeto;
import br.gov.pbh.sitra.core.model.entity.ObjetoItem;
import br.gov.pbh.sitra.core.model.entity.Status;
import br.gov.pbh.sitra.core.model.services.CoreService;

/**
 * Controller implementation of Objeto
 */
@Component("core.ObjetoController")
@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller(serviceClass = CoreService.class, detailsConfig = { @DetailConfig(name = "objetoItens", propertyName = "entity.objetoItens", despiseFields = "nomeObjeto", startNewDetails = 5, newDetails = 1) }, pageSize = 5)
public class ObjetoController extends ApplicationBaseController<Objeto, java.lang.Long> {

	@Override
	protected void createAfter() {
		super.createAfter();
		for (ObjetoItem item : getEntity().getObjetoItens()) {
			item.setStatus(Status.N);
		}
	}

}
