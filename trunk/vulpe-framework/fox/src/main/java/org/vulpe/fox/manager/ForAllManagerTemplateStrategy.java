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
import org.vulpe.model.entity.AbstractVulpeBaseEntityImpl;

import com.sun.mirror.declaration.FieldDeclaration;

public class ForAllManagerTemplateStrategy extends VulpeForAllTemplateStrategy {

	@Override
	public boolean preProcess(final TemplateBlock block,
			final TemplateOutput<TemplateBlock> output, final TemplateModel model)
			throws IOException, TemplateException {
		if (super.preProcess(block, output, model)
				&& getDeclaration() instanceof DecoratedClassDeclaration) {
			final DecoratedClassDeclaration clazz = (DecoratedClassDeclaration) getDeclaration();
			if (getClassName(clazz.getSuperclass()).equals(
					"org.vulpe.model.entity.VulpeBaseSimpleEntity")) {
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

			if (clazz.getSuperclass() != null
					&& !getClassName(clazz.getSuperclass()).equals(Object.class.getName())
					&& !getClassName(clazz.getSuperclass()).equals(
							AbstractVulpeBaseEntityImpl.class.getName())) {
				manager.setSuperclassName(getClassName(clazz.getSuperclass()));
				manager.setManagerSuperclassName(StringUtils.replace(manager.getSuperclassName(),
						".model.entity", ".model.manager"));
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