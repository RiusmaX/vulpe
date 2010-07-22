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
package org.vulpe.fox.view;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class DecoratedView implements Serializable {

	private String name;
	private String projectName;
	private String moduleName;
	private String popupProperties;
	private int columnSpan;
	private List<DecoratedViewDetail> details;
	private List<String> types;
	private List<DecoratedViewField> arguments;
	private List<DecoratedViewField> items;
	private List<DecoratedViewField> fields;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(final String projectName) {
		this.projectName = projectName;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(final String moduleName) {
		this.moduleName = moduleName;
	}

	public void setFields(final List<DecoratedViewField> fields) {
		this.fields = fields;
	}

	public List<DecoratedViewField> getFields() {
		return fields;
	}

	public void setTypes(final List<String> types) {
		this.types = types;
	}

	public List<String> getTypes() {
		return types;
	}

	public void setItems(final List<DecoratedViewField> items) {
		this.items = items;
	}

	public List<DecoratedViewField> getItems() {
		return items;
	}

	public void setArguments(final List<DecoratedViewField> arguments) {
		this.arguments = arguments;
	}

	public List<DecoratedViewField> getArguments() {
		return arguments;
	}

	public void setColumnSpan(final int columnSpan) {
		this.columnSpan = columnSpan;
	}

	public int getColumnSpan() {
		return columnSpan;
	}

	public void setPopupProperties(final String popupProperties) {
		this.popupProperties = popupProperties;
	}

	public String getPopupProperties() {
		return popupProperties;
	}

	public void setDetails(final List<DecoratedViewDetail> details) {
		this.details = details;
	}

	public List<DecoratedViewDetail> getDetails() {
		return details;
	}

}