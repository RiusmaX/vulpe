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

import java.util.Collection;

import org.vulpe.exception.VulpeSystemException;
import org.vulpe.model.entity.VulpeEntity;
import org.vulpe.model.entity.impl.AbstractVulpeBaseEntityDelegate;

/**
 * 
 * @author <a href="mailto:fabio.viana@vulpe.org">Fábio Viana</a>
 */
@SuppressWarnings({ "unchecked" })
public class ListBeanWSConvert<ENTITY extends VulpeEntity, VODELEGATE extends AbstractVulpeBaseEntityDelegate>
		implements WSConvert<Collection<ENTITY>, Collection<VODELEGATE>> {

	public Collection<ENTITY> toBean(final Collection<VODELEGATE> wsBean) {
		if (wsBean != null) {
			try {
				final Collection<ENTITY> collection = wsBean.getClass().newInstance();
				for (VODELEGATE voDelegate : wsBean) {
					collection.add((ENTITY) voDelegate.getBean());
				}
				return collection;
			} catch (Exception e) {
				throw new VulpeSystemException(e);
			}
		}
		return null;
	}

	public Collection<VODELEGATE> toWSBean(final Collection<ENTITY> bean) {
		if (bean != null) {
			try {
				final Collection<VODELEGATE> collection = bean.getClass().newInstance();
				for (ENTITY vo : bean) {
					collection.add(getInstanceVO(vo));
				}
				return collection;
			} catch (Exception e) {
				throw new VulpeSystemException(e);
			}
		}
		return null;
	}

	private VODELEGATE getInstanceVO(final ENTITY bean) {
		if (bean == null) {
			return null;
		}

		final String className = bean.getClass().getName() + "Delegate";
		try {
			final Class classe = Class.forName(className);
			return (VODELEGATE) classe.getConstructor(bean.getClass()).newInstance(bean);
		} catch (Exception e) {
			throw new VulpeSystemException(e);
		}
	}
}