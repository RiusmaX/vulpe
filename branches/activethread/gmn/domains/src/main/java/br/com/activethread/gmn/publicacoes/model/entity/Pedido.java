package br.com.activethread.gmn.publicacoes.model.entity;

import java.util.Date;
import java.util.List;

import org.vulpe.model.annotations.QueryParameter;
import org.vulpe.model.annotations.QueryParameter.OperatorType;
import org.vulpe.model.entity.impl.VulpeBaseDB4OEntity;
import org.vulpe.view.annotations.input.VulpeCheckbox;
import org.vulpe.view.annotations.input.VulpeDate;
import org.vulpe.view.annotations.input.VulpeSelectPopup;
import org.vulpe.view.annotations.logic.crud.Detail;
import org.vulpe.view.annotations.output.VulpeColumn;

import br.com.activethread.gmn.core.model.entity.Congregacao;
import br.com.activethread.gmn.core.model.entity.Publicador;

@SuppressWarnings("serial")
public class Pedido extends VulpeBaseDB4OEntity<Long> {

	@VulpeColumn(sortable = true, attribute = "nome")
	@VulpeSelectPopup(identifier = "id", description = "nome", action = "/core/Publicador/select", popupWidth = 420, argument = true, required = true, autoComplete = true)
	private Publicador publicador;

	@Detail(clazz = PedidoPublicacao.class)
	private List<PedidoPublicacao> publicacoes;

	@VulpeColumn
	@VulpeDate(required = true)
	private Date data;

	@QueryParameter(name = "data", operator = OperatorType.GREATER_OR_EQUAL)
	private transient Date dataInicial;

	@QueryParameter(name = "data", operator = OperatorType.SMALLER_OR_EQUAL)
	private transient Date dataFinal;

	@VulpeColumn
	private Date dataValidade;

	@VulpeColumn(booleanTo = "{Yes}|{No}")
	@VulpeCheckbox(argument = true, fieldValue = "true")
	private boolean entregue;

	@VulpeColumn
	private Date dataEntrega;

	private Congregacao congregacao;

	public void setPublicador(Publicador publicador) {
		this.publicador = publicador;
	}

	public Publicador getPublicador() {
		return publicador;
	}

	public void setPublicacoes(List<PedidoPublicacao> publicacoes) {
		this.publicacoes = publicacoes;
	}

	public List<PedidoPublicacao> getPublicacoes() {
		return publicacoes;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Date getData() {
		return data;
	}

	public void setDataEntrega(Date dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

	public Date getDataEntrega() {
		return dataEntrega;
	}

	public void setEntregue(boolean entregue) {
		this.entregue = entregue;
	}

	public boolean isEntregue() {
		return entregue;
	}

	public void setDataValidade(Date dataValidade) {
		this.dataValidade = dataValidade;
	}

	public Date getDataValidade() {
		return dataValidade;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setCongregacao(Congregacao congregacao) {
		this.congregacao = congregacao;
	}

	public Congregacao getCongregacao() {
		return congregacao;
	}

}
