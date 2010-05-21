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
package org.vulpe.model.entity.validate;

public class EntityAttributeValidate {

	private String name;

	private String label;

	private String identifier;

	private String description;

	private String type;

	private Integer size;

	private Integer minimumSize;

	private Integer maximumSize;

	public EntityAttributeValidate() {
		// default constructor
	}

	public EntityAttributeValidate(final String name, final String type, final Integer size) {
		this.name = name;
		this.type = type;
		this.size = size;
	}

	public EntityAttributeValidate(final String name, final String type) {
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(final Integer size) {
		this.size = size;
	}

	public Integer getMinimumSize() {
		return minimumSize;
	}

	public void setMinimumSize(final Integer minimumSize) {
		this.minimumSize = minimumSize;
	}

	public Integer getMaximumSize() {
		return maximumSize;
	}

	public void setMaximumSize(final Integer maximumSize) {
		this.maximumSize = maximumSize;
	}

	public String getType() {
		return type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return getName() + (getSize() == null ? "" : " - " + getSize());
	}

	public void setLabel(final String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setIdentifier(final String identifier) {
		this.identifier = identifier;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

}
