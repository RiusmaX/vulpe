package br.com.activethread.gmn.comuns.model.entity;

import org.vulpe.model.entity.impl.VulpeBaseDB4OEntity;

@SuppressWarnings("serial")
public class Telefone extends VulpeBaseDB4OEntity<Long> {

	private String ddd;

	private String numero;

	private TipoTelefone tipo;

	private boolean principal;

	public void setDdd(String ddd) {
		this.ddd = ddd;
	}

	public String getDdd() {
		return ddd;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getNumero() {
		return numero;
	}

	public void setPrincipal(boolean principal) {
		this.principal = principal;
	}

	public boolean isPrincipal() {
		return principal;
	}

	public void setTipo(TipoTelefone tipo) {
		this.tipo = tipo;
	}

	public TipoTelefone getTipo() {
		return tipo;
	}

}
