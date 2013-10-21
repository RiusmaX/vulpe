package org.vulpe.portal.core.model.entity;

import lombok.Getter;
import lombok.Setter;

import org.vulpe.model.annotations.CachedClass;
import org.vulpe.model.annotations.SkipAutoFilter;
import org.vulpe.model.db4o.annotations.Inheritance;
import org.vulpe.portal.commons.model.entity.TextTranslate;
import org.vulpe.view.annotations.input.VulpeCheckbox;
import org.vulpe.view.annotations.input.VulpeText;
import org.vulpe.view.annotations.input.VulpeTextArea;
import org.vulpe.view.annotations.output.VulpeColumn;

@CachedClass
@Inheritance
// @CodeGenerator(controller = @Controller(select = @Select(pageSize = 1)), view
// = @View(viewType = {
// ViewType.MAIN, ViewType.SELECT }))
@SuppressWarnings("serial")
@Getter
@Setter
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

	@SkipAutoFilter
	@VulpeColumn(booleanTo = "{Yes|{No}}")
	@VulpeCheckbox(fieldValue = "true")
	private boolean offline;

	@VulpeText(size = 40, maxlength = 100, required = true)
	private TextTranslate offlineMessage;

	private Section homeSection;

	private String pageTracker;

}
