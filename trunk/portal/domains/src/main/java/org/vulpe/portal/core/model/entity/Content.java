package org.vulpe.portal.core.model.entity;

import org.vulpe.model.annotations.IgnoreAutoFilter;
import org.vulpe.model.annotations.db4o.Inheritance;
import org.vulpe.portal.commons.model.entity.TextTranslate;
import org.vulpe.view.annotations.input.VulpeCheckbox;
import org.vulpe.view.annotations.input.VulpeSelect;
import org.vulpe.view.annotations.input.VulpeText;
import org.vulpe.view.annotations.input.VulpeTextArea;
import org.vulpe.view.annotations.output.VulpeColumn;

//@CodeGenerator(controller = @Controller(select = @Select(pageSize = 5, requireOneFilter = true)), view = @View(viewType = {
//		ViewType.CRUD, ViewType.SELECT }))
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
	private Long views;

	@IgnoreAutoFilter
	@VulpeCheckbox(fieldValue = "true", required = true)
	private boolean escapeXml;

	private Boolean showComplete;

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

	@Override
	public String getOrderBy() {
		return "date desc";
	}

	public void setViews(Long views) {
		this.views = views;
	}

	public Long getViews() {
		return views;
	}

	public void increaseView() {
		if (views == null) {
			views = 1L;
		} else {
			++views;
		}
	}

	public void setShowComplete(Boolean showComplete) {
		this.showComplete = showComplete;
	}

	public Boolean getShowComplete() {
		return showComplete;
	}

}
