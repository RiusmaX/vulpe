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
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jelly.apt.TemplateBlock;
import net.sf.jelly.apt.TemplateException;
import net.sf.jelly.apt.TemplateModel;
import net.sf.jelly.apt.TemplateOutput;
import net.sf.jelly.apt.decorations.declaration.DecoratedClassDeclaration;

import org.apache.commons.lang.StringUtils;
import org.vulpe.commons.helper.VulpeConfigHelper;
import org.vulpe.commons.util.VulpeReflectUtil;
import org.vulpe.commons.util.VulpeStringUtil;
import org.vulpe.fox.view.DecoratedView;
import org.vulpe.fox.view.DecoratedViewDetail;
import org.vulpe.fox.view.DecoratedViewField;
import org.vulpe.model.annotations.Autocomplete;
import org.vulpe.model.annotations.CodeGenerator;
import org.vulpe.model.entity.impl.VulpeBaseSimpleEntity;
import org.vulpe.view.annotations.View.ViewType;
import org.vulpe.view.annotations.input.VulpeCheckbox;
import org.vulpe.view.annotations.input.VulpeCheckboxlist;
import org.vulpe.view.annotations.input.VulpeDate;
import org.vulpe.view.annotations.input.VulpePassword;
import org.vulpe.view.annotations.input.VulpeRadio;
import org.vulpe.view.annotations.input.VulpeSelect;
import org.vulpe.view.annotations.input.VulpeSelectPopup;
import org.vulpe.view.annotations.input.VulpeText;
import org.vulpe.view.annotations.input.VulpeTextArea;
import org.vulpe.view.annotations.input.VulpeValidate;
import org.vulpe.view.annotations.input.VulpeValidate.VulpeValidateScope;
import org.vulpe.view.annotations.logic.main.Detail;
import org.vulpe.view.annotations.output.VulpeColumn;

import com.sun.mirror.declaration.FieldDeclaration;

public class ForAllViewTemplateStrategy extends VulpeForAllTemplateStrategy {

