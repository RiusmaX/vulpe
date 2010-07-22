package br.com.activethread.gmn.anuncios.model.entity;

import java.util.Date;
import java.util.List;

import org.vulpe.commons.annotations.DetailConfig;
import org.vulpe.commons.annotations.DetailConfig.CardinalityType;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.model.annotations.CodeGenerator;
import org.vulpe.model.entity.impl.VulpeBaseDB4OEntity;
import org.vulpe.view.annotations.View;
import org.vulpe.view.annotations.View.ViewType;
import org.vulpe.view.annotations.input.VulpeDate;
import org.vulpe.view.annotations.input.VulpeSelectPopup;
import org.vulpe.view.annotations.logic.crud.Detail;
import org.vulpe.view.annotations.output.VulpeColumn;

import br.com.activethread.gmn.core.model.entity.Publicador;

@CodeGenerator(controller = @Controller(pageSize = 5, detailsConfig = { @DetailConfig(name = "discursos", propertyName = "entity.discursos", despiseFields = "tema", cardinalityType = CardinalityType.ONE_OR_MORE, newDetails = 3) }), manager = true, view = @View(viewType = {
		ViewType.SELECT, ViewType.CRUD }))
@SuppressWarnings("serial")
public class ReuniaoServico extends VulpeBaseDB4OEntity<Long> {

	@VulpeColumn(sortable = true, attribute = "nome")
	@VulpeSelectPopup(identifier = "id", description = "nome", action = "/core/Publicador/select/prepare", popupWidth = 420, argument = true, required = true, autoComplete = true)
	private Publicador presidente;

	@VulpeColumn
	@VulpeDate(required = true)
	private Date data;

	@Detail(clazz = ReuniaoServicoDiscurso.class)
	private List<ReuniaoServicoDiscurso> discursos;

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

	public void setDiscursos(List<ReuniaoServicoDiscurso> discursos) {
		this.discursos = discursos;
	}

	public List<ReuniaoServicoDiscurso> getDiscursos() {
		return discursos;
	}

}
