package org.jw.mmn.commons.model.entity;

import org.jw.mmn.commons.model.entity.PhoneType;
import org.vulpe.model.annotations.db4o.Inheritance;
import org.vulpe.model.entity.impl.VulpeBaseDB4OEntity;

@Inheritance
@SuppressWarnings("serial")
public class Phone extends VulpeBaseDB4OEntity<Long> {

	private String number;

	private PhoneType type;

	private boolean principal;

	public void setNumber(String number) {
		this.number = number;
	}

	public String getNumber() {
		return number;
	}

	public void setType(PhoneType type) {
		this.type = type;
	}

	public PhoneType getType() {
		return type;
	}

	public void setPrincipal(boolean principal) {
		this.principal = principal;
	}

	public boolean isPrincipal() {
		return principal;
	}

}
