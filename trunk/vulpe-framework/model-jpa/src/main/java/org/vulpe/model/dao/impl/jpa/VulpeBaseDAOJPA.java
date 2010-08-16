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
package org.vulpe.model.dao.impl.jpa;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.Transient;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.orm.jpa.JpaCallback;
import org.vulpe.audit.model.entity.AuditOccurrenceType;
import org.vulpe.commons.beans.Paging;
import org.vulpe.commons.util.VulpeReflectUtil;
import org.vulpe.commons.util.VulpeValidationUtil;
import org.vulpe.commons.util.VulpeReflectUtil.DeclaredType;
import org.vulpe.exception.VulpeApplicationException;
import org.vulpe.exception.VulpeSystemException;
import org.vulpe.model.annotations.IgnoreAutoFilter;
import org.vulpe.model.annotations.Like;
import org.vulpe.model.annotations.NotExistEqual;
import org.vulpe.model.annotations.OrderBy;
import org.vulpe.model.annotations.Param;
import org.vulpe.model.annotations.Like.LikeType;
import org.vulpe.model.entity.VulpeEntity;
import org.vulpe.model.entity.VulpeLogicEntity;
import org.vulpe.model.entity.VulpeLogicEntity.Status;

/**
 * Default implementation of DAO with JPA.
 *
 * @author <a href="mailto:fabio.viana@activethread.com.br">Fábio Viana</a>
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 */
@SuppressWarnings( { "unchecked" })
public class VulpeBaseDAOJPA<ENTITY extends VulpeEntity<ID>, ID extends Serializable & Comparable>
		extends AbstractVulpeBaseDAOJPA<ENTITY, ID> {

	private static final Logger LOG = Logger.getLogger(VulpeBaseDAOJPA.class.getName());

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.vulpe.model.dao.VulpeDAO#create(org.vulpe.model.entity.VulpeEntity)
	 */
	public ENTITY create(final ENTITY entity) throws VulpeApplicationException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Creating object: ".concat(entity.toString()));
		}
		if (entity instanceof VulpeLogicEntity) {
			final VulpeLogicEntity logicEntity = (VulpeLogicEntity) entity;
			logicEntity.setStatus(Status.C);
		}
		final ENTITY newEntity = merge(entity);
		audit(newEntity, AuditOccurrenceType.INSERT, null);
		return newEntity;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.vulpe.model.dao.VulpeDAO#delete(org.vulpe.model.entity.VulpeEntity)
	 */
	public void delete(final ENTITY entity) throws VulpeApplicationException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Deleting object: ".concat(entity.toString()));
		}
		// persistent entity
		final ENTITY entityDeleted = (ENTITY) getJpaTemplate().getReference(entity.getClass(),
				entity.getId());
		audit(entity, AuditOccurrenceType.DELETE, null);
		if (entity instanceof VulpeLogicEntity) {
			final VulpeLogicEntity logicEntity = (VulpeLogicEntity) entityDeleted;
			logicEntity.setStatus(Status.D);
			// make merge of entity
			merge(entityDeleted);
		} else {
			getJpaTemplate().remove(entityDeleted);
		}
	}

	public void delete(final List<ENTITY> entities) throws VulpeApplicationException {
		for (ENTITY entity : entities) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("Deleting object: ".concat(entity.toString()));
			}
			// persistent entity
			final ENTITY entityDeleted = (ENTITY) getJpaTemplate().getReference(entity.getClass(),
					entity.getId());
			if (entity instanceof VulpeLogicEntity) {
				final VulpeLogicEntity logicEntity = (VulpeLogicEntity) entityDeleted;
				logicEntity.setStatus(Status.D);
				// make merge of entity
				merge(entityDeleted);
			} else {
				getJpaTemplate().remove(entityDeleted);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.vulpe.model.dao.VulpeDAO#update(org.vulpe.model.entity.VulpeEntity)
	 */
	public void update(final ENTITY entity) throws VulpeApplicationException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Updating object: ".concat(entity.toString()));
		}
		audit(entity, AuditOccurrenceType.UPDATE, null);
		if (entity instanceof VulpeLogicEntity) {
			final VulpeLogicEntity logicEntity = (VulpeLogicEntity) entity;
			logicEntity.setStatus(Status.U);
		}
		merge(entity);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.vulpe.model.dao.impl.AbstractVulpeBaseDAO#find(java
	 * .io.Serializable)
	 */
	public ENTITY find(final ID identifier) throws VulpeApplicationException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Retriving id: ".concat(identifier.toString()));
		}

		final ENTITY entity = getJpaTemplate().find(getEntityClass(), identifier);
		if (entity instanceof VulpeLogicEntity) {
			final VulpeLogicEntity logicEntity = (VulpeLogicEntity) entity;
			if (Status.D.equals(logicEntity.getStatus())) {
				return null;
			}
		}
		return entity;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.vulpe.model.dao.VulpeDAO#read(org.vulpe.model.entity.VulpeEntity)
	 */
	public List<ENTITY> read(final ENTITY entity) throws VulpeApplicationException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Reading object: ".concat(entity.toString()));
		}
		final Map<String, Object> params = new HashMap<String, Object>();
		final String hql = getHQL(entity, params);

		return execute(hql, params);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.vulpe.model.dao.VulpeDAO#paging(org.vulpe.model.entity.VulpeEntity,
	 * java.lang.Integer, java.lang.Integer)
	 */
	public Paging<ENTITY> paging(final ENTITY entity, final Integer pageSize, final Integer page)
			throws VulpeApplicationException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Paging object: ".concat(entity.toString()));
		}
		final Map<String, Object> params = new HashMap<String, Object>();
		final String hql = getHQL(entity, params);

		// getting total records
		final Long size = (Long) getJpaTemplate().execute(new JpaCallback() {
			public Object doInJpa(final EntityManager entityManager) throws PersistenceException {
				StringBuilder hqlCount = new StringBuilder();
				hqlCount.append("select count(*) from ");
				hqlCount.append(entity.getClass().getSimpleName()).append(
						" objc where objc.id in (");
				hqlCount.append("select distinct obj.id ");
				String hqlString = hql.substring(hql.toLowerCase().indexOf("from"));
				if (hqlString.contains("order by")) {
					hqlString = hqlString.substring(0, hqlString.toLowerCase().indexOf("order by"));
				}
				hqlCount.append(hqlString);
				hqlCount.append(")");
				final Query query = entityManager.createQuery(hqlCount.toString());
				setParams(query, params);
				return query.getSingleResult();
			}
		});

		final Paging paging = new Paging<ENTITY>(size.intValue(), pageSize, page);
		if (size.longValue() > 0) {
			// getting list by size of page
			paging.setList((List<ENTITY>) getJpaTemplate().execute(new JpaCallback() {
				public Object doInJpa(final EntityManager entityManager)
						throws PersistenceException {
					final Query query = entityManager.createQuery(hql);
					setParams(query, params);
					query.setFirstResult(paging.getFromIndex());
					query.setMaxResults(pageSize);
					return query.getResultList();
				}
			}));
		}

		return paging;
	}

	/**
	 *
	 * @param entity
	 * @param params
	 * @return
	 */
	protected String getHQL(final ENTITY entity, final Map<String, Object> params) {
		final List<Field> fields = VulpeReflectUtil.getInstance().getFields(entity.getClass());
		final StringBuilder order = new StringBuilder();
		int countParam = 0;
		if (StringUtils.isNotEmpty(entity.getAutoComplete())) {
			try {
				String value = "%" + PropertyUtils.getProperty(entity, entity.getAutoComplete())
						+ "%";
				params.put(entity.getAutoComplete(), value);
			} catch (Exception e) {
				throw new VulpeSystemException(e);
			}
		} else {
			for (Field field : fields) {
				if ((field.isAnnotationPresent(IgnoreAutoFilter.class)
						|| field.isAnnotationPresent(Transient.class) || Modifier.isTransient(field
						.getModifiers()))
						&& !field.isAnnotationPresent(Param.class)) {
					continue;
				}
				final OrderBy orderBy = field.getAnnotation(OrderBy.class);
				if (orderBy != null) {
					if (StringUtils.isNotBlank(order.toString())) {
						order.append(",");
					}
					order.append("obj.").append(field.getName()).append(" ").append(
							orderBy.type().name());
				}
				Object value = null;
				try {
					value = PropertyUtils.getProperty(entity, field.getName());
				} catch (Exception e) {
					throw new VulpeSystemException(e);
				}
				if (isNotEmpty(value)) {
					final String paramName = field.getName();
					final Like like = field.getAnnotation(Like.class);
					if (like != null) {
						if (like.type().equals(LikeType.BEGIN)) {
							value = value + "%";
						} else if (like.type().equals(LikeType.END)) {
							value = "%" + value;
						} else {
							value = "%" + value + "%";
						}
					}
					params.put(paramName, value);
					countParam++;
				}
			}
		}

		final StringBuilder hql = new StringBuilder();
		final NamedQuery namedQuery = getNamedQuery(getEntityClass(), getEntityClass()
				.getSimpleName().concat(".read"));
		if (namedQuery == null) {
			hql.append("select obj");
			if (StringUtils.isNotEmpty(entity.getAutoComplete())) {
				hql.append(".id, obj.").append(entity.getAutoComplete());
			}
			hql.append(" from ");
			hql.append(entity.getClass().getSimpleName());
			hql.append(" obj");
		} else {
			hql.append(namedQuery.query());
		}

		if (!params.isEmpty()) {
			if (!hql.toString().toLowerCase().contains("where")) {
				hql.append(" where ");
			}

			int count = 0;
			for (String name : params.keySet()) {
				final Object value = params.get(name);
				count++;
				final Param param = VulpeReflectUtil.getInstance().getAnnotationInField(
						Param.class, entity.getClass(), name);
				if (param == null) {
					if (value instanceof String) {
						final Like like = VulpeReflectUtil.getInstance().getAnnotationInField(
								Like.class, entity.getClass(), name);
						hql.append("upper(obj.").append(name).append(") ").append(
								like != null ? "like" : "=").append(" upper(:").append(name)
								.append(")");
					} else {
						hql.append("obj.").append(name).append(" = :").append(name);
					}
				} else {
					hql.append(param.alias());
					hql.append('.');
					if (param.name().equals("")) {
						hql.append(name);
					} else {
						hql.append(param.name());
					}
					hql.append(" ");
					final Like like = VulpeReflectUtil.getInstance().getAnnotationInField(
							Like.class, entity.getClass(), name);
					if (like != null) {
						hql.append("like");
					} else {
						hql.append(param.operator().getValue());
					}
					hql.append(" :").append(name);
				}
				if (count < countParam) {
					hql.append(" and ");
				}
			}
		}

		if (entity instanceof VulpeLogicEntity) {
			if (hql.toString().toLowerCase().contains("where")) {
				hql.append(" and");
			} else {
				hql.append(" where");
			}
			hql.append(" obj.status <> :status");
			params.put("status", Status.D);
		}

		// add order by
		if (StringUtils.isNotEmpty(entity.getOrderBy()) || StringUtils.isNotEmpty(order.toString())) {
			if (hql.toString().toLowerCase().contains("order by")) {
				hql.append(",");
			} else {
				hql.append(" order by");
			}
			hql.append(" ");
			hql.append(StringUtils.isNotEmpty(order.toString()) ? order.toString() : entity
					.getOrderBy());
		}
		return hql.toString();
	}

	@Transient
	private transient Class<ENTITY> entityClass;
	{
		getEntityClass();
	}

	protected Class<ENTITY> getEntityClass() {
		if (entityClass == null) {
			final DeclaredType declaredType = VulpeReflectUtil.getInstance().getDeclaredType(
					getClass(), getClass().getGenericSuperclass());
			if (declaredType.getItems().isEmpty()) {
				return null;
			}
			entityClass = (Class<ENTITY>) declaredType.getItems().get(0).getType();
		}
		return entityClass;
	}

	public void setEntityClass(final Class<ENTITY> entityClass) {
		this.entityClass = entityClass;
	}

	/**
	 *
	 * @param value
	 * @return
	 */
	public boolean isNotEmpty(final Object value) {
		if (VulpeValidationUtil.isNotEmpty(value)) {
			if (value instanceof VulpeEntity) {
				final VulpeEntity entity = (VulpeEntity) value;
				return VulpeValidationUtil.isNotEmpty(entity.getId());
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.vulpe.model.dao.VulpeDAO#exists(org.vulpe.model.entity.VulpeEntity)
	 */
	@Override
	public boolean exists(final ENTITY entity) throws VulpeApplicationException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Exists object: ".concat(entity.toString()));
		}
		final NotExistEqual notExistEqual = entity.getClass().getAnnotation(NotExistEqual.class);
		if (notExistEqual != null) {
			final Param[] params = notExistEqual.params();
			// getting total records
			final Long size = (Long) getJpaTemplate().execute(new JpaCallback() {
				public Object doInJpa(final EntityManager entityManager)
						throws PersistenceException {
					final StringBuilder hql = new StringBuilder();
					hql.append("select count(*) from ");
					hql.append(entity.getClass().getSimpleName()).append(" obj where ");
					final Map<String, Object> values = new HashMap<String, Object>();
					for (Param param : params) {
						hql.append("obj.").append(param.name()).append(" ").append(
								param.operator().getValue()).append(" :").append(param.name());
						try {
							values.put(param.name(), PropertyUtils
									.getProperty(entity, param.name()));
						} catch (Exception e) {
							LOG.error(e);
						}
					}
					final Query query = entityManager.createQuery(hql.toString());
					setParams(query, values);
					return query.getSingleResult();
				}
			});
			return size > 0;
		}
		return false;
	}

}