package br.gov.caixa.sirci.core.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.vulpe.controller.annotations.Controller;
import org.vulpe.model.annotations.CachedClass;
import org.vulpe.model.annotations.CodeGenerator;
import org.vulpe.model.entity.AbstractVulpeBaseEntityImpl;
import org.vulpe.view.annotations.View;
import org.vulpe.view.annotations.View.ViewType;
import org.vulpe.view.annotations.input.VulpeText;
import org.vulpe.view.annotations.input.VulpeValidate;
import org.vulpe.view.annotations.input.VulpeValidate.VulpeValidateScope;
import org.vulpe.view.annotations.output.VulpeColumn;

@CachedClass
@CodeGenerator(controller = @Controller(pageSize = 5), manager = true, view = @View(viewType = { ViewType.ALL }))
@Entity
@SuppressWarnings("serial")
public class TipoApontamento extends AbstractVulpeBaseEntityImpl<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@VulpeValidate(requiredScope = { VulpeValidateScope.CRUD, VulpeValidateScope.SELECT })
	@VulpeColumn(sortable = true)
	@VulpeText(required = true, argument = true, size = 50, maxlength = 50)
	@Column(length = 50)
	private String descricao;

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	@Transient
	@Override
	public String getOrderBy() {
		return "obj.descricao";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
