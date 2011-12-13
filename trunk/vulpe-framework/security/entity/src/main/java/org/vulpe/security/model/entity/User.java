/**
 * Vulpe Framework - Quick and Smart ;)
 * Copyright (C) 2011 Active Thread
 * 
 * Este programa é software livre; você pode redistribuí-lo e/ou
 * modificá-lo sob os termos da Licença Pública Geral GNU, conforme
 * publicada pela Free Software Foundation; tanto a versão 2 da
 * Licença como (a seu critério) qualquer versão mais nova.
 * 
 * Este programa é distribuído na expectativa de ser útil, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia implícita de
 * COMERCIALIZAÇÃO ou de ADEQUAÇÃO A QUALQUER PROPÓSITO EM
 * PARTICULAR. Consulte a Licença Pública Geral GNU para obter mais
 * detalhes.
 * 
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU
 * junto com este programa; se não, escreva para a Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307, USA.
 */
/**
 * Vulpe Framework - Quick and Smart ;)
 * Copyright (C) 2011 Active Thread
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.vulpe.security.model.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.QueryHint;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
import org.vulpe.model.annotations.Like;
import org.vulpe.model.annotations.NotExistEquals;
import org.vulpe.model.annotations.Parameter;
import org.vulpe.model.annotations.QueryParameter;
import org.vulpe.model.annotations.Parameter.OperatorType;

@NamedQuery(name = "User.getUsersByRole", query = "select new User(obj.username, obj.name, obj.email) from User obj inner join obj.userRoles userRole where userRole.role.name = :name", hints = @QueryHint(name = "return", value = "java.util.List<org.vulpe.security.model.entity.User>"))
@Entity
@Table(name = "VulpeUser")
@SuppressWarnings("serial")
@NotExistEquals(parameters = { @QueryParameter(equals = @Parameter(name = "username", operator = OperatorType.EQUAL)) }, message = "{vulpe.security.error.user.exists}")
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

	public User(final Long id) {
		super();
		this.id = id;
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

	public User(final String username, final String password, final boolean active,
			final List<UserRole> userRoles) {
		super();
		this.setUsername(username);
		this.setPassword(password);
		this.setActive(active);
		this.setUserRoles(userRoles);
	}

	public User(final String username, final String password, final String name,
			final String email, final List<UserRole> userRoles) {
		super();
		this.setUsername(username);
		this.setPassword(password);
		this.name = name;
		this.email = email;
		this.setActive(true);
		this.setUserRoles(userRoles);
	}

	public User(final String username, final String password, final String name,
			final List<UserRole> userRoles) {
		super();
		this.setUsername(username);
		this.setPassword(password);
		this.name = name;
		this.email = username + "@localhost";
		this.setActive(true);
		this.setUserRoles(userRoles);
	}

	public User(final String username, final String name, final String email) {
		super();
		this.setUsername(username);
		this.name = name;
		this.email = email;
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
