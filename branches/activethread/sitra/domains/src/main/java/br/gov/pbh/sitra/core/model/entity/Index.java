package br.gov.pbh.sitra.core.model.entity;

import org.vulpe.model.entity.impl.VulpeBaseSimpleEntity;

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
