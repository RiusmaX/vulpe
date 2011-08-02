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
import org.vulpe.commons.helper.VulpeConfigHelper;
import org.vulpe.commons.util.VulpeStringUtil;
import org.vulpe.fox.all.DecoratedAll;
import org.vulpe.model.annotations.CodeGenerator;
import org.vulpe.model.entity.impl.VulpeBaseSimpleEntity;
import org.vulpe.view.annotations.View.ViewType;

public class ForAllTemplateStrategy extends VulpeForAllTemplateStrategy {

	private DecoratedAll all;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.jelly.apt.strategies.TemplateBlockStrategy#preProcess(net.sf.jelly
	 * .apt.TemplateBlock, net.sf.jelly.apt.TemplateOutput,
	 * net.sf.jelly.apt.TemplateModel)
	 */
	@Override
	public boolean preProcess(final TemplateBlock block, final TemplateOutput<TemplateBlock> output,
			final TemplateModel model) throws IOException, TemplateException {
		if (super.preProcess(block, output, model) && getDeclaration() instanceof DecoratedClassDeclaration) {
			final DecoratedClassDeclaration clazz = (DecoratedClassDeclaration) getDeclaration();
			if (getClassName(clazz.getSuperclass()).equals(VulpeBaseSimpleEntity.class.getName())) {
				return false;
			}
			final CodeGenerator codeGenerator = clazz.getAnnotation(CodeGenerator.class);
			if (codeGenerator == null || codeGenerator.view().viewType()[0].equals(ViewType.NONE)) {
				return false;
			}
			all = new DecoratedAll();
			all.setOverride(codeGenerator.override());
			all.setName(StringUtils.isNotEmpty(codeGenerator.baseName()) ? codeGenerator.baseName() : clazz
					.getSimpleName());
			if (StringUtils.isNotEmpty(codeGenerator.label())) {
				all.setLabel(codeGenerator.label());
			} else {
				all.setLabel(VulpeStringUtil.separateWords(VulpeStringUtil.upperCaseFirst(all.getName())));
			}
			all.setProjectName(VulpeConfigHelper.getProjectName());
			all.setModuleName(getModuleName(clazz));
			final List<String> types = new ArrayList<String>();
			for (final ViewType viewType : codeGenerator.view().viewType()) {
				types.add(viewType.toString());
			}
			all.setTypes(types);
			all.setManager(new ForAllManagerTemplateStrategy().execute(clazz, block, output, model));
			all.setController(new ForAllControllerTemplateStrategy().execute(clazz, block, output, model));
			all.setView(new ForAllViewTemplateStrategy().execute(clazz, block, output, model));
			model.setVariable(getVar(), all);
			return true;
		}
		return false;
	}

}