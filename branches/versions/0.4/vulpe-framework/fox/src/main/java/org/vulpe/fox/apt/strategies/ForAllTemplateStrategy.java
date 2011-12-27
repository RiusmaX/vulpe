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