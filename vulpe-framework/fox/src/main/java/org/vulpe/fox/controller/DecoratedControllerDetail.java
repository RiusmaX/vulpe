package org.vulpe.fox.controller;

import java.io.Serializable;

@SuppressWarnings("serial")
public class DecoratedControllerDetail implements Serializable {

	private String name;
	private int detailNews;
	private String despiseFields;
	private String view;
	private String propertyName;
	private String parentDetailName;
	private String next;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public int getDetailNews() {
		return detailNews;
	}

	public void setDetailNews(final int detailNews) {
		this.detailNews = detailNews;
	}

	public String getDespiseFields() {
		return despiseFields;
	}

	public void setDespiseFields(final String despiseFields) {
		this.despiseFields = despiseFields;
	}

	public String getView() {
		return view;
	}

	public void setView(final String view) {
		this.view = view;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(final String propertyName) {
		this.propertyName = propertyName;
	}

	public String getParentDetailName() {
		return parentDetailName;
	}

	public void setParentDetailName(final String parentDetailName) {
		this.parentDetailName = parentDetailName;
	}

	public void setNext(final String next) {
		this.next = next;
	}

	public String getNext() {
		return next;
	}
}