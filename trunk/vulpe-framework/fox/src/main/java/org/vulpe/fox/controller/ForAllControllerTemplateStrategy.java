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
package org.vulpe.fox.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.jelly.apt.TemplateBlock;
import net.sf.jelly.apt.TemplateException;
import net.sf.jelly.apt.TemplateModel;
import net.sf.jelly.apt.TemplateOutput;
import net.sf.jelly.apt.decorations.declaration.DecoratedClassDeclaration;

import org.apache.commons.lang.StringUtils;
import org.vulpe.commons.annotations.DetailConfig;
import org.vulpe.commons.helper.VulpeConfigHelper;
import org.vulpe.controller.commons.VulpeControllerConfig.ControllerType;
import org.vulpe.fox.VulpeForAllTemplateStrategy;
import org.vulpe.model.annotations.CodeGenerator;
import org.vulpe.model.entity.AbstractVulpeBaseEntityImpl;

import com.sun.mirror.declaration.FieldDeclaration;

public class ForAllControllerTemplateStrategy extends VulpeForAllTemplateStrategy {

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
			if (codeGenerator == null
					|| codeGenerator.controller().controllerType().equals(ControllerType.NONE)) {
				return false;
			}
			final DecoratedController controller = new DecoratedController();
			controller.setName(clazz.getSimpleName());
			controller.setPackageName(clazz.getPackage().toString());
			controller.setProjectPackageName(VulpeConfigHelper.getProjectPackage());
			controller.setServicePackageName(StringUtils.replace(clazz.getPackage().toString(),
					".entity", ".services"));
			controller.setControllerPackageName(StringUtils.replace(clazz.getPackage().toString(),
					".model.entity", ".controller"));
			controller.setModuleName(getModuleName(clazz));

			if (clazz.getSuperclass() != null
					&& !getClassName(clazz.getSuperclass()).equals(Object.class.getName())
					&& !getClassName(clazz.getSuperclass()).equals(
							AbstractVulpeBaseEntityImpl.class.getName())) {
				controller.setSuperclassName(getClassName(clazz.getSuperclass()));
				controller.setControllerSuperclassName(StringUtils.replace(
						controller.getSuperclassName(), ".model.entity", ".controller"));
			}

			final List<String> types = new ArrayList<String>();
			final ControllerType controllerType = codeGenerator.controller().controllerType();
			types.add(controllerType.toString());
			if (controllerType.equals(ControllerType.ALL)
					|| controllerType.equals(ControllerType.CRUD)) {
				final List<DecoratedControllerDetail> details = new ArrayList<DecoratedControllerDetail>();
				int count = codeGenerator.controller().detailsConfig().length;
				for (DetailConfig detailConfig : codeGenerator.controller().detailsConfig()) {
					final DecoratedControllerDetail detail = new DecoratedControllerDetail();
					if (count > 1) {
						detail.setNext(",");
					}
					if (detailConfig.despiseFields().length > 1) {
						final StringBuilder despiseFields = new StringBuilder();
						for (String despise : detailConfig.despiseFields()) {
							if (StringUtils.isBlank(despiseFields.toString())) {
								despiseFields.append("\"" + despise + "\"");
							} else {
								despiseFields.append(", \"" + despise + "\"");
							}
						}
						detail.setDespiseFields(despiseFields.toString());
					} else {
						detail.setDespiseFields(detailConfig.despiseFields()[0]);
					}

					detail.setNewDetails(detailConfig.newDetails());
					detail.setStartNewDetails(detailConfig.startNewDetails());
					detail.setName(detailConfig.name());
					detail.setParentDetailName(detailConfig.parentDetailName());
					detail.setPropertyName(detailConfig.propertyName());
					detail.setView(detailConfig.view());
					details.add(detail);
					count--;
				}
				if (!details.isEmpty()) {
					controller.setDetails(details);
				}

				if (controllerType.equals(ControllerType.ALL)
						|| controllerType.equals(ControllerType.SELECT)) {
					controller.setPageSize(codeGenerator.controller().pageSize());
				}
				if (controllerType.equals(ControllerType.ALL)
						|| controllerType.equals(ControllerType.TABULAR)) {
					final StringBuilder tabularDespise = new StringBuilder();
					if (codeGenerator.controller().tabularDespiseFields().length > 0) {
						for (String s : codeGenerator.controller().tabularDespiseFields()) {
							if (StringUtils.isBlank(tabularDespise.toString())) {
								tabularDespise.append("\"" + s + "\"");
							} else {
								tabularDespise.append(", \"" + s + "\"");
							}
						}
					}
					controller.setTabularDespiseFields(tabularDespise.toString());
					controller.setTabularStartNewDetails(codeGenerator.controller()
							.tabularStartNewDetails());
					controller.setTabularNewDetails(codeGenerator.controller().tabularNewDetails());
					controller.setTabularName(codeGenerator.controller().tabularName());
					controller.setTabularPropertyName(codeGenerator.controller()
							.tabularPropertyName());
				}
			}
			controller.setTypes(types);

			controller.setIdType(getIDType(clazz.getSuperclass()));
			if (controller.getIdType() == null) {
				final FieldDeclaration field = getField(clazz, "id");
				controller.setIdType(field.getType().toString());
			}

			prepareMethods(clazz, controller);

			model.setVariable(getVar(), controller);
			return true;
		}
		return false;
	}

	protected void prepareMethods(final DecoratedClassDeclaration clazz,
			final DecoratedController controller) {
		// prepare methods to controller
	}

}