package org.vulpe.fox.controller;

import java.io.Serializable;

@SuppressWarnings("serial")
public class DecoratedControllerParameter implements Serializable {

	private String name;
	private String type;

	public String getType() {
		return type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}
}