package br.gov.pbh.sitra.core.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.commons.annotations.DetailConfig;
import org.vulpe.commons.annotations.Quantity;
import org.vulpe.commons.annotations.Quantity.QuantityType;
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
@Controller(serviceClass = CoreService.class, ownerController = "core/Objeto", viewBaseName = "Objeto", detailsConfig = { @DetailConfig(name = "objetoItens", propertyName = "entity.objetoItens", despiseFields = "nomeObjeto", startNewDetails = 5, newDetails = 1, quantity = @Quantity(type = QuantityType.ONE_OR_MORE)) }, select = @Select(pageSize = 5))
public class ObjetoPublicacaoController extends ObjetoBaseController {

	private static final List<ValueBean> origem = new ArrayList<ValueBean>();
	private static final List<ValueBean> destino = new ArrayList<ValueBean>();

	{
		now.put("publicacao", true);
	}

	@Override
	public void loadNow() {
		super.loadNow();
		final String keyPrefix = Ambiente.class.getName();
		if (origem.isEmpty()) {
			origem.add(new ValueBean(Ambiente.D.name(), getText(keyPrefix.concat(".").concat(
					Ambiente.D.name()))));
			origem.add(new ValueBean(Ambiente.H.name(), getText(keyPrefix.concat(".").concat(
					Ambiente.H.name()))));
		}
		now.put("ORIGEM", origem);
		if (destino.isEmpty()) {
			destino.add(new ValueBean(Ambiente.H.name(), getText(keyPrefix.concat(".").concat(
					Ambiente.H.name()))));
			destino.add(new ValueBean(Ambiente.P.name(), getText(keyPrefix.concat(".").concat(
					Ambiente.P.name()))));
		}
		now.put("destino", destino);
		if (getControllerType().equals(ControllerType.CRUD)) {
			getTabs().put(getControllerConfig().getMasterTitleKey(), new Tab("Publicar em"));
			getTabs().put(getControllerConfig().getDetail("objetoItens").getTitleKey(),
					new Tab("Objetos a serem Publicados"));
		}
	}

}
