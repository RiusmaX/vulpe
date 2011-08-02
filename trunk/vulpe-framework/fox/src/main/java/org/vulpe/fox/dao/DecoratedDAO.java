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
package org.vulpe.fox.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.vulpe.fox.Decorated;

@SuppressWarnings("serial")
public class DecoratedDAO extends Decorated {

	private String name;
	private String idType;
	private String daoName;
	private String superclassName;
	private String daoSuperclassName;
	private String packageName;
	private String daoPackageName;
	private String daoSuperclassPackageName;
	private boolean inheritance;

	private List<DecoratedDAOMethod> methods;

	public List<DecoratedDAOMethod> getMethods() {
		if (methods == null) {
			methods = new ArrayList<DecoratedDAOMethod>();
		}
		return methods;
	}

	public void setMethods(final List<DecoratedDAOMethod> methods) {
		this.methods = methods;
	}

	public String getDaoName() {
		return daoName;
	}

	public void setDaoName(final String daoName) {
		this.daoName = daoName;
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

	public boolean isInheritance() {
		return inheritance;
	}

	public void setInheritance(final boolean inheritance) {
		this.inheritance = inheritance;
	}

	public String getSuperclassSimpleName() {
		return StringUtils.substring(superclassName, StringUtils.lastIndexOf(
				superclassName, ".") + 1);
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

	public String getDaoPackageName() {
		return daoPackageName;
	}

	public void setDaoPackageName(final String daoPackageName) {
		this.daoPackageName = daoPackageName;
	}

	public String getDaoSuperclassSimpleName() {
		return StringUtils.substring(daoSuperclassName, StringUtils
				.lastIndexOf(daoSuperclassName, ".") + 1);
	}

	public String getDaoSuperclassName() {
		return daoSuperclassName;
	}

	public void setDaoSuperclassName(final String daoSuperclassName) {
		this.daoSuperclassName = daoSuperclassName;
	}

	public String getDaoSuperclassPackageName() {
		return daoSuperclassPackageName;
	}

	public void setDaoSuperclassPackageName(final String daoSuperclassPackageName) {
		this.daoSuperclassPackageName = daoSuperclassPackageName;
	}
}