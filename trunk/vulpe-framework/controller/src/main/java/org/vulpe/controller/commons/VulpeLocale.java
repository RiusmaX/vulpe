package org.vulpe.controller.commons;

import java.util.Locale;

import org.springframework.stereotype.Component;

@Component
public class VulpeLocale {

	private Locale locale;

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public Locale getLocale() {
		return locale;
	}
	
	
}
