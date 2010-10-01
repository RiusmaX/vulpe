package org.vulpe.portal.core.model.entity;

import java.util.List;

import org.vulpe.model.annotations.db4o.Inheritance;
import org.vulpe.portal.commons.model.entity.Position;
import org.vulpe.portal.commons.model.entity.Target;
import org.vulpe.portal.commons.model.entity.TextTranslate;
import org.vulpe.view.annotations.input.VulpeSelect;
import org.vulpe.view.annotations.input.VulpeText;
import org.vulpe.view.annotations.output.VulpeColumn;

//@CodeGenerator(controller = @Controller(select = @Select(pageSize = 5)), view = @View(viewType = {
//		ViewType.CRUD, ViewType.SELECT }))
@Inheritance
@SuppressWarnings("serial")
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

	@VulpeColumn
	@VulpeText(size = 100, required = true)
	private String url;

	@VulpeColumn
	private Long clicks;

	@VulpeSelect
	private Target target;

	private List<Position> positions;

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

	public void setCategory(Category category) {
		this.category = category;
	}

	public Category getCategory() {
		return category;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public Section getSection() {
		return section;
	}

	public void setTarget(Target target) {
		this.target = target;
	}

	public Target getTarget() {
		return target;
	}

	public void setPositions(List<Position> positions) {
		this.positions = positions;
	}

	public List<Position> getPositions() {
		return positions;
	}
}
