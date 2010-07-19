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

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.vulpe.commons.helper.VulpeConfigHelper;
import org.vulpe.config.annotations.VulpeDomains;
import org.vulpe.model.entity.AbstractVulpeBaseEntity;

@Entity
@Table(name = "VulpeSecureResourceRole")
@SuppressWarnings("serial")
public class SecureResourceRole extends AbstractVulpeBaseEntity<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vulpeSecureResource", referencedColumnName = "id")
	private SecureResource secureResource;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "vulpeTole", referencedColumnName = "id")
	private Role role;

	public SecureResourceRole() {
		// default constructor
	}

	/**
	 *
	 * @param role
	 */
	public SecureResourceRole(final Role role) {
		this.role = role;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(final Role role) {
		this.role = role;
	}

	public SecureResource getSecureResource() {
		return secureResource;
	}

	public void setSecureResource(final SecureResource secureResource) {
		this.secureResource = secureResource;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String getOrderBy() {
		if (!VulpeConfigHelper.get(VulpeDomains.class).useDB4O()) {
			super.setOrderBy("obj.id");
		}
		return super.getOrderBy();
	}

}
