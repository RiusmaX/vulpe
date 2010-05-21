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

import org.vulpe.model.entity.AbstractVulpeBaseEntityImpl;

@SuppressWarnings("serial")
public class SecureResourceRole extends AbstractVulpeBaseEntityImpl<Long> {

	private Long id;

	private SecureResource secureResource;

	private Role role;

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
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

}
