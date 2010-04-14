package org.vulpe.fox.manager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

@SuppressWarnings("serial")
public class DecoratedManager implements Serializable {

	private String name;
	private String entityName;
	private String idType;
	private String moduleName;
	private String superclassName;
	private String managerSuperclassName;
	private String packageName;
	private String daoPackageName;
	private String managerPackageName;
	private String managerSuperclassPackageName;

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

	public String getManagerPackageName() {
		return managerPackageName;
	}

	public void setManagerPackageName(final String daoPackageName) {
		this.managerPackageName = daoPackageName;
	}

	public String getManagerSuperclassSimpleName() {
		return StringUtils.substring(managerSuperclassName, StringUtils
				.lastIndexOf(managerSuperclassName, ".") + 1);
	}

	public String getManagerSuperclassName() {
		return managerSuperclassName;
	}

	public void setManagerSuperclassName(final String daoSuperclassName) {
		this.managerSuperclassName = daoSuperclassName;
	}

	public String getManagerSuperclassPackageName() {
		return managerSuperclassPackageName;
	}

	public void setManagerSuperclassPackageName(final String daoSuperclassPackageName) {
		this.managerSuperclassPackageName = daoSuperclassPackageName;
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

}