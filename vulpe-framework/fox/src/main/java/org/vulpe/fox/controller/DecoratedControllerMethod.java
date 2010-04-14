package org.vulpe.fox.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class DecoratedControllerMethod implements Serializable {

	private String name;
	private String returnType;
	private List<DecoratedControllerParameter> parameters;

	public List<DecoratedControllerParameter> getParameters() {
		if (parameters == null) {
			parameters = new ArrayList<DecoratedControllerParameter>();
		}
		return parameters;
	}

	public void setParameters(final List<DecoratedControllerParameter> parameters) {
		this.parameters = parameters;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(final String returnType) {
		this.returnType = returnType;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}
}