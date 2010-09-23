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
package org.vulpe.fox.manager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class DecoratedManager implements Serializable {

	private String name;
	private String entityName;
	private String idType;
	private String moduleName;
	private String packageName;
	private String daoPackageName;
	private String managerPackageName;
	private boolean inheritance;

	private List<DecoratedManagerMethod> methods;

	public List<DecoratedManagerMethod> getMethods() {
		if (methods == null) {
			methods = new ArrayList<DecoratedManagerMethod>();
		}
		return methods;
	}

	public void setMethods(final List<DecoratedManagerMethod> methods) {
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

	public String getIdType() {
		return idType;
	}

	public void setIdType(final String idType) {
		this.idType = idType;
	}

	public String getManagerPackageName() {
		return managerPackageName;
	}

	public void setManagerPackageName(final String daoPackageName) {
		this.managerPackageName = daoPackageName;
	}

	public void setModuleName(final String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setEntityName(final String entityName) {
		this.entityName = entityName;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setDaoPackageName(final String daoPackageName) {
		this.daoPackageName = daoPackageName;
	}

	public String getDaoPackageName() {
		return daoPackageName;
	}

	public void setInheritance(boolean inheritance) {
		this.inheritance = inheritance;
	}

	public boolean isInheritance() {
		return inheritance;
	}

}