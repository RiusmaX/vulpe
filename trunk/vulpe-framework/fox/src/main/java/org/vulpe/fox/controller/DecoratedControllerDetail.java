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
package org.vulpe.fox.controller;

import java.io.Serializable;

@SuppressWarnings("serial")
public class DecoratedControllerDetail implements Serializable {

	private String name;
	private int detailNews;
	private String despiseFields;
	private String view;
	private String propertyName;
	private String parentDetailName;
	private String next;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public int getDetailNews() {
		return detailNews;
	}

	public void setDetailNews(final int detailNews) {
		this.detailNews = detailNews;
	}

	public String getDespiseFields() {
		return despiseFields;
	}

	public void setDespiseFields(final String despiseFields) {
		this.despiseFields = despiseFields;
	}

	public String getView() {
		return view;
	}

	public void setView(final String view) {
		this.view = view;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(final String propertyName) {
		this.propertyName = propertyName;
	}

	public String getParentDetailName() {
		return parentDetailName;
	}

	public void setParentDetailName(final String parentDetailName) {
		this.parentDetailName = parentDetailName;
	}

	public void setNext(final String next) {
		this.next = next;
	}

	public String getNext() {
		return next;
	}
}