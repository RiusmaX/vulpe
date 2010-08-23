package br.com.activethread.gmn.core.model.entity;

import org.vulpe.model.entity.impl.VulpeBaseDB4OEntity;
import org.vulpe.security.model.entity.User;

@SuppressWarnings("serial")
public class CongregacaoUsuario extends VulpeBaseDB4OEntity<Long> {

	private Congregacao congregacao;

	private User usuario;

	public void setCongregacao(Congregacao congregacao) {
		this.congregacao = congregacao;
	}

	public Congregacao getCongregacao() {
		return congregacao;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}

	public User getUsuario() {
		return usuario;
	}

}
