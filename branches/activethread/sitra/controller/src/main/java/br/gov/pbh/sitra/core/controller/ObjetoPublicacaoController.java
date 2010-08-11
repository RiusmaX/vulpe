package br.gov.pbh.sitra.core.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.commons.annotations.DetailConfig;
import org.vulpe.commons.beans.Tab;
import org.vulpe.commons.beans.ValueBean;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Select;
import org.vulpe.controller.commons.VulpeControllerConfig.ControllerType;

import br.gov.pbh.sitra.core.model.entity.Ambiente;
import br.gov.pbh.sitra.core.model.services.CoreService;

/**
 * Controller implementation of Objeto
 */
@Component("core.ObjetoPublicacaoController")
@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller(serviceClass = CoreService.class, ownerController = "core/Objeto", viewBaseName = "Objeto", detailsConfig = { @DetailConfig(name = "objetoItens", propertyName = "entity.objetoItens", despiseFields = "nomeObjeto", startNewDetails = 5, newDetails = 1) }, select = @Select(pageSize = 5))
public class ObjetoPublicacaoController extends ObjetoBaseController {

	@Override
	public void loadNow() {
		super.loadNow();
		final String keyPrefix = Ambiente.class.getName();
		List<ValueBean> origem = new ArrayList<ValueBean>();
		origem.add(new ValueBean(Ambiente.D.name(), getText(keyPrefix.concat(".").concat(
				Ambiente.D.name()))));
		origem.add(new ValueBean(Ambiente.H.name(), getText(keyPrefix.concat(".").concat(
				Ambiente.H.name()))));
		now.put("origem", origem);
		List<ValueBean> destino = new ArrayList<ValueBean>();
		destino.add(new ValueBean(Ambiente.H.name(), getText(keyPrefix.concat(".").concat(
				Ambiente.H.name()))));
		destino.add(new ValueBean(Ambiente.P.name(), getText(keyPrefix.concat(".").concat(
				Ambiente.P.name()))));
		now.put("destino", destino);
		now.put("publicacao", true);
	}

	@Override
	public void showButtons(String method) {
		super.showButtons(method);
		if (getControllerType().equals(ControllerType.CRUD)) {
			getTabs().put("master", new Tab("Publicar em"));
			getTabs().put("objetoItens", new Tab("Objetos a serem Publicados"));
		}
	}

}
