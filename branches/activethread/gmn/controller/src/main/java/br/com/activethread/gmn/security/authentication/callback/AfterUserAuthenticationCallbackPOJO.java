package br.com.activethread.gmn.security.authentication.callback;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.vulpe.commons.util.VulpeValidationUtil;
import org.vulpe.exception.VulpeApplicationException;
import org.vulpe.security.authentication.callback.AfterUserAuthenticationCallback;
import org.vulpe.security.commons.VulpeSecurityStrutsCallbackUtil;
import org.vulpe.security.context.VulpeSecurityContext;
import org.vulpe.security.model.entity.User;

import br.com.activethread.gmn.commons.ApplicationConstants.Core;
import br.com.activethread.gmn.core.model.entity.Congregacao;
import br.com.activethread.gmn.core.model.entity.CongregacaoUsuario;
import br.com.activethread.gmn.core.model.entity.Grupo;
import br.com.activethread.gmn.core.model.entity.Publicador;
import br.com.activethread.gmn.core.model.services.CoreService;

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
				final CongregacaoUsuario congregacaoUsuario = new CongregacaoUsuario();
				final User usuario = new User();
				usuario.setId(userId);
				congregacaoUsuario.setUsuario(usuario);
				final List<CongregacaoUsuario> congregacaoUsuarios = getService(CoreService.class)
						.readCongregacaoUsuario(congregacaoUsuario);
				final List<Congregacao> congregacoes = new ArrayList<Congregacao>();
				for (CongregacaoUsuario congregacaoUsuario2 : congregacaoUsuarios) {
					congregacoes.add(congregacaoUsuario2.getCongregacao());
				}
				if (congregacoes.size() == 1) {
					final Congregacao congregacao = congregacoes.get(0);
					getSession().setAttribute(Core.CONGREGACAO_SELECIONADA, congregacao);
					final Grupo grupo = new Grupo();
					grupo.setCongregacao(congregacao);
					final List<Grupo> grupos = getService(CoreService.class).readGrupo(grupo);
					final List<Publicador> publicadores = new ArrayList<Publicador>();
					for (Grupo grupo2 : grupos) {
						final Publicador publicador = new Publicador();
						publicador.setGrupo(grupo2);
						final List<Publicador> publicadoresPorGrupo = getService(CoreService.class)
								.readPublicador(publicador);
						if (VulpeValidationUtil.isNotEmpty(publicadoresPorGrupo)) {
							publicadores.addAll(publicadoresPorGrupo);
						}
					}
					getSession().setAttribute(Core.PUBLICADORES_CONGREGACAO_SELECIONADA,
							publicadores);
					getSession().setAttribute(Core.GRUPOS_CONGREGACAO_SELECIONADA, grupos);
				}
				getSession().setAttribute(Core.CONGREGACOES_USUARIO, congregacoes);
			} catch (VulpeApplicationException e) {
				LOG.error(e);
			}
		}
	}

}
