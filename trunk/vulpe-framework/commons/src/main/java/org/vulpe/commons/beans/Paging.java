/**
 * Vulpe Framework - Quick and Smart ;)
 * Copyright (C) 2011 Active Thread
 * 
 * Este programa é software livre; você pode redistribuí-lo e/ou
 * modificá-lo sob os termos da Licença Pública Geral GNU, conforme
 * publicada pela Free Software Foundation; tanto a versão 2 da
 * Licença como (a seu critério) qualquer versão mais nova.
 * 
 * Este programa é distribuído na expectativa de ser útil, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia implícita de
 * COMERCIALIZAÇÃO ou de ADEQUAÇÃO A QUALQUER PROPÓSITO EM
 * PARTICULAR. Consulte a Licença Pública Geral GNU para obter mais
 * detalhes.
 * 
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU
 * junto com este programa; se não, escreva para a Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307, USA.
 */
/**
 * Vulpe Framework - Quick and Smart ;)
 * Copyright (C) 2011 Active Thread
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.vulpe.commons.beans;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import org.vulpe.commons.util.VulpeValidationUtil;

@XmlAccessorType(javax.xml.bind.annotation.XmlAccessType.PROPERTY)
@XmlType(name = "paging", propOrder = { "firstPage", "lastPage", "list", "realList", "nextPage",
		"page", "pages", "pageSize", "previousPage", "size" })
@SuppressWarnings("serial")
public class Paging<BEAN extends Serializable> implements Serializable {

	/**
	 * List of items paged
	 */
	private List<BEAN> list;
	/**
	 * List of all items
	 */
	private List<BEAN> realList;
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
		final Integer pages = this.size / this.pageSize;
		this.pages = this.size % this.pageSize > 0 ? pages + 1 : pages;
		this.page = this.page > this.pages ? this.pages : page;
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

	public void processPage() {
		if (VulpeValidationUtil.isNotEmpty(getRealList())) {
			setSize(getRealList().size());
			final Integer pages = getSize() / getPageSize();
			setPages(getSize() % getPageSize() > 0 ? pages + 1 : pages);
		}
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
		int start = (int) ((int) (getPage() * getPageSize()) - getPageSize());
		if (start < 0) {
			start = 0;
		}
		return start;
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

	public void setRealList(List<BEAN> realList) {
		this.realList = realList;
	}

	public List<BEAN> getRealList() {
		return realList;
	}

}