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
package org.vulpe.model.services.impl.ws.convert;

import java.util.ArrayList;

import org.vulpe.commons.beans.Paging;
import org.vulpe.exception.VulpeSystemException;
import org.vulpe.model.entity.VulpeEntity;
import org.vulpe.model.entity.impl.AbstractVulpeBaseEntityDelegate;

@SuppressWarnings("unchecked")
public class PagingWSConvert<BEAN extends VulpeEntity<?>, BEANDELEGATE extends AbstractVulpeBaseEntityDelegate<?, ?>>
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