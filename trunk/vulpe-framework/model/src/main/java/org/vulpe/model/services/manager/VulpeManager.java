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
package org.vulpe.model.services.manager;

import java.io.Serializable;
import java.util.List;

import org.vulpe.commons.beans.Paging;
import org.vulpe.exception.VulpeApplicationException;
import org.vulpe.model.dao.VulpeDAO;
import org.vulpe.model.entity.VulpeEntity;

/**
 * Default Manager interface to CRUD's
 *
 * @author <a href="mailto:fabio.viana@vulpe.org">Fábio Viana</a>
 */
@SuppressWarnings("unchecked")
public interface VulpeManager<ENTITY_CLASS extends VulpeEntity<ENTITY_ID>, ENTITY_ID extends Serializable & Comparable, ENTITY_DAO extends VulpeDAO<ENTITY_CLASS, ENTITY_ID>> {
	/**
	 * Method used to add business rules on create entity.
	 *
	 * @param entity
	 * @return Return id of created entity
	 * @throws VulpeApplicationException
	 */
	ENTITY_CLASS create(ENTITY_CLASS entity) throws VulpeApplicationException;

	/**
	 * Method used to add business rules on delete entity.
	 *
	 * @param entity
	 * @throws VulpeApplicationException
	 */
	void delete(ENTITY_CLASS entity) throws VulpeApplicationException;

	/**
	 * Method used to add business rules on delete entities.
	 *
	 * @param entities
	 * @throws VulpeApplicationException
	 */
	void delete(List<ENTITY_CLASS> entities) throws VulpeApplicationException;

	/**
	 * Method used to add business rules on update entity.
	 *
	 * @param entity
	 * @throws VulpeApplicationException
	 */
	void update(ENTITY_CLASS entity) throws VulpeApplicationException;

	/**
	 * Method used to add business rules on read list of entities.
	 *
	 * @param entity
	 * @return List of entities
	 * @throws VulpeApplicationException
	 */
	List<ENTITY_CLASS> read(ENTITY_CLASS entity) throws VulpeApplicationException;

	/**
	 * Method used to add business rules on paging entities.
	 *
	 * @param entity
	 * @param pageSize
	 * @param page
	 * @return
	 * @throws VulpeApplicationException
	 */
	Paging<ENTITY_CLASS> paging(ENTITY_CLASS entity, Integer pageSize, Integer page)
			throws VulpeApplicationException;

	/**
	 * Method used to add business rules on find entity by id.
	 *
	 * @param entity
	 * @return Entity
	 * @throws VulpeApplicationException
	 */
	ENTITY_CLASS find(ENTITY_CLASS entity) throws VulpeApplicationException;

	/**
	 * Method used to add business rules on persist list of entities.
	 *
	 * @param entities
	 * @return List of persisted entities
	 * @throws VulpeApplicationException
	 */
	List<ENTITY_CLASS> persist(List<ENTITY_CLASS> entities) throws VulpeApplicationException;
}