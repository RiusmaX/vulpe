package br.com.activethread.gmn.core.model.entity;

import br.com.activethread.gmn.comuns.model.entity.Telefone;

@SuppressWarnings("serial")
public class PublicadorTelefone extends Telefone {

	private Publicador publicador;

	public void setPublicador(Publicador publicador) {
		this.publicador = publicador;
	}

	public Publicador getPublicador() {
		return publicador;
	}

}
