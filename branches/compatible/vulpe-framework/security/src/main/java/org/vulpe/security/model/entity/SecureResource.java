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

import org.apache.commons.lang.builder.CompareToBuilder;
import org.vulpe.model.annotations.CachedClass;
import org.vulpe.model.entity.AbstractVulpeBaseEntityImpl;
import org.vulpe.security.commons.VulpeSecurityConstants;

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
	private String type = VulpeSecurityConstants.RESOURCE_TYPE_FI;

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

	/**
	 * Creates a SecureResource with <code>resourceName</code> and
	 * <code>secureResourceRoles</code> params.
	 * 
	 * @param resourceName
	 *            secured resource. It can be URL or method name.
	 * @param secureResourceRoles
	 *            roles to grant access
	 */
	public SecureResource(final String resourceName,
			final List<SecureResourceRole> secureResourceRoles) {
		this.resourceName = resourceName;
		this.secureResourceRoles = secureResourceRoles;
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

	public void setSecureResourceRoles(final List<SecureResourceRole> secureResourceRoles) {
		this.secureResourceRoles = secureResourceRoles;
	}
}
