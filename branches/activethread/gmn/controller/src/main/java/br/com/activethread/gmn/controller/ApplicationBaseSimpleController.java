package br.com.activethread.gmn.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.vulpe.commons.VulpeConstants.View;
import org.vulpe.controller.struts.VulpeStrutsSimpleController;

import br.com.activethread.gmn.commons.ApplicationConstants.Core;
import br.com.activethread.gmn.core.model.entity.Congregacao;
import br.com.activethread.gmn.core.model.entity.Index;

@SuppressWarnings( { "serial", "unchecked" })
public class ApplicationBaseSimpleController extends VulpeStrutsSimpleController {

	protected static final Logger LOG = Logger.getLogger(ApplicationBaseSimpleController.class);

	protected final List<Congregacao> congregacoes = (List<Congregacao>) getCachedClass().get(
			Congregacao.class.getSimpleName());

	private Index entity;

	public void setEntity(Index entity) {
		this.entity = entity;
	}

	public Index getEntity() {
		return entity;
	}

	public String selecionarValidate() {
		if (getEntity().getCongregacao() != null && getEntity().getCongregacao().getId() != null) {
			for (Congregacao congregacao : congregacoes) {
				if (congregacao.getId().equals(getEntity().getCongregacao().getId())) {
				setSessionAttribute(Core.CONGREGACAO_SELECIONADA, congregacao);
					return redirectTo("/core/Grupo/select", true);
				}
			}
		}
		final String currentLayout = getSessionAttribute(View.CURRENT_LAYOUT);
		String url = "FRONTEND".equals(currentLayout) ? "/frontend/Index" : "/backend/Index";
		return redirectTo(url, true);
	}
}
