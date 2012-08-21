package org.vulpe.portal.commons.model.entity;

import org.vulpe.model.annotations.CachedEnum;

@CachedEnum
public enum Target {

	SELF("_self"), BLANK("_blank"), PARENT("_parent"), TOP("_top");

	private String value;

	private Target(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
