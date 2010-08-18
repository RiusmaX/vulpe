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
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

@SuppressWarnings("serial")
public class DecoratedController implements Serializable {

	private String name;
	private String idType;
	private String moduleName;
	private String superclassName;
	private String controllerSuperclassName;
	private String projectPackageName;
	private String packageName;
	private String servicePackageName;
	private String controllerPackageName;
	private String controllerSuperclassPackageName;
	private int pageSize = 5;
	private String tabularDespiseFields = "";
	private int tabularNewDetails = 1;
	private int tabularStartNewDetails = 1;
	private String tabularName = "";
	private String tabularPropertyName = "";
	private List<String> types;
	private List<DecoratedControllerDetail> details;

	private List<DecoratedControllerMethod> methods;

	public List<DecoratedControllerMethod> getMethods() {
		if (methods == null) {
			methods = new ArrayList<DecoratedControllerMethod>();
		}
		return methods;
	}

	public void setMethods(final List<DecoratedControllerMethod> methods) {
		this.methods = methods;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(final String packageName) {
		this.packageName = packageName;
	}

	public String getSuperclassSimpleName() {
		return StringUtils.substring(superclassName,
				StringUtils.lastIndexOf(superclassName, ".") + 1);
	}

	public String getSuperclassName() {
		return superclassName;
	}

	public void setSuperclassName(final String superclassName) {
		this.superclassName = superclassName;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(final String idType) {
		this.idType = idType;
	}

	public String getControllerPackageName() {
		return controllerPackageName;
	}

	public void setControllerPackageName(final String daoPackageName) {
		this.controllerPackageName = daoPackageName;
	}

	public String getControllerSuperclassSimpleName() {
		return StringUtils.substring(controllerSuperclassName, StringUtils.lastIndexOf(
				controllerSuperclassName, ".") + 1);
	}

	public String getControllerSuperclassName() {
		return controllerSuperclassName;
	}

	public void setControllerSuperclassName(final String daoSuperclassName) {
		this.controllerSuperclassName = daoSuperclassName;
	}

	public String getControllerSuperclassPackageName() {
		return controllerSuperclassPackageName;
	}

	public void setControllerSuperclassPackageName(final String daoSuperclassPackageName) {
		this.controllerSuperclassPackageName = daoSuperclassPackageName;
	}

	public void setTypes(final List<String> types) {
		this.types = types;
	}

	public List<String> getTypes() {
		return types;
	}

	public void setModuleName(final String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setPageSize(final int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setTabularName(final String tabularName) {
		this.tabularName = tabularName;
	}

	public String getTabularName() {
		return tabularName;
	}

	public void setTabularDespiseFields(final String tabularDespiseFields) {
		this.tabularDespiseFields = tabularDespiseFields;
	}

	public String getTabularDespiseFields() {
		return tabularDespiseFields;
	}

	public void setTabularPropertyName(final String tabularPropertyName) {
		this.tabularPropertyName = tabularPropertyName;
	}

	public String getTabularPropertyName() {
		return tabularPropertyName;
	}

	public void setServicePackageName(final String servicePackageName) {
		this.servicePackageName = servicePackageName;
	}

	public String getServicePackageName() {
		return servicePackageName;
	}

	public void setDetails(final List<DecoratedControllerDetail> details) {
		this.details = details;
	}

	public List<DecoratedControllerDetail> getDetails() {
		return details;
	}

	public void setProjectPackageName(String projectPackageName) {
		this.projectPackageName = projectPackageName;
	}

	public String getProjectPackageName() {
		return projectPackageName;
	}

	public void setTabularNewDetails(int tabularNewDetails) {
		this.tabularNewDetails = tabularNewDetails;
	}

	public int getTabularNewDetails() {
		return tabularNewDetails;
	}

	public void setTabularStartNewDetails(int tabularStartNewDetails) {
		this.tabularStartNewDetails = tabularStartNewDetails;
	}

	public int getTabularStartNewDetails() {
		return tabularStartNewDetails;
	}
}