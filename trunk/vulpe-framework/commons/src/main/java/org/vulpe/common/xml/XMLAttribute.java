package org.vulpe.common.xml;

public class XMLAttribute {

	private String name;
	private String value;

	public XMLAttribute() {
		// default constructor
	}

	public XMLAttribute(final String name, final String value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(final String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return getName() + " = " + getValue();
	}

}
