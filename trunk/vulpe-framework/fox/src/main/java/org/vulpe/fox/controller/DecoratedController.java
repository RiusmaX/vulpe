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
	private String packageName;
	private String servicePackageName;
	private String controllerPackageName;
	private String controllerSuperclassPackageName;
	private int pageSize = 5;
	private String tabularDespiseFields = "";
	private int tabularDetailNews = 1;
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

	public String getControllerPackageName() {
		return controllerPackageName;
	}

	public void setControllerPackageName(final String daoPackageName) {
		this.controllerPackageName = daoPackageName;
	}

	public String getControllerSuperclassSimpleName() {
		return StringUtils.substring(controllerSuperclassName, StringUtils
				.lastIndexOf(controllerSuperclassName, ".") + 1);
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

	public void setControllerSuperclassPackageName(
			final String daoSuperclassPackageName) {
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

	public void setTabularDetailNews(final int tabularDetailNews) {
		this.tabularDetailNews = tabularDetailNews;
	}

	public int getTabularDetailNews() {
		return tabularDetailNews;
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
}