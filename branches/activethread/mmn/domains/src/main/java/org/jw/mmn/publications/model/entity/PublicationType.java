package org.jw.mmn.publications.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.vulpe.model.annotations.CachedClass;
import org.vulpe.model.annotations.Like;
import org.vulpe.model.annotations.OrderBy;
import org.vulpe.model.entity.impl.VulpeBaseDB4OEntity;
import org.vulpe.view.annotations.input.VulpeText;
import org.vulpe.view.annotations.input.VulpeValidate;
import org.vulpe.view.annotations.input.VulpeValidate.VulpeValidateType;
import org.vulpe.view.annotations.output.VulpeColumn;

@CachedClass
//@CodeGenerator(controller =
//@Controller(select = @Select(pageSize = 5), tabular = @Tabular(despiseFields = { "descricao" }, startNewRecords = 5, newRecords = 1)), view = @View(popupProperties = "id,nome", viewType = { ViewType.TABULAR }))
@SuppressWarnings("serial")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PublicationType extends VulpeBaseDB4OEntity<Long> {

	@VulpeColumn(sortable = true)
	@VulpeText(maxlength = 100, size = 50, argument = true)
	@VulpeValidate(type = VulpeValidateType.STRING, minlength = 3, maxlength = 100)
	@OrderBy
	@Like
	private String description;

}
