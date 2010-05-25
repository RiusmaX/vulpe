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
package org.vulpe.view.struts.form.beans;

import java.io.Serializable;
import java.util.List;

import org.vulpe.commons.beans.Paging;


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