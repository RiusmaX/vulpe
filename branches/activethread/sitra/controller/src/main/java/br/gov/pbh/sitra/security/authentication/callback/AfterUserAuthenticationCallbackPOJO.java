package br.gov.pbh.sitra.security.authentication.callback;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.vulpe.commons.beans.ValueBean;
import org.vulpe.exception.VulpeApplicationException;
import org.vulpe.security.authentication.callback.AfterUserAuthenticationCallback;
import org.vulpe.security.commons.VulpeSecurityStrutsCallbackUtil;
import org.vulpe.security.context.VulpeSecurityContext;
import org.vulpe.security.model.entity.User;

import br.gov.pbh.sitra.commons.ApplicationConstants.Sessao;
import br.gov.pbh.sitra.core.model.entity.Sistema;
import br.gov.pbh.sitra.core.model.entity.SistemaUsuario;
import br.gov.pbh.sitra.core.model.services.CoreService;

@Component("AfterUserAuthenticationCallback")
public class AfterUserAuthenticationCallbackPOJO extends VulpeSecurityStrutsCallbackUtil implements
		AfterUserAuthenticationCallback {

	protected static final Logger LOG = Logger.getLogger(AfterUserAuthenticationCallbackPOJO.class);

	@Override
	public void execute() {
		final VulpeSecurityContext securityContext = getBean(VulpeSecurityContext.class);
		if (securityContext != null) {
			final Long userId = securityContext.getUser().getId();
			try {
				final List<Sistema> sistemasCached = getCachedClass().getSelf(
						Sistema.class.getSimpleName());
				SistemaUsuario sistemaUsuarioFind = new SistemaUsuario();
				final User usuario = new User();
				usuario.setId(userId);
				sistemaUsuarioFind.setUsuario(usuario);
				List<SistemaUsuario> sistemaUsuarios = getService(CoreService.class)
						.readSistemaUsuario(sistemaUsuarioFind);
				final List<Sistema> sistemas = new ArrayList<Sistema>();
				for (SistemaUsuario sistemaUsuario : sistemaUsuarios) {
					for (Sistema sistema : sistemasCached) {
						if (sistema.getId().equals(sistemaUsuario.getSistema().getId())) {
							sistemas.add(sistemaUsuario.getSistema());
						}
					}
				}
				getSession().setAttribute(Sessao.SISTEMAS_USUARIO, sistemas);
				if (sistemas.size() == 1) {
					final Sistema sistema = sistemas.get(0);
					getSession().setAttribute(Sessao.SISTEMA_SELECIONADO, sistema);
					sistemaUsuarioFind = new SistemaUsuario();
					sistemaUsuarioFind.setSistema(sistema);
					sistemaUsuarios = getService(CoreService.class).readSistemaUsuario(
							sistemaUsuarioFind);
					final List<ValueBean> usuarios = new ArrayList<ValueBean>();
					for (SistemaUsuario sistemaUsuario : sistemaUsuarios) {
						final String username = sistemaUsuario.getUsuario().getUsername();
						usuarios.add(new ValueBean(username, username));
					}
					getSession().setAttribute(Sessao.USUARIOS_SISTEMA_SELECIONADO, usuarios);
				}
			} catch (VulpeApplicationException e) {
				LOG.error(e);
			}
		}
	}

}
