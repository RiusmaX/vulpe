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
package org.vulpe.model.services.impl.pojo;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vulpe.exception.VulpeApplicationException;
import org.vulpe.model.dao.impl.jpa.VulpeBaseDAOJPA;
import org.vulpe.model.entity.VulpeEntity;
import org.vulpe.model.entity.impl.AbstractVulpeBaseEntity;
import org.vulpe.model.services.GenericService;

/**
 * 
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 */
@SuppressWarnings( { "unchecked" })
@Service("GenericService")
@Transactional
public class GenericServiceJPAPOJO<ENTITY extends AbstractVulpeBaseEntity<ID>, ID extends Serializable & Comparable>
		implements GenericService {

	private static final Logger LOG = Logger.getLogger(GenericServiceJPAPOJO.class);

	@PersistenceContext
	private EntityManager entityManager;

	private VulpeBaseDAOJPA<ENTITY, ID> dao = new VulpeBaseDAOJPA<ENTITY, ID>();

	@PostConstruct
	public void postConstruct() {
		dao.setEntityManager(entityManager);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.model.services.GenericService#getList(
	 * org.vulpe.model.entity.VulpeEntity)
	 */
	public <T extends VulpeEntity<?>> List<T> getList(final T entity) {
		List<T> list = null;
		try {
			dao.setEntityClass((Class<ENTITY>) entity.getClass());
			list = (List<T>) dao.read((ENTITY) entity);
		} catch (VulpeApplicationException e) {
			LOG.error(e);
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.model.services.GenericService#exists(org.vulpe.model.entity
	 * .VulpeEntity)
	 */
	@Override
	public <T extends VulpeEntity<?>> boolean exists(T entity) {
		try {
			dao.setEntityClass((Class<ENTITY>) entity.getClass());
			return dao.exists((ENTITY) entity);
		} catch (VulpeApplicationException e) {
			LOG.error(e);
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.model.services.GenericService#notExistEquals(org.vulpe.model
	 * .entity.VulpeEntity)
	 */
	@Override
	public <T extends VulpeEntity<?>> boolean notExistEquals(T entity) {
		try {
			dao.setEntityClass((Class<ENTITY>) entity.getClass());
			return dao.notExistEquals((ENTITY) entity);
		} catch (VulpeApplicationException e) {
			LOG.error(e);
		}
		return false;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

}