package br.com.activethread.gmn.publicacoes.model.entity;

import org.vulpe.model.annotations.AutoComplete;
import org.vulpe.model.annotations.CachedClass;
import org.vulpe.model.annotations.CodeGenerator;
import org.vulpe.model.annotations.Like;
import org.vulpe.model.annotations.NotExistEqual;
import org.vulpe.model.annotations.OrderBy;
import org.vulpe.model.annotations.QueryParameter;
import org.vulpe.model.entity.impl.VulpeBaseDB4OEntity;
import org.vulpe.view.annotations.View;
import org.vulpe.view.annotations.View.ViewType;
import org.vulpe.view.annotations.input.VulpeSelect;
import org.vulpe.view.annotations.input.VulpeText;
import org.vulpe.view.annotations.output.VulpeColumn;

@CodeGenerator(view = @View(popupProperties = "id,codigo,nome", viewType = { ViewType.CRUD,
		ViewType.SELECT }))
@CachedClass
@NotExistEqual(parameters = @QueryParameter(name = "nome"))
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
	private TipoPublicacao tipo;

	@VulpeColumn(sortable = true)
	@VulpeSelect(required = true)
	private ClassificacaoPublicacao classificacao;

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

	public void setClassificacao(ClassificacaoPublicacao classificacao) {
		this.classificacao = classificacao;
	}

	public ClassificacaoPublicacao getClassificacao() {
		return classificacao;
	}

	public void setTipo(TipoPublicacao tipo) {
		this.tipo = tipo;
	}

	public TipoPublicacao getTipo() {
		return tipo;
	}

}
