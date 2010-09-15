package br.com.activethread.gmn.comuns.model.entity;

import org.vulpe.model.entity.impl.VulpeBaseDB4OEntity;

@SuppressWarnings("serial")
public class Estado extends VulpeBaseDB4OEntity<Long> {

	private String nome;

	private String sigla;

	private Pais pais;

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getSigla() {
		return sigla;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public Pais getPais() {
		return pais;
	}
}
