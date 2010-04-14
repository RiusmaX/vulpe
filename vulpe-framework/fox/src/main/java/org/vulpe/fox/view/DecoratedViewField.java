package org.vulpe.fox.view;

import java.io.Serializable;

@SuppressWarnings("serial")
public class DecoratedViewField implements Serializable {

	private String name;
	private String attribute = "";
	private String label;
	private int size;
	private int maxlength;
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

}