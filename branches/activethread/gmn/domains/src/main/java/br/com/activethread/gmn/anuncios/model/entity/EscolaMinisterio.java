package br.com.activethread.gmn.anuncios.model.entity;

import java.util.Date;
import java.util.List;

import org.vulpe.commons.annotations.DetailConfig;
import org.vulpe.commons.annotations.DetailConfig.CardinalityType;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Controller.ControllerType;
import org.vulpe.model.annotations.CodeGenerator;
import org.vulpe.view.annotations.View;
import org.vulpe.view.annotations.View.ViewType;
import org.vulpe.view.annotations.input.VulpeDate;
import org.vulpe.view.annotations.input.VulpeSelectPopup;
import org.vulpe.view.annotations.logic.crud.Detail;

import org.vulpe.model.entity.AbstractVulpeBaseEntityImpl;
import br.com.activethread.gmn.core.model.entity.Publicador;

@CodeGenerator(controller = {
		@Controller(controllerType = ControllerType.CRUD, detailsConfig = { @DetailConfig(name = "discursos", propertyName = "entity.discursos", despiseFields = "tema", cardinalityType = CardinalityType.ONE_OR_MORE, newDetails = 4) }),
		@Controller(controllerType = ControllerType.SELECT) }, manager = true, view = @View(viewType = {
		ViewType.SELECT, ViewType.CRUD }))
@SuppressWarnings("serial")
public class EscolaMinisterio extends AbstractVulpeBaseEntityImpl<Long> {

	@VulpeSelectPopup(identifier = "id", description = "nome", action = "/core/Publicador/select/prepare", popupWidth = 420, argument = true, required = true, autoComplete = true)
	private Publicador presidente;

	@VulpeDate(required = true)
	private Date data;

	@Detail(clazz = EscolaMinisterioDiscurso.class)
	private List<EscolaMinisterioDiscurso> discursos;

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

	public void setDiscursos(List<EscolaMinisterioDiscurso> discursos) {
		this.discursos = discursos;
	}

	public List<EscolaMinisterioDiscurso> getDiscursos() {
		return discursos;
	}

}
