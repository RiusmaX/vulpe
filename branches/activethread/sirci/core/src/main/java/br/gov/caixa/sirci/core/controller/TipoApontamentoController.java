package br.gov.caixa.sirci.core.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.commons.VulpeConstants;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.commons.VulpeControllerConfig.ControllerType;
import org.vulpe.exception.VulpeApplicationException;

import br.gov.caixa.sirci.controller.ApplicationBaseController;
import br.gov.caixa.sirci.core.model.entity.Apontamento;
import br.gov.caixa.sirci.core.model.entity.TipoApontamento;
import br.gov.caixa.sirci.core.model.services.CoreServices;

/**
 * Controller implementation of TipoApontamento
 */
@Component("core.TipoApontamentoController")
@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller(controllerType = ControllerType.TWICE, serviceClass = CoreServices.class, pageSize = 5, tabularStartNewDetails = 1, tabularNewDetails = 1)
public class TipoApontamentoController extends
		ApplicationBaseController<TipoApontamento, java.lang.Long> {

	private static final Logger LOG = Logger.getLogger(TipoApontamentoController.class);

	private boolean verificarApontamentos(final String method) {
		List<Apontamento> apontamentos = null;
		try {
			apontamentos = getService(CoreServices.class).readApontamento(
					new Apontamento(prepareEntity(method)));
		} catch (VulpeApplicationException e) {
			LOG.error(e);
		}
		return apontamentos != null && !apontamentos.isEmpty();
	}

	@Override
	protected boolean onUpdatePost() {
		if (verificarApontamentos(VulpeConstants.Action.UPDATE_POST)) {
			addActionError(getText("sirci.mn.002"));
			return false;
		}
		return super.onUpdatePost();
	}

	@Override
	protected boolean onDelete() {
		if (verificarApontamentos(VulpeConstants.Action.DELETE)) {
			addActionError(getText("sirci.mn.002"));
			return false;
		}
		return super.onDelete();
	}
}
