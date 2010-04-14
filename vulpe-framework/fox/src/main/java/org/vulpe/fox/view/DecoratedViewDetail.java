package org.vulpe.fox.view;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class DecoratedViewDetail implements Serializable {

	private String name;
	private String popupProperties;
	private List<DecoratedViewField> fields;

	public void setName(final String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setPopupProperties(final String popupProperties) {
		this.popupProperties = popupProperties;
	}

	public String getPopupProperties() {
		return popupProperties;
	}

	public void setFields(final List<DecoratedViewField> fields) {
		this.fields = fields;
	}

	public List<DecoratedViewField> getFields() {
		return fields;
	}

}