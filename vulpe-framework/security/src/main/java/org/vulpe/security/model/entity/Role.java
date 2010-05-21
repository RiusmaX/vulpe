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

import org.vulpe.model.annotations.AutoComplete;
import org.vulpe.model.annotations.db4o.Like;
import org.vulpe.model.entity.AbstractVulpeBaseEntityImpl;

@SuppressWarnings("serial")
public class Role extends AbstractVulpeBaseEntityImpl<Long> {

	private Long id;

	@Like
	private String name;

	@Like
	@AutoComplete
	private String description;

	public Role() {
		// default constructor
	}

	public Role(final String name) {
		this.name = name;
	}

	public Role(final String name, final String description) {
		this.name = name;
		this.description = description;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
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

}
