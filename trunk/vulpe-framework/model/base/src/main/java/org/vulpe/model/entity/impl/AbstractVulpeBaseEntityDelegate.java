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
package org.vulpe.model.entity.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import org.vulpe.exception.VulpeSystemException;
import org.vulpe.model.entity.VulpeEntity;

/**
 * Default class Entity Delegate to transmit data on WebServices-Client
 *
 * @author <a href="mailto:fabio.viana@vulpe.org">Fábio Viana</a>
 */
@SuppressWarnings({ "unchecked", "serial", "rawtypes" })
public abstract class AbstractVulpeBaseEntityDelegate<T extends VulpeEntity<ID>, ID extends Serializable & Comparable<VulpeEntity<ID>>>
		implements Serializable {

	private T bean;

	public T getBean() {
		if (this.bean == null && getClass().getGenericSuperclass() instanceof ParameterizedType) {
			final ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
			if (type.getActualTypeArguments() != null && type.getActualTypeArguments().length > 0
					&& type.getActualTypeArguments()[0] instanceof Class) {
				try {
					this.bean = (T) ((Class) type.getActualTypeArguments()[0]).newInstance();
				} catch (Exception e) {
					throw new VulpeSystemException(e);
				}
			}
		}
		return this.bean;
	}

	public AbstractVulpeBaseEntityDelegate() {
		// default constructor
	}

	public AbstractVulpeBaseEntityDelegate(final T baseEntity) {
		this.bean = baseEntity;
	}
}