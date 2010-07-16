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
package org.vulpe.model.dao;

import java.io.Serializable;
import java.util.List;

import org.vulpe.commons.beans.Paging;
import org.vulpe.exception.VulpeApplicationException;
import org.vulpe.model.entity.VulpeBaseEntity;

/**
 * Default Interface of DAO for CRUD's
 * 
 * @author <a href="mailto:fabio.viana@activethread.com.br">Fábio Viana</a>
 */
@SuppressWarnings("unchecked")
public interface VulpeBaseCRUDDAO<ENTITY extends VulpeBaseEntity<ID>, ID extends Serializable & Comparable>
		extends VulpeBaseDAO {
	/**
	 * Creates ENTITY
	 * 
	 * @param entity
	 * @return ENTITY
	 */
	ENTITY create(ENTITY entity) throws VulpeApplicationException;

	/**
	 * Returns list of ENTITY
	 * 
	 * @param entity
	 * @return List of ENTITY filter by parameters of ENTITY
	 */
	List<ENTITY> read(ENTITY entity) throws VulpeApplicationException;

	/**
	 * Returns list of ENTITY with paging.
	 * 
	 * @param entity
	 * @param pageSize
	 *            Page size
	 * @param page
	 *            Current page
	 * @return
	 * @throws VulpeApplicationException
	 */
	Paging<ENTITY> paging(ENTITY entity, Integer pageSize, Integer page)
			throws VulpeApplicationException;

	/**
	 * Updates ENTITY.
	 * 
	 * @param entity
	 */
	void update(ENTITY entity) throws VulpeApplicationException;

	/**
	 * Remove ENTITY.
	 * 
	 * @param entity
	 */
	void delete(ENTITY entity) throws VulpeApplicationException;

	/**
	 * Remove list of ENTITY.
	 * 
	 * @param entity
	 */
	void delete(List<ENTITY> entities) throws VulpeApplicationException;

	/**
	 * Returns ENTITY by id.
	 * 
	 * @param id
	 * @return ENTITY
	 */
	ENTITY find(ID id) throws VulpeApplicationException;
}