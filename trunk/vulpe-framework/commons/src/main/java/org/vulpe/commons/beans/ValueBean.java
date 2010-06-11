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
package org.vulpe.commons.beans;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ValueBean implements Serializable{

	private String id;
	private String value;

	public ValueBean(){
		// default constructor
	}

	public ValueBean(final String id, final String value){
		this.id = id;
		this.value = value;
	}

	public String getId() {
		return id;
	}
	public void setId(final String id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(final String value) {
		this.value = value;
	}
}