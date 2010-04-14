package org.vulpe.view.struts.form.beans;

import java.io.Serializable;
import java.util.List;

import org.vulpe.common.beans.Paging;


@SuppressWarnings( { "unchecked", "serial" })
public class SessionPaging<BEAN extends Serializable> extends Paging<BEAN> {
	private List<BEAN> fullList;

	public SessionPaging(final Integer pageSize, final List fullList) {
		super(fullList.size(), pageSize, 1);
		this.fullList = fullList;
	}

	public List getFullList() {
		return fullList;
	}

	public void setFullList(final List fullList) {
		this.fullList = fullList;
	}

	public List getList() {
		processPage();
		try {
			setList(getFullList().subList(getFromIndex(), getToIndex()));
		} catch (Exception e) {
			setList(null);
		}
		return super.getList();
	}
}