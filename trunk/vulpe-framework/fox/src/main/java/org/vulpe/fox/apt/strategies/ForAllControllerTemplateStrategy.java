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
package org.vulpe.fox.apt.strategies;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.jelly.apt.TemplateBlock;
import net.sf.jelly.apt.TemplateException;
import net.sf.jelly.apt.TemplateModel;
import net.sf.jelly.apt.TemplateOutput;
import net.sf.jelly.apt.decorations.declaration.DecoratedClassDeclaration;

import org.apache.commons.lang.StringUtils;
import org.vulpe.commons.VulpeConstants.Code;
import org.vulpe.commons.annotations.DetailConfig;
import org.vulpe.commons.helper.VulpeConfigHelper;
import org.vulpe.controller.commons.VulpeControllerConfig.ControllerType;
import org.vulpe.fox.controller.DecoratedController;
import org.vulpe.fox.controller.DecoratedControllerDetail;
import org.vulpe.model.annotations.CodeGenerator;
import org.vulpe.model.entity.impl.VulpeBaseSimpleEntity;

import com.sun.mirror.declaration.FieldDeclaration;

public class ForAllControllerTemplateStrategy extends VulpeForAllTemplateStrategy {

	private DecoratedController controller;

	@Override
	public boolean preProcess(final TemplateBlock block, final TemplateOutput<TemplateBlock> output,
			final TemplateModel model) throws IOException, TemplateException {
		if (super.preProcess(block, output, model) && getDeclaration() instanceof DecoratedClassDeclaration) {
			final DecoratedClassDeclaration clazz = (DecoratedClassDeclaration) getDeclaration();
			return executePreProcess(clazz, block, output, model);
		}
		return false;
	}

	protected void prepareMethods(final DecoratedClassDeclaration clazz, final DecoratedController controller) {
		// prepare methods to controller
	}

	private boolean executePreProcess(final DecoratedClassDeclaration clazz, final TemplateBlock block,
			final TemplateOutput<TemplateBlock> output, final TemplateModel model) throws IOException,
			TemplateException {
		if (getClassName(clazz.getSuperclass()).equals(VulpeBaseSimpleEntity.class.getName())) {
			return false;
		}
		final CodeGenerator codeGenerator = clazz.getAnnotation(CodeGenerator.class);
		if (codeGenerator == null || codeGenerator.controller().type().equals(ControllerType.NONE)) {
			return false;
		}
		controller = new DecoratedController();
		controller.setOverride(codeGenerator.override());
		controller.setName(StringUtils.isNotEmpty(codeGenerator.baseName()) ? codeGenerator.baseName() : clazz
				.getSimpleName());
		controller.setEntityName(clazz.getSimpleName());
		controller.setPackageName(clazz.getPackage().toString());
		controller.setProjectPackageName(VulpeConfigHelper.getProjectPackage());
		controller.setServicePackageName(StringUtils.replace(clazz.getPackage().toString(),
				Code.Generator.ENTITY_PACKAGE, Code.Generator.SERVICE_PACKAGE));
		controller.setControllerPackageName(StringUtils.replace(clazz.getPackage().toString(),
				Code.Generator.ENTITY_PACKAGE, Code.Generator.CONTROLLER_PACKAGE));
		controller.setModuleName(getModuleName(clazz));

		final List<String> types = new ArrayList<String>();
		final ControllerType controllerType = codeGenerator.controller().type();
		types.add(controllerType.toString());
		if (controllerType.equals(ControllerType.ALL) || controllerType.equals(ControllerType.MAIN)) {
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

			if (controllerType.equals(ControllerType.ALL) || controllerType.equals(ControllerType.SELECT)) {
				controller.setPageSize(codeGenerator.controller().select().pageSize());
			}
			if (controllerType.equals(ControllerType.ALL) || controllerType.equals(ControllerType.TABULAR)) {
				final StringBuilder tabularDespise = new StringBuilder();
				if (codeGenerator.controller().tabular().despiseFields().length > 0) {
					for (String s : codeGenerator.controller().tabular().despiseFields()) {
						if (StringUtils.isBlank(tabularDespise.toString())) {
							tabularDespise.append("\"" + s + "\"");
						} else {
							tabularDespise.append(", \"" + s + "\"");
						}
					}
				}
				controller.setTabularDespiseFields(tabularDespise.toString());
				controller.setTabularStartNewRecords(codeGenerator.controller().tabular().startNewRecords());
				controller.setTabularNewRecords(codeGenerator.controller().tabular().newRecords());
				controller.setTabularName(codeGenerator.controller().tabular().name());
				controller.setTabularPropertyName(codeGenerator.controller().tabular().propertyName());
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

	public DecoratedController execute(final DecoratedClassDeclaration clazz, final TemplateBlock block,
			final TemplateOutput<TemplateBlock> output, final TemplateModel model) throws IOException,
			TemplateException {
		executePreProcess(clazz, block, output, model);
		return controller;
	}

}