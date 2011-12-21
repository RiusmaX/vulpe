package org.vulpe.portal.core.model.entity;

import lombok.Getter;
import lombok.Setter;

import org.vulpe.model.annotations.IgnoreAutoFilter;
import org.vulpe.model.db4o.annotations.Inheritance;
import org.vulpe.portal.commons.model.entity.Status;
import org.vulpe.portal.commons.model.entity.TextTranslate;
import org.vulpe.view.annotations.input.VulpeCheckbox;
import org.vulpe.view.annotations.input.VulpeSelect;
import org.vulpe.view.annotations.input.VulpeText;
import org.vulpe.view.annotations.input.VulpeTextArea;
import org.vulpe.view.annotations.output.VulpeColumn;

//@CodeGenerator(controller = @Controller(select = @Select(pageSize = 5, requireOneFilter = true)), view = @View(viewType = {
//		ViewType.MAIN, ViewType.SELECT }))
@Inheritance
@SuppressWarnings("serial")
@Getter
@Setter
public class Content extends BasePortal {

	@VulpeColumn(attribute = "name")
	@VulpeSelect(argument = true, autoLoad = true, items = "Section", itemKey = "id", itemLabel = "name", showBlank = true, required = true)
	private Section section;

	@VulpeColumn(attribute = "name")
	@VulpeSelect(autoLoad = true, items = "Category", itemKey = "id", itemLabel = "name", showBlank = true, required = true)
	private Category category;

	@VulpeColumn
	@VulpeText(argument = true, size = 40, required = true)
	private TextTranslate title;

	@VulpeText(size = 40, required = true)
	private TextTranslate subtitle;

	@VulpeText(size = 100, required = true)
	private TextTranslate miniText;

	private String videoURL;

	private Integer videoWidth;

	private Integer videoHeight;

	@VulpeTextArea(rows = 5, cols = 80, required = true)
	private TextTranslate fullText;

	@VulpeColumn
	private Long views;

	@IgnoreAutoFilter
	@VulpeCheckbox(fieldValue = "true", required = true)
	private boolean escapeXml;

	private Boolean showComplete;

	public Content() {
	}

	public Content(final Long id) {
		setId(id);
	}

	public Content(final Status status) {
		setStatus(status);
	}

	@Override
	public String toString() {
		if (getTitle() != null) {
			return getTitle().toString();
		}
		return super.toString();
	}

	public void increaseView() {
		if (views == null) {
			views = 1L;
		} else {
			++views;
		}
	}

}
