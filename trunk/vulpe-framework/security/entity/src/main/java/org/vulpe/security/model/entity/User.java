/**
 * Vulpe Framework - Copyright (c) Active Thread
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.vulpe.security.model.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
import org.vulpe.model.annotations.Like;
import org.vulpe.model.annotations.NotExistEqual;
import org.vulpe.model.annotations.Parameter;
import org.vulpe.model.annotations.QueryParameter;
import org.vulpe.model.annotations.Parameter.OperatorType;

@Entity
@Table(name = "VulpeUser")
@SuppressWarnings("serial")
@NotExistEqual(parameters = { @QueryParameter(equals = @Parameter(name = "username", operator = OperatorType.EQUAL)) }, message = "vulpe.security.error.user.exists")
public class User extends BasicUser {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Like
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
		super();
		// default constructor
	}

	public User(final String username) {
		super();
		this.setUsername(username);
	}

	public User(final Long id, final String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public User(final String username, final String password, final boolean active, final List<UserRole> userRoles) {
		super();
		this.setUsername(username);
		this.setPassword(password);
		this.setActive(active);
		this.setUserRoles(userRoles);
	}

	public User(final String username, final String password, final String name, final String email,
			final List<UserRole> userRoles) {
		super();
		this.setUsername(username);
		this.setPassword(password);
		this.name = name;
		this.email = email;
		this.setActive(true);
		this.setUserRoles(userRoles);
	}

	public User(final String username, final String password, final String name, final List<UserRole> userRoles) {
		super();
		this.setUsername(username);
		this.setPassword(password);
		this.name = name;
		this.email = username + "@localhost";
		this.setActive(true);
		this.setUserRoles(userRoles);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		if (StringUtils.isNotEmpty(getUsername())) {
			return getUsername();
		}
		return super.toString();
	}
}
