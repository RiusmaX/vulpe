package org.vulpe.portal.core.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.vulpe.model.db4o.annotations.Inheritance;
import org.vulpe.portal.commons.model.entity.Status;
import org.vulpe.portal.commons.model.entity.TextTranslate;
import org.vulpe.view.annotations.input.VulpeSelect;
import org.vulpe.view.annotations.input.VulpeText;
import org.vulpe.view.annotations.output.VulpeColumn;

//@CodeGenerator(controller = @Controller(select = @Select(pageSize = 5)), view = @View(viewType = {
//		ViewType.MAIN, ViewType.SELECT }))
@Inheritance
@SuppressWarnings("serial")
@Getter
@Setter
@NoArgsConstructor
public class Download extends BasePortal {

	@VulpeColumn(attribute = "name")
	@VulpeSelect(argument = true, autoLoad = true, items = "Section", itemKey = "id", itemLabel = "name", showBlank = true, required = true)
	private Section section;

	@VulpeColumn(attribute = "name")
	@VulpeSelect(autoLoad = true, items = "Category", itemKey = "id", itemLabel = "name", showBlank = true, required = true)
	private Category category;

	@VulpeColumn
	@VulpeText(size = 60, argument = true, required = true)
	private TextTranslate name;

	@VulpeText(size = 100, required = true)
	private TextTranslate description;

	@VulpeColumn
	@VulpeText(size = 100)
	private String url;

	private Long downloads;
	
	public Download(final Long id) {
		setId(id);
	}
	
	public Download(Status status) {
		setStatus(status);
	}

	@Override
	public String toString() {
		if (getName() != null) {
			return getName().toString();
		}
		return super.toString();
	}

	public void increaseDownload() {
		if (downloads == null) {
			downloads = 1L;
		} else {
			++downloads;
		}
	}

}
