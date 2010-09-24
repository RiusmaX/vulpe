package org.vulpe.portal.core.model.entity;

import org.vulpe.model.annotations.CachedClass;
import org.vulpe.model.annotations.db4o.Inheritance;
import org.vulpe.portal.commons.model.entity.BasePortal;
import org.vulpe.portal.commons.model.entity.TextTranslate;
import org.vulpe.view.annotations.input.VulpeText;

//@CodeGenerator(controller = @Controller(tabular = @Tabular(despiseFields = "name", startNewRecords = 5, newRecords = 1)), view = @View(viewType = ViewType.TABULAR))
@CachedClass
@Inheritance
@SuppressWarnings("serial")
public class Category extends BasePortal {

	@VulpeText(size = 40)
	private TextTranslate name;

	@VulpeText(size = 60)
	private TextTranslate description;

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

	@Override
	public String toString() {
		if (getName() != null) {
			return getName().toString();
		}
		return super.toString();
	}

}
