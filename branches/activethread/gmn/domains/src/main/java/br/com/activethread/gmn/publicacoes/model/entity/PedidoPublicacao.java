package br.com.activethread.gmn.publicacoes.model.entity;

import org.vulpe.model.entity.impl.VulpeBaseDB4OEntity;
import org.vulpe.view.annotations.input.VulpeCheckbox;
import org.vulpe.view.annotations.input.VulpeSelectPopup;
import org.vulpe.view.annotations.input.VulpeText;
import org.vulpe.view.annotations.output.VulpeColumn;

import br.com.activethread.gmn.publicacoes.model.entity.Pedido;
import br.com.activethread.gmn.publicacoes.model.entity.Publicacao;

@SuppressWarnings("serial")
public class PedidoPublicacao extends VulpeBaseDB4OEntity<Long> {

	private Pedido pedido;

	@VulpeText(mask = "I", size = 5, maxlength = 5, required = true)
	private Integer quantidade;

	@VulpeSelectPopup(name = "publicacao", identifier = "id", description = "nome", action = "/publicacoes/Publicacao/select", popupWidth = 420, size = 35, required = true)
	private Publicacao publicacao;

	@VulpeColumn(align = "center")
	@VulpeCheckbox(argument = true, fieldValue = "true")
	private boolean entregue;

	@VulpeText(mask = "I", size = 5, maxlength = 5)
	private Integer quantidadeEntregue;

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Publicacao getPublicacao() {
		return publicacao;
	}

	public void setPublicacao(Publicacao publicacao) {
		this.publicacao = publicacao;
	}

	public void setEntregue(boolean entregue) {
		this.entregue = entregue;
	}

	public boolean isEntregue() {
		return entregue;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidadeEntregue(Integer quantidadeEntregue) {
		this.quantidadeEntregue = quantidadeEntregue;
	}

	public Integer getQuantidadeEntregue() {
		return quantidadeEntregue;
	}

}
