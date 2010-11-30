package org.vulpe.portal.core.model.entity;

import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Select;
import org.vulpe.model.annotations.CodeGenerator;
import org.vulpe.model.annotations.db4o.Inheritance;
import org.vulpe.portal.commons.model.entity.Target;
import org.vulpe.portal.commons.model.entity.TextTranslate;
import org.vulpe.view.annotations.View;
import org.vulpe.view.annotations.View.ViewType;
import org.vulpe.view.annotations.input.VulpeSelect;
import org.vulpe.view.annotations.input.VulpeText;
import org.vulpe.view.annotations.output.VulpeColumn;

@CodeGenerator(controller = @Controller(select = @Select(pageSize = 5)), view = @View(viewType = {
		ViewType.SELECT, ViewType.MAIN }))
@Inheritance
@SuppressWarnings("serial")
public class Community extends BasePortal {

	@VulpeColumn
	@VulpeText(size = 40, required = true, argument = true)
	private TextTranslate name;

	@VulpeText(size = 100, required = true)
	private TextTranslate description;

	@VulpeColumn
	@VulpeText(size = 100)
	private String url;

	@VulpeSelect(required = true)
	private Target target;

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

	public void setTarget(Target target) {
		this.target = target;
	}

	public Target getTarget() {
		return target;
	}

}
