package br.gov.caixa.sirci.core.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Select;
import org.vulpe.controller.annotations.Tabular;
import org.vulpe.controller.commons.VulpeControllerConfig.ControllerType;
import org.vulpe.exception.VulpeApplicationException;

import br.gov.caixa.sirci.controller.ApplicationBaseController;
import br.gov.caixa.sirci.core.model.entity.Apontamento;
import br.gov.caixa.sirci.core.model.entity.TipoApontamento;
import br.gov.caixa.sirci.core.model.services.CoreService;

/**
 * Controller implementation of TipoApontamento
 */
@Component("core.TipoApontamentoController")
@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller(controllerType = ControllerType.TWICE, serviceClass = CoreService.class, select = @Select(pageSize = 5), tabular = @Tabular(startNewRecords = 5, newRecords = 1, despiseFields = "descricao", pageSize = 10, showFilter = true))
public class TipoApontamentoController extends
		ApplicationBaseController<TipoApontamento, java.lang.Long> {

	private static final Logger LOG = Logger.getLogger(TipoApontamentoController.class);

	private boolean verificarApontamentos(final Operation operation) {
		List<Apontamento> apontamentos = null;
		try {
			apontamentos = getService(CoreService.class).readApontamento(
					new Apontamento(prepareEntity(operation)));
		} catch (VulpeApplicationException e) {
			LOG.error(e);
		}
		return apontamentos != null && !apontamentos.isEmpty();
	}

	@Override
	protected boolean onUpdatePost() {
		if (verificarApontamentos(Operation.UPDATE_POST)) {
			addActionError(getText("sirci.mn.002"));
			return false;
		}
		return super.onUpdatePost();
	}

	@Override
	protected boolean onDelete() {
		if (verificarApontamentos(Operation.DELETE)) {
			addActionError(getText("sirci.mn.002"));
			return false;
		}
		return super.onDelete();
	}

	@Override
	protected int onDeleteDetail() {
		if (verificarApontamentos(Operation.DELETE)) {
			addActionError(getText("sirci.mn.002"));
			return 0;
		}
		return super.onDeleteDetail();
	}
}
