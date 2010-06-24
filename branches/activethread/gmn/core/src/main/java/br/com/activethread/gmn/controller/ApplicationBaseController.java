package br.com.activethread.gmn.controller;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.vulpe.controller.struts.VulpeStrutsController;
import org.vulpe.model.entity.VulpeBaseEntity;

import br.com.activethread.gmn.commons.ApplicationConstants.Core;
import br.com.activethread.gmn.core.model.entity.Congregacao;

@SuppressWarnings({ "serial", "rawtypes" })
public class ApplicationBaseController<ENTITY extends VulpeBaseEntity<ID>, ID extends Serializable & Comparable>
		extends VulpeStrutsController<ENTITY, ID> {

	protected static final Logger LOG = Logger.getLogger(ApplicationBaseController.class);

	private Congregacao congregacao;

	public void setCongregacao(final Congregacao congregacao) {
		this.congregacao = congregacao;
	}

	public Congregacao getCongregacao() {
		return congregacao;
	}

	@Override
	protected void onPrepare() {
		setCongregacao((Congregacao) getSession().getAttribute(Core.CONGREGACAO_USUARIO));
		super.onPrepare();
	}
}
