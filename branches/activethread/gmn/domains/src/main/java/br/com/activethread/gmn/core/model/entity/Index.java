package br.com.activethread.gmn.core.model.entity;

import org.vulpe.model.entity.impl.VulpeBaseSimpleEntity;

@SuppressWarnings("serial")
public class Index extends VulpeBaseSimpleEntity {

	private Congregacao congregacao;

	public void setCongregacao(Congregacao congregacao) {
		this.congregacao = congregacao;
	}

	public Congregacao getCongregacao() {
		return congregacao;
	}

}
