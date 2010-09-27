package org.vulpe.portal.core.model.entity;

import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Select;
import org.vulpe.model.annotations.CodeGenerator;
import org.vulpe.model.annotations.IgnoreAutoFilter;
import org.vulpe.model.annotations.db4o.Inheritance;
import org.vulpe.portal.commons.model.entity.BasePortal;
import org.vulpe.portal.commons.model.entity.TextTranslate;
import org.vulpe.view.annotations.View;
import org.vulpe.view.annotations.View.ViewType;
import org.vulpe.view.annotations.input.VulpeCheckbox;
import org.vulpe.view.annotations.input.VulpeSelect;
import org.vulpe.view.annotations.input.VulpeText;
import org.vulpe.view.annotations.input.VulpeTextArea;
import org.vulpe.view.annotations.output.VulpeColumn;

@CodeGenerator(controller = @Controller(select = @Select(pageSize = 5, requireOneFilter = true)), view = @View(viewType = {
		ViewType.CRUD, ViewType.SELECT }))
@Inheritance
@SuppressWarnings("serial")
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

	@VulpeTextArea(rows = 5, cols = 80, required = true)
	private TextTranslate fullText;

	@VulpeColumn
	private Long viewCount;

	@IgnoreAutoFilter
	@VulpeCheckbox(fieldValue = "true", required = true)
	private boolean escapeXml;

	public TextTranslate getTitle() {
		return title;
	}

	public void setTitle(TextTranslate title) {
		this.title = title;
	}

	public TextTranslate getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(TextTranslate subtitle) {
		this.subtitle = subtitle;
	}

	public TextTranslate getMiniText() {
		return miniText;
	}

	public void setMiniText(TextTranslate miniText) {
		this.miniText = miniText;
	}

	public TextTranslate getFullText() {
		return fullText;
	}

	public void setFullText(TextTranslate fullText) {
		this.fullText = fullText;
	}

	public Long getViewCount() {
		return viewCount;
	}

	public void setViewCount(Long viewCount) {
		this.viewCount = viewCount;
	}

	public boolean isEscapeXml() {
		return escapeXml;
	}

	public void setEscapeXml(boolean escapeXml) {
		this.escapeXml = escapeXml;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public Section getSection() {
		return section;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Category getCategory() {
		return category;
	}

	@Override
	public String toString() {
		if (getTitle() != null) {
			return getTitle().toString();
		}
		return super.toString();
	}
}
