package br.com.activethread.gmn.controller;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.vulpe.commons.VulpeConstants.View;
import org.vulpe.commons.VulpeConstants.Action.URI;
import org.vulpe.controller.struts.VulpeStrutsController;
import org.vulpe.model.entity.VulpeEntity;

import br.com.activethread.gmn.commons.ApplicationConstants.Core;
import br.com.activethread.gmn.core.model.entity.Congregacao;

@SuppressWarnings( { "serial", "unchecked" })
public class ApplicationBaseController<ENTITY extends VulpeEntity<ID>, ID extends Serializable & Comparable>
		extends VulpeStrutsController<ENTITY, ID> {

	protected static final Logger LOG = Logger.getLogger(ApplicationBaseController.class);

	public Congregacao getCongregacao() {
		return getSessionAttribute(Core.CONGREGACAO_SELECIONADA);
	}

	@Override
	public String select() {
		final String redirecionar = validarCongregacaoSelecionada();
		if (StringUtils.isNotEmpty(redirecionar)) {
			return redirecionar;
		}
		return StringUtils.isNotEmpty(redirecionar) ? redirecionar : super.select();
	}

	@Override
	public String create() {
		final String redirecionar = validarCongregacaoSelecionada();
		return StringUtils.isNotEmpty(redirecionar) ? redirecionar : super.create();
	}

	@Override
	public String update() {
		final String redirecionar = validarCongregacaoSelecionada();
		return StringUtils.isNotEmpty(redirecionar) ? redirecionar : super.update();
	}

	@Override
	public String tabular() {
		final String redirecionar = validarCongregacaoSelecionada();
		return StringUtils.isNotEmpty(redirecionar) ? redirecionar : super.tabular();
	}

	private String validarCongregacaoSelecionada() {
		if (getSessionAttribute(Core.CONGREGACAO_SELECIONADA) == null) {
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
