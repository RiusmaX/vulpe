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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
import org.vulpe.commons.VulpeConstants.Security;
import org.vulpe.commons.helper.VulpeConfigHelper;
import org.vulpe.config.annotations.VulpeDomains;
import org.vulpe.model.annotations.CachedClass;
import org.vulpe.model.annotations.Like;
import org.vulpe.model.entity.impl.AbstractVulpeBaseEntity;

@CachedClass
@Entity
@Table(name = "VulpeRole")
@SuppressWarnings("serial")
public class Role extends AbstractVulpeBaseEntity<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Like
	private String name;

	private transient String simpleName;

	@Like
	private String description;

	public Role() {
		// default constructor
	}

	public Role(final String name) {
		this.name = name;
	}

	public Role(final Long id, final String description) {
		this.id = id;
		this.description = description;
	}

	public Role(final String name, final String description) {
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setSimpleName(String simpleName) {
		if (StringUtils.isNotBlank(simpleName) && !simpleName.startsWith(Security.ROLE_PREFIX)) {
			setName(Security.ROLE_PREFIX + simpleName);
		}
		this.simpleName = simpleName;
	}

	public String getSimpleName() {
		if (StringUtils.isNotBlank(getName())) {
			simpleName = getName().replace(Security.ROLE_PREFIX, "");
		}
		return simpleName;
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
		if (StringUtils.isNotEmpty(getName())) {
			return getName();
		}
		return super.toString();
	}
}