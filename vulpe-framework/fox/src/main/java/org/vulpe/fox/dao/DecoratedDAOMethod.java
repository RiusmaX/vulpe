package org.vulpe.fox.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class DecoratedDAOMethod implements Serializable {

	private String name;
	private String returnType;
	private List<DecoratedDAOParameter> parameters;

	public List<DecoratedDAOParameter> getParameters() {
		if (parameters == null) {
			parameters = new ArrayList<DecoratedDAOParameter>();
		}
		return parameters;
	}

	public void setParameters(final List<DecoratedDAOParameter> parameters) {
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