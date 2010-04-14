/**
 *
 */
package org.vulpe.security.model.entity;

import java.util.List;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.vulpe.model.annotations.CachedClass;
import org.vulpe.model.entity.AbstractVulpeBaseEntityImpl;
import org.vulpe.security.authentication.SecurityConstants;

/**
 * Contains the information of a secured resource. They can be of two types. One
 * for filter invocation and another for method invocation.
 *
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 * @version 1.0
 * @since 1.0
 *
 */
@CachedClass
@SuppressWarnings("serial")
public class SecureResource extends AbstractVulpeBaseEntityImpl<Long> {

	private Long id;

	/**
	 * name of secured resource
	 */
	private String resourceName;

	/**
	 * type of secured resource. One for function invocation and another for
	 * method invocation.
	 */
	private String type = SecurityConstants.RESOURCE_TYPE_FI;

	private List<SecureResourceRole> secureResourceRoles;

	/**
	 * Returns the resourceName
	 *
	 * @return String
	 */
	public String getResourceName() {
		return resourceName;
	}

	/**
	 * Sets the resourceName
	 *
	 * @param resourceName
	 *            The resourceName to set.
	 */
	public void setResourceName(final String resourceName) {
		this.resourceName = resourceName;
	}

	/**
	 * Returns the type
	 *
	 * @return String
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type
	 *
	 * @param type
	 *            The type to set.
	 */
	public void setType(final String type) {
		this.type = type;
	}

	/**
	 * Creates a SecureResource with <code>resourceName</code> and
	 * <code>type</code> params.
	 *
	 * @param resourceName
	 *            secured resource. It can be URL or method name.
	 * @param type
	 *            there can be two types. "FI" for filter invocation and "MI"
	 *            for method invocation.
	 */
	public SecureResource(final String resourceName, final String type) {
		this.resourceName = resourceName;
		this.type = type;
	}

	public SecureResource() {
		// default constructor
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(final SecureResource o) {
		return CompareToBuilder.reflectionCompare(this, o);
	}

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public List<SecureResourceRole> getSecureResourceRoles() {
		return secureResourceRoles;
	}

	public void setSecureResourceRoles(
			final List<SecureResourceRole> secureResourceRoles) {
		this.secureResourceRoles = secureResourceRoles;
	}
}
