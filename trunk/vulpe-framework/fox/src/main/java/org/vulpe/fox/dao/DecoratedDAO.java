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