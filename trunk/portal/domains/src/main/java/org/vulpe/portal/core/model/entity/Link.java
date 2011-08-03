package org.vulpe.portal.core.model.entity;

import lombok.Data;

import org.vulpe.model.annotations.db4o.Inheritance;
import org.vulpe.portal.commons.model.entity.Position;
import org.vulpe.portal.commons.model.entity.Target;
import org.vulpe.portal.commons.model.entity.TextTranslate;
import org.vulpe.view.annotations.input.VulpeSelect;
import org.vulpe.view.annotations.input.VulpeText;
import org.vulpe.view.annotations.output.VulpeColumn;

//@CodeGenerator(controller = @Controller(select = @Select(pageSize = 5)), view = @View(viewType = {
//		ViewType.MAIN, ViewType.SELECT }))
@Inheritance
@SuppressWarnings("serial")
@Data
public class Link extends BasePortal {

	@VulpeColumn(attribute = "name")
	@VulpeSelect(argument = true, autoLoad = true, items = "Section", itemKey = "id", itemLabel = "name", showBlank = true)
	private Section section;

	@VulpeColumn(attribute = "name")
	@VulpeSelect(autoLoad = true, items = "Category", itemKey = "id", itemLabel = "name", showBlank = true)
	private Category category;

	@VulpeColumn
	@VulpeText(size = 60, required = true, argument = true)
	private TextTranslate name;

	@VulpeText(size = 100, required = true)
	private TextTranslate description;

	private Content content;

	@VulpeColumn
	@VulpeText(size = 100)
	private String url;

	@VulpeColumn
	private Long clicks;

	@VulpeSelect
	private Target target;

	private Position position;
	
	public Link() {
	}

	public Link(final Long id) {
		setId(id);
	}

		@Override
	public String toString() {
		if (getName() != null) {
			return getName().toString();
		}
		return super.toString();
	}

	public void setClicks(Long clicks) {
		this.clicks = clicks;
	}

	public Long getClicks() {
		return clicks;
	}

	public void increaseClick() {
		if (clicks == null) {
			clicks = 1L;
		} else {
			++clicks;
		}
	}

}
