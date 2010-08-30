package br.com.activethread.gmn.core.model.entity;

import java.util.List;

import org.vulpe.model.annotations.AutoComplete;
import org.vulpe.model.annotations.CodeGenerator;
import org.vulpe.model.annotations.Like;
import org.vulpe.model.annotations.NotExistEqual;
import org.vulpe.model.annotations.OrderBy;
import org.vulpe.model.annotations.QueryParameter;
import org.vulpe.model.entity.impl.VulpeBaseDB4OEntity;
import org.vulpe.view.annotations.View;
import org.vulpe.view.annotations.View.ViewType;
import org.vulpe.view.annotations.input.VulpeCheckbox;
import org.vulpe.view.annotations.input.VulpeSelect;
import org.vulpe.view.annotations.input.VulpeText;
import org.vulpe.view.annotations.output.VulpeColumn;

import br.com.activethread.gmn.comuns.model.entity.Cargo;
import br.com.activethread.gmn.comuns.model.entity.PrivilegioAdicional;
import br.com.activethread.gmn.comuns.model.entity.Sexo;
import br.com.activethread.gmn.comuns.model.entity.TipoMinisterio;

@NotExistEqual(parameters = { @QueryParameter(name = "nome") })
@CodeGenerator(view = @View(popupProperties = "id,nome", viewType = { ViewType.CRUD,
		ViewType.SELECT }))
@SuppressWarnings("serial")
public class Publicador extends VulpeBaseDB4OEntity<Long> {

	@VulpeColumn(sortable = true)
	@VulpeText(maxlength = 60, size = 50, required = true, argument = true)
	@OrderBy
	@Like
	@AutoComplete
	private String nome;

	@VulpeSelect(required = true)
	private Sexo sexo;

	@VulpeColumn(sortable = true, attribute = "nome")
	@VulpeSelect(items = "Grupo", itemKey = "id", itemLabel = "nome", autoLoad = true, argument = true)
	private Grupo grupo;

	@VulpeColumn(booleanTo = "{Yes}|{No}")
	@VulpeCheckbox(argument = true, fieldValue = "true")
	private Boolean batizado;

	@VulpeSelect(required = true)
	private TipoMinisterio tipoMinisterio;

	@VulpeSelect(required = true)
	private Cargo cargo;

	private List<PrivilegioAdicional> privilegiosAdicionais;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public final Grupo getGrupo() {
		return grupo;
	}

	public final void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setTipoMinisterio(TipoMinisterio tipoMinisterio) {
		this.tipoMinisterio = tipoMinisterio;
	}

	public TipoMinisterio getTipoMinisterio() {
		return tipoMinisterio;
	}

	public void setBatizado(Boolean batizado) {
		this.batizado = batizado;
	}

	public Boolean getBatizado() {
		return batizado;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setPrivilegiosAdicionais(List<PrivilegioAdicional> privilegiosAdicionais) {
		this.privilegiosAdicionais = privilegiosAdicionais;
	}

	public List<PrivilegioAdicional> getPrivilegiosAdicionais() {
		return privilegiosAdicionais;
	}

}
