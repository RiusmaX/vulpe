package org.vulpe.common.beans;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ValueBean implements Serializable{

	private String id;
	private String value;

	public ValueBean(){
		// default constructor
	}

	public ValueBean(final String id, final String value){
		this.id = id;
		this.value = value;
	}

	public String getId() {
		return id;
	}
	public void setId(final String id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(final String value) {
		this.value = value;
	}
}