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
import org.vulpe.controller.annotations.Select;
import org.vulpe.model.annotations.CodeGenerator;
import org.vulpe.model.entity.impl.AbstractVulpeBaseJPAEntity;
import org.vulpe.view.annotations.View;
import org.vulpe.view.annotations.View.ViewType;
import org.vulpe.view.annotations.input.VulpeSelect;
import org.vulpe.view.annotations.input.VulpeTextArea;
import org.vulpe.view.annotations.output.VulpeColumn;

@CodeGenerator(controller = @Controller(select = @Select(pageSize = 5)), manager = true, view = @View(viewType = {
		ViewType.SELECT, ViewType.CRUD }))
@Entity
@SuppressWarnings("serial")
public class Apontamento extends AbstractVulpeBaseJPAEntity<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@VulpeColumn(sortable = true)
	@VulpeTextArea(required = true)
	@Column(length = 256)
	private String descricao;

	@VulpeColumn(sortable = true, attribute = "descricao")
	@VulpeSelect(items = "TipoApontamento", itemKey = "id", itemLabel = "descricao", required = true, autoLoad = true)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipoApontamento", referencedColumnName = "id")
	private TipoApontamento tipoApontamento;

	@VulpeSelect(items = "OrgaoOrigem", itemKey = "id", itemLabel = "descricao", required = true, autoLoad = true)
	private transient OrgaoOrigem orgaoOrigem;

	@VulpeSelect(items = "${listaDocumentoOrigem}", itemKey = "id", itemLabel = "descricao", required = true, autoLoad = true, size = 3)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "documentoOrigem", referencedColumnName = "id")
	private DocumentoOrigem documentoOrigem;

	@VulpeSelect(items = "Unidade", itemKey = "id", itemLabel = "nome", required = true, autoLoad = true)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "unidadeResponsavel", referencedColumnName = "id")
	private Unidade unidadeResponsavel;

	@VulpeSelect(items = "Unidade", itemKey = "id", itemLabel = "nome", required = true, autoLoad = true)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "unidadeCoresponsavel", referencedColumnName = "id")
	private Unidade unidadeCoresponsavel;

	@VulpeColumn(sortable = true)
	@VulpeSelect(argument = true)
	@Enumerated(EnumType.ORDINAL)
	private SituacaoApontamento situacaoApontamento;

	@VulpeColumn(sortable = true)
	@VulpeSelect(argument = true)
	@Enumerated(EnumType.ORDINAL)
	private ImpactoApontamento impactoApontamento;

	@VulpeTextArea(required = true)
	@Column(length = 2048)
	private String recomendacao;

	public Apontamento() {
	}

	public Apontamento(final TipoApontamento tipoApontamento) {
		this.tipoApontamento = tipoApontamento;
	}

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OrgaoOrigem getOrgaoOrigem() {
		return orgaoOrigem;
	}

	public void setOrgaoOrigem(OrgaoOrigem orgaoOrigem) {
		this.orgaoOrigem = orgaoOrigem;
	}

	public DocumentoOrigem getDocumentoOrigem() {
		return documentoOrigem;
	}

	public void setDocumentoOrigem(DocumentoOrigem documentoOrigem) {
		this.documentoOrigem = documentoOrigem;
	}

	public Unidade getUnidadeResponsavel() {
		return unidadeResponsavel;
	}

	public void setUnidadeResponsavel(Unidade unidadeResponsavel) {
		this.unidadeResponsavel = unidadeResponsavel;
	}

	public Unidade getUnidadeCoresponsavel() {
		return unidadeCoresponsavel;
	}

	public void setUnidadeCoresponsavel(Unidade unidadeCoresponsavel) {
		this.unidadeCoresponsavel = unidadeCoresponsavel;
	}
}
