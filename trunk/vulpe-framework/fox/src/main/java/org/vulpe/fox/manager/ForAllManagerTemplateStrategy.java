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

import java.io.IOException;

import net.sf.jelly.apt.TemplateBlock;
import net.sf.jelly.apt.TemplateException;
import net.sf.jelly.apt.TemplateModel;
import net.sf.jelly.apt.TemplateOutput;
import net.sf.jelly.apt.decorations.declaration.DecoratedClassDeclaration;

import org.apache.commons.lang.StringUtils;
import org.vulpe.fox.VulpeForAllTemplateStrategy;
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

	@Override
	public boolean preProcess(final TemplateBlock block,
			final TemplateOutput<TemplateBlock> output, final TemplateModel model)
			throws IOException, TemplateException {
		if (super.preProcess(block, output, model)
				&& getDeclaration() instanceof DecoratedClassDeclaration) {
			final DecoratedClassDeclaration clazz = (DecoratedClassDeclaration) getDeclaration();
			if (getClassName(clazz.getSuperclass()).equals(VulpeBaseSimpleEntity.class.getName())) {
				return false;
			}
			final CodeGenerator codeGenerator = clazz.getAnnotation(CodeGenerator.class);
			if (codeGenerator == null || !codeGenerator.manager()) {
				return false;
			}
			final DecoratedManager manager = new DecoratedManager();
			manager.setName(clazz.getSimpleName().concat("Manager"));
			manager.setEntityName(clazz.getSimpleName());
			manager.setPackageName(clazz.getPackage().toString());
			manager.setDaoPackageName(StringUtils.replace(clazz.getPackage().toString(), ".entity",
					".dao"));
			manager.setManagerPackageName(StringUtils.replace(clazz.getPackage().toString(),
					".model.entity", ".model.manager"));
			manager.setModuleName(getModuleName(clazz));
			if (clazz.getAnnotation(javax.persistence.Inheritance.class) != null
					|| clazz.getAnnotation(org.vulpe.model.annotations.db4o.Inheritance.class) != null) {
				manager.setInheritance(true);
				manager.setManagerSuperclassPackageName(StringUtils.replace(clazz.getSuperclass()
						.getDeclaration().getPackage().toString(), ".entity", ".manager"));
			}
			// if super class isn't Object
			if (clazz.getSuperclass() != null
					&& !getClassName(clazz.getSuperclass()).equals(Object.class.getName())
					&& (!getClassName(clazz.getSuperclass()).equals(
							AbstractVulpeBaseEntity.class.getName())
							&& (!getClassName(clazz.getSuperclass()).equals(
									AbstractVulpeBaseAuditEntity.class.getName()))
							&& !getClassName(clazz.getSuperclass()).equals(
									AbstractVulpeBaseJPAEntity.class.getName())
							&& !getClassName(clazz.getSuperclass()).equals(
									AbstractVulpeBaseJPAAuditEntity.class.getName())
							&& !getClassName(clazz.getSuperclass()).equals(
									VulpeBaseDB4OEntity.class.getName()) && !getClassName(
							clazz.getSuperclass()).equals(VulpeBaseDB4OAuditEntity.class.getName()))) {
				manager.setSuperclassName(getClassName(clazz.getSuperclass()));
				manager.setManagerSuperclassName(StringUtils.replace(manager.getSuperclassName(), ".model.entity",
						".model.manager")
						+ "Manager");
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
		return false;
	}

	protected void prepareMethods(final DecoratedClassDeclaration clazz, final DecoratedManager dao) {
		// prepare methods for manager
	}

}