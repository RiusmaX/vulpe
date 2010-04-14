package org.vulpe.security.model.entity;

import org.vulpe.model.entity.AbstractVulpeBaseEntityImpl;

@SuppressWarnings("serial")
public class UserRole extends AbstractVulpeBaseEntityImpl<Long> {

	private Long id;

	private User user;

	private Role role;

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(final Role role) {
		this.role = role;
	}

}
