package br.gov.caixa.sirci.core.model.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.vulpe.model.annotations.CodeGenerator;
import org.vulpe.model.entity.AbstractVulpeBaseEntityImpl;

@CodeGenerator(manager = true)
@Entity
@SuppressWarnings("serial")
public class DocumentoOrigem extends AbstractVulpeBaseEntityImpl<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String descricao;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orgaoOrigem", referencedColumnName = "id")
	private OrgaoOrigem orgaoOrigem;

	public DocumentoOrigem() {
	}

	public DocumentoOrigem(final OrgaoOrigem orgaoOrigem) {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setOrgaoOrigem(OrgaoOrigem orgaoOrigem) {
		this.orgaoOrigem = orgaoOrigem;
	}

	public OrgaoOrigem getOrgaoOrigem() {
		return orgaoOrigem;
	}

}
