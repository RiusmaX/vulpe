package org.vulpe.portal.core.model.entity;

import org.vulpe.model.annotations.CachedClass;
import org.vulpe.model.annotations.IgnoreAutoFilter;
import org.vulpe.model.annotations.db4o.Inheritance;
import org.vulpe.portal.commons.model.entity.BasePortal;
import org.vulpe.portal.commons.model.entity.TextTranslate;
import org.vulpe.view.annotations.input.VulpeCheckbox;
import org.vulpe.view.annotations.input.VulpeText;
import org.vulpe.view.annotations.input.VulpeTextArea;
import org.vulpe.view.annotations.output.VulpeColumn;

@CachedClass
@Inheritance
//@CodeGenerator(controller = @Controller(select = @Select(pageSize = 1)), view = @View(viewType = {
//		ViewType.CRUD, ViewType.SELECT }))
@SuppressWarnings("serial")
public class Portal extends BasePortal {

	@VulpeColumn
	@VulpeText(size = 40, maxlength = 100, required = true)
	private TextTranslate name;

	@VulpeText(size = 40, maxlength = 100, required = true)
	private TextTranslate title;

	@VulpeTextArea(cols = 40, rows = 3, required = true)
	private TextTranslate description;

	@VulpeText(size = 40, maxlength = 100, required = true)
	private TextTranslate copyright;

	@IgnoreAutoFilter
	@VulpeColumn(booleanTo = "{Yes|{No}}")
	@VulpeCheckbox(fieldValue = "true")
	private boolean offline;

	@VulpeText(size = 40, maxlength = 100, required = true)
	private TextTranslate offlineMessage;

	public TextTranslate getName() {
		return name;
	}

	public void setName(TextTranslate name) {
		this.name = name;
	}

	public TextTranslate getTitle() {
		return title;
	}

	public void setTitle(TextTranslate title) {
		this.title = title;
	}

	public TextTranslate getDescription() {
		return description;
	}

	public void setDescription(TextTranslate description) {
		this.description = description;
	}

	public TextTranslate getCopyright() {
		return copyright;
	}

	public void setCopyright(TextTranslate copyright) {
		this.copyright = copyright;
	}

	public boolean isOffline() {
		return offline;
	}

	public void setOffline(boolean offline) {
		this.offline = offline;
	}

	public TextTranslate getOfflineMessage() {
		return offlineMessage;
	}

	public void setOfflineMessage(TextTranslate offlineMessage) {
		this.offlineMessage = offlineMessage;
	}


}
