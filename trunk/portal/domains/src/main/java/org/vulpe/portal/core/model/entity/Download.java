package org.vulpe.portal.core.model.entity;

import org.vulpe.model.annotations.CodeGenerator;
import org.vulpe.model.annotations.db4o.Inheritance;
import org.vulpe.portal.commons.model.entity.TextTranslate;

@CodeGenerator
@Inheritance
@SuppressWarnings("serial")
public class Download extends BasePortal {

	private TextTranslate name;

	private TextTranslate description;

	private String url;

	private Long downloadCount;

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

	public void setDownloadCount(Long downloadCount) {
		this.downloadCount = downloadCount;
	}

	public Long getDownloadCount() {
		return downloadCount;
	}

	@Override
	public String toString() {
		if (getName() != null) {
			return getName().toString();
		}
		return super.toString();
	}

}
