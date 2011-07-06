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
package org.vulpe.controller.commons;

public class DuplicatedBean {

	private Object bean;

	private Integer rowNumber;

	public DuplicatedBean() {
		// default constructor
	}

	public DuplicatedBean(final Object bean, final Integer rowNumber) {
		this.bean = bean;
		this.rowNumber = rowNumber;
	}

	public Integer getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(final Integer rowNumber) {
		this.rowNumber = rowNumber;
	}

	public void setBean(final Object bean) {
		this.bean = bean;
	}

	public Object getBean() {
		return bean;
	}

}
