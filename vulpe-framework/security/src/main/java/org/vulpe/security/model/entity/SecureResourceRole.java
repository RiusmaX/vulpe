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
