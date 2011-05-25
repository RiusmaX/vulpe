package org.jw.mmn.core.model.entity;

import org.vulpe.model.entity.impl.VulpeBaseDB4OEntity;
import org.vulpe.security.model.entity.User;

import org.jw.mmn.core.model.entity.Congregation;

@SuppressWarnings("serial")
public class CongregationUser extends VulpeBaseDB4OEntity<Long> {

	private Congregation congregation;

	private User user;

	public void setCongregation(Congregation congregation) {
		this.congregation = congregation;
	}

	public Congregation getCongregation() {
		return congregation;
	}

	public void setUser(User usuario) {
		this.user = usuario;
	}

	public User getUser() {
		return user;
	}

}
