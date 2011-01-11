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

import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Query;
import javax.persistence.Transient;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import org.vulpe.audit.model.entity.AuditOccurrenceType;
import org.vulpe.commons.VulpeConstants.Model.Entity;
import org.vulpe.commons.beans.Paging;
import org.vulpe.commons.util.VulpeReflectUtil;
import org.vulpe.commons.util.VulpeValidationUtil;
import org.vulpe.commons.util.VulpeReflectUtil.DeclaredType;
import org.vulpe.exception.VulpeApplicationException;
import org.vulpe.exception.VulpeSystemException;
import org.vulpe.model.annotations.Autocomplete;
import org.vulpe.model.annotations.IgnoreAutoFilter;
import org.vulpe.model.annotations.Like;
import org.vulpe.model.annotations.NotExistEqual;
import org.vulpe.model.annotations.OrderBy;
import org.vulpe.model.annotations.Parameter;
import org.vulpe.model.annotations.QueryConfiguration;
import org.vulpe.model.annotations.QueryConfigurations;
import org.vulpe.model.annotations.QueryParameter;
import org.vulpe.model.annotations.Like.LikeType;
import org.vulpe.model.entity.VulpeEntity;
import org.vulpe.model.entity.VulpeLogicEntity;
import org.vulpe.model.entity.VulpeLogicEntity.Status;

/**
 * Default implementation of DAO with JPA.
 * 
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 */
@SuppressWarnings( { "unchecked" })
@Repository
public class VulpeBaseDAOJPA<ENTITY extends VulpeEntity<ID>, ID extends Serializable & Comparable> extends
		AbstractVulpeBaseDAOJPA<ENTITY, ID> {

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
		newEntity.setMap(entity.getMap());
		loadEntityRelationships(newEntity);
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
		final ENTITY entityDeleted = (ENTITY) getEntityManager().getReference(entity.getClass(), entity.getId());
		audit(entity, AuditOccurrenceType.DELETE, null);
		if (entity instanceof VulpeLogicEntity) {
			final VulpeLogicEntity logicEntity = (VulpeLogicEntity) entityDeleted;
			logicEntity.setStatus(Status.D);
			// make merge of entity
			merge(entityDeleted);
		} else {
			getEntityManager().remove(entityDeleted);
		}
	}

	public void delete(final List<ENTITY> entities) throws VulpeApplicationException {
		for (ENTITY entity : entities) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("Deleting object: ".concat(entity.toString()));
			}
			// persistent entity
			final ENTITY entityDeleted = (ENTITY) getEntityManager().getReference(entity.getClass(), entity.getId());
			if (entity instanceof VulpeLogicEntity) {
				final VulpeLogicEntity logicEntity = (VulpeLogicEntity) entityDeleted;
				logicEntity.setStatus(Status.D);
				// make merge of entity
				merge(entityDeleted);
			} else {
				getEntityManager().remove(entityDeleted);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.model.dao.VulpeDAO#update(org.vulpe.model.entity.VulpeEntity)
	 */
	public ENTITY update(final ENTITY entity) throws VulpeApplicationException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Updating object: ".concat(entity.toString()));
		}
		audit(entity, AuditOccurrenceType.UPDATE, null);
		if (entity instanceof VulpeLogicEntity) {
			final VulpeLogicEntity logicEntity = (VulpeLogicEntity) entity;
			logicEntity.setStatus(Status.U);
		}
		return merge(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.model.dao.impl.AbstractVulpeBaseDAO#find(java
	 * .io.Serializable)
	 */
	public ENTITY find(final ENTITY entity) throws VulpeApplicationException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Retriving id: ".concat(entity.getId().toString()));
		}
		final ENTITY newEntity = getEntityManager().find(getEntityClass(), entity.getId());
		if (newEntity.getId() instanceof VulpeLogicEntity) {
			final VulpeLogicEntity logicEntity = (VulpeLogicEntity) newEntity;
			if (Status.D.equals(logicEntity.getStatus())) {
				return null;
			}
		}
		newEntity.setMap(entity.getMap());
		loadEntityRelationships(newEntity);
		return newEntity;
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
		final List<ENTITY> entities = execute(hql, params);
		loadRelationships(entities, params, false);
		return entities;
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
		StringBuilder hqlCount = new StringBuilder();
		hqlCount.append("select count(*) from ");
		hqlCount.append(entity.getClass().getSimpleName()).append(" objc where objc.id in (");
		hqlCount.append("select distinct obj.id ");
		String hqlString = hql.substring(hql.toLowerCase().indexOf("from"));
		if (hqlString.contains("order by")) {
			hqlString = hqlString.substring(0, hqlString.toLowerCase().indexOf("order by"));
		}
		hqlCount.append(hqlString);
		hqlCount.append(")");
		Query query = getEntityManager().createQuery(hqlCount.toString());
		setParams(query, params);
		final Long size = (Long) query.getSingleResult();

		final Paging paging = new Paging<ENTITY>(size.intValue(), pageSize, page);
		if (size.longValue() > 0) {
			// getting list by size of page
			query = getEntityManager().createQuery(hql);
			setParams(query, params);
			query.setFirstResult(paging.getFromIndex());
			query.setMaxResults(pageSize);
			final List<ENTITY> entities = query.getResultList();
			for (ENTITY entity2 : entities) {
				entity2.setMap(entity.getMap());
			}
			loadRelationships(entities, params, false);
			paging.setList(entities);
		}

		return paging;
	}

	private void mountParameters(final ENTITY entity, final Map<String, Object> params, final String parent) {
		final List<Field> fields = VulpeReflectUtil.getFields(entity.getClass());
		final StringBuilder order = new StringBuilder();
		if (StringUtils.isNotEmpty(entity.getAutocomplete())) {
			try {
				if (entity.getId() != null) {
					params.put("id", entity.getId());
				} else {
					final String value = "%" + PropertyUtils.getProperty(entity, entity.getAutocomplete()) + "%";
					params.put(entity.getAutocomplete(), value);
				}
			} catch (Exception e) {
				throw new VulpeSystemException(e);
			}
		} else {
			for (final Field field : fields) {
				if ((field.isAnnotationPresent(IgnoreAutoFilter.class) || field.isAnnotationPresent(Transient.class) || Modifier
						.isTransient(field.getModifiers()))
						&& !field.isAnnotationPresent(QueryParameter.class)) {
					continue;
				}
				final OrderBy orderBy = field.getAnnotation(OrderBy.class);
				if (orderBy != null) {
					if (StringUtils.isNotBlank(order.toString())) {
						order.append(",");
					}
					order.append("obj.").append(field.getName()).append(" ").append(orderBy.type().name());
				}
				try {
					Object value = PropertyUtils.getProperty(entity, field.getName());
					Class<?> valueClass = PropertyUtils.getPropertyType(entity, field.getName());
					if (VulpeEntity.class.isAssignableFrom(valueClass)) {
						final ManyToOne manyToOne = field.getAnnotation(ManyToOne.class);
						final QueryParameter queryParameter = field.getAnnotation(QueryParameter.class);
						final String paramName = (StringUtils.isNotEmpty(parent) ? parent + "." : "")
								+ (queryParameter != null && StringUtils.isNotEmpty(queryParameter.value()) ? "_"
										+ queryParameter.value() : field.getName());
						if (value != null) {
							if (manyToOne != null
									|| (queryParameter != null && StringUtils.isNotEmpty(queryParameter.value()))) {
								mountParameters((ENTITY) value, params, paramName);
							} else {
								params.put(paramName, value);
							}
						}
					} else if (isNotEmpty(value)) {
						final String paramName = (StringUtils.isNotEmpty(parent) ? parent + "." : "") + field.getName();
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
					}
				} catch (Exception e) {
					throw new VulpeSystemException(e);
				}
			}
		}
	}

	/**
	 * Retrieves HQL select string to current entity.
	 * 
	 * @param entity
	 * @param params
	 * @return
	 */
	protected String getHQL(final ENTITY entity, final Map<String, Object> params) {
		final StringBuilder order = new StringBuilder();
		mountParameters(entity, params, null);
		final StringBuilder hql = new StringBuilder();
		final NamedQuery namedQuery = getNamedQuery(getEntityClass(), getEntityClass().getSimpleName().concat(".read"));
		QueryConfiguration queryConfiguration = null;
		final QueryConfigurations queryConfigurations = entity.getClass().getAnnotation(QueryConfigurations.class);
		if (queryConfigurations != null) {
			final String queryConfigurationName = entity.getMap().containsKey(Entity.QUERY_CONFIGURATION_NAME) ? (String) entity
					.getMap().get(Entity.QUERY_CONFIGURATION_NAME)
					: "default";
			for (QueryConfiguration queryConfig : queryConfigurations.value()) {
				if (queryConfig.name().equals(queryConfigurationName)) {
					queryConfiguration = queryConfig;
					break;
				}
			}
		}
		final boolean complement = queryConfiguration != null && queryConfiguration.complement() != null;
		final boolean replace = queryConfiguration != null && queryConfiguration.replace() != null;
		if (namedQuery == null) {
			hql.append("select ");
			if (complement && queryConfiguration.complement().distinct()) {
				hql.append("distinct ");
			}
			if (replace && StringUtils.isNotEmpty(queryConfiguration.replace().select())) {
				hql.append(queryConfiguration.replace().select());
			} else {
				if (StringUtils.isNotEmpty(entity.getAutocomplete())) {
					hql.append("new ");
					hql.append(entity.getClass().getSimpleName());
					hql.append("(obj.id, obj.").append(entity.getAutocomplete());
					final List<Field> autocompleteFields = VulpeReflectUtil.getFieldsWithAnnotation(entity.getClass(),
							Autocomplete.class);
					for (Field field : autocompleteFields) {
						if (!field.getName().equals(entity.getAutocomplete())) {
							hql.append(", obj.").append(field.getName());
						}
					}
					hql.append(")");
				} else {
					hql.append("obj");
					if (complement && StringUtils.isNotEmpty(queryConfiguration.complement().select())) {
						hql.append(", ");
						hql.append(queryConfiguration.complement().select());
					}
				}
			}
			hql.append(" from ");
			if (replace && StringUtils.isNotEmpty(queryConfiguration.replace().from())) {
				hql.append(queryConfiguration.replace().from());
			} else {
				hql.append(entity.getClass().getSimpleName());
				hql.append(" obj ");
			}
			if (replace && StringUtils.isNotEmpty(queryConfiguration.replace().join())) {
				hql.append(queryConfiguration.replace().join());
			} else if (complement && StringUtils.isNotEmpty(queryConfiguration.complement().join())) {
				hql.append(queryConfiguration.complement().join());
			}
		} else {
			hql.append(namedQuery.query());
		}

		if (replace && StringUtils.isNotEmpty(queryConfiguration.replace().where())) {
			hql.append(" where ");
			hql.append(queryConfiguration.replace().where());
		} else {
			if (!params.isEmpty()) {
				if (!hql.toString().toLowerCase().contains("where")) {
					hql.append(" where ");
				}
				int count = 0;
				for (final String name : params.keySet()) {
					final Object value = params.get(name);
					count++;
					final QueryParameter queryParameter = VulpeReflectUtil.getAnnotationInField(QueryParameter.class,
							entity.getClass(), name);
					if (queryParameter == null || StringUtils.isEmpty(queryParameter.equals().name())) {
						String hqlAttributeName = name.startsWith("_") ? name.replace("_", "") : "obj." + name;
						String hqlParamName = name.replace("_", "").replaceAll("\\.", "_");
						if (value instanceof String) {
							final Like like = VulpeReflectUtil
									.getAnnotationInField(Like.class, entity.getClass(), name);
							hql.append("upper(").append(hqlAttributeName).append(") ").append(
									like != null ? "like" : "=").append(" upper(:").append(hqlParamName).append(")");
						} else {
							hql.append(hqlAttributeName).append(" = :").append(hqlParamName);
						}
					} else {
						final Like like = VulpeReflectUtil.getAnnotationInField(Like.class, entity.getClass(), name);
						if (queryParameter.orEquals().length > 0) {
							hql.append("(");
						}
						hql.append(addHQLQueryParameter(queryParameter.equals(), name, like));
						if (queryParameter.orEquals().length > 0) {
							for (Parameter parameter : queryParameter.orEquals()) {
								hql.append(" or ");
								hql.append(addHQLQueryParameter(parameter, name, like));
							}
							hql.append(")");
						}
					}
					if (count < params.size()) {
						hql.append(" and ");
					}
				}
			}
			if (entity instanceof VulpeLogicEntity) {
				if (hql.toString().toLowerCase().contains("where")) {
					hql.append(" and ");
				} else {
					hql.append(" where ");
				}
				hql.append("obj.status <> :status");
				params.put("status", Status.D);
			}
			if (complement && StringUtils.isNotEmpty(queryConfiguration.complement().where())) {
				if (!hql.toString().toLowerCase().contains("where")) {
					hql.append(" where ");
				} else {
					hql.append(" and ");
				}
				hql.append(queryConfiguration.complement().where());
			}
		}
		// add order by
		if (replace && StringUtils.isNotEmpty(queryConfiguration.replace().orderBy())) {
			hql.append(" order by ");
			hql.append(queryConfiguration.replace().orderBy());
		} else {
			if (StringUtils.isNotEmpty(entity.getOrderBy()) || StringUtils.isNotEmpty(order.toString())) {
				if (hql.toString().toLowerCase().contains("order by")) {
					hql.append(", ");
				} else {
					hql.append(" order by ");
				}
				hql.append(StringUtils.isNotEmpty(order.toString()) ? order.toString() : entity.getOrderBy());
			}
			if (complement && StringUtils.isNotEmpty(queryConfiguration.complement().orderBy())) {
				if (!hql.toString().toLowerCase().contains("order by")) {
					hql.append(" order by ");
				} else {
					hql.append(", ");
				}
				hql.append(queryConfiguration.complement().orderBy());
			}
		}
		return hql.toString();
	}

	private String addHQLQueryParameter(final Parameter parameter, final String paramName, final Like like) {
		final StringBuilder hql = new StringBuilder();
		if (like != null) {
			hql.append("upper(");
		}
		hql.append(parameter.alias());
		hql.append('.');
		if (StringUtils.isNotEmpty(parameter.name())) {
			hql.append(parameter.name());
		} else {
			hql.append(paramName);
		}
		if (like != null) {
			hql.append(")");
		}
		hql.append(" ");
		if (like != null) {
			hql.append("like upper(");
		} else {
			hql.append(parameter.operator().getValue());
		}
		if (StringUtils.isNotEmpty(parameter.value())) {
			hql.append("'").append(parameter.value()).append("'");
		} else {
			hql.append(" :").append(paramName.replaceAll("\\.", "_"));
		}
		if (like != null) {
			hql.append(")");
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
			final DeclaredType declaredType = VulpeReflectUtil.getDeclaredType(getClass(), getClass()
					.getGenericSuperclass());
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
	 * Checks if value is not empty.
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
			final QueryParameter[] queryParameters = notExistEqual.parameters();
			// getting total records
			final StringBuilder hql = new StringBuilder();
			hql.append("select count(*) from ");
			hql.append(entity.getClass().getSimpleName()).append(" obj where ");
			final Map<String, Object> values = new HashMap<String, Object>();
			for (QueryParameter queryParameter : queryParameters) {
				hql.append("obj.").append(queryParameter.equals().name()).append(" ").append(
						queryParameter.equals().operator().getValue()).append(" :").append(
						queryParameter.equals().name());
				try {
					values.put(queryParameter.equals().name(), PropertyUtils.getProperty(entity, queryParameter
							.equals().name()));
				} catch (Exception e) {
					LOG.error(e);
				}
			}
			final Query query = getEntityManager().createQuery(hql.toString());
			setParams(query, values);
			final Long size = (Long) query.getSingleResult();
			return size > 0;
		}
		return false;
	}

}