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
package org.vulpe.fox.view;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import net.sf.jelly.apt.TemplateBlock;
import net.sf.jelly.apt.TemplateException;
import net.sf.jelly.apt.TemplateModel;
import net.sf.jelly.apt.TemplateOutput;
import net.sf.jelly.apt.decorations.declaration.DecoratedClassDeclaration;

import org.apache.commons.lang.StringUtils;
import org.vulpe.commons.VulpeReflectUtil;
import org.vulpe.commons.helper.VulpeConfigHelper;
import org.vulpe.fox.VulpeForAllTemplateStrategy;
import org.vulpe.model.annotations.AutoComplete;
import org.vulpe.model.annotations.CodeGenerator;
import org.vulpe.model.entity.VulpeBaseSimpleEntity;
import org.vulpe.view.annotations.View.ViewType;
import org.vulpe.view.annotations.input.VulpeCheckbox;
import org.vulpe.view.annotations.input.VulpeCheckboxlist;
import org.vulpe.view.annotations.input.VulpeDate;
import org.vulpe.view.annotations.input.VulpePassword;
import org.vulpe.view.annotations.input.VulpeSelect;
import org.vulpe.view.annotations.input.VulpeSelectPopup;
import org.vulpe.view.annotations.input.VulpeText;
import org.vulpe.view.annotations.input.VulpeValidate;
import org.vulpe.view.annotations.logic.crud.Detail;
import org.vulpe.view.annotations.output.VulpeColumn;

import com.sun.mirror.declaration.FieldDeclaration;

