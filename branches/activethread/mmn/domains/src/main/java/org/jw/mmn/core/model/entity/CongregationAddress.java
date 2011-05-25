package org.jw.mmn.core.model.entity;

import org.jw.mmn.commons.model.entity.Address;
import org.vulpe.model.annotations.db4o.Inheritance;

@Inheritance
@SuppressWarnings("serial")
public class CongregationAddress extends Address {

	private Congregation congregation;

	public void setCongregation(Congregation congregation) {
		this.congregation = congregation;
	}

	public Congregation getCongregation() {
		return congregation;
	}

}
