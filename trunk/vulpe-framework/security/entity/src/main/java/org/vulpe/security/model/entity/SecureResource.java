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
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.vulpe.commons.helper.VulpeConfigHelper;
import org.vulpe.config.annotations.VulpeDomains;
import org.vulpe.model.annotations.CachedClass;
import org.vulpe.model.annotations.SkipAutoFilter;
import org.vulpe.model.annotations.Like;
import org.vulpe.model.entity.impl.AbstractVulpeBaseEntity;

/**
 * Contains the information of a secured resource. They can be of two types. One
 * for filter invocation and another for method invocation.
 *
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 * @version 1.0
 * @since 1.0
 *
 */
@CachedClass
@Entity
@Table(name = "VulpeSecureResource")
@SuppressWarnings("serial")
public class SecureResource extends AbstractVulpeBaseEntity<Long> {

	/**
	 * Resource type function invocation
	 */
	public static final String RESOURCE_TYPE_FI = "FI";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * name of secured resource
	 */
	@Like
	private String resourceName;

	/**
	 * type of secured resource. One for function invocation and another for
	 * method invocation.
	 */
	@SkipAutoFilter
	private String type = RESOURCE_TYPE_FI;

	@OneToMany(targetEntity = SecureResourceRole.class, mappedBy = "secureResource", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@OrderBy("id")
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
	public SecureResource(final String resourceName, final List<SecureResourceRole> secureResourceRoles) {
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

	public List<SecureResourceRole> getSecureResourceRoles() {
		return secureResourceRoles;
	}

	public void setSecureResourceRoles(final List<SecureResourceRole> secureResourceRoles) {
		this.secureResourceRoles = secureResourceRoles;
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

	@Override
	public String toString() {
		if (StringUtils.isNotEmpty(getResourceName())) {
			return getResourceName();
		}
		return super.toString();
	}
}
