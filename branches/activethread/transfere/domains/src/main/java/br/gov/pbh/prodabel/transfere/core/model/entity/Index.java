package br.gov.pbh.prodabel.transfere.core.model.entity;

import org.vulpe.model.entity.impl.VulpeBaseSimpleEntity;

import br.gov.pbh.prodabel.transfere.core.model.entity.Sistema;

@SuppressWarnings("serial")
public class Index extends VulpeBaseSimpleEntity {

	private Sistema sistema;

	public void setSistema(Sistema sistema) {
		this.sistema = sistema;
	}

	public Sistema getSistema() {
		return sistema;
	}

}
