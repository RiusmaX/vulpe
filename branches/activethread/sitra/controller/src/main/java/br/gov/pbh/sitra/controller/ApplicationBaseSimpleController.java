package br.gov.pbh.sitra.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.vulpe.commons.VulpeConstants.View;
import org.vulpe.commons.beans.ValueBean;
import org.vulpe.controller.struts.VulpeStrutsSimpleController;
import org.vulpe.exception.VulpeApplicationException;

import br.gov.pbh.sitra.commons.ApplicationConstants.Sessao;
import br.gov.pbh.sitra.core.model.entity.Index;
import br.gov.pbh.sitra.core.model.entity.Sistema;
import br.gov.pbh.sitra.core.model.entity.SistemaUsuario;
import br.gov.pbh.sitra.core.model.services.CoreService;

@SuppressWarnings("serial")
public class ApplicationBaseSimpleController extends VulpeStrutsSimpleController {

	protected static final Logger LOG = Logger.getLogger(ApplicationBaseSimpleController.class);

	protected final List<Sistema> sistemasCached = getCachedClass().getSelf(
			Sistema.class.getSimpleName());

	private Index entity;

	public void setEntity(Index entity) {
		this.entity = entity;
	}

	public Index getEntity() {
		return entity;
	}

	public String selecionarValidate() {
		if (getEntity().getSistema() != null && getEntity().getSistema().getId() != null) {
			for (Sistema sistema : sistemasCached) {
				if (sistema.getId().equals(getEntity().getSistema().getId())) {
					setSessionAttribute(Sessao.SISTEMA_SELECIONADO, sistema);
					carregarUsuariosSistema(sistema);
					return redirectTo("/core/Objeto/select", true);
				}
			}
		}
		final String currentLayout = getSessionAttribute(View.CURRENT_LAYOUT);
		String url = "FRONTEND".equals(currentLayout) ? "/frontend/Index" : "/backend/Index";
		return redirectTo(url, true);
	}

	private void carregarUsuariosSistema(final Sistema sistema) {
		try {
			final SistemaUsuario sistemaUsuarioFind = new SistemaUsuario();
			sistemaUsuarioFind.setSistema(sistema);
			final List<SistemaUsuario> sistemaUsuarios = getService(CoreService.class)
					.readSistemaUsuario(sistemaUsuarioFind);
			final List<ValueBean> usuarios = new ArrayList<ValueBean>();
			for (SistemaUsuario sistemaUsuario : sistemaUsuarios) {
				final String username = sistemaUsuario.getUsuario().getUsername();
				usuarios.add(new ValueBean(username, username));
			}
			getSession().setAttribute(Sessao.USUARIOS_SISTEMA_SELECIONADO, usuarios);
		} catch (VulpeApplicationException e) {
			LOG.error(e);
		}
	}

	@Override
	public String frontend() {
		inicializar();
		return super.frontend();
	}

	@Override
	public String backend() {
		inicializar();
		return super.backend();
	}

	private void inicializar() {
		final Sistema sistema = getSessionAttribute(Sessao.SISTEMA_SELECIONADO);
		if (sistema != null) {
			entity = new Index();
			entity.setSistema(sistema);
		}
	}
}
