package br.com.activethread.gmn.security.authentication.callback.impl.pojo;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.vulpe.exception.VulpeApplicationException;
import org.vulpe.security.authentication.callback.AfterUserAuthenticationCallback;
import org.vulpe.security.authentication.callback.UserAuthenticatedCallback;
import org.vulpe.security.commons.VulpeSecurityStrutsCallbackUtil;
import org.vulpe.security.model.entity.User;

import br.com.activethread.gmn.commons.ApplicationConstants.Core;
import br.com.activethread.gmn.core.model.entity.Publicador;
import br.com.activethread.gmn.core.model.services.CoreServices;

@Component("AfterUserAuthenticationCallback")
public class AfterUserAuthenticationCallbackPOJOImpl extends VulpeSecurityStrutsCallbackUtil
		implements AfterUserAuthenticationCallback {

	protected static final Logger LOG = Logger
			.getLogger(AfterUserAuthenticationCallbackPOJOImpl.class);

	@Override
	public void execute() {
		UserAuthenticatedCallback userAuthenticatedCallback = getBean(UserAuthenticatedCallback.class);
		if (userAuthenticatedCallback != null) {
			userAuthenticatedCallback.execute();
			final Long userId = userAuthenticatedCallback.getId();
			try {
				Publicador publicador = new Publicador();
				final User usuario = new User();
				usuario.setId(userId);
				publicador.setUsuario(usuario);
				List<Publicador> publicadores = getService(CoreServices.class).readPublicador(
						publicador);
				if (publicadores != null && !publicadores.isEmpty()) {
					publicador = publicadores.get(0);
					getSession()
							.setAttribute(Core.CONGREGACAO_USUARIO, publicador.getCongregacao());
				}
			} catch (VulpeApplicationException e) {
				LOG.error(e);
			}
		}
	}

}
