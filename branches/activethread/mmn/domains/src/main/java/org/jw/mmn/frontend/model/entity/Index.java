package org.jw.mmn.frontend.model.entity;

import org.vulpe.model.entity.impl.VulpeBaseSimpleEntity;

import org.jw.mmn.core.model.entity.Congregation;

@SuppressWarnings("serial")
public class Index extends VulpeBaseSimpleEntity {

	private Congregation congregation;

	public void setCongregation(Congregation congregation) {
		this.congregation = congregation;
	}

	public Congregation getCongregation() {
		return congregation;
	}

}
