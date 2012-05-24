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
package org.vulpe.model.services.manager;

import java.io.Serializable;
import java.util.List;

import org.vulpe.commons.beans.Paging;
import org.vulpe.exception.VulpeApplicationException;
import org.vulpe.model.dao.VulpeDAO;
import org.vulpe.model.entity.VulpeEntity;

/**
 * Default Manager interface to MAIN's
 *
 * @author <a href="mailto:fabio.viana@vulpe.org">Fábio Viana</a>
 */
@SuppressWarnings("rawtypes")
public interface VulpeManager<ENTITY extends VulpeEntity<ID>, ID extends Serializable & Comparable, DAO extends VulpeDAO<ENTITY, ID>> {
	/**
	 * Method used to add business rules on create entity.
	 *
	 * @param entity
	 * @return Return id of created entity
	 * @throws VulpeApplicationException
	 */
	ENTITY create(ENTITY entity) throws VulpeApplicationException;

	/**
	 * Method used to add business rules on delete entity.
	 *
	 * @param entity
	 * @throws VulpeApplicationException
	 */
	void delete(ENTITY entity) throws VulpeApplicationException;

	/**
	 * Method used to add business rules on delete entities.
	 *
	 * @param entities
	 * @throws VulpeApplicationException
	 */
	void delete(List<ENTITY> entities) throws VulpeApplicationException;

	/**
	 * Method used to add business rules on update entity.
	 *
	 * @param entity
	 * @return
	 * @throws VulpeApplicationException
	 */
	ENTITY update(ENTITY entity) throws VulpeApplicationException;

	/**
	 *
	 * @param entity
	 * @throws VulpeApplicationException
	 */
	void updateSomeAttributes(ENTITY entity) throws VulpeApplicationException;

	void updateSomeAttributes(List<ENTITY> list) throws VulpeApplicationException;

	/**
	 *
	 * @param entity
	 * @param ids
	 * @throws VulpeApplicationException
	 */
	void updateSomeAttributes(ENTITY entity, List<ID> ids) throws VulpeApplicationException;

	/**
	 * Method used to add business rules on read list of entities.
	 *
	 * @param entity
	 * @return List of entities
	 * @throws VulpeApplicationException
	 */
	List<ENTITY> read(ENTITY entity) throws VulpeApplicationException;

	/**
	 * Method used to add business rules on paging entities.
	 *
	 * @param entity
	 * @param pageSize
	 * @param page
	 * @return
	 * @throws VulpeApplicationException
	 */
	Paging<ENTITY> paging(ENTITY entity, Integer pageSize, Integer page) throws VulpeApplicationException;

	/**
	 * Method used to add business rules on find entity by id.
	 *
	 * @param entity
	 * @return Entity
	 * @throws VulpeApplicationException
	 */
	ENTITY find(ENTITY entity) throws VulpeApplicationException;

	/**
	 * Method used to add business rules on persist list of entities.
	 *
	 * @param entities
	 * @return List of persisted entities
	 * @throws VulpeApplicationException
	 */
	List<ENTITY> persist(List<ENTITY> entities) throws VulpeApplicationException;
}