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

import java.io.IOException;

import net.sf.jelly.apt.TemplateBlock;
import net.sf.jelly.apt.TemplateException;
import net.sf.jelly.apt.TemplateModel;
import net.sf.jelly.apt.TemplateOutput;
import net.sf.jelly.apt.decorations.declaration.DecoratedClassDeclaration;

import org.apache.commons.lang.StringUtils;
import org.vulpe.commons.VulpeConstants.Code;
import org.vulpe.fox.manager.DecoratedManager;
import org.vulpe.model.annotations.CodeGenerator;
import org.vulpe.model.entity.impl.AbstractVulpeBaseAuditEntity;
import org.vulpe.model.entity.impl.AbstractVulpeBaseEntity;
import org.vulpe.model.entity.impl.AbstractVulpeBaseJPAAuditEntity;
import org.vulpe.model.entity.impl.AbstractVulpeBaseJPAEntity;
import org.vulpe.model.entity.impl.VulpeBaseDB4OAuditEntity;
import org.vulpe.model.entity.impl.VulpeBaseDB4OEntity;
import org.vulpe.model.entity.impl.VulpeBaseSimpleEntity;

import com.sun.mirror.declaration.FieldDeclaration;

public class ForAllManagerTemplateStrategy extends VulpeForAllTemplateStrategy {

	private DecoratedManager manager;

	@Override
	public boolean preProcess(final TemplateBlock block, final TemplateOutput<TemplateBlock> output,
			final TemplateModel model) throws IOException, TemplateException {
		if (super.preProcess(block, output, model) && getDeclaration() instanceof DecoratedClassDeclaration) {
			final DecoratedClassDeclaration clazz = (DecoratedClassDeclaration) getDeclaration();
			return executePreProcess(clazz, block, output, model);
		}
		return false;
	}

	protected void prepareMethods(final DecoratedClassDeclaration clazz, final DecoratedManager dao) {
		// prepare methods for manager
	}

	private boolean executePreProcess(final DecoratedClassDeclaration clazz, final TemplateBlock block,
			final TemplateOutput<TemplateBlock> output, final TemplateModel model) throws IOException,
			TemplateException {
		if (getClassName(clazz.getSuperclass()).equals(VulpeBaseSimpleEntity.class.getName())) {
			return false;
		}
		final CodeGenerator codeGenerator = clazz.getAnnotation(CodeGenerator.class);
		if (codeGenerator == null || !codeGenerator.manager()) {
			return false;
		}
		manager = new DecoratedManager();
		manager.setOverride(codeGenerator.override());
		manager.setName(clazz.getSimpleName().concat(Code.Generator.MANAGER_SUFFIX));
		manager.setEntityName(clazz.getSimpleName());
		manager.setPackageName(clazz.getPackage().toString());
		manager.setDaoPackageName(StringUtils.replace(clazz.getPackage().toString(), Code.Generator.ENTITY_PACKAGE,
				Code.Generator.DAO_PACKAGE));
		manager.setManagerPackageName(StringUtils.replace(clazz.getPackage().toString(), Code.Generator.ENTITY_PACKAGE,
				Code.Generator.MANAGER_PACKAGE));
		manager.setModuleName(getModuleName(clazz));
		if (clazz.getAnnotation(javax.persistence.Inheritance.class) != null
				|| clazz.getAnnotation(org.vulpe.model.db4o.annotations.Inheritance.class) != null) {
			manager.setInheritance(true);
			manager.setManagerSuperclassPackageName(StringUtils.replace(clazz.getSuperclass().getDeclaration()
					.getPackage().toString(), Code.Generator.ENTITY_PACKAGE, Code.Generator.MANAGER_PACKAGE));
		}
		// if super class isn't Object
		if (clazz.getSuperclass() != null
				&& !getClassName(clazz.getSuperclass()).equals(Object.class.getName())
				&& (!getClassName(clazz.getSuperclass()).equals(AbstractVulpeBaseEntity.class.getName())
						&& (!getClassName(clazz.getSuperclass()).equals(AbstractVulpeBaseAuditEntity.class.getName()))
						&& !getClassName(clazz.getSuperclass()).equals(AbstractVulpeBaseJPAEntity.class.getName())
						&& !getClassName(clazz.getSuperclass()).equals(AbstractVulpeBaseJPAAuditEntity.class.getName())
						&& !getClassName(clazz.getSuperclass()).equals(VulpeBaseDB4OEntity.class.getName()) && !getClassName(
						clazz.getSuperclass()).equals(VulpeBaseDB4OAuditEntity.class.getName()))) {
			final String superClassName = getClassName(clazz.getSuperclass());
			final String simpleSuperClassName = superClassName.substring(superClassName.lastIndexOf(".") + 1);
			if (!simpleSuperClassName.startsWith(Code.Generator.ABSTRACT_PREFIX)) {
				manager.setSuperclassName(superClassName);
				manager.setManagerSuperclassName(StringUtils.replace(manager.getSuperclassName(),
						Code.Generator.ENTITY_PACKAGE, Code.Generator.MANAGER_PACKAGE)
						+ Code.Generator.MANAGER_SUFFIX);
			}
		}

		manager.setIdType(getIDType(clazz.getSuperclass()));
		if (manager.getIdType() == null) {
			final FieldDeclaration field = getField(clazz, "id");
			manager.setIdType(field.getType().toString());
		}

		prepareMethods(clazz, manager);

		model.setVariable(getVar(), manager);

		return true;
	}

	public DecoratedManager execute(final DecoratedClassDeclaration clazz, final TemplateBlock block,
			final TemplateOutput<TemplateBlock> output, final TemplateModel model) throws IOException,
			TemplateException {
		executePreProcess(clazz, block, output, model);
		return manager;
	}

}