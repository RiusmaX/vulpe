package br.gov.caixa.sirci.core.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.vulpe.controller.annotations.Controller;
import org.vulpe.model.annotations.CodeGenerator;
import org.vulpe.model.entity.AbstractVulpeBaseEntityImpl;
import org.vulpe.view.annotations.View;
import org.vulpe.view.annotations.View.ViewType;
import org.vulpe.view.annotations.input.VulpeSelect;
import org.vulpe.view.annotations.input.VulpeTextArea;
import org.vulpe.view.annotations.output.VulpeColumn;

@CodeGenerator(controller = @Controller(pageSize = 5), manager = true, view = @View(viewType = {
		ViewType.SELECT, ViewType.CRUD }))
@Entity
@SuppressWarnings("serial")
public class Apontamento extends AbstractVulpeBaseEntityImpl<Long> {

	@VulpeColumn(sortable = true)
	@VulpeTextArea(required = true)
	@Column(length = 256)
	private String descricao;

	@VulpeColumn(sortable = true, attribute = "descricao")
	@VulpeSelect(items = "TipoApontamento", itemKey = "id", itemLabel = "descricao", required = true, autoLoad = true)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipoApontamento", referencedColumnName = "id")
	private TipoApontamento tipoApontamento;

	@VulpeColumn(sortable = true)
	@VulpeSelect(argument = true)
	@Enumerated(EnumType.STRING)
	private SituacaoApontamento situacaoApontamento;

	@VulpeColumn(sortable = true)
	@VulpeSelect(argument = true)
	@Enumerated(EnumType.STRING)
	private ImpactoApontamento impactoApontamento;

	@VulpeTextArea(required = true)
	@Column(length = 2048)
	private String recomendacao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getRecomendacao() {
		return recomendacao;
	}

	public void setRecomendacao(String recomendacao) {
		this.recomendacao = recomendacao;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Override
	public Long getId() {
		return super.getId();
	}

	public void setTipoApontamento(TipoApontamento tipoApontamento) {
		this.tipoApontamento = tipoApontamento;
	}

	public TipoApontamento getTipoApontamento() {
		return tipoApontamento;
	}

	public SituacaoApontamento getSituacaoApontamento() {
		return situacaoApontamento;
	}

	public void setSituacaoApontamento(SituacaoApontamento situacaoApontamento) {
		this.situacaoApontamento = situacaoApontamento;
	}

	public ImpactoApontamento getImpactoApontamento() {
		return impactoApontamento;
	}

	public void setImpactoApontamento(ImpactoApontamento impactoApontamento) {
		this.impactoApontamento = impactoApontamento;
	}
}
