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
package org.vulpe.model.services.manager.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.TransactionAttributeType;
import javax.persistence.Transient;

import org.apache.log4j.Logger;
import org.vulpe.commons.beans.Paging;
import org.vulpe.commons.factory.AbstractVulpeBeanFactory;
import org.vulpe.commons.util.VulpeReflectUtil;
import org.vulpe.commons.util.VulpeValidationUtil;
import org.vulpe.exception.VulpeApplicationException;
import org.vulpe.model.annotations.GenerateSuffix;
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
public class VulpeBaseManager<ENTITY extends VulpeEntity<ID>, ID extends Serializable & Comparable<?>, DAO extends VulpeDAO<ENTITY, ID>>
		implements VulpeManager<ENTITY, ID, DAO> {

	protected static final Logger LOG = Logger.getLogger(VulpeBaseManager.class);

	@Transient
	private transient Class<DAO> daoClass;

	/**
	 * Method returns DAO interface.
	 *
	 * @since 1.0
	 * @return
	 */
	protected Class<DAO> getDaoClass() {
		if (daoClass == null) {
			daoClass = (Class<DAO>) VulpeReflectUtil.getIndexClass(getClass(), 2);
		}
		return daoClass;
	}

	/**
	 * Method returns implementation of DAO to current Manager.
	 *
	 * @since 1.0
	 * @return DAO implementation.
	 */
	protected DAO getDAO() {
		return (DAO) AbstractVulpeBeanFactory.getInstance().getBean(getDaoClass().getSimpleName());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.vulpe.model.services.manager.impl.VulpeBaseManager#
	 * create(org.vulpe.model.entity.VulpeEntity)
	 */
	@GenerateSuffix
	public ENTITY create(final ENTITY entity) throws VulpeApplicationException {
		createBefore(entity);
		final ENTITY persistentEntity = getDAO().create(entity);
		createAfter(entity, persistentEntity);
		return persistentEntity;
	}

	/**
	 * Extension point to code rules before create.
	 */
	protected void createBefore(final ENTITY entity) throws VulpeApplicationException {
		// extension point
	}

	/**
	 * Extension point to code rules after create.
	 */
	protected void createAfter(final ENTITY entity, final ENTITY persistentEntity) throws VulpeApplicationException {
		// extension point
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.vulpe.model.services.manager.impl.VulpeBaseManager#
	 * delete(org.vulpe.model.entity.VulpeEntity)
	 */
	@GenerateSuffix
	public void delete(final ENTITY entity) throws VulpeApplicationException {
		deleteBefore(entity);
		getDAO().delete(entity);
		deleteAfter(entity);
	}

	/**
	 * Extension point to code rules before delete
	 */
	protected void deleteBefore(final ENTITY entity) throws VulpeApplicationException {
		// extension point
	}

	/**
	 * Extension point to code rules after delete
	 */
	protected void deleteAfter(final ENTITY entity) throws VulpeApplicationException {
		// extension point
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.vulpe.model.services.manager.impl.VulpeBaseManager#
	 * delete(java.util.List)
	 */
	@GenerateSuffix
	public void delete(final List<ENTITY> entities) throws VulpeApplicationException {
		deleteBefore(entities);
		getDAO().delete(entities);
		deleteAfter(entities);
	}

	/**
	 * Extension point to code rules before delete.
	 */
	protected void deleteBefore(final List<ENTITY> entities) throws VulpeApplicationException {
		// extension point
	}

	/**
	 * Extension point to code rules after delete.
	 */
	protected void deleteAfter(final List<ENTITY> entities) throws VulpeApplicationException {
		// extension point
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.vulpe.model.services.manager.impl.VulpeBaseManager#
	 * read(org.vulpe.model.entity.VulpeEntity)
	 */
	@TransactionType(TransactionAttributeType.NEVER)
	@GenerateSuffix
	public List<ENTITY> read(final ENTITY entity) throws VulpeApplicationException {
		readBefore(entity);
		final List<ENTITY> entities = getDAO().read(entity);
		readAfter(entity, entities);
		return entities;
	}

	/**
	 * Extension point to code rules before read.
	 */
	protected void readBefore(final ENTITY entity) throws VulpeApplicationException {
		// extension point
	}

	/**
	 * Extension point to code rules after read.
	 */
	protected void readAfter(final ENTITY entity, final List<ENTITY> entities) throws VulpeApplicationException {
		// extension point
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.vulpe.model.services.manager.impl.VulpeBaseManager#
	 * update(org.vulpe.model.entity.VulpeEntity)
	 */
	@GenerateSuffix
	public ENTITY update(final ENTITY entity) throws VulpeApplicationException {
		updateBefore(entity);
		if (VulpeValidationUtil.isNotEmpty(entity.getDeletedDetails())) {
			final List<ENTITY> details = new ArrayList<ENTITY>();
			for (final VulpeEntity<?> detail : entity.getDeletedDetails()) {
				details.add((ENTITY) detail);
			}
			delete(details);
		}
		final ENTITY entityUpdated = getDAO().update(entity);
		updateAfter(entityUpdated);
		return entityUpdated;
	}

	/**
	 * Extension point to code rules before update.
	 */
	protected void updateBefore(final ENTITY entity) throws VulpeApplicationException {
		// extension point
	}

	/**
	 * Extension point to code rules after update.
	 */
	protected void updateAfter(final ENTITY entity) throws VulpeApplicationException {
		// extension point
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.vulpe.model.services.manager.VulpeManager#updateSomeAttributes(org
	 * .vulpe.model.entity.VulpeEntity)
	 */
	@GenerateSuffix
	public void updateSomeAttributes(ENTITY entity) throws VulpeApplicationException {
		updateSomeAttributesBefore(entity);
		getDAO().updateSomeAttributes(entity);
		updateSomeAttributesAfter(entity);
	}

	/**
	 * Extension point to code rules before update some attributes.
	 */
	protected void updateSomeAttributesBefore(final ENTITY entity) throws VulpeApplicationException {
		// extension point
	}

	/**
	 * Extension point to code rules after update some attributes.
	 */
	protected void updateSomeAttributesAfter(final ENTITY entity) throws VulpeApplicationException {
		// extension point
	}

	@GenerateSuffix
	public void updateSomeAttributes(final List<ENTITY> list) throws VulpeApplicationException {
		updateSomeAttributesBefore(list);
		getDAO().updateSomeAttributes(list);
		updateSomeAttributesAfter(list);
	}

	/**
	 * Extension point to code rules before update some attributes.
	 */
	protected void updateSomeAttributesBefore(final List<ENTITY> list) throws VulpeApplicationException {
		// extension point
	}

	/**
	 * Extension point to code rules after update some attributes.
	 */
	protected void updateSomeAttributesAfter(final List<ENTITY> list) throws VulpeApplicationException {
		// extension point
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.vulpe.model.services.manager.VulpeManager#updateSomeAttributes(org
	 * .vulpe.model.entity.VulpeEntity, java.util.List)
	 */
	@GenerateSuffix
	public void updateSomeAttributes(final ENTITY entity, final List<ID> ids) throws VulpeApplicationException {
		updateSomeAttributesBefore(entity, ids);
		getDAO().updateSomeAttributes(entity, ids);
		updateSomeAttributesAfter(entity, ids);
	}

	/**
	 * Extension point to code rules before update some attributes.
	 */
	protected void updateSomeAttributesBefore(final ENTITY entity, final List<ID> ids) throws VulpeApplicationException {
		// extension point
	}

	/**
	 * Extension point to code rules after update some attributes.
	 */
	protected void updateSomeAttributesAfter(final ENTITY entity, final List<ID> ids) throws VulpeApplicationException {
		// extension point
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.vulpe.model.services.manager.impl.VulpeBaseManager#
	 * find(org.vulpe.model.entity.VulpeEntity)
	 */
	@TransactionType(TransactionAttributeType.NEVER)
	@GenerateSuffix
	public ENTITY find(final ENTITY entity) throws VulpeApplicationException {
		findBefore(entity.getId());
		final ENTITY newEntity = getDAO().find(entity);
		findAfter(entity.getId(), newEntity);
		return newEntity;
	}

	/**
	 * Extension point to code rules before select entity.
	 */
	protected void findBefore(final ID entityId) throws VulpeApplicationException {
		// extension point
	}

	/**
	 * Extension point to code rules after select entity.
	 */
	protected void findAfter(final ID entityId, final ENTITY entity) throws VulpeApplicationException {
		// extension point
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.vulpe.model.services.manager.impl.VulpeBaseManager#
	 * paging(org.vulpe.model.entity.VulpeEntity, java.lang.Integer,
	 * java.lang.Integer)
	 */
	@TransactionType(TransactionAttributeType.NEVER)
	@GenerateSuffix
	public Paging<ENTITY> paging(final ENTITY entity, final Integer pageSize, final Integer page)
			throws VulpeApplicationException {
		pagingBefore(entity);
		final Paging<ENTITY> paging = getDAO().paging(entity, pageSize, page);
		pagingAfter(entity, paging);
		return paging;
	}

	/**
	 * Extension point to code rules before paging entity.
	 */
	protected void pagingBefore(final ENTITY entity) throws VulpeApplicationException {
		// extension point
	}

	/**
	 * Extension point to code rules after paging entity.
	 */
	protected void pagingAfter(final ENTITY entity, final Paging<ENTITY> paging) throws VulpeApplicationException {
		// extension point
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.vulpe.model.services.manager.impl.VulpeBaseManager#
	 * persist(java.util.List)
	 */
	@GenerateSuffix
	public List<ENTITY> persist(final List<ENTITY> entities) throws VulpeApplicationException {
		persistBefore(entities);
		final List<ENTITY> entitiesPersist = new ArrayList<ENTITY>();
		for (ENTITY entity : entities) {
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
	protected void persistBefore(final List<ENTITY> entities) throws VulpeApplicationException {
		// extension point
	}

	/**
	 * Extension point to code rules after persist entities
	 */
	protected void persistAfter(final List<ENTITY> entities, final List<ENTITY> entitiesPersist)
			throws VulpeApplicationException {
		// extension point
	}
}