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
		controller.setApplicationPackageName(VulpeConfigHelper.getApplicationPackage());
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