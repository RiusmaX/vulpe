package org.vulpe.model.services.impl.ws.convert;

import java.util.ArrayList;

import org.vulpe.common.beans.Paging;
import org.vulpe.exception.VulpeSystemException;
import org.vulpe.model.entity.VulpeBaseEntity;
import org.vulpe.model.entity.AbstractVulpeBaseEntityDelegate;

public class PagingWSConvert<BEAN extends VulpeBaseEntity<?>, BEANDELEGATE extends AbstractVulpeBaseEntityDelegate<?, ?>>
		implements WSConvert<Paging<BEAN>, Paging<BEANDELEGATE>> {

	@SuppressWarnings("unchecked")
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

	@SuppressWarnings("unchecked")
	private BEANDELEGATE getInstanceVO(final BEAN bean) {
		if (bean == null) {
			return null;
		}

		final String className = bean.getClass().getName() + "Delegate";
		try {
			final Class classe = Class.forName(className);
			return (BEANDELEGATE) classe.getConstructor(bean.getClass())
					.newInstance(bean);
		} catch (Exception e) {
			throw new VulpeSystemException(e);
		}
	}
}