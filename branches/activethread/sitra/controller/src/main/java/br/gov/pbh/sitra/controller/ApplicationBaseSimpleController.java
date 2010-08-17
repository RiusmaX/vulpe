package br.gov.pbh.sitra.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.vulpe.commons.VulpeConstants.View;
import org.vulpe.commons.beans.ValueBean;
import org.vulpe.controller.struts.VulpeStrutsSimpleController;
import org.vulpe.exception.VulpeApplicationException;

import br.gov.pbh.sitra.commons.ApplicationConstants;
import br.gov.pbh.sitra.core.model.entity.Index;
import br.gov.pbh.sitra.core.model.entity.Sistema;
import br.gov.pbh.sitra.core.model.entity.SistemaUsuario;
import br.gov.pbh.sitra.core.model.services.CoreService;

@SuppressWarnings( { "serial", "unchecked" })
public class ApplicationBaseSimpleController extends VulpeStrutsSimpleController {

	protected static final Logger LOG = Logger.getLogger(ApplicationBaseSimpleController.class);

	protected final List<Sistema> sistemas = (List<Sistema>) getCachedClass().get(
			Sistema.class.getSimpleName());
	protected final List<Sistema> sistemasUsuario = new ArrayList<Sistema>();
	private List<SistemaUsuario> usuariosSistema;

	private Index entity;

	public void carregarDados() {
		for (Sistema sistema : sistemas) {
			if (sistema.getUsuarios() != null && !sistema.getUsuarios().isEmpty()) {
				try {
					final List<SistemaUsuario> sistemaUsuarios = getService(CoreService.class)
							.readSistemaUsuario(new SistemaUsuario(sistema));
					for (SistemaUsuario usuario : sistemaUsuarios) {
						if (usuario.getUsuario().getUsername().equals(getUserAuthenticated())) {
							sistemasUsuario.add(sistema);
							break;
						}
					}
				} catch (VulpeApplicationException e) {
					e.printStackTrace();
				}
			}
		}
		now.put("sistemas", sistemasUsuario);
		if (sistemasUsuario.size() == 1) {
			setSessionAttribute(ApplicationConstants.SISTEMA_SELECIONADO, sistemasUsuario.get(0));
		}
		final Sistema sistema = getSessionAttribute(ApplicationConstants.SISTEMA_SELECIONADO);
		if (sistema != null) {
			entity = new Index();
			entity.setSistema(sistema);
		}

	}

	public void setEntity(Index entity) {
		this.entity = entity;
	}

	public Index getEntity() {
		return entity;
	}

	public String selecionarValidate() {
		if (getEntity().getSistema() != null && getEntity().getSistema().getId() != null) {
			setSessionAttribute(ApplicationConstants.SISTEMA_SELECIONADO, getEntity().getSistema());
			if (usuariosSistema == null) {
				try {
					final Sistema sistema = getSessionAttribute(ApplicationConstants.SISTEMA_SELECIONADO);
					usuariosSistema = getService(CoreService.class).readSistemaUsuario(
							new SistemaUsuario(sistema));
					final List<ValueBean> usuarios = new ArrayList<ValueBean>();
					for (SistemaUsuario sistemaUsuario : usuariosSistema) {
						final String username = sistemaUsuario.getUsuario().getUsername();
						usuarios.add(new ValueBean(username, username));
					}
					setSessionAttribute("usuarios", usuarios);
				} catch (VulpeApplicationException e) {
					LOG.error(e);
				}
			}
			return redirectTo("/core/Objeto/select", true);
		}
		final String currentLayout = getSessionAttribute(View.CURRENT_LAYOUT);
		String url = "FRONTEND".equals(currentLayout) ? "/frontend/Index" : "/backend/Index";
		return redirectTo(url, true);
	}

}
