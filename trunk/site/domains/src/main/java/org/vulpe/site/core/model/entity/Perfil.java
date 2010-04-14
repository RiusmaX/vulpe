package org.vulpe.site.core.model.entity;

import org.vulpe.model.entity.VulpeBaseSimpleEntity;

@SuppressWarnings("serial")
public class Perfil extends VulpeBaseSimpleEntity {

	private String tema;

	public String getTema() {
		return tema;
	}

	public void setTema(String tema) {
		this.tema = tema;
	}

}