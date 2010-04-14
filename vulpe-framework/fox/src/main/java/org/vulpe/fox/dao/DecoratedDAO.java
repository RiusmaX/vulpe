package org.vulpe.fox.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

@SuppressWarnings("serial")
public class DecoratedDAO implements Serializable {

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