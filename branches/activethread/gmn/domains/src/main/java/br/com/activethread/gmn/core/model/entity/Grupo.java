package br.com.activethread.gmn.core.model.entity;

import java.util.List;

import org.vulpe.commons.annotations.DetailConfig;
import org.vulpe.commons.annotations.Quantity;
import org.vulpe.commons.annotations.Quantity.QuantityType;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Select;
import org.vulpe.controller.annotations.Tabular;
import org.vulpe.model.annotations.CodeGenerator;
import org.vulpe.model.annotations.Like;
import org.vulpe.model.annotations.OrderBy;
import org.vulpe.model.entity.impl.VulpeBaseDB4OEntity;
import org.vulpe.view.annotations.View;
import org.vulpe.view.annotations.View.ViewType;
import org.vulpe.view.annotations.input.VulpeSelect;
import org.vulpe.view.annotations.input.VulpeText;
import org.vulpe.view.annotations.input.VulpeValidate;
import org.vulpe.view.annotations.input.VulpeValidate.VulpeValidateType;
import org.vulpe.view.annotations.logic.crud.Detail;
import org.vulpe.view.annotations.output.VulpeColumn;

@CodeGenerator(controller = @Controller(select =@Select(pageSize = 5), tabular = @Tabular(despiseFields = "nome", startNewRecords = 5, newRecords = 1), detailsConfig = { @DetailConfig(name = "publicadores", propertyName = "entity.publicadores", despiseFields = "nome", startNewDetails = 10, newDetails = 1, quantity = @Quantity(type = QuantityType.ONE)) }), manager = true, view = @View(viewType = {
		ViewType.TABULAR, ViewType.CRUD, ViewType.SELECT }))
@SuppressWarnings("serial")
public class Grupo extends VulpeBaseDB4OEntity<Long> {

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
