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

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

import org.apache.commons.lang.StringUtils;
import org.vulpe.commons.helper.VulpeConfigHelper;
import org.vulpe.commons.util.VulpeDigestUtil;
import org.vulpe.config.annotations.VulpeDomains;
import org.vulpe.model.entity.impl.AbstractVulpeBaseEntity;

@MappedSuperclass
@SuppressWarnings("serial")
public abstract class BasicUser extends AbstractVulpeBaseEntity<Long> {

	private String username;

	private String password;

	private transient String currentPassword;

	private transient String passwordConfirm;

	private boolean active = true;

	@OneToMany(targetEntity = UserRole.class, mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<UserRole> userRoles;

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
		if (StringUtils.isNotEmpty(password)) {
			this.password = VulpeDigestUtil.encrypt(password, "md5");
		}
	}

	public void setPasswordEncrypted(final String password) {
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
		if (StringUtils.isNotEmpty(passwordConfirm)) {
			this.passwordConfirm = VulpeDigestUtil.encrypt(passwordConfirm, "md5");
		}
	}

	public void setPasswordConfirmEncrypted(final String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	@Override
	public String getOrderBy() {
		if (!VulpeConfigHelper.get(VulpeDomains.class).useDB4O()) {
			super.setOrderBy("obj.id");
		}
		return super.getOrderBy();
	}

	public void setCurrentPassword(String currentPassword) {
		if (StringUtils.isNotEmpty(currentPassword)) {
			this.currentPassword = VulpeDigestUtil.encrypt(currentPassword, "md5");
		}
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

}
