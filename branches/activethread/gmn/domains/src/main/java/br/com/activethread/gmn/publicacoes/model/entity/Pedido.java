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
	private transient Date dataInicio;

	@QueryParameter(name = "data", operator = OperatorType.SMALLER_OR_EQUAL)
	private transient Date dataFim;

	@VulpeColumn
	private Date dataValidade;

	@VulpeColumn(booleanTo = "{Yes}|{No}")
	@VulpeCheckbox(argument = true, fieldValue = "true")
	private boolean entregue;

	@VulpeColumn
	private Date dataEntrega;

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

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public Date getDataFim() {
		return dataFim;
	}

}
