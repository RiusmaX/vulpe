package br.com.activethread.gmn.ministerio.model.entity;

import java.util.Date;

import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Select;
import org.vulpe.model.annotations.CodeGenerator;
import org.vulpe.model.entity.impl.VulpeBaseDB4OEntity;
import org.vulpe.view.annotations.View;
import org.vulpe.view.annotations.View.ViewType;
import org.vulpe.view.annotations.input.VulpeDate;
import org.vulpe.view.annotations.input.VulpeSelect;
import org.vulpe.view.annotations.input.VulpeSelectPopup;
import org.vulpe.view.annotations.input.VulpeText;
import org.vulpe.view.annotations.output.VulpeColumn;

import br.com.activethread.gmn.comuns.model.entity.TipoMinisterio;
import br.com.activethread.gmn.core.model.entity.Publicador;

@CodeGenerator(controller = @Controller(select = @Select(pageSize = 5)), view = @View(viewType = {
		ViewType.SELECT, ViewType.CRUD }))
@SuppressWarnings("serial")
public class Relatorio extends VulpeBaseDB4OEntity<Long> {

	@VulpeColumn(sortable = true, attribute = "nome")
	@VulpeSelectPopup(identifier = "id", description = "nome", action = "/core/Publicador/select/prepare", popupWidth = 420, argument = true, autoComplete = true)
	private Publicador pulicador;

	@VulpeColumn(sortable = true)
	@VulpeDate
	private Date data;

	@VulpeText(mask = "I", size = 10)
	private Integer horas;

	@VulpeText(mask = "I", size = 10)
	private Integer livros;

	@VulpeText(mask = "I", size = 10)
	private Integer revistas;

	@VulpeText(mask = "I", size = 10)
	private Integer revisitas;

	@VulpeText(mask = "I", size = 10)
	private Integer estudos;

	@VulpeSelect
	private TipoMinisterio tipoMinisterio;

	public final Publicador getPulicador() {
		return pulicador;
	}

	public final void setPulicador(Publicador pulicador) {
		this.pulicador = pulicador;
	}

	public final Date getData() {
		return data;
	}

	public final void setData(Date data) {
		this.data = data;
	}

	public final Integer getHoras() {
		return horas;
	}

	public final void setHoras(Integer horas) {
		this.horas = horas;
	}

	public final Integer getLivros() {
		return livros;
	}

	public final void setLivros(Integer livros) {
		this.livros = livros;
	}

	public final Integer getRevistas() {
		return revistas;
	}

	public final void setRevistas(Integer revistas) {
		this.revistas = revistas;
	}

	public final Integer getRevisitas() {
		return revisitas;
	}

	public final void setRevisitas(Integer revisitas) {
		this.revisitas = revisitas;
	}

	public final Integer getEstudos() {
		return estudos;
	}

	public final void setEstudos(Integer estudos) {
		this.estudos = estudos;
	}

	public final TipoMinisterio getTipoMinisterio() {
		return tipoMinisterio;
	}

	public final void setTipoMinisterio(TipoMinisterio tipoMinisterio) {
		this.tipoMinisterio = tipoMinisterio;
	}

}
