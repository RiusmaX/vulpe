package br.com.activethread.gmn.anuncios.model.entity;

import java.util.Date;
import java.util.List;

import org.vulpe.commons.annotations.DetailConfig;
import org.vulpe.commons.annotations.Quantity;
import org.vulpe.commons.annotations.Quantity.QuantityType;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Select;
import org.vulpe.model.annotations.CodeGenerator;
import org.vulpe.model.entity.impl.VulpeBaseDB4OEntity;
import org.vulpe.view.annotations.View;
import org.vulpe.view.annotations.View.ViewType;
import org.vulpe.view.annotations.input.VulpeDate;
import org.vulpe.view.annotations.input.VulpeSelect;
import org.vulpe.view.annotations.input.VulpeSelectPopup;
import org.vulpe.view.annotations.logic.crud.Detail;
import org.vulpe.view.annotations.output.VulpeColumn;

import br.com.activethread.gmn.core.model.entity.Congregacao;
import br.com.activethread.gmn.core.model.entity.Publicador;

@CodeGenerator(controller = @Controller(select = @Select(pageSize = 5), detailsConfig = { @DetailConfig(name = "discursos", propertyName = "entity.discursos", despiseFields = "tema", quantity = @Quantity(type = QuantityType.ONE_OR_MORE), newDetails = 3) }), manager = true, view = @View(viewType = {
		ViewType.SELECT, ViewType.CRUD }))
@SuppressWarnings("serial")
public class Reuniao extends VulpeBaseDB4OEntity<Long> {

	@VulpeColumn(sortable = true, attribute = "nome")
	@VulpeSelectPopup(identifier = "id", description = "nome", action = "/core/Publicador/select", popupWidth = 420, argument = true, required = true, autoComplete = true)
	private Publicador presidente;

	@VulpeColumn
	@VulpeDate(required = true)
	private Date data;

	@VulpeSelect
	private TipoReuniao tipo;

	private Integer canticoInicial;

	private Integer canticoFinal;

	@Detail(clazz = Discurso.class)
	private List<Discurso> discursos;

	private Congregacao congregacao;

	public Publicador getPresidente() {
		return presidente;
	}

	public void setPresidente(Publicador presidente) {
		this.presidente = presidente;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public void setDiscursos(List<Discurso> discursos) {
		this.discursos = discursos;
	}

	public List<Discurso> getDiscursos() {
		return discursos;
	}

	public void setTipo(TipoReuniao tipo) {
		this.tipo = tipo;
	}

	public TipoReuniao getTipo() {
		return tipo;
	}

	public void setCanticoInicial(Integer canticoInicial) {
		this.canticoInicial = canticoInicial;
	}

	public Integer getCanticoInicial() {
		return canticoInicial;
	}

	public void setCanticoFinal(Integer canticoFinal) {
		this.canticoFinal = canticoFinal;
	}

	public Integer getCanticoFinal() {
		return canticoFinal;
	}

	public void setCongregacao(Congregacao congregacao) {
		this.congregacao = congregacao;
	}

	public Congregacao getCongregacao() {
		return congregacao;
	}

}
