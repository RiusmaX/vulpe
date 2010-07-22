package br.gov.caixa.sirci.core.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.vulpe.model.annotations.CachedClass;
import org.vulpe.model.annotations.CodeGenerator;
import org.vulpe.model.entity.impl.AbstractVulpeBaseJPAEntity;

@CodeGenerator(manager = true)
@CachedClass
@Entity
@SuppressWarnings("serial")
public class Unidade extends AbstractVulpeBaseJPAEntity<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String nome;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
