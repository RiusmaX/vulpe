package org.vulpe.security.model.entity;

import org.vulpe.model.annotations.AutoComplete;
import org.vulpe.model.entity.AbstractVulpeBaseEntityImpl;

@SuppressWarnings("serial")
public class Role extends AbstractVulpeBaseEntityImpl<Long> {

	private Long id;

	private String name;

	@AutoComplete
	private String description;

	public Role() {
		// default constructor
	}

	public Role(final String name) {
		this.name = name;
	}

	public Role(final String name, final String description) {
		this.name = name;
		this.description = description;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

}
