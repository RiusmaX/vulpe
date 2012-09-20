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
package org.vulpe.model.services.impl.ws;

/**
 * 
 * @author <a href="mailto:fabio.viana@vulpe.org">F�bio Viana</a>
 */
public class XMLType {
	private String type;
	private String typeClass;

	public XMLType(final String type, final String typeClass) {
		this.type = type;
		this.typeClass = typeClass;
	}

	public String getType() {
		return type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	public String getTypeClass() {
		return typeClass;
	}

	public void setTypeClass(final String typeClass) {
		this.typeClass = typeClass;
	}
}