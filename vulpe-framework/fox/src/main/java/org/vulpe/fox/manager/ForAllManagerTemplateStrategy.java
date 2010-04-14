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
			final TemplateOutput<TemplateBlock> output,
			final TemplateModel model) throws IOException, TemplateException {
		if (super.preProcess(block, output, model)
				&& getDeclaration() instanceof DecoratedClassDeclaration) {
			final DecoratedClassDeclaration clazz = (DecoratedClassDeclaration) getDeclaration();
			if (getClassName(clazz.getSuperclass()).equals(
					"org.vulpe.model.entity.VulpeBaseSimpleEntity")) {
				return false;
			}
			final CodeGenerator codeGenerator = clazz
					.getAnnotation(CodeGenerator.class);
			if (codeGenerator == null || !codeGenerator.manager()) {
				return false;
			}
			final DecoratedManager controller = new DecoratedManager();
			controller.setName(clazz.getSimpleName().concat("Manager"));
			controller.setEntityName(clazz.getSimpleName());
			controller.setPackageName(clazz.getPackage().toString());
			controller.setDaoPackageName(StringUtils.replace(clazz.getPackage()
					.toString(), ".entity", ".dao"));
			controller.setManagerPackageName(StringUtils
					.replace(clazz.getPackage().toString(), ".model.entity",
							".model.manager"));
			controller.setModuleName(getModuleName(clazz));

			if (clazz.getSuperclass() != null
					&& !getClassName(clazz.getSuperclass()).equals(
							Object.class.getName())
					&& !getClassName(clazz.getSuperclass()).equals(
							AbstractVulpeBaseEntityImpl.class.getName())) {
				controller
						.setSuperclassName(getClassName(clazz.getSuperclass()));
				controller.setManagerSuperclassName(StringUtils.replace(
						controller.getSuperclassName(), ".model.entity",
						".model.manager"));
			}

			final FieldDeclaration field = getField(clazz, "id");
			controller.setIdType(field.getType().toString());

			prepareMethods(clazz, controller);

			model.setVariable(getVar(), controller);
			return true;
		}
		return false;
	}

	protected void prepareMethods(final DecoratedClassDeclaration clazz,
			final DecoratedManager dao) {
		// prepare methods for manager
	}

}