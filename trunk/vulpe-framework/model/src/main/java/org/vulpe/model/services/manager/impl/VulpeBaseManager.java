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
package org.vulpe.model.services.manager.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.TransactionAttributeType;
import javax.persistence.Transient;

import org.vulpe.commons.beans.Paging;
import org.vulpe.commons.factory.AbstractVulpeBeanFactory;
import org.vulpe.commons.util.VulpeReflectUtil;
import org.vulpe.exception.VulpeApplicationException;
import org.vulpe.model.annotations.Sufix;
import org.vulpe.model.annotations.TransactionType;
import org.vulpe.model.dao.VulpeDAO;
import org.vulpe.model.entity.VulpeEntity;
import org.vulpe.model.services.manager.VulpeManager;

/**
 * Default implementation to Manager for MAIN's.
 *
 * @author <a href="mailto:fabio.viana@vulpe.org">Fábio Viana</a>
 */
@TransactionType
@SuppressWarnings( { "unchecked" })
public class VulpeBaseManager<ENTITY_CLASS extends VulpeEntity<ENTITY_ID>, ENTITY_ID extends Serializable & Comparable, ENTITY_DAO extends VulpeDAO<ENTITY_CLASS, ENTITY_ID>>
		implements VulpeManager<ENTITY_CLASS, ENTITY_ID, ENTITY_DAO> {

	@Transient
	private transient Class<ENTITY_DAO> daoClass;

	/**
	 * Method returns DAO interface.
	 *
	 * @since 1.0
	 * @return
	 */
	protected Class<ENTITY_DAO> getDaoClass() {
		if (daoClass == null) {
			daoClass = (Class<ENTITY_DAO>) VulpeReflectUtil.getInstance().getIndexClass(getClass(), 2);
		}
		return daoClass;
	}

	/**
	 * Method returns implementation of DAO to current Manager.
	 *
	 * @since 1.0
	 * @return DAO implementation.
	 */
	protected ENTITY_DAO getDAO() {
		return (ENTITY_DAO) AbstractVulpeBeanFactory.getInstance().getBean(getDaoClass().getSimpleName());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.vulpe.model.services.manager.impl.VulpeBaseManager#
	 * create(org.vulpe.model.entity.VulpeEntity)
	 */
	@Sufix
	public ENTITY_CLASS create(final ENTITY_CLASS entity) throws VulpeApplicationException {
		createBefore(entity);
		final ENTITY_CLASS persistentEntity = getDAO().create(entity);
		createAfter(entity, persistentEntity);
		return persistentEntity;
	}

	/**
	 * Extension point to code rules before create.
	 */
	protected void createBefore(final ENTITY_CLASS entity) throws VulpeApplicationException {
		// extension point
	}

	/**
	 * Extension point to code rules after create.
	 */
	protected void createAfter(final ENTITY_CLASS entity, final ENTITY_CLASS persistentEntity)
			throws VulpeApplicationException {
		// extension point
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.vulpe.model.services.manager.impl.VulpeBaseManager#
	 * delete(org.vulpe.model.entity.VulpeEntity)
	 */
	@Sufix
	public void delete(final ENTITY_CLASS entity) throws VulpeApplicationException {
		deleteBefore(entity);
		getDAO().delete(entity);
		deleteAfter(entity);
	}

	/**
	 * Extension point to code rules before delete
	 */
	protected void deleteBefore(final ENTITY_CLASS entity) throws VulpeApplicationException {
		// extension point
	}

	/**
	 * Extension point to code rules after delete
	 */
	protected void deleteAfter(final ENTITY_CLASS entity) throws VulpeApplicationException {
		// extension point
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.vulpe.model.services.manager.impl.VulpeBaseManager#
	 * delete(java.util.List)
	 */
	@Sufix
	public void delete(final List<ENTITY_CLASS> entities) throws VulpeApplicationException {
		deleteBefore(entities);
		getDAO().delete(entities);
		deleteAfter(entities);
	}

	/**
	 * Extension point to code rules before delete.
	 */
	protected void deleteBefore(final List<ENTITY_CLASS> entities) throws VulpeApplicationException {
		// extension point
	}

	/**
	 * Extension point to code rules after delete.
	 */
	protected void deleteAfter(final List<ENTITY_CLASS> entities) throws VulpeApplicationException {
		// extension point
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.vulpe.model.services.manager.impl.VulpeBaseManager#
	 * read(org.vulpe.model.entity.VulpeEntity)
	 */
	@TransactionType(TransactionAttributeType.NOT_SUPPORTED)
	@Sufix
	public List<ENTITY_CLASS> read(final ENTITY_CLASS entity) throws VulpeApplicationException {
		readBefore(entity);
		final List<ENTITY_CLASS> entities = getDAO().read(entity);
		readAfter(entity, entities);
		return entities;
	}

	/**
	 * Extension point to code rules before read.
	 */
	protected void readBefore(final ENTITY_CLASS entity) throws VulpeApplicationException {
		// extension point
	}

	/**
	 * Extension point to code rules after read.
	 */
	protected void readAfter(final ENTITY_CLASS entity, final List<ENTITY_CLASS> entities)
			throws VulpeApplicationException {
		// extension point
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.vulpe.model.services.manager.impl.VulpeBaseManager#
	 * update(org.vulpe.model.entity.VulpeEntity)
	 */
	@Sufix
	public void update(final ENTITY_CLASS entity) throws VulpeApplicationException {
		updateBefore(entity);
		getDAO().update(entity);
		updateAfter(entity);
	}

	/**
	 * Extension point to code rules before update.
	 */
	protected void updateBefore(final ENTITY_CLASS entity) throws VulpeApplicationException {
		// extension point
	}

	/**
	 * Extension point to code rules after update.
	 */
	protected void updateAfter(final ENTITY_CLASS entity) throws VulpeApplicationException {
		// extension point
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.vulpe.model.services.manager.impl.VulpeBaseManager#
	 * find(org.vulpe.model.entity.VulpeEntity)
	 */
	@TransactionType(TransactionAttributeType.NOT_SUPPORTED)
	@Sufix
	public ENTITY_CLASS find(final ENTITY_CLASS entity) throws VulpeApplicationException {
		findBefore(entity.getId());
		final ENTITY_CLASS newEntity = getDAO().find(entity);
		findAfter(entity.getId(), newEntity);
		return newEntity;
	}

	/**
	 * Extension point to code rules before select entity.
	 */
	protected void findBefore(final ENTITY_ID entityId) throws VulpeApplicationException {
		// extension point
	}

	/**
	 * Extension point to code rules after select entity.
	 */
	protected void findAfter(final ENTITY_ID entityId, final ENTITY_CLASS entity) throws VulpeApplicationException {
		// extension point
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.vulpe.model.services.manager.impl.VulpeBaseManager#
	 * paging(org.vulpe.model.entity.VulpeEntity, java.lang.Integer,
	 * java.lang.Integer)
	 */
	@TransactionType(TransactionAttributeType.NOT_SUPPORTED)
	@Sufix
	public Paging<ENTITY_CLASS> paging(final ENTITY_CLASS entity, final Integer pageSize, final Integer page)
			throws VulpeApplicationException {
		pagingBefore(entity);
		final Paging<ENTITY_CLASS> paging = getDAO().paging(entity, pageSize, page);
		pagingAfter(entity, paging);
		return paging;
	}

	/**
	 * Extension point to code rules before paging entity.
	 */
	protected void pagingBefore(final ENTITY_CLASS entity) throws VulpeApplicationException {
		// extension point
	}

	/**
	 * Extension point to code rules after paging entity.
	 */
	protected void pagingAfter(final ENTITY_CLASS entity, final Paging<ENTITY_CLASS> paging)
			throws VulpeApplicationException {
		// extension point
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.vulpe.model.services.manager.impl.VulpeBaseManager#
	 * persist(java.util.List)
	 */
	@Sufix
	public List<ENTITY_CLASS> persist(final List<ENTITY_CLASS> entities) throws VulpeApplicationException {
		persistBefore(entities);
		final List<ENTITY_CLASS> entitiesPersist = new ArrayList<ENTITY_CLASS>();
		for (ENTITY_CLASS entity : entities) {
			if (entity.isSelected() && entity.getId() != null) {
				delete(entity);
			} else {
				// if not selected, insert or update
				if (!entity.isSelected()) {
					if (entity.getId() == null) {
						entity = create(entity);
					} else {
						update(entity);
					}
				}
				entitiesPersist.add(entity);
			}
		}
		persistAfter(entities, entitiesPersist);
		return entitiesPersist;
	}

	/**
	 * Extension point to code rules before persist entities.
	 *
	 * @param entities
	 * @since 1.0
	 * @throws VulpeApplicationException
	 */
	protected void persistBefore(final List<ENTITY_CLASS> entities) throws VulpeApplicationException {
		// extension point
	}

	/**
	 * Extension point to code rules after persist entities
	 */
	protected void persistAfter(final List<ENTITY_CLASS> entities, final List<ENTITY_CLASS> entitiesPersist)
			throws VulpeApplicationException {
		// extension point
	}
}