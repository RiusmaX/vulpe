package org.vulpe.portal.core.model.entity;

import lombok.Getter;
import lombok.Setter;

import org.vulpe.model.annotations.CachedClass;
import org.vulpe.model.db4o.annotations.Inheritance;
import org.vulpe.portal.commons.model.entity.CategoryType;
import org.vulpe.portal.commons.model.entity.TextTranslate;
import org.vulpe.view.annotations.input.VulpeSelect;
import org.vulpe.view.annotations.input.VulpeText;
import org.vulpe.view.annotations.output.VulpeColumn;

//@CodeGenerator(controller = @Controller(tabular = @Tabular(despiseFields = "name", startNewRecords = 5, newRecords = 1)), view = @View(viewType = ViewType.TABULAR))
@CachedClass
@Inheritance
@SuppressWarnings("serial")
@Getter
@Setter
public class Category extends BasePortal {

	private String code;

	@VulpeText(size = 40)
	private TextTranslate name;

	@VulpeText(size = 60)
	private TextTranslate description;

	@VulpeColumn
	@VulpeSelect(required = true)
	private CategoryType categoryType;

	@Override
	public String toString() {
		if (getName() != null) {
			return getName().toString();
		}
		return super.toString();
	}

}
