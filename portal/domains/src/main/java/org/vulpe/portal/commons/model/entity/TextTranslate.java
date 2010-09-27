package org.vulpe.portal.commons.model.entity;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.vulpe.commons.VulpeContext;
import org.vulpe.commons.util.VulpeValidationUtil;
import org.vulpe.model.annotations.CreateIfNotExist;
import org.vulpe.model.entity.impl.VulpeBaseDB4OAuditEntity;

@CreateIfNotExist
@SuppressWarnings("serial")
public class TextTranslate extends VulpeBaseDB4OAuditEntity<Long> {

	private transient Long languageId;

	private transient String text;

	private List<TextTranslateLanguage> languages;

	public void setLanguages(List<TextTranslateLanguage> languages) {
		this.languages = languages;
	}

	public List<TextTranslateLanguage> getLanguages() {
		return languages;
	}

	public String toString() {
		return getText();
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}

	public Long getLanguageId() {
		return languageId;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		if (VulpeValidationUtil.isNotEmpty(languages)) {
			for (TextTranslateLanguage translateLanguage : languages) {
				if (translateLanguage.getLanguage() != null
						&& StringUtils.isNotEmpty(translateLanguage.getLanguage().getLocaleCode())
						&& translateLanguage.getLanguage().getLocaleCode().equals(
								VulpeContext.getInstance().getLocale().toString())) {
					setLanguageId(translateLanguage.getLanguage().getId());
					text = translateLanguage.getText();
				}
			}
			if (StringUtils.isEmpty(text)) {
				if (languages.size() == 1) {
					TextTranslateLanguage translateLanguage = languages.get(0);
					if (translateLanguage.getLanguage() != null) {
						setLanguageId(translateLanguage.getLanguage().getId());
						text = translateLanguage.getText();
					}
				} else {
					for (TextTranslateLanguage translateLanguage : languages) {
						if (translateLanguage.getLanguage() != null
								&& translateLanguage.getLanguage().isDefaultLanguage()) {
							setLanguageId(translateLanguage.getLanguage().getId());
							text = translateLanguage.getText();
						}
					}
				}
			}
		}
		return text;
	}
}
