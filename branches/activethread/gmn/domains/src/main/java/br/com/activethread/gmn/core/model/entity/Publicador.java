package br.com.activethread.gmn.core.model.entity;

import org.vulpe.model.annotations.AutoComplete;
import org.vulpe.model.annotations.CodeGenerator;
import org.vulpe.model.annotations.OrderBy;
import org.vulpe.model.annotations.db4o.Like;
import org.vulpe.model.entity.VulpeBaseDB4OEntity;
import org.vulpe.security.model.entity.User;
import org.vulpe.view.annotations.View;
import org.vulpe.view.annotations.View.ViewType;
import org.vulpe.view.annotations.input.VulpeCheckbox;
import org.vulpe.view.annotations.input.VulpeSelect;
import org.vulpe.view.annotations.input.VulpeSelectPopup;
import org.vulpe.view.annotations.input.VulpeText;
import org.vulpe.view.annotations.output.VulpeColumn;

import br.com.activethread.gmn.comuns.model.entity.Sexo;

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

	@VulpeColumn(sortable = true, attribute = "name")
	@VulpeSelectPopup(identifier = "id", description = "name", action = "/security/User/select/prepare", popupWidth = 420, argument = true, autoComplete = true)
	private User usuario;

	@VulpeColumn(booleanTo = "{Yes}|{No}")
	@VulpeCheckbox(argument = true, fieldValue = "true")
	private boolean indicador;

	@VulpeColumn(booleanTo = "{Yes}|{No}")
	@VulpeCheckbox(argument = true, fieldValue = "true")
	private boolean leitor;

	@VulpeColumn(booleanTo = "{Yes}|{No}")
	@VulpeCheckbox(argument = true, fieldValue = "true")
	private boolean microfone;

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

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}

	public User getUsuario() {
		return usuario;
	}

	public void setIndicador(boolean indicador) {
		this.indicador = indicador;
	}

	public boolean isIndicador() {
		return indicador;
	}

	public void setLeitor(boolean leitor) {
		this.leitor = leitor;
	}

	public boolean isLeitor() {
		return leitor;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setMicrofone(boolean microfone) {
		this.microfone = microfone;
	}

	public boolean isMicrofone() {
		return microfone;
	}
}
