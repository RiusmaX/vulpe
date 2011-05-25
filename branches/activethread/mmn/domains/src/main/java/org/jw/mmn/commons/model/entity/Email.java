package org.jw.mmn.commons.model.entity;

import org.vulpe.model.annotations.db4o.Inheritance;
import org.vulpe.model.entity.impl.VulpeBaseDB4OEntity;

@Inheritance
@SuppressWarnings("serial")
public class Email extends VulpeBaseDB4OEntity<Long> {

	private String email;

	private boolean principal;

	public void setPrincipal(boolean principal) {
		this.principal = principal;
	}

	public boolean isPrincipal() {
		return principal;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}
}
