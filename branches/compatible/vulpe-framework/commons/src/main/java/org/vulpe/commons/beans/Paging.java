/**
 * Vulpe Framework - Copyright (c) Active Thread
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.vulpe.commons.beans;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(javax.xml.bind.annotation.XmlAccessType.PROPERTY)
@XmlType(name = "paging", propOrder = { "firstPage", "lastPage", "list",
		"nextPage", "page", "pages", "pageSize", "previousPage", "size" })
@SuppressWarnings("serial")
public class Paging<BEAN extends Serializable> implements Serializable {
	private List<BEAN> list;
	/**
	 * Items size
	 */
	private Integer size;
	/**
	 * Total pages
	 */
	private Integer pages;
	/**
	 * Current page
	 */
	private Integer page;
	/**
	 * Page size
	 */
	private Integer pageSize;
	/**
	 * First page
	 */
	private Integer firstPage;
	/**
	 * Last page
	 */
	private Integer lastPage;
	/**
	 * Next page
	 */
	private Integer nextPage;
	/**
	 * Previous page
	 */
	private Integer previousPage;

	public Paging() {
		// default constructor
	}

	public Paging(final Integer size, final Integer pageSize, final Integer page) {
		this.size = size;
		this.page = page;
		this.pageSize = pageSize;
		Integer pages = this.size / this.pageSize;
		if (this.size % this.pageSize > 0) {
			this.pages = pages + 1;
		} else {
			this.pages = pages;
		}
		if (this.page > this.pages) {
			this.page = this.pages;
		} else {
			this.page = page;
		}
		if (this.page == null || this.page <= 1) {
			this.firstPage = null;
			this.previousPage = null;
			this.page = 1;
		} else {
			this.firstPage = 1;
			this.previousPage = this.page - 1;
		}

		if (this.page.equals(this.pages)) {
			this.nextPage = null;
			this.lastPage = null;
		} else {
			if (this.pages > 1) {
				this.nextPage = this.page + 1;
				this.lastPage = this.pages;
			} else {
				this.nextPage = null;
				this.lastPage = null;
			}
		}
	}

	protected void processPage() {
		if (getPage() == null || getPage() <= 1) {
			setFirstPage(null);
			setPreviousPage(null);
			setPage(1);
		} else {
			setFirstPage(1);
			setPreviousPage(getPage() - 1);
		}

		if (getPage().equals(getPages())) {
			setNextPage(null);
			setLastPage(null);
		} else {
			if (getPages() > 1) {
				setNextPage(getPage() + 1);
				setLastPage(getPages());
			} else {
				setNextPage(null);
				setLastPage(null);
			}
		}
	}

	public Integer getFromIndex() {
		int inicio = (int) ((int) (getPage() * getPageSize()) - getPageSize());
		if (inicio < 0) {
			inicio = 0;
		}
		return inicio;
	}

	public Integer getToIndex() {
		int toIndex = (int) (getFromIndex() + getPageSize());
		if (getSize() < toIndex) {
			toIndex = getSize();
		}
		return toIndex;
	}

	public List<BEAN> getList() {
		return list;
	}

	public void setList(final List<BEAN> list) {
		this.list = list;
	}

	public Integer getPages() {
		return pages;
	}

	public void setPages(final Integer pages) {
		this.pages = pages;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(final Integer page) {
		this.page = page;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(final Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getFirstPage() {
		return firstPage;
	}

	public void setFirstPage(final Integer firstPage) {
		this.firstPage = firstPage;
	}

	public Integer getLastPage() {
		return lastPage;
	}

	public void setLastPage(final Integer lastPage) {
		this.lastPage = lastPage;
	}

	public Integer getNextPage() {
		return nextPage;
	}

	public void setNextPage(final Integer nextPage) {
		this.nextPage = nextPage;
	}

	public Integer getPreviousPage() {
		return previousPage;
	}

	public void setPreviousPage(final Integer previousPage) {
		this.previousPage = previousPage;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(final Integer size) {
		this.size = size;
	}
}