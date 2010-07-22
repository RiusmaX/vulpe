package br.com.activethread.gmn.publicacoes.model.entity;

import org.vulpe.model.annotations.AutoComplete;
import org.vulpe.model.annotations.CachedClass;
import org.vulpe.model.annotations.CodeGenerator;
import org.vulpe.model.annotations.OrderBy;
import org.vulpe.model.annotations.db4o.Like;
import org.vulpe.model.entity.impl.VulpeBaseDB4OEntity;
import org.vulpe.view.annotations.View;
import org.vulpe.view.annotations.View.ViewType;
import org.vulpe.view.annotations.input.VulpeSelect;
import org.vulpe.view.annotations.input.VulpeText;
import org.vulpe.view.annotations.output.VulpeColumn;

import br.com.activethread.gmn.comuns.model.entity.ClassificacaoPublicacao;

@CodeGenerator(view = @View(popupProperties = "id,codigo,nome", viewType = {
		ViewType.CRUD, ViewType.SELECT }))
@CachedClass
@SuppressWarnings("serial")
public class Publicacao extends VulpeBaseDB4OEntity<Long> {

	@VulpeColumn(sortable = true)
	@VulpeText(mask = "I", size = 10, maxlength = 10, argument = true)
	private Integer codigo;

	@VulpeColumn(sortable = true)
	@VulpeText(maxlength = 40, size = 40, required = true, argument = true)
	@Like
	@OrderBy
	@AutoComplete
	private String nome;

	@VulpeColumn(sortable = true, attribute = "descricao")
	@VulpeSelect(items = "TipoPublicacao", itemKey = "id", itemLabel = "descricao", required = true, autoLoad = true)
	private TipoPublicacao tipoPublicacao;

	@VulpeColumn(sortable = true)
	@VulpeSelect(required = true)
	private ClassificacaoPublicacao classificacaoPublicacao;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public TipoPublicacao getTipoPublicacao() {
		return tipoPublicacao;
	}

	public void setTipoPublicacao(TipoPublicacao tipoPublicacao) {
		this.tipoPublicacao = tipoPublicacao;
	}

	public ClassificacaoPublicacao getClassificacaoPublicacao() {
		return classificacaoPublicacao;
	}

	public void setClassificacaoPublicacao(
			ClassificacaoPublicacao classificacaoPublicacao) {
		this.classificacaoPublicacao = classificacaoPublicacao;
	}

}