	private DecoratedView view;

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
			return executePreProcess(clazz, block, output, model);
		}
		return false;
	}

	protected String getLabel(final DecoratedView view, final String name, final String type) {
		final String classSimpleName = this.getClass().getSimpleName();
		return "label.".concat(view.getProjectName()).concat(".").concat(view.getModuleName()).concat(".").concat(
				classSimpleName).concat(".").concat(type).concat(".").concat(name);
	}

	protected void prepareFields(final DecoratedClassDeclaration clazz, final DecoratedView view) {
		final List<DecoratedViewDetail> details = new ArrayList<DecoratedViewDetail>();
		final List<DecoratedViewField> fields = new ArrayList<DecoratedViewField>();
		final Map<String, String> labels = new HashMap<String, String>();
		final List<DecoratedViewField> items = new ArrayList<DecoratedViewField>();
		final List<DecoratedViewField> arguments = new ArrayList<DecoratedViewField>();
		for (FieldDeclaration field : clazz.getFields()) {
			final Detail detail = field.getAnnotation(Detail.class);
			if (detail != null) {
				final DecoratedViewDetail decoratedViewDetail = new DecoratedViewDetail();
				final List<DecoratedViewField> detailFields = new ArrayList<DecoratedViewField>();
				decoratedViewDetail.setName(field.getSimpleName());
				decoratedViewDetail.setLabel(VulpeStringUtil.separateWords(VulpeStringUtil
						.upperCaseFirst(decoratedViewDetail.getName())));
				if (StringUtils.isNotBlank(detail.relationship().identifier())) {
					final DecoratedViewField decoratedViewFieldDetail = new DecoratedViewField();
					decoratedViewFieldDetail.setName(detail.relationship().name());
					decoratedViewFieldDetail.setLabel(VulpeStringUtil.separateWords(VulpeStringUtil
							.upperCaseFirst(decoratedViewFieldDetail.getName())));
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
					final List<Field> listDetailField = VulpeReflectUtil.getFields(detail.target());
					for (final Field detailField : listDetailField) {
						if (detailField.getName().toString().equals(clazz.getSimpleName().toLowerCase())) {
							continue;
						}
						final VulpeColumn column = detailField.getAnnotation(VulpeColumn.class);
						final DecoratedViewField decoratedViewFieldDetail = generateViewField(null, detailField);
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
			if (column != null || decoratedViewField.isArgument()) {
				if (StringUtils.isNotEmpty(decoratedViewField.getLabel())) {
					labels.put(decoratedViewField.getName(), decoratedViewField.getLabel());
				} else {
					labels.put(decoratedViewField.getName(), VulpeStringUtil.separateWords(VulpeStringUtil
							.upperCaseFirst(decoratedViewField.getName())));
				}
			}
		}
		view.setDetails(details);
		view.setFields(fields);
		view.setArguments(arguments);
		view.setItems(items);
		view.setLabels(labels);
		if (view.getTypes().size() == 1 && view.getTypes().get(0).equals("TABULAR")) {
			view.setColumnSpan(fields.size() + 3);
		} else {
			view.setColumnSpan(items.size() + 5);
		}
	}

	private DecoratedViewField generateViewField(FieldDeclaration fDeclaration, Field field) {
		final DecoratedViewField decoratedViewField = new DecoratedViewField();
		String name = "";
		final VulpeText text = fDeclaration == null ? field.getAnnotation(VulpeText.class) : fDeclaration
				.getAnnotation(VulpeText.class);
		if (text != null) {
			name = text.name();
			decoratedViewField.setLabel(text.label());
			decoratedViewField.setArgument(text.argument());
			decoratedViewField.setRequired(text.required());
			decoratedViewField.setMaxlength(text.maxlength());
			decoratedViewField.setSize(text.size());
			decoratedViewField.setMask(text.mask());
			decoratedViewField.setType("text");
		}
		final VulpeTextArea textarea = fDeclaration == null ? field.getAnnotation(VulpeTextArea.class) : fDeclaration
				.getAnnotation(VulpeTextArea.class);
		if (textarea != null) {
			name = textarea.name();
			decoratedViewField.setLabel(textarea.label());
			decoratedViewField.setArgument(textarea.argument());
			decoratedViewField.setRequired(textarea.required());
			decoratedViewField.setCols(textarea.cols());
			decoratedViewField.setRows(textarea.rows());
			decoratedViewField.setType("textarea");
		}
		final VulpePassword password = fDeclaration == null ? field.getAnnotation(VulpePassword.class) : fDeclaration
				.getAnnotation(VulpePassword.class);
		if (password != null) {
			name = password.name();
			decoratedViewField.setLabel(password.label());
			decoratedViewField.setArgument(password.argument());
			decoratedViewField.setRequired(password.required());
			decoratedViewField.setMaxlength(password.maxlength());
			decoratedViewField.setSize(password.size());
			decoratedViewField.setType("password");
		}
		final VulpeDate date = fDeclaration == null ? field.getAnnotation(VulpeDate.class) : fDeclaration
				.getAnnotation(VulpeDate.class);
		if (date != null) {
			name = date.name();
			decoratedViewField.setLabel(date.label());
			decoratedViewField.setArgument(date.argument());
			decoratedViewField.setRequired(date.required());
			decoratedViewField.setMaxlength(date.maxlength());
			decoratedViewField.setSize(date.size());
			decoratedViewField.setType("date");
		}
		final VulpeCheckbox checkbox = fDeclaration == null ? field.getAnnotation(VulpeCheckbox.class) : fDeclaration
				.getAnnotation(VulpeCheckbox.class);
		if (checkbox != null) {
			name = checkbox.name();
			decoratedViewField.setLabel(checkbox.label());
			decoratedViewField.setArgument(checkbox.argument());
			decoratedViewField.setRequired(checkbox.required());
			decoratedViewField.setFieldValue(checkbox.fieldValue());
			decoratedViewField.setType("checkbox");
		}
		final VulpeSelect select = fDeclaration == null ? field.getAnnotation(VulpeSelect.class) : fDeclaration
				.getAnnotation(VulpeSelect.class);
		if (select != null) {
			name = select.name();
			decoratedViewField.setLabel(select.label());
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
			if (select.size() > 0) {
				decoratedViewField.setSize(select.size());
			}
			decoratedViewField.setShowBlank(select.showBlank());
			decoratedViewField.setAutoLoad(select.autoLoad());
			decoratedViewField.setType("select");
		}
		final VulpeCheckboxlist checkboxlist = fDeclaration == null ? field.getAnnotation(VulpeCheckboxlist.class)
				: fDeclaration.getAnnotation(VulpeCheckboxlist.class);
		if (checkboxlist != null) {
			name = checkboxlist.name();
			decoratedViewField.setLabel(checkboxlist.label());
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
		final VulpeRadio radio = fDeclaration == null ? field.getAnnotation(VulpeRadio.class) : fDeclaration
				.getAnnotation(VulpeRadio.class);
		if (radio != null) {
			name = radio.name();
			decoratedViewField.setLabel(radio.label());
			decoratedViewField.setArgument(radio.argument());
			decoratedViewField.setRequired(radio.required());
			if (StringUtils.isNotBlank(radio.list())) {
				if (StringUtils.isNotBlank(radio.list())) {
					decoratedViewField.setList(radio.list());
					if (StringUtils.isNotBlank(radio.listKey())) {
						decoratedViewField.setListKey(radio.listKey());
					}
					if (StringUtils.isNotBlank(radio.listValue())) {
						decoratedViewField.setListValue(radio.listValue());
					}
				}
			}
			decoratedViewField.setType("radio");
		}
		final VulpeSelectPopup selectPopup = fDeclaration == null ? field.getAnnotation(VulpeSelectPopup.class)
				: fDeclaration.getAnnotation(VulpeSelectPopup.class);
		if (selectPopup != null) {
			name = selectPopup.name();
			decoratedViewField.setLabel(selectPopup.label());
			decoratedViewField.setArgument(selectPopup.argument());
			decoratedViewField.setRequired(selectPopup.required());
			decoratedViewField.setIdentifier(selectPopup.identifier());
			decoratedViewField.setDescription(selectPopup.description());
			decoratedViewField.setAction(selectPopup.action());
			decoratedViewField.setSize(selectPopup.size());
			decoratedViewField.setPopupWidth(selectPopup.popupWidth());
			decoratedViewField.setAutocomplete(selectPopup.autocomplete());
			decoratedViewField.setType("selectPopup");
		}
		final Autocomplete autoComplete = fDeclaration == null ? field.getAnnotation(Autocomplete.class) : fDeclaration
				.getAnnotation(Autocomplete.class);
		if (autoComplete != null) {
			decoratedViewField.setAutocomplete(true);
		}
		final VulpeValidate validate = fDeclaration == null ? field.getAnnotation(VulpeValidate.class) : fDeclaration
				.getAnnotation(VulpeValidate.class);
		if (validate != null) {
			decoratedViewField.setValidateType(validate.type().name());
			StringBuilder scope = new StringBuilder();
			for (VulpeValidateScope validadeScope : validate.scope()) {
				if ("".equals(scope.toString())) {
					scope.append(validadeScope.name());
				} else {
					scope.append(",").append(validadeScope.name());
				}
			}
			decoratedViewField.setValidateScope(scope.toString());
			StringBuilder requiredScope = new StringBuilder();
			for (VulpeValidateScope validadeRequiredScope : validate.requiredScope()) {
				if ("".equals(requiredScope.toString())) {
					requiredScope.append(validadeRequiredScope.name());
				} else {
					requiredScope.append(",").append(validadeRequiredScope.name());
				}
			}
			decoratedViewField.setValidateRequiredScope(requiredScope.toString());
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
		decoratedViewField.setName(StringUtils.isNotBlank(name) ? name : fDeclaration == null ? field.getName()
				: fDeclaration.getSimpleName());
		if (StringUtils.isNotEmpty(decoratedViewField.getLabel())) {
			decoratedViewField.setLabel(decoratedViewField.getLabel());
		} else {
			decoratedViewField.setLabel(VulpeStringUtil.separateWords(VulpeStringUtil.upperCaseFirst(decoratedViewField
					.getName())));
		}
		return decoratedViewField;
	}

	private boolean executePreProcess(final DecoratedClassDeclaration clazz, final TemplateBlock block,
			final TemplateOutput<TemplateBlock> output, final TemplateModel model) throws IOException,
			TemplateException {
		if (getClassName(clazz.getSuperclass()).equals(VulpeBaseSimpleEntity.class.getName())) {
			return false;
		}
		final CodeGenerator codeGenerator = clazz.getAnnotation(CodeGenerator.class);
		if (codeGenerator == null || codeGenerator.view().viewType()[0].equals(ViewType.NONE)) {
			return false;
		}
		view = new DecoratedView();
		view.setOverride(codeGenerator.override());
		view.setName(StringUtils.isNotEmpty(codeGenerator.baseName()) ? codeGenerator.baseName() : clazz
				.getSimpleName());
		if (StringUtils.isNotEmpty(codeGenerator.label())) {
			view.setLabel(codeGenerator.label());
		} else {
			view.setLabel(VulpeStringUtil.separateWords(VulpeStringUtil.upperCaseFirst(view.getName())));
		}
		view.setPrefixLabelOfView(VulpeConfigHelper.getApplicationConfiguration().codeGenerator().prefixLabelOfView());
		view.setPrefixLabelOfMaintenance(VulpeConfigHelper.getApplicationConfiguration().codeGenerator()
				.prefixLabelOfMaintenance());
		view.setPrefixLabelOfSelection(VulpeConfigHelper.getApplicationConfiguration().codeGenerator()
				.prefixLabelOfSelection());
		view.setPrefixLabelOfSelectionList(VulpeConfigHelper.getApplicationConfiguration().codeGenerator()
				.prefixLabelOfSelectionList());
		view
				.setPrefixLabelOfTabular(VulpeConfigHelper.getApplicationConfiguration().codeGenerator()
						.prefixLabelOfTabular());
		view.setProjectName(VulpeConfigHelper.getApplicationName());
		view.setModuleName(getModuleName(clazz));
		view.setPopupProperties(codeGenerator.view().popupProperties());
		final List<String> types = new ArrayList<String>();
		for (final ViewType viewType : codeGenerator.view().viewType()) {
			types.add(viewType.toString());
		}
		view.setTypes(types);
		prepareFields(clazz, view);
		model.setVariable(getVar(), view);
		return true;
	}

	public DecoratedView execute(final DecoratedClassDeclaration clazz, final TemplateBlock block,
			final TemplateOutput<TemplateBlock> output, final TemplateModel model) throws IOException,
			TemplateException {
		executePreProcess(clazz, block, output, model);
		return view;
	}
}