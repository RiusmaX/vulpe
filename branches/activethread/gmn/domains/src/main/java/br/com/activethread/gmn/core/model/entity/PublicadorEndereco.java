package br.com.activethread.gmn.core.model.entity;

import br.com.activethread.gmn.comuns.model.entity.Endereco;

@SuppressWarnings("serial")
public class PublicadorEndereco extends Endereco {

	private Publicador publicador;

	public void setPublicador(Publicador publicador) {
		this.publicador = publicador;
	}

	public Publicador getPublicador() {
		return publicador;
	}

}