public class ForAllViewTemplateStrategy extends VulpeForAllTemplateStrategy {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.jelly.apt.strategies.TemplateBlockStrategy#preProcess(net.sf.jelly
	 * .apt.TemplateBlock, net.sf.jelly.apt.TemplateOutput,
	 * net.sf.jelly.apt.TemplateModel)
	 */
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
			if (codeGenerator == null || codeGenerator.view().viewType()[0].equals(ViewType.NONE)) {
				return false;
			}
			final DecoratedView view = new DecoratedView();
			view.setName(clazz.getSimpleName());
			view.setProjectName(VulpeConfigHelper.getProjectName());
			view.setModuleName(getModuleName(clazz));
			view.setPopupProperties(codeGenerator.view().popupProperties());
			final List<String> types = new ArrayList<String>();
			for (ViewType viewType : codeGenerator.view().viewType()) {
				types.add(viewType.toString());
			}
			view.setTypes(types);

			prepareFields(clazz, view);

			model.setVariable(getVar(), view);
			return true;
		}
		return false;
	}

	protected String getLabel(final DecoratedView view, final String name, final String type) {
		final String classSimpleName = this.getClass().getSimpleName();
		return "label.".concat(view.getProjectName()).concat(".").concat(view.getModuleName())
				.concat(".").concat(classSimpleName).concat(".").concat(type).concat(".").concat(
						name);
	}

	protected void prepareFields(final DecoratedClassDeclaration clazz, final DecoratedView view) {
		final List<DecoratedViewDetail> details = new ArrayList<DecoratedViewDetail>();
		final List<DecoratedViewField> fields = new ArrayList<DecoratedViewField>();
		final List<DecoratedViewField> items = new ArrayList<DecoratedViewField>();
		final List<DecoratedViewField> arguments = new ArrayList<DecoratedViewField>();
		for (FieldDeclaration field : clazz.getFields()) {
			final Detail detail = field.getAnnotation(Detail.class);
			if (detail != null) {
				final DecoratedViewDetail decoratedViewDetail = new DecoratedViewDetail();
				final List<DecoratedViewField> detailFields = new ArrayList<DecoratedViewField>();
				decoratedViewDetail.setName(field.getSimpleName());
				if (StringUtils.isNotBlank(detail.relationship().identifier())) {
					final DecoratedViewField decoratedViewFieldDetail = new DecoratedViewField();
					decoratedViewFieldDetail.setName(detail.relationship().name());
					decoratedViewFieldDetail.setAction(detail.relationship().action());
					decoratedViewFieldDetail.setSize(detail.relationship().size());
					decoratedViewFieldDetail.setPopupWidth(detail.relationship().popupWidth());
					decoratedViewFieldDetail.setIdentifier(detail.relationship().identifier());
					decoratedViewFieldDetail.setDescription(detail.relationship().description());
					decoratedViewFieldDetail.setSize(detail.relationship().size());
					decoratedViewFieldDetail.setType("selectPopup");
					detailFields.add(decoratedViewFieldDetail);
					decoratedViewDetail.setFields(detailFields);
				} else {
					final List<Field> listDetailField = VulpeReflectUtil.getInstance().getFields(
							detail.clazz());
					for (Field detailField : listDetailField) {
						final VulpeColumn column = detailField.getAnnotation(VulpeColumn.class);
						final DecoratedViewField decoratedViewFieldDetail = generateViewField(null,
								detailField);
						if (column != null) {
							decoratedViewFieldDetail.setAlign(column.align());
						}
						if (StringUtils.isNotBlank(decoratedViewFieldDetail.getType())) {
							detailFields.add(decoratedViewFieldDetail);
						}
					}
					decoratedViewDetail.setFields(detailFields);
				}
				details.add(decoratedViewDetail);
				continue;
			}

			final VulpeColumn column = field.getAnnotation(VulpeColumn.class);
			final DecoratedViewField decoratedViewField = generateViewField(field, null);
			if (StringUtils.isNotBlank(decoratedViewField.getType())) {
				fields.add(decoratedViewField);
				if (decoratedViewField.isArgument()) {
					arguments.add(decoratedViewField);
				}
			}
			if (column != null) {
				decoratedViewField.setSortable(column.sortable());
				if (StringUtils.isNotBlank(column.attribute())) {
					decoratedViewField.setAttribute(column.attribute());
				}
				if (StringUtils.isNotBlank(column.booleanTo())) {
					decoratedViewField.setBooleanTo(column.booleanTo());
				}
				items.add(decoratedViewField);
			}
		}
		view.setDetails(details);
		view.setFields(fields);
		view.setArguments(arguments);
		view.setItems(items);
		view.setColumnSpan(items.size() + 5);
	}

	private DecoratedViewField generateViewField(FieldDeclaration fDeclaration, Field field) {
		DecoratedViewField decoratedViewField = new DecoratedViewField();
		String name = "";
		final VulpeText text = fDeclaration == null ? field.getAnnotation(VulpeText.class)
				: fDeclaration.getAnnotation(VulpeText.class);
		if (text != null) {
			name = text.name();
			decoratedViewField.setArgument(text.argument());
			decoratedViewField.setRequired(text.required());
			decoratedViewField.setMaxlength(text.maxlength());
			decoratedViewField.setSize(text.size());
			decoratedViewField.setMask(text.mask());
			decoratedViewField.setType("text");
		}
		final VulpePassword password = fDeclaration == null ? field
				.getAnnotation(VulpePassword.class) : fDeclaration
				.getAnnotation(VulpePassword.class);
		if (password != null) {
			name = password.name();
			decoratedViewField.setArgument(password.argument());
			decoratedViewField.setRequired(password.required());
			decoratedViewField.setMaxlength(password.maxlength());
			decoratedViewField.setSize(password.size());
			decoratedViewField.setType("password");
		}
		final VulpeDate date = fDeclaration == null ? field.getAnnotation(VulpeDate.class)
				: fDeclaration.getAnnotation(VulpeDate.class);
		if (date != null) {
			name = date.name();
			decoratedViewField.setArgument(date.argument());
			decoratedViewField.setRequired(date.required());
			decoratedViewField.setMaxlength(date.maxlength());
			decoratedViewField.setSize(date.size());
			decoratedViewField.setType("date");
		}
		final VulpeCheckbox checkbox = fDeclaration == null ? field
				.getAnnotation(VulpeCheckbox.class) : fDeclaration
				.getAnnotation(VulpeCheckbox.class);
		if (checkbox != null) {
			name = checkbox.name();
			decoratedViewField.setArgument(checkbox.argument());
			decoratedViewField.setRequired(checkbox.required());
			decoratedViewField.setFieldValue(checkbox.fieldValue());
			decoratedViewField.setType("checkbox");
		}
		final VulpeSelect select = fDeclaration == null ? field.getAnnotation(VulpeSelect.class)
				: fDeclaration.getAnnotation(VulpeSelect.class);
		if (select != null) {
			name = select.name();
			decoratedViewField.setArgument(select.argument());
			decoratedViewField.setRequired(select.required());
			if (StringUtils.isNotBlank(select.items())) {
				decoratedViewField.setItems(select.items());
				if (StringUtils.isNotBlank(select.itemKey())) {
					decoratedViewField.setItemKey(select.itemKey());
				}
				if (StringUtils.isNotBlank(select.itemLabel())) {
					decoratedViewField.setItemLabel(select.itemLabel());
				}
			}
			decoratedViewField.setShowBlank(select.showBlank());
			decoratedViewField.setAutoLoad(select.autoLoad());
			decoratedViewField.setType("select");
		}
		final VulpeCheckboxlist checkboxlist = fDeclaration == null ? field
				.getAnnotation(VulpeCheckboxlist.class) : fDeclaration
				.getAnnotation(VulpeCheckboxlist.class);
		if (checkboxlist != null) {
			name = checkboxlist.name();
			decoratedViewField.setArgument(checkboxlist.argument());
			decoratedViewField.setRequired(checkboxlist.required());
			if (StringUtils.isNotBlank(checkboxlist.list())) {
				if (StringUtils.isNotBlank(checkboxlist.list())) {
					decoratedViewField.setList(checkboxlist.list());
					if (StringUtils.isNotBlank(checkboxlist.listKey())) {
						decoratedViewField.setListKey(checkboxlist.listKey());
					}
					if (StringUtils.isNotBlank(checkboxlist.listValue())) {
						decoratedViewField.setListValue(checkboxlist.listValue());
					}
				}
				if (StringUtils.isNotBlank(checkboxlist.enumeration())) {
					decoratedViewField.setEnumeration(checkboxlist.enumeration());
				}
			}
			decoratedViewField.setType("checkboxlist");
		}
		final VulpeSelectPopup selectPopup = fDeclaration == null ? field
				.getAnnotation(VulpeSelectPopup.class) : fDeclaration
				.getAnnotation(VulpeSelectPopup.class);
		final AutoComplete autoComplete = fDeclaration == null ? field
				.getAnnotation(AutoComplete.class) : fDeclaration.getAnnotation(AutoComplete.class);
		if (selectPopup != null) {
			name = selectPopup.name();
			decoratedViewField.setArgument(selectPopup.argument());
			decoratedViewField.setRequired(selectPopup.required());
			decoratedViewField.setIdentifier(selectPopup.identifier());
			decoratedViewField.setDescription(selectPopup.description());
			decoratedViewField.setAction(selectPopup.action());
			decoratedViewField.setSize(selectPopup.size());
			decoratedViewField.setPopupWidth(selectPopup.popupWidth());
			decoratedViewField.setAutoComplete(selectPopup.autoComplete());
			decoratedViewField.setType("selectPopup");
		}
		if (autoComplete != null) {
			decoratedViewField.setAutoComplete(true);
		}
		final VulpeValidate validate = fDeclaration == null ? field
				.getAnnotation(VulpeValidate.class) : fDeclaration
				.getAnnotation(VulpeValidate.class);
		if (validate != null) {
			decoratedViewField.setValidateType(validate.type().name());
			switch (validate.type()) {
			case STRING:
				if (validate.minlength() > 0) {
					decoratedViewField.setValidateMinLength(validate.minlength());
				}
				if (validate.maxlength() > 0) {
					decoratedViewField.setValidateMaxLength(validate.maxlength());
				}
				break;
			case DATE:
				decoratedViewField.setValidateDatePattern(validate.datePattern());
				break;
			case INTEGER:
				if (validate.min() > 1) {
					decoratedViewField.setValidateMin(validate.min());
				}
				if (validate.max() > 1) {
					decoratedViewField.setValidateMax(validate.max());
				}
				if (validate.range()[0] > 0 && validate.range()[1] > 0) {
					decoratedViewField.setValidateRange(validate.range());
				}
				break;
			case LONG:
				if (validate.min() > 1) {
					decoratedViewField.setValidateMin(validate.min());
				}
				if (validate.max() > 1) {
					decoratedViewField.setValidateMax(validate.max());
				}
				if (validate.range()[0] > 0 && validate.range()[1] > 0) {
					decoratedViewField.setValidateRange(validate.range());
				}
				break;
			case DOUBLE:
				if (validate.min() > 1) {
					decoratedViewField.setValidateMin(validate.min());
				}
				if (validate.max() > 1) {
					decoratedViewField.setValidateMax(validate.max());
				}
				if (validate.range()[0] > 0 && validate.range()[1] > 0) {
					decoratedViewField.setValidateRange(validate.range());
				}
				break;
			case FLOAT:
				if (validate.min() > 1) {
					decoratedViewField.setValidateMin(validate.min());
				}
				if (validate.max() > 1) {
					decoratedViewField.setValidateMax(validate.max());
				}
				if (validate.range()[0] > 0 && validate.range()[1] > 0) {
					decoratedViewField.setValidateRange(validate.range());
				}
				break;
			default:
				decoratedViewField.setValidateType("");
			}
		}
		decoratedViewField.setName(StringUtils.isNotBlank(name) ? name
				: fDeclaration == null ? field.getName() : fDeclaration.getSimpleName());
		return decoratedViewField;
	}
}