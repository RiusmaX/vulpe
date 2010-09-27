package org.vulpe.portal.core.model.entity;

import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Select;
import org.vulpe.model.annotations.CodeGenerator;
import org.vulpe.model.annotations.db4o.Inheritance;
import org.vulpe.portal.commons.model.entity.TextTranslate;
import org.vulpe.view.annotations.View;
import org.vulpe.view.annotations.View.ViewType;

@CodeGenerator(controller = @Controller(select = @Select(pageSize = 5)), view = @View(viewType = {
		ViewType.SELECT, ViewType.CRUD }))
@Inheritance
@SuppressWarnings("serial")
public class Menu extends BasePortal {

	private TextTranslate name;

	private TextTranslate description;

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

}
