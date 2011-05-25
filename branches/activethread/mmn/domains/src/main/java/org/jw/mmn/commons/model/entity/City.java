package org.jw.mmn.commons.model.entity;

import org.jw.mmn.commons.model.entity.State;
import org.vulpe.model.entity.impl.VulpeBaseDB4OEntity;

@SuppressWarnings("serial")
public class City extends VulpeBaseDB4OEntity<Long> {

	private String name;

	private String abbreviation;

	private State state;

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

	public void setState(State state) {
		this.state = state;
	}

	public State getState() {
		return state;
	}

}
