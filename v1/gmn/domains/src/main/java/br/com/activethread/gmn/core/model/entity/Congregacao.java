package br.com.activethread.gmn.core.model.entity;

import java.util.List;

import org.vulpe.commons.annotations.DetailConfig;
import org.vulpe.commons.annotations.DetailConfig.CardinalityType;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.commons.VulpeControllerConfig.ControllerType;
import org.vulpe.model.annotations.CachedClass;
import org.vulpe.model.annotations.CodeGenerator;
import org.vulpe.model.entity.impl.VulpeBaseDB4OEntity;
import org.vulpe.view.annotations.View;
import org.vulpe.view.annotations.View.ViewType;
import org.vulpe.view.annotations.input.VulpeText;
import org.vulpe.view.annotations.input.VulpeValidate;
import org.vulpe.view.annotations.input.VulpeValidate.VulpeValidateType;
import org.vulpe.view.annotations.logic.crud.Detail;
import org.vulpe.view.annotations.output.VulpeColumn;

@CachedClass
@CodeGenerator(controller = @Controller(pageSize = 5, tabularDespiseFields = "nome", tabularStartNewDetails = 5, tabularNewDetails = 1, controllerType = ControllerType.CRUD, detailsConfig = { @DetailConfig(name = "grupos", propertyName = "entity.grupos", despiseFields = "nome", startNewDetails = 3, newDetails = 1, cardinalityType = CardinalityType.ONE) }), manager = true, view = @View(viewType = {
		ViewType.CRUD, ViewType.SELECT, ViewType.TABULAR }))
@SuppressWarnings("serial")
public class Congregacao extends VulpeBaseDB4OEntity<Long> {

	@VulpeColumn(sortable = true)
	@VulpeValidate(type = VulpeValidateType.STRING, minlength = 5, maxlength = 60)
	@VulpeText(argument = true, size = 40, maxlength = 60, required = true)
	private String nome;

	@Detail(clazz = Grupo.class)
	private List<Grupo> grupos;

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}

	public List<Grupo> getGrupos() {
		return grupos;
	}

}