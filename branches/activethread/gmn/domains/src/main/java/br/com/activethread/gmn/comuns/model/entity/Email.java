package br.com.activethread.gmn.comuns.model.entity;

import org.vulpe.model.entity.impl.VulpeBaseDB4OEntity;

@SuppressWarnings("serial")
public class Email extends VulpeBaseDB4OEntity<Long> {

	private String endereco;

	private boolean principal;

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setPrincipal(boolean principal) {
		this.principal = principal;
	}

	public boolean isPrincipal() {
		return principal;
	}
}
