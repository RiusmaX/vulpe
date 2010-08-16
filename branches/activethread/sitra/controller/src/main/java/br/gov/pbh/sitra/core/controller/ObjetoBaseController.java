package br.gov.pbh.sitra.core.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.vulpe.commons.VulpeConstants.View;
import org.vulpe.commons.VulpeConstants.Action.Button;
import org.vulpe.commons.VulpeConstants.Action.URI;
import org.vulpe.controller.commons.VulpeControllerConfig.ControllerType;

import br.gov.pbh.sitra.commons.ApplicationConstants;
import br.gov.pbh.sitra.controller.ApplicationBaseController;
import br.gov.pbh.sitra.core.model.entity.Objeto;
import br.gov.pbh.sitra.core.model.entity.Sistema;

/**
 * Controller implementation of Objeto
 */
@SuppressWarnings( { "serial", "unchecked" })
public class ObjetoBaseController extends ApplicationBaseController<Objeto, java.lang.Long> {

	protected final List<Sistema> sistemas = (List<Sistema>) getCachedClass().get(
			Sistema.class.getSimpleName());

	@Override
	public void showButtons(String method) {
		super.showButtons(method);
		if (getControllerType().equals(ControllerType.SELECT)) {
			hideButton(Button.CREATE);
		}
		if (getControllerType().equals(ControllerType.CRUD)) {
			final List<Objeto> objetos = getSessionAttribute(getSelectTableKey());
			if (objetos == null || objetos.isEmpty()) {
				hideButton(Button.PREPARE);
			}
			hideButton(Button.CREATE);
		}
	}

	@Override
	protected void createPostBefore() {
		super.createPostBefore();
		atualizarDadosObjeto();
	}

	@Override
	protected void updatePostBefore() {
		super.updatePostBefore();
		atualizarDadosObjeto();
	}

	protected void atualizarDadosObjeto() {
		final Objeto objeto = getEntity();
		objeto.setUsuario(getUserAuthenticated());
	}

	@Override
	public String select() {
		final String redirecionar = validarSistemaSelecionado();
		if (StringUtils.isNotEmpty(redirecionar)) {
			return redirecionar;
		}
		return super.select();
	}

	@Override
	public String create() {
		final String redirecionar = validarSistemaSelecionado();
		if (StringUtils.isNotEmpty(redirecionar)) {
			return redirecionar;
		}
		return super.create();
	}

	@Override
	public String update() {
		final String redirecionar = validarSistemaSelecionado();
		if (StringUtils.isNotEmpty(redirecionar)) {
			return redirecionar;
		}
		return super.update();
	}

	private String validarSistemaSelecionado() {
		if (getSessionAttribute(ApplicationConstants.SISTEMA_SELECIONADO) == null) {
			if (getRequest().getRequestURI().endsWith(URI.AJAX)) {
				setAjax(true);
			}
			final String currentLayout = getSessionAttribute(View.CURRENT_LAYOUT);
			String url = "FRONTEND".equals(currentLayout) ? "/frontend/Index" : "/backend/Index";
			return redirectTo(url, isAjax());
		}
		return null;
	}
}
