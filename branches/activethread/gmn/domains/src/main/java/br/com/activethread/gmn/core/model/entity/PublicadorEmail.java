package br.com.activethread.gmn.core.model.entity;

import br.com.activethread.gmn.comuns.model.entity.Email;

@SuppressWarnings("serial")
public class PublicadorEmail extends Email {

	private Publicador publicador;

	public void setPublicador(Publicador publicador) {
		this.publicador = publicador;
	}

	public Publicador getPublicador() {
		return publicador;
	}

}
