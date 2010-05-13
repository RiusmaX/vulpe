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
import org.vulpe.common.annotations.DetailConfig;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Controller.ControllerType;
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
					|| (codeGenerator.controller().length == 1 && codeGenerator.controller()[0]
							.controllerType().equals(ControllerType.NONE))) {
				return false;
			}
			final DecoratedController controller = new DecoratedController();
			controller.setName(clazz.getSimpleName());
			controller.setPackageName(clazz.getPackage().toString());
			controller.setServicePackageName(StringUtils.replace(clazz.getPackage().toString(),
					".entity", ".services"));
			controller.setControllerPackageName(StringUtils.replace(clazz.getPackage().toString(),
					".model.entity", ".controller.action"));
			controller.setModuleName(getModuleName(clazz));

			if (clazz.getSuperclass() != null
					&& !getClassName(clazz.getSuperclass()).equals(Object.class.getName())
					&& !getClassName(clazz.getSuperclass()).equals(
							AbstractVulpeBaseEntityImpl.class.getName())) {
				controller.setSuperclassName(getClassName(clazz.getSuperclass()));
				controller.setControllerSuperclassName(StringUtils.replace(controller
						.getSuperclassName(), ".model.entity", ".controller.action"));
			}

			final List<String> types = new ArrayList<String>();
			for (Controller control : codeGenerator.controller()) {
				types.add(control.controllerType().toString());
				if (control.controllerType().equals(ControllerType.CRUD)
						&& control.detailsConfig().length > 0) {
					final List<DecoratedControllerDetail> details = new ArrayList<DecoratedControllerDetail>();
					int count = control.detailsConfig().length;
					for (DetailConfig detailConfig : control.detailsConfig()) {
						final DecoratedControllerDetail detail = new DecoratedControllerDetail();
						if (count > 1) {
							detail.setNext(",");
						}
						final StringBuilder despiseFields = new StringBuilder();
						for (String despise : detailConfig.despiseFields()) {
							if (StringUtils.isBlank(despiseFields.toString())) {
								despiseFields.append("\"" + despise + "\"");
							} else {
								despiseFields.append(", \"" + despise + "\"");
							}
						}
						detail.setDespiseFields(despiseFields.toString());
						detail.setDetailNews(detailConfig.detailNews());
						detail.setName(detailConfig.name());
						detail.setParentDetailName(detailConfig.parentDetailName());
						detail.setPropertyName(detailConfig.propertyName());
						detail.setView(detailConfig.view());
						details.add(detail);
						count--;
					}
				}
				if (control.controllerType().equals(ControllerType.SELECT)) {
					controller.setPageSize(control.pageSize());
				}
				if (control.controllerType().equals(ControllerType.TABULAR)) {
					final StringBuilder tabularDespise = new StringBuilder();
					if (control.tabularDespiseFields().length > 0) {
						for (String s : control.tabularDespiseFields()) {
							if (StringUtils.isBlank(tabularDespise.toString())) {
								tabularDespise.append("\"" + s + "\"");
							} else {
								tabularDespise.append(", \"" + s + "\"");
							}
						}
					}
					controller.setTabularDespiseFields(tabularDespise.toString());
					controller.setTabularDetailNews(control.tabularDetailNews());
					controller.setTabularName(control.tabularName());
					controller.setTabularPropertyName(control.tabularPropertyName());
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