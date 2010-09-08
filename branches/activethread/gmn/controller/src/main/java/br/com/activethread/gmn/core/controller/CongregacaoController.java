package br.com.activethread.gmn.core.controller;

import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.commons.annotations.DetailConfig;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Select;
import org.vulpe.controller.annotations.Tabular;

import br.com.activethread.gmn.commons.ApplicationConstants.Core;
import br.com.activethread.gmn.controller.ApplicationBaseController;
import br.com.activethread.gmn.core.model.entity.Congregacao;
import br.com.activethread.gmn.core.model.entity.CongregacaoUsuario;
import br.com.activethread.gmn.core.model.services.CoreService;

/**
 * Controller implementation of Congregacao
 */
@Component("core.CongregacaoController")
@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller(serviceClass = CoreService.class, detailsConfig = {
		@DetailConfig(name = "grupos", propertyName = "entity.grupos", despiseFields = "nome", startNewDetails = 3, newDetails = 1),
		@DetailConfig(name = "usuarios", propertyName = "entity.usuarios", despiseFields = "usuario", startNewDetails = 3, newDetails = 1) }, select = @Select(pageSize = 5), tabular = @Tabular(startNewRecords = 1, newRecords = 1))
public class CongregacaoController extends ApplicationBaseController<Congregacao, java.lang.Long> {

	@Override
	protected void createPostAfter(Congregacao entity) {
		super.createPostAfter(entity);
		atualizar();
	}

	@Override
	protected void updatePostAfter() {
		super.updatePostAfter();
		atualizar();
	}

	private void atualizar() {
		boolean existe = false;
		final List<Congregacao> congregacoes = getSessionAttribute(Core.CONGREGACOES_USUARIO);
		for (Congregacao congregacao : congregacoes) {
			if (congregacao.getId().equals(getEntity().getId())) {
				existe = true;
				break;
			}
		}
		if (!existe) {
			final String username = getUserAuthenticated();
			if (getEntity().getUsuarios() != null) {
				for (CongregacaoUsuario congregacaoUsuario : getEntity().getUsuarios()) {
					if (congregacaoUsuario.getUsuario().getUsername().equals(username)) {
						congregacoes.add(getEntity());
						getSession().setAttribute(Core.CONGREGACOES_USUARIO, congregacoes);
						break;
					}
				}
			}
		}
	}
}
