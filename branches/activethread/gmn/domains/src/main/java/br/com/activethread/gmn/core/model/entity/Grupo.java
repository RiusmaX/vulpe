package br.com.activethread.gmn.core.model.entity;

import java.util.List;

import org.vulpe.commons.annotations.DetailConfig;
import org.vulpe.commons.annotations.DetailConfig.CardinalityType;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Controller.ControllerType;
import org.vulpe.model.annotations.CodeGenerator;
import org.vulpe.model.annotations.db4o.Like;
import org.vulpe.model.annotations.db4o.OrderBy;
import org.vulpe.model.entity.AbstractVulpeBaseEntityImpl;
import org.vulpe.view.annotations.View;
import org.vulpe.view.annotations.View.ViewType;
import org.vulpe.view.annotations.input.VulpeSelect;
import org.vulpe.view.annotations.input.VulpeText;
import org.vulpe.view.annotations.input.VulpeValidate;
import org.vulpe.view.annotations.input.VulpeValidate.VulpeValidateType;
import org.vulpe.view.annotations.logic.crud.Detail;
import org.vulpe.view.annotations.output.VulpeColumn;


@CodeGenerator(controller = {
		@Controller(controllerType = ControllerType.TABULAR, tabularDespiseFields = "nome", tabularStartNewDetails = 5, tabularNewDetails = 1),
		@Controller(controllerType = ControllerType.SELECT, pageSize = 5),
		@Controller(controllerType = ControllerType.CRUD, detailsConfig = { @DetailConfig(name = "publicadores", propertyName = "entity.publicadores", despiseFields = "nome", startNewDetails = 10, newDetails = 1, cardinalityType = CardinalityType.ONE) }) }, manager = true, view = @View(viewType = {
		ViewType.TABULAR, ViewType.CRUD, ViewType.SELECT }))
@SuppressWarnings("serial")
public class Grupo extends AbstractVulpeBaseEntityImpl<Long> {

	@OrderBy
	@Like
	@VulpeColumn(sortable = true)
	@VulpeValidate(type = VulpeValidateType.STRING, minlength = 5, maxlength = 40)
	@VulpeText(size = 40, maxlength = 40)
	private String nome;

	@Detail(clazz = Publicador.class)
	private List<Publicador> publicadores;

	@VulpeSelect(items = "Congregacao", itemKey = "id", itemLabel = "nome", required = true, autoLoad = true, argument = true)
	private Congregacao congregacao;

	public final String getNome() {
		return nome;
	}

	public final void setNome(String nome) {
		this.nome = nome;
	}

	public void setCongregacao(Congregacao congregacao) {
		this.congregacao = congregacao;
	}

	public Congregacao getCongregacao() {
		return congregacao;
	}

	public void setPublicadores(List<Publicador> publicadores) {
		this.publicadores = publicadores;
	}

	public List<Publicador> getPublicadores() {
		return publicadores;
	}

}
