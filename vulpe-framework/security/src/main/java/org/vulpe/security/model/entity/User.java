package org.vulpe.security.model.entity;

import java.util.List;


@SuppressWarnings("serial")
public class User extends BasicUser {

	private String name;

	private String email;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public User() {
		// default constructor
	}

	public User(final String username, final String password, final boolean active, final List<UserRole> userRoles) {
		this.setUsername(username);
		this.setPassword(password);
		this.setActive(active);
		this.setUserRoles(userRoles);
	}
}
