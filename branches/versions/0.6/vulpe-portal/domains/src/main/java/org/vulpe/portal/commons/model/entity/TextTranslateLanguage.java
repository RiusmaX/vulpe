package org.vulpe.portal.commons.model.entity;

import org.apache.commons.lang.StringUtils;
import org.vulpe.model.entity.impl.VulpeBaseDB4OAuditEntity;
import org.vulpe.portal.core.model.entity.Language;

@SuppressWarnings("serial")
public class TextTranslateLanguage extends VulpeBaseDB4OAuditEntity<Long> {

	private TextTranslate textTranslate;

	private Language language;

	private String text;

	public TextTranslateLanguage() {
	}

	public TextTranslateLanguage(final Language language, final String text) {
		this.language = language;
		this.text = text;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public Language getLanguage() {
		return language;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setTextTranslate(TextTranslate textTranslate) {
		this.textTranslate = textTranslate;
	}

	public TextTranslate getTextTranslate() {
		return textTranslate;
	}

	@Override
	public String toString() {
		if (StringUtils.isNotEmpty(getText())) {
			return getText();
		}
		return super.toString();
	}

}
