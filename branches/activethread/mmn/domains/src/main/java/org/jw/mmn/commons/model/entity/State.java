package org.jw.mmn.commons.model.entity;

import org.jw.mmn.commons.model.entity.Country;
import org.vulpe.model.entity.impl.VulpeBaseDB4OEntity;

@SuppressWarnings("serial")
public class State extends VulpeBaseDB4OEntity<Long> {

	private String name;

	private String abbreviation;

	private Country country;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Country getCountry() {
		return country;
	}

}
