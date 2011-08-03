package org.jw.mmn.core.model.entity;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.vulpe.model.annotations.Like;
import org.vulpe.model.annotations.OrderBy;
import org.vulpe.model.entity.impl.VulpeBaseDB4OEntity;
import org.vulpe.view.annotations.input.VulpeSelect;
import org.vulpe.view.annotations.input.VulpeText;
import org.vulpe.view.annotations.input.VulpeValidate;
import org.vulpe.view.annotations.input.VulpeValidate.VulpeValidateType;
import org.vulpe.view.annotations.logic.main.Detail;
import org.vulpe.view.annotations.output.VulpeColumn;

//@CodeGenerator(controller = @Controller(select =@Select(pageSize = 5), tabular = @Tabular(despiseFields = "nome", startNewRecords = 5, newRecords = 1), detailsConfig = { @DetailConfig(name = "publicadores", propertyName = "entity.publicadores", despiseFields = "nome", startNewDetails = 10, newDetails = 1, quantity = @Quantity(type = QuantityType.ONE)) }), manager = true, view = @View(viewType = {
//		ViewType.TABULAR, ViewType.MAIN, ViewType.SELECT }))
@SuppressWarnings("serial")
@Getter
@Setter
public class Group extends VulpeBaseDB4OEntity<Long> {

	@OrderBy
	@Like
	@VulpeColumn(sortable = true)
	@VulpeValidate(type = VulpeValidateType.STRING, minlength = 5, maxlength = 40)
	@VulpeText(size = 40, maxlength = 40)
	private String name;

	@Detail(target = Member.class)
	private List<Member> members;

	@VulpeSelect(items = "Congregation", itemKey = "id", itemLabel = "name", required = true, autoLoad = true, argument = true)
	private Congregation congregation;

}
