package org.vulpe.controller.common;

public class DuplicatedBean {

	private Object bean;

	private Integer line;

	public DuplicatedBean() {
		// default constructor
	}

	public DuplicatedBean(final Object bean, final Integer line) {
		this.bean = bean;
		this.line = line;
	}

	public Integer getLine() {
		return line;
	}

	public void setLine(final Integer line) {
		this.line = line;
	}

	public void setBean(final Object bean) {
		this.bean = bean;
	}

	public Object getBean() {
		return bean;
	}

}
