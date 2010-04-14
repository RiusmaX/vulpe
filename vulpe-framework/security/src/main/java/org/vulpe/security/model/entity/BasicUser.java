package org.vulpe.security.model.entity;

import java.util.List;

import org.vulpe.model.entity.AbstractVulpeBaseEntityImpl;


@SuppressWarnings("serial")
public class BasicUser extends AbstractVulpeBaseEntityImpl<Long> {

	private Long id;

	private String username;

	private String password;

	private transient String passwordConfirm;

	private boolean active = true;

	private List<UserRole> userRoles;

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(final boolean active) {
		this.active = active;
	}

	public List<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(final List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(final String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}
}
