package org.jw.mmn.core.model.entity;

import org.vulpe.model.entity.impl.VulpeBaseDB4OEntity;

@SuppressWarnings("serial")
public class CharacteristicOratory extends VulpeBaseDB4OEntity<Long> {

	private Integer number;

	private String name;

	private String description;

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getNumber() {
		return number;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

}
