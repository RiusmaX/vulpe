package org.vulpe.portal.core.model.entity;

import org.vulpe.model.annotations.db4o.Inheritance;
import org.vulpe.portal.commons.model.entity.TextTranslate;
import org.vulpe.view.annotations.input.VulpeSelect;
import org.vulpe.view.annotations.input.VulpeText;
import org.vulpe.view.annotations.output.VulpeColumn;

//@CodeGenerator(controller = @Controller(select = @Select(pageSize = 5)), view = @View(viewType = {
//		ViewType.SELECT, ViewType.CRUD }))
@Inheritance
@SuppressWarnings("serial")
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

	public void setName(TextTranslate name) {
		this.name = name;
	}

	public TextTranslate getName() {
		return name;
	}

	public void setDescription(TextTranslate description) {
		this.description = description;
	}

	public TextTranslate getDescription() {
		return description;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	@Override
	public String toString() {
		if (getName() != null) {
			return getName().toString();
		}
		return super.toString();
	}

	public void setNavigateToSection(Section navigateToSection) {
		this.navigateToSection = navigateToSection;
	}

	public Section getNavigateToSection() {
		return navigateToSection;
	}

}
