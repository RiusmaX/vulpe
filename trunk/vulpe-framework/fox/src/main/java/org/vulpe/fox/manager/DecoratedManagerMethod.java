package org.vulpe.fox.manager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class DecoratedManagerMethod implements Serializable {

	private String name;
	private String returnType;
	private List<DecoratedManagerParameter> parameters;

	public List<DecoratedManagerParameter> getParameters() {
		if (parameters == null) {
			parameters = new ArrayList<DecoratedManagerParameter>();
		}
		return parameters;
	}

	public void setParameters(final List<DecoratedManagerParameter> parameters) {
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