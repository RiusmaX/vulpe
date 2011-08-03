package org.vulpe.portal.core.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.vulpe.model.annotations.db4o.Inheritance;
import org.vulpe.portal.commons.model.entity.TextTranslate;
import org.vulpe.view.annotations.input.VulpeCheckbox;
import org.vulpe.view.annotations.input.VulpeSelect;
import org.vulpe.view.annotations.input.VulpeText;
import org.vulpe.view.annotations.output.VulpeColumn;

//@CodeGenerator(controller = @Controller(select = @Select(pageSize = 5)), view = @View(viewType = {
//		ViewType.SELECT, ViewType.MAIN }))
@Inheritance
@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper = true)
public class Menu extends BasePortal {

	@VulpeColumn
	@VulpeText(size = 40, required = true, argument = true)
	private TextTranslate name;

	@VulpeText(size = 100, required = true)
	private TextTranslate description;

	@VulpeColumn(attribute = "name")
	@VulpeSelect(items = "Section", itemKey = "id", itemLabel = "name", showBlank = true, autoLoad = true)
	private Section navigateToSection;

	@VulpeColumn
	@VulpeText(size = 100)
	private String url;

	@VulpeCheckbox(fieldValue = "true")
	private Boolean selectOnLoad;

	@Override
	public String toString() {
		if (getName() != null) {
			return getName().toString();
		}
		return super.toString();
	}

}
