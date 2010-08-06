package br.com.activethread.gmn.publicacoes.model.entity;

import org.vulpe.controller.annotations.Controller;
import org.vulpe.model.annotations.CachedClass;
import org.vulpe.model.annotations.CodeGenerator;
import org.vulpe.model.annotations.Like;
import org.vulpe.model.annotations.OrderBy;
import org.vulpe.model.entity.impl.VulpeBaseDB4OEntity;
import org.vulpe.view.annotations.View;
import org.vulpe.view.annotations.View.ViewType;
import org.vulpe.view.annotations.input.VulpeText;
import org.vulpe.view.annotations.input.VulpeValidate;
import org.vulpe.view.annotations.input.VulpeValidate.VulpeValidateType;
import org.vulpe.view.annotations.output.VulpeColumn;

@CachedClass
@CodeGenerator(controller =

@Controller(pageSize = 5, tabularDespiseFields = { "descricao" }, tabularStartNewDetails = 5, tabularNewDetails = 1), view = @View(popupProperties = "id,nome", viewType = { ViewType.TABULAR }), manager = true)
@SuppressWarnings("serial")
public class TipoPublicacao extends VulpeBaseDB4OEntity<Long> {

	@VulpeColumn(sortable = true)
	@VulpeText(maxlength = 100, size = 50, argument = true)
	@VulpeValidate(type = VulpeValidateType.STRING, minlength = 3, maxlength = 100)
	@OrderBy
	@Like
	private String descricao;

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
