package br.gov.caixa.sirci.core.controller;

import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.commons.VulpeConstants.Action.Forward;
import org.vulpe.commons.VulpeConstants.View.Layout;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.exception.VulpeApplicationException;

import br.gov.caixa.sirci.controller.ApplicationBaseController;
import br.gov.caixa.sirci.core.model.entity.Apontamento;
import br.gov.caixa.sirci.core.model.entity.DocumentoOrigem;
import br.gov.caixa.sirci.core.model.services.CoreServices;

/**
 * Controller implementation of Apontamento
 */
@Component("core.ApontamentoController")
@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller(serviceClass = CoreServices.class, pageSize = 5, tabularStartNewDetails = 1, tabularNewDetails = 1)
public class ApontamentoController extends ApplicationBaseController<Apontamento, java.lang.Long> {

	private static final String LISTA_DOCUMENTO_ORIGEM = "listaDocumentoOrigem";

	public String listarDocumentoOrigem() {
		try {
			if (getEntity().getOrgaoOrigem() != null
					&& getEntity().getOrgaoOrigem().getId() != null) {
				final List<DocumentoOrigem> listaDocumentoOrigem = getService(CoreServices.class)
						.readDocumentoOrigem(new DocumentoOrigem(getEntity().getOrgaoOrigem()));
				setSessionAttribute(LISTA_DOCUMENTO_ORIGEM, listaDocumentoOrigem);
			} else {
				getSession().removeAttribute(LISTA_DOCUMENTO_ORIGEM);
			}
		} catch (VulpeApplicationException e) {
			e.printStackTrace();
		}
		setResultForward(Layout.PROTECTED_JSP + "core/Apontamento/documentos.jsp");
		return Forward.SUCCESS;
	}
}