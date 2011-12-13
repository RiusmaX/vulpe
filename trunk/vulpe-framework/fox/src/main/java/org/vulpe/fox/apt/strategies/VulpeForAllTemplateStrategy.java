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
package org.vulpe.fox.apt.strategies;

import javax.persistence.QueryHint;

import net.sf.jelly.apt.TemplateBlock;
import net.sf.jelly.apt.decorations.declaration.DecoratedClassDeclaration;
import net.sf.jelly.apt.strategies.TemplateBlockStrategy;

import org.apache.commons.lang.StringUtils;

import com.sun.mirror.declaration.ClassDeclaration;
import com.sun.mirror.declaration.FieldDeclaration;
import com.sun.mirror.type.ClassType;
import com.sun.mirror.type.InterfaceType;

public class VulpeForAllTemplateStrategy extends TemplateBlockStrategy<TemplateBlock> {
	private String var;
	private Object declaration;

	public String getVar() {
		return var;
	}

	public void setVar(final String var) {
		this.var = var;
	}

	public Object getDeclaration() {
		return declaration;
	}

	public void setDeclaration(final Object declaration) {
		this.declaration = declaration;
	}

	protected String getModuleName(final DecoratedClassDeclaration clazz) {
		final String className = getClassName(clazz);
		final String classSimpleName = clazz.getSimpleName();
		final String moduleName = className.substring(0, className.indexOf(".model.entity."
				.concat(classSimpleName)));
		return moduleName.substring(moduleName.lastIndexOf(".") + 1);
	}

	protected FieldDeclaration getField(final DecoratedClassDeclaration clazz, final String param) {
		clazz.getFields();
		for (FieldDeclaration field : clazz.getFields()) {
			if (field.getSimpleName().equals(param)) {
				return field;
			}
		}
		if (clazz.getSuperclass() != null
				&& !clazz.getSuperclass().toString().equals(Object.class.getName())
				&& clazz.getSuperclass().getDeclaration() != null) {
			return getField(clazz.getSuperclass().getDeclaration(), param);
		}
		return null;
	}

	protected FieldDeclaration getField(final ClassDeclaration clazz, final String param) {
		for (FieldDeclaration field : clazz.getFields()) {
			if (field.getSimpleName().equals(param)) {
				return field;
			}
		}
		if (clazz.getSuperclass() != null
				&& !clazz.getSuperclass().toString().equals(Object.class.getName())
				&& clazz.getSuperclass().getDeclaration() != null) {
			return getField(clazz.getSuperclass().getDeclaration(), param);
		}
		return null;
	}

	protected String getType(final String paramName, final QueryHint[] hints) {
		return findQueryHint(":".concat(paramName), hints);
	}

	protected boolean isReturnEntity(final QueryHint[] hints) {
		return "entity".equals(findQueryHint("return", hints));
	}

	protected String findQueryHint(final String name, final QueryHint[] hints) {
		for (int i = 0; i < hints.length; i++) {
			if (hints[i].name().equals(name)) {
				return hints[i].value();
			}
		}
		return null;
	}

	public boolean isInstanceOf(final InterfaceType clazz, final String classSuper) {
		if (clazz == null || clazz.toString().equals(Object.class.getName())) {
			return false;
		}

		if (getClassName(clazz).equals(classSuper)) {
			return true;
		} else if (clazz.getSuperinterfaces() != null) {
			for (InterfaceType i : clazz.getSuperinterfaces()) {
				if (isInstanceOf(i, classSuper)) {
					return true;
				}
			}
		}

		return false;
	}

	public boolean isInstanceOf(final DecoratedClassDeclaration clazz, final String classSuper) {
		if (clazz == null || clazz.getSimpleName().equals(Object.class.getName())) {
			return false;
		}

		if (clazz.getSimpleName().equals(classSuper)) {
			return true;
		} else if (clazz.getSuperinterfaces() != null) {
			for (InterfaceType i : clazz.getSuperinterfaces()) {
				if (isInstanceOf(i, classSuper)) {
					return true;
				}
			}
		}

		if (isInstanceOf(clazz.getSuperclass(), classSuper)) {
			return true;
		}

		return false;
	}

	public boolean isInstanceOf(final ClassType clazz, final String classSuper) {
		if (clazz == null || clazz.toString().equals(Object.class.getName())) {
			return false;
		}

		if (getClassName(clazz).equals(classSuper)) {
			return true;
		} else if (clazz.getSuperinterfaces() != null) {
			for (InterfaceType i : clazz.getSuperinterfaces()) {
				if (isInstanceOf(i, classSuper)) {
					return true;
				}
			}
		}

		if (isInstanceOf(clazz.getSuperclass(), classSuper)) {
			return true;
		}

		return false;
	}

	protected String getClassName(final Object clazz) {
		if (StringUtils.indexOf(clazz.toString(), "<") > -1) {
			return StringUtils.substring(clazz.toString(), 0, StringUtils.indexOf(clazz.toString(),
					"<"));
		}
		return clazz.toString();
	}

	protected String getIDType(final Object clazz) {
		if (StringUtils.indexOf(clazz.toString(), "<") > -1) {
			return StringUtils.substring(clazz.toString(), StringUtils.indexOf(clazz.toString(),
					"<") + 1, StringUtils.indexOf(clazz.toString(), ">"));
		}
		return "java.lang.Long";
	}
}