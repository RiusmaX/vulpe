package br.com.activethread.gmn.relatorio.model.entity;

import br.com.activethread.gmn.publicacoes.model.entity.Publicacao;

public class PedidoSimples {

	private Publicacao publicacao;

	private Integer quantidade;

	public PedidoSimples() {
	}

	public PedidoSimples(final Publicacao publicacao, final Integer quantidade) {
		this.publicacao = publicacao;
		this.quantidade = quantidade;
	}

	public Publicacao getPublicacao() {
		return publicacao;
	}

	public void setPublicacao(Publicacao publicacao) {
		this.publicacao = publicacao;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

}
