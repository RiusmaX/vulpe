package org.jw.mmn.publications.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.vulpe.model.annotations.CachedClass;
import org.vulpe.model.annotations.Like;
import org.vulpe.model.annotations.NotExistEquals;
import org.vulpe.model.annotations.OrderBy;
import org.vulpe.model.annotations.Parameter;
import org.vulpe.model.annotations.QueryParameter;
import org.vulpe.model.entity.impl.VulpeBaseDB4OEntity;
import org.vulpe.view.annotations.input.VulpeSelect;
import org.vulpe.view.annotations.input.VulpeText;
import org.vulpe.view.annotations.output.VulpeColumn;

@CachedClass
@NotExistEquals(parameters = @QueryParameter(equals = @Parameter(name = "name")))
@SuppressWarnings("serial")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Publication extends VulpeBaseDB4OEntity<Long> {

	@VulpeColumn(sortable = true)
	@VulpeText(mask = "I", size = 10, maxlength = 10, argument = true)
	private Integer code;

	@VulpeColumn(sortable = true)
	@VulpeText(maxlength = 40, size = 40, required = true, argument = true)
	@Like
	@OrderBy
	private String name;

	@VulpeColumn(sortable = true, attribute = "descricao")
	@VulpeSelect(items = "PublicationType", itemKey = "id", itemLabel = "descricao", required = true, autoLoad = true)
	private PublicationType type;

	@VulpeColumn(sortable = true)
	@VulpeSelect(required = true)
	private Classification classification;

}
