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
public class DecoratedViewDetail implements Serializable {

	private String name;
	private String popupProperties;
	private List<DecoratedViewField> fields;

	public void setName(final String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setPopupProperties(final String popupProperties) {
		this.popupProperties = popupProperties;
	}

	public String getPopupProperties() {
		return popupProperties;
	}

	public void setFields(final List<DecoratedViewField> fields) {
		this.fields = fields;
	}

	public List<DecoratedViewField> getFields() {
		return fields;
	}

}