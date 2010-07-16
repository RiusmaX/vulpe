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
package org.vulpe.model.services.impl.ws.convert;

import java.util.ArrayList;

import org.vulpe.commons.beans.Paging;
import org.vulpe.exception.VulpeSystemException;
import org.vulpe.model.entity.VulpeBaseEntity;
import org.vulpe.model.entity.AbstractVulpeBaseEntityDelegate;

@SuppressWarnings("unchecked")
public class PagingWSConvert<BEAN extends VulpeBaseEntity<?>, BEANDELEGATE extends AbstractVulpeBaseEntityDelegate<?, ?>>
		implements WSConvert<Paging<BEAN>, Paging<BEANDELEGATE>> {

	public Paging<BEAN> toBean(final Paging<BEANDELEGATE> wsBean) {
		final Paging<BEAN> paging = new Paging<BEAN>();
		paging.setFirstPage(wsBean.getFirstPage());
		paging.setLastPage(wsBean.getLastPage());
		paging.setNextPage(wsBean.getNextPage());
		paging.setPage(wsBean.getPage());
		paging.setPages(wsBean.getPages());
		paging.setPageSize(wsBean.getPageSize());
		paging.setPreviousPage(wsBean.getPreviousPage());
		paging.setSize(wsBean.getSize());
		paging.setList(new ArrayList<BEAN>());
		for (BEANDELEGATE beanDelegate : wsBean.getList()) {
			paging.getList().add((BEAN) beanDelegate.getBean());
		}
		return paging;
	}

	public Paging<BEANDELEGATE> toWSBean(final Paging<BEAN> bean) {
		final Paging<BEANDELEGATE> paging = new Paging<BEANDELEGATE>();
		paging.setFirstPage(bean.getFirstPage());
		paging.setLastPage(bean.getLastPage());
		paging.setNextPage(bean.getNextPage());
		paging.setPage(bean.getPage());
		paging.setPages(bean.getPages());
		paging.setPageSize(bean.getPageSize());
		paging.setPreviousPage(bean.getPreviousPage());
		paging.setSize(bean.getSize());
		paging.setList(new ArrayList<BEANDELEGATE>());
		for (BEAN beanDelegate : bean.getList()) {
			paging.getList().add(getInstanceVO(beanDelegate));
		}
		return paging;
	}

	private BEANDELEGATE getInstanceVO(final BEAN bean) {
		if (bean == null) {
			return null;
		}

		final String className = bean.getClass().getName() + "Delegate";
		try {
			final Class classe = Class.forName(className);
			return (BEANDELEGATE) classe.getConstructor(bean.getClass()).newInstance(bean);
		} catch (Exception e) {
			throw new VulpeSystemException(e);
		}
	}
}