/**
 * Vulpe Framework - Quick and Smart ;)
 * Copyright (C) 2011 Active Thread
 * 
 * Este programa é software livre; você pode redistribuí-lo e/ou
 * modificá-lo sob os termos da Licença Pública Geral GNU, conforme
 * publicada pela Free Software Foundation; tanto a versão 2 da
 * Licença como (a seu critério) qualquer versão mais nova.
 * 
 * Este programa é distribuído na expectativa de ser útil, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia implícita de
 * COMERCIALIZAÇÃO ou de ADEQUAÇÃO A QUALQUER PROPÓSITO EM
 * PARTICULAR. Consulte a Licença Pública Geral GNU para obter mais
 * detalhes.
 * 
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU
 * junto com este programa; se não, escreva para a Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307, USA.
 */
/**
 * Vulpe Framework - Quick and Smart ;)
 * Copyright (C) 2011 Active Thread
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.vulpe.fox.controller;

import java.util.ArrayList;
import java.util.List;

import org.vulpe.fox.Decorated;

@SuppressWarnings("serial")
public class DecoratedController extends Decorated {

	private String name;
	private String entityName;
	private String idType;
	private String moduleName;
	private String projectPackageName;
	private String packageName;
	private String servicePackageName;
	private String controllerPackageName;
	private int pageSize = 5;
	private String tabularDespiseFields = "";
	private int tabularNewRecords = 1;
	private int tabularStartNewRecords = 1;
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

	public void setTabularNewRecords(int tabularNewRecords) {
		this.tabularNewRecords = tabularNewRecords;
	}

	public int getTabularNewRecords() {
		return tabularNewRecords;
	}

	public void setTabularStartNewRecords(int tabularStartNewRecords) {
		this.tabularStartNewRecords = tabularStartNewRecords;
	}

	public int getTabularStartNewRecords() {
		return tabularStartNewRecords;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getEntityName() {
		return entityName;
	}
}