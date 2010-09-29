package org.vulpe.portal.core.model.entity;

import org.apache.commons.lang.StringUtils;
import org.vulpe.model.annotations.CachedClass;
import org.vulpe.model.annotations.IgnoreAutoFilter;
import org.vulpe.model.annotations.OrderBy;
import org.vulpe.model.annotations.db4o.Inheritance;
import org.vulpe.view.annotations.input.VulpeCheckbox;
import org.vulpe.view.annotations.input.VulpeText;

@Inheritance
//@CodeGenerator(controller = @Controller(tabular = @Tabular(despiseFields = "localeCode", newRecords = 1, startNewRecords = 3)), view = @View(viewType = { ViewType.TABULAR }))
@CachedClass
@SuppressWarnings("serial")
public class Language extends BasePortal {

	@VulpeText(size = 10)
	private String localeCode;

	@OrderBy
	@VulpeText(size = 30)
	private String name;

	@IgnoreAutoFilter
	@VulpeCheckbox(fieldValue = "true")
	private boolean defaultLanguage;

	public void setDefaultLanguage(boolean defaultLanguage) {
		this.defaultLanguage = defaultLanguage;
	}

	public boolean isDefaultLanguage() {
		return defaultLanguage;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}

	public String getLocaleCode() {
		return localeCode;
	}

	@Override
	public String toString() {
		if (StringUtils.isNotEmpty(getLocaleCode())) {
			return getLocaleCode();
		}
		return super.toString();
	}

}
