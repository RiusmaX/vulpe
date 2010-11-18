package org.vulpe.model.entity.impl;

import java.io.Serializable;

@SuppressWarnings({ "serial", "unchecked" })
public class VulpeBaseDB4OEntity<ID extends Serializable & Comparable> extends
		AbstractVulpeBaseEntity<ID> {

	private ID id;

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}
}
