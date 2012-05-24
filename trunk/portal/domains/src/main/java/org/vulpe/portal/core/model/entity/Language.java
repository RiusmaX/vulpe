package org.vulpe.portal.core.model.entity;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang.StringUtils;
import org.vulpe.model.annotations.CachedClass;
import org.vulpe.model.annotations.SkipAutoFilter;
import org.vulpe.model.annotations.OrderBy;
import org.vulpe.model.db4o.annotations.Inheritance;
import org.vulpe.view.annotations.input.VulpeCheckbox;
import org.vulpe.view.annotations.input.VulpeText;

@Inheritance
// @CodeGenerator(controller = @Controller(tabular = @Tabular(despiseFields =
// "localeCode", newRecords = 1, startNewRecords = 3)), view = @View(viewType =
// { ViewType.TABULAR }))
@CachedClass
@SuppressWarnings("serial")
@Getter
@Setter
public class Language extends BasePortal {

	@VulpeText(size = 10)
	private String localeCode;

	@OrderBy
	@VulpeText(size = 30)
	private String name;

	@SkipAutoFilter
	@VulpeCheckbox(fieldValue = "true")
	private boolean defaultLanguage;

	@Override
	public String toString() {
		if (StringUtils.isNotEmpty(getLocaleCode())) {
			return getLocaleCode();
		}
		return super.toString();
	}

}
