package br.gov.pbh.prodabel.transfere.core.controller;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.commons.VulpeConstants.Controller.Button;
import org.vulpe.commons.VulpeConstants.Controller.Forward;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Select;
import org.vulpe.exception.VulpeApplicationException;

import br.gov.pbh.prodabel.transfere.controller.ApplicationBaseController;
import br.gov.pbh.prodabel.transfere.core.model.entity.Agenda;
import br.gov.pbh.prodabel.transfere.core.model.services.CoreService;

/**
 * Controller implementation of Agenda
 */
@Component("core.AgendaController")
@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller(serviceClass = CoreService.class, select = @Select(pageSize = 5, requireOneFilter = true))
public class AgendaController extends ApplicationBaseController<Agenda, java.lang.Long> {

	@Override
	public void showButtons(Operation operation) {
		super.showButtons(operation);
		hideButtons(Button.CREATE, Button.CREATE_POST, Button.DELETE);
	}

	public String cancelar() {
		try {
			final Integer retorno = getService(CoreService.class).cancelar(getEntity());
			if (retorno != null && retorno == 0) {
				addActionMessage("Cancelamento realizado com sucesso!");
			}
		} catch (VulpeApplicationException e) {
			LOG.error(e);
		}
		return Forward.SUCCESS;
	}

	public String autorizar() {
		try {
			final Integer retorno = getService(CoreService.class).autorizar(getEntity());
			if (retorno != null && retorno == 0) {
				addActionMessage("Autorização realizada com sucesso!");
			}
		} catch (VulpeApplicationException e) {
			LOG.error(e);
		}
		return Forward.SUCCESS;
	}

	public String reiniciar() {
		try {
			final Integer retorno = getService(CoreService.class).reiniciar(getEntity());
			if (retorno != null && retorno == 0) {
				addActionMessage("Reinicialização realizada com sucesso!");
			}
		} catch (VulpeApplicationException e) {
			LOG.error(e);
		}
		return Forward.SUCCESS;
	}

}
