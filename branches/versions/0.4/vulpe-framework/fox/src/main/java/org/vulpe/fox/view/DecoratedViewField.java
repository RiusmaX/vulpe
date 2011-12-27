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
package org.vulpe.fox.view;

import java.io.Serializable;

@SuppressWarnings("serial")
public class DecoratedViewField implements Serializable {

	private String name;
	private String attribute = "";
	private String label;
	private int size;
	private int maxlength;
	private int rows;
	private int cols;
	private String mask = "";
	private String items = "";
	private String itemKey = "";
	private String itemLabel = "";
	private String list = "";
	private String listKey = "";
	private String listValue = "";
	private String enumeration = "";
	private String fieldValue = "";
	private String action;
	private String identifier;
	private String description;
	private int popupWidth;
	private String type;
	private String align = "";
	private String width = "";
	private String booleanTo = "";
	private boolean sortable;
	private boolean showBlank;
	private boolean autoLoad;
	private boolean argument;
	private boolean autocomplete = false;
	private boolean required = false;
	private String validateType;
	private String validateScope;
	private String validateRequiredScope = "ALL";
	private String validateMask;
	private String validateDatePattern;
	private int validateMin;
	private int validateMax;
	private int validateMinLength;
	private int validateMaxLength;
	private int[] validateRange;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public int getSize() {
		return size;
	}

	public void setSize(final int size) {
		this.size = size;
	}

	public int getMaxlength() {
		return maxlength;
	}

	public void setMaxlength(final int maxlength) {
		this.maxlength = maxlength;
	}

	public String getType() {
		return type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	public String getItems() {
		return items;
	}

	public void setItems(final String items) {
		this.items = items;
	}

	public String getItemKey() {
		return itemKey;
	}

	public void setItemKey(final String itemKey) {
		this.itemKey = itemKey;
	}

	public String getItemLabel() {
		return itemLabel;
	}

	public void setItemLabel(final String itemLabel) {
		this.itemLabel = itemLabel;
	}

	public void setMask(final String mask) {
		this.mask = mask;
	}

	public String getMask() {
		return mask;
	}

	public void setSortable(final boolean sortable) {
		this.sortable = sortable;
	}

	public boolean isSortable() {
		return sortable;
	}

	public void setWidth(final String width) {
		this.width = width;
	}

	public String getWidth() {
		return width;
	}

	public void setLabel(final String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setIdentifier(final String identifier) {
		this.identifier = identifier;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setShowBlank(final boolean showBlank) {
		this.showBlank = showBlank;
	}

	public boolean isShowBlank() {
		return showBlank;
	}

	public void setPopupWidth(final int popupWidth) {
		this.popupWidth = popupWidth;
	}

	public int getPopupWidth() {
		return popupWidth;
	}

	public void setAction(final String action) {
		this.action = action;
	}

	public String getAction() {
		return action;
	}

	public void setAttribute(final String attribute) {
		this.attribute = attribute;
	}

	public String getAttribute() {
		return attribute;
	}

	public String getList() {
		return list;
	}

	public void setList(final String list) {
		this.list = list;
	}

	public String getListKey() {
		return listKey;
	}

	public void setListKey(final String listKey) {
		this.listKey = listKey;
	}

	public String getListValue() {
		return listValue;
	}

	public void setListValue(final String listValue) {
		this.listValue = listValue;
	}

	public String getEnumeration() {
		return enumeration;
	}

	public void setEnumeration(final String enumeration) {
		this.enumeration = enumeration;
	}

	public void setFieldValue(final String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setAutoLoad(final boolean autoLoad) {
		this.autoLoad = autoLoad;
	}

	public boolean isAutoLoad() {
		return autoLoad;
	}

	public void setBooleanTo(final String booleanTo) {
		this.booleanTo = booleanTo;
	}

	public String getBooleanTo() {
		return booleanTo;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public String getAlign() {
		return align;
	}

	public void setArgument(boolean argument) {
		this.argument = argument;
	}

	public boolean isArgument() {
		return argument;
	}

	public void setAutocomplete(boolean autoComplete) {
		this.autocomplete = autoComplete;
	}

	public boolean isAutocomplete() {
		return autocomplete;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public boolean isRequired() {
		return required;
	}

	public String getValidateType() {
		return validateType;
	}

	public void setValidateType(String validateType) {
		this.validateType = validateType;
	}

	public String getValidateMask() {
		return validateMask;
	}

	public void setValidateMask(String validateMask) {
		this.validateMask = validateMask;
	}

	public int getValidateMin() {
		return validateMin;
	}

	public void setValidateMin(int validateMin) {
		this.validateMin = validateMin;
	}

	public int getValidateMax() {
		return validateMax;
	}

	public void setValidateMax(int validateMax) {
		this.validateMax = validateMax;
	}

	public int getValidateMinLength() {
		return validateMinLength;
	}

	public void setValidateMinLength(int validateMinLength) {
		this.validateMinLength = validateMinLength;
	}

	public int getValidateMaxLength() {
		return validateMaxLength;
	}

	public void setValidateMaxLength(int validateMaxLength) {
		this.validateMaxLength = validateMaxLength;
	}

	public int[] getValidateRange() {
		return validateRange;
	}

	public void setValidateRange(int[] validateRange) {
		this.validateRange = validateRange;
	}

	public void setValidateDatePattern(String validateDatePattern) {
		this.validateDatePattern = validateDatePattern;
	}

	public String getValidateDatePattern() {
		return validateDatePattern;
	}

	public void setValidateScope(String validateScope) {
		this.validateScope = validateScope;
	}

	public String getValidateScope() {
		return validateScope;
	}

	public void setValidateRequiredScope(String validateRequiredScope) {
		this.validateRequiredScope = validateRequiredScope;
	}

	public String getValidateRequiredScope() {
		return validateRequiredScope;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getRows() {
		return rows;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	public int getCols() {
		return cols;
	}

}