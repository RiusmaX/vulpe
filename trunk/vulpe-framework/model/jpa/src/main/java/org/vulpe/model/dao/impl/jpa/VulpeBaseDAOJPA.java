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
import org.hibernate.Session;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.FilterDefs;
import org.springframework.stereotype.Repository;
import org.vulpe.audit.model.entity.AuditOccurrenceType;
import org.vulpe.commons.beans.Paging;
import org.vulpe.commons.util.VulpeReflectUtil;
import org.vulpe.commons.util.VulpeStringUtil;
import org.vulpe.commons.util.VulpeValidationUtil;
import org.vulpe.commons.util.VulpeReflectUtil.DeclaredType;
import org.vulpe.exception.VulpeApplicationException;
import org.vulpe.exception.VulpeSystemException;
import org.vulpe.model.annotations.Autocomplete;
import org.vulpe.model.annotations.IgnoreAutoFilter;
import org.vulpe.model.annotations.Like;
import org.vulpe.model.annotations.NotDeleteIf;
import org.vulpe.model.annotations.NotExistEquals;
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
		if (checkNotDeleteIf(entity)) {
			simpleDelete(entity);
		}
	}

	private void simpleDelete(final ENTITY entity) throws VulpeApplicationException {
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
		boolean used = false;
		for (final ENTITY entity : entities) {
			if (!checkNotDeleteIf(entity)) {
				used = true;
			}
		}
		if (!used) {
			for (final ENTITY entity : entities) {
				simpleDelete(entity);
			}
		}
	}

	private boolean checkNotDeleteIf(final ENTITY entity) {
		boolean valid = true;
		final NotDeleteIf notDeleteIf = entity.getClass().getAnnotation(NotDeleteIf.class);
		if (notDeleteIf != null) {
			final String propertyName = VulpeStringUtil.lowerCaseFirst(entity.getClass().getSimpleName());
			StringBuilder hqlNotDeleteIf = new StringBuilder("select count(obj.id) from ");
			for (final Class<? extends VulpeEntity<?>> entityClass : notDeleteIf.usedBy().value()) {
				hqlNotDeleteIf.append(entityClass.getSimpleName()).append(" obj where obj.").append(propertyName)
						.append(".id = :").append(propertyName);
				final Query query = getEntityManager().createQuery(hqlNotDeleteIf.toString());
				query.setParameter(propertyName, entity.getId());
				final Long size = (Long) query.getSingleResult();
				if (size > 0) {
					entity.setUsed(true);
					valid = false;
					break;
				}
			}
			if (valid && notDeleteIf.conditions().value().length > 0) {
				hqlNotDeleteIf = new StringBuilder("select count(obj.id) from ").append(
						entity.getClass().getSimpleName()).append(" obj ").append(
						notDeleteIf.conditions().queryInject()).append(" where obj.id = ").append(entity.getId())
						.append(" and (");
				int count = 0;
				for (final String condition : notDeleteIf.conditions().value()) {
					if (count > 0 && count < notDeleteIf.conditions().value().length) {
						hqlNotDeleteIf.append(" or ");
					}
					hqlNotDeleteIf.append(condition);
					++count;
				}
				hqlNotDeleteIf.append(")");
				final Long size = (Long) getEntityManager().createQuery(hqlNotDeleteIf.toString()).getSingleResult();
				if (size == 1) {
					entity.setConditional(true);
					valid = false;
				}
			}
		}
		return valid;
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
		repairRelationship(entity);
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
		enableFilters();
		final ENTITY newEntity = getEntityManager().find(getEntityClass(), entity.getId());
		if (newEntity.getId() instanceof VulpeLogicEntity) {
			final VulpeLogicEntity logicEntity = (VulpeLogicEntity) newEntity;
			if (Status.D.equals(logicEntity.getStatus())) {
				return null;
			}
		}
		newEntity.setMap(entity.getMap());
		loadEntityRelationships(newEntity);
		disableFilters();
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
		enableFilters();
		final Map<String, Object> params = new HashMap<String, Object>();
		final String hql = getHQL(entity, params);
		final List<ENTITY> entities = execute(hql, params);
		for (final ENTITY entity2 : entities) {
			entity2.setMap(entity.getMap());
		}
		loadRelationships(entities, params, false);
		disableFilters();
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
		enableFilters();
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
			for (final ENTITY entity2 : entities) {
				entity2.setMap(entity.getMap());
			}
			loadRelationships(entities, params, false);
			paging.setList(entities);
		}
		disableFilters();
		return paging;
	}

	private void mountOrder(final ENTITY entity, final StringBuilder order) {
		final List<Field> fields = VulpeReflectUtil.getFields(entity.getClass());
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
		}
	}

	private void mountParameters(final ENTITY entity, final Map<String, Object> params, final String parent) {
		final List<Field> fields = VulpeReflectUtil.getFields(entity.getClass());
		if (StringUtils.isNotEmpty(entity.getAutocomplete())) {
			try {
				if (entity.getId() != null) {
					params.put("id", entity.getId());
				} else {
					final String[] autocompleteParts = entity.getAutocomplete().split(",");
					if (autocompleteParts.length > 1) {
						for (final String autocomplete : autocompleteParts) {
							final String value = "[like]%" + entity.getAutocompleteTerm() + "%";
							params.put(autocomplete, value);
						}
					} else {
						final String value = "[like]%" + entity.getAutocompleteTerm() + "%";
						params.put(entity.getAutocomplete(), value);
					}
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
				try {
					Object value = PropertyUtils.getProperty(entity, field.getName());
					Class<?> valueClass = PropertyUtils.getPropertyType(entity, field.getName());
					if (VulpeEntity.class.isAssignableFrom(valueClass)) {
						final ManyToOne manyToOne = field.getAnnotation(ManyToOne.class);
						final QueryParameter queryParameter = field.getAnnotation(QueryParameter.class);
						String paramName = (StringUtils.isNotEmpty(parent) ? parent + "." : "")
								+ (queryParameter != null && StringUtils.isNotEmpty(queryParameter.value()) ? "_"
										+ queryParameter.value() : field.getName());
						if (value != null) {
							if (manyToOne != null
									|| (queryParameter != null && StringUtils.isNotEmpty(queryParameter.value()))) {
								if (!value.getClass().getSimpleName().contains(CGLIB_ENHANCER)) {
									mountParameters((ENTITY) value, params, paramName);
								}
							} else if (isNotEmpty(value)) {
								params.put(paramName, value);
							}
						}
					} else if (isNotEmpty(value)) {
						final QueryParameter queryParameter = field.getAnnotation(QueryParameter.class);
						final String paramName = (StringUtils.isNotEmpty(parent) ? parent + "." : "")
								+ (queryParameter != null && StringUtils.isNotEmpty(queryParameter.value())
										&& queryParameter.fake() ? "!" + queryParameter.value() : field.getName());
						final Like like = field.getAnnotation(Like.class);
						if (like != null) {
							if (like.type().equals(LikeType.BEGIN)) {
								value = value + "%";
							} else if (like.type().equals(LikeType.END)) {
								value = "%" + value;
							} else {
								value = "%" + value + "%";
							}
							value = "[like]" + value;
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
		mountOrder(entity, order);
		mountParameters(entity, params, null);
		StringBuilder hql = new StringBuilder();
		final NamedQuery namedQuery = getNamedQuery(getEntityClass(), getEntityClass().getSimpleName().concat(".read"));
		QueryConfiguration queryConfiguration = null;
		final QueryConfigurations queryConfigurations = entity.getClass().getAnnotation(QueryConfigurations.class);
		if (queryConfigurations != null) {
			final String queryConfigurationName = entity.getQueryConfigurationName();
			for (final QueryConfiguration queryConfig : queryConfigurations.value()) {
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
					hql.append("(obj.id");
					final String[] autocompleteParts = entity.getAutocomplete().split(",");
					if (autocompleteParts.length > 1) {
						for (final String autocomplete : autocompleteParts) {
							hql.append(", obj.").append(autocomplete);
						}
					} else {
						hql.append(", obj.").append(entity.getAutocomplete());
					}
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
			StringBuilder hqlJoin = new StringBuilder();
			if (!params.isEmpty()) {
				for (final String name : params.keySet()) {
					if (name.contains(".")) {
						final String paramName = name.substring(0, name.lastIndexOf("."));
						final String[] joins = paramName.split("\\.");
						if (joins.length > 0) {
							String parent = "obj";
							for (final String join : joins) {
								final StringBuilder joinString = new StringBuilder("join ");
								joinString.append(parent).append(".").append(join).append(" ").append(join).append(" ");
								if (!hql.toString().contains(joinString.toString())) {
									hqlJoin.append(joinString.toString());
								}
								parent = join;
							}
						}
					}
				}
			}
			if (replace && StringUtils.isNotEmpty(queryConfiguration.replace().join())) {
				hqlJoin = new StringBuilder(queryConfiguration.replace().join());
			} else if (complement && StringUtils.isNotEmpty(queryConfiguration.complement().join())) {
				hqlJoin.append(queryConfiguration.complement().join());
			}
			hql.append(hqlJoin.toString());
		} else {
			hql.append(namedQuery.query());
		}
		if (replace && StringUtils.isNotEmpty(queryConfiguration.replace().where())) {
			hql.append(" where ").append(queryConfiguration.replace().where());
		} else {
			if (!params.isEmpty()) {
				if (!hql.toString().toLowerCase().contains("where")) {
					hql.append(" where ");
				}
				int size = 0;
				for (final String name : params.keySet()) {
					if (!name.startsWith("!")) {
						++size;
					}
				}
				int count = 0;
				for (final String name : params.keySet()) {
					if (name.startsWith("!")) {
						continue;
					}
					++count;
					final Object value = params.get(name);
					final QueryParameter queryParameter = VulpeReflectUtil.getAnnotationInField(QueryParameter.class,
							entity.getClass(), name);
					if (queryParameter == null || StringUtils.isEmpty(queryParameter.equals().name())) {
						String hqlAttributeName = name.startsWith("_") ? name.replace("_", "") : "obj." + name;
						String hqlParamName = name.replace("_", "").replaceAll("\\.", "_");
						if (VulpeStringUtil.count(name, ".") > 1) {
							final String attributeName = name.substring(name.lastIndexOf("."));
							final String paramName = name.substring(0, name.lastIndexOf("."));
							final String[] joins = paramName.split("\\.");
							hqlAttributeName = joins[joins.length - 1] + attributeName;
						}
						if (value instanceof String) {
							final String valueString = (String) value;
							final boolean useLike = valueString.startsWith("[like]");
							hql.append("upper(").append(hqlAttributeName).append(") ").append(useLike ? "like" : "=")
									.append(" upper(:").append(hqlParamName).append(")");
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
					if (count < size) {
						if (StringUtils.isNotBlank(entity.getAutocompleteTerm())) {
							hql.append(" or ");
						} else {
							hql.append(" and ");
						}
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
				final String complementWhere = validateQueryFunctions(entity, queryConfiguration.complement().where());
				if (StringUtils.isNotBlank(complementWhere)) {
					if (!hql.toString().toLowerCase().contains("where")) {
						hql.append(" where ");
					} else if (!hql.toString().toLowerCase().trim().endsWith("where")) {
						hql.append(" and ");
					}
					hql.append(complementWhere);
				}
			}
		}
		if (replace && StringUtils.isNotEmpty(queryConfiguration.replace().groupBy())) {
			hql.append(" group by ");
			hql.append(queryConfiguration.replace().groupBy());
		} else if (complement && StringUtils.isNotEmpty(queryConfiguration.complement().groupBy())) {
			if (!hql.toString().contains("group by")) {
				hql.append(" group by ");
			}
			hql.append(queryConfiguration.complement().groupBy());
		}
		// add order by
		if (replace && StringUtils.isNotEmpty(queryConfiguration.replace().orderBy())) {
			hql.append(" order by ");
			hql.append(queryConfiguration.replace().orderBy());
		} else {
			if (StringUtils.isNotEmpty(order.toString())
					&& (entity.getOrderBy().equals("obj.id") || StringUtils.isBlank(entity.getOrderBy()))) {
				entity.setOrderBy(order.toString());
			}
			if (StringUtils.isNotEmpty(entity.getOrderBy())) {
				if (hql.toString().toLowerCase().contains("order by")) {
					hql.append(", ");
				} else {
					hql.append(" order by ");
				}
				hql.append(entity.getOrderBy());
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

		hql = new StringBuilder(validateQueryFunctions(entity, hql.toString()));
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
		final StringBuilder hql = new StringBuilder();
		Long size = 0L;
		if (entity.getId() != null) {
			hql.append("select count(*) from ");
			hql.append(initializeAndUnproxy(entity).getClass().getSimpleName()).append(" obj where ");
			hql.append(" obj.id = :id ");
			final Query query = getEntityManager().createQuery(hql.toString());
			query.setParameter("id", entity.getId());
			size = (Long) query.getSingleResult();
		}
		return size == 1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.vulpe.model.dao.VulpeDAO#notExistEquals(org.vulpe.model.entity.
	 * VulpeEntity)
	 */
	@Override
	public boolean notExistEquals(final ENTITY entity) throws VulpeApplicationException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Not Exists object: ".concat(entity.toString()));
		}
		final StringBuilder hql = new StringBuilder();
		Long size = 0L;
		final NotExistEquals notExistEqual = entity.getClass().getAnnotation(NotExistEquals.class);
		if (notExistEqual != null) {
			hql.append("select count(*) from ");
			hql.append(initializeAndUnproxy(entity).getClass().getSimpleName()).append(" obj where ");
			final QueryParameter[] queryParameters = notExistEqual.parameters();
			// getting total records
			final Map<String, Object> values = new HashMap<String, Object>();
			int count = 0;
			for (final QueryParameter queryParameter : queryParameters) {
				if (count > 0) {
					hql.append(" and ");
				}
				if (queryParameter.equals().upper()) {
					hql.append("upper(");
				}
				hql.append("obj.").append(queryParameter.equals().name());
				if (queryParameter.equals().upper()) {
					hql.append(")");
				}
				hql.append(" ").append(queryParameter.equals().operator().getValue());
				if (queryParameter.equals().upper()) {
					hql.append(" upper(");
				}
				hql.append(":").append(queryParameter.equals().name());
				if (queryParameter.equals().upper()) {
					hql.append(")");
				}
				try {
					values.put(queryParameter.equals().name(), PropertyUtils.getProperty(entity, queryParameter
							.equals().name()));
				} catch (Exception e) {
					LOG.error(e);
				}
				++count;
			}
			if (entity.getId() != null) {
				hql.append(" and obj.id <> :id ");
			}
			final Query query = getEntityManager().createQuery(hql.toString());
			setParams(query, values);
			if (entity.getId() != null) {
				query.setParameter("id", entity.getId());
			}
			size = (Long) query.getSingleResult();
		}
		return size > 0;
	}

	private void enableFilters() {
		final FilterDefs filterDefs = getEntityClass().getAnnotation(FilterDefs.class);
		if (filterDefs != null && filterDefs.value().length > 0) {
			final Session session = (Session) getEntityManager().getDelegate();
			for (final FilterDef filterDef : filterDefs.value()) {
				session.enableFilter(filterDef.name());
			}
		} else {
			final FilterDef filterDef = getEntityClass().getAnnotation(FilterDef.class);
			if (filterDef != null) {
				final Session session = (Session) getEntityManager().getDelegate();
				session.enableFilter(filterDef.name());
			}
		}
	}

	private void disableFilters() {
		final FilterDefs filterDefs = getEntityClass().getAnnotation(FilterDefs.class);
		if (filterDefs != null && filterDefs.value().length > 0) {
			final Session session = (Session) getEntityManager().getDelegate();
			for (final FilterDef filterDef : filterDefs.value()) {
				session.disableFilter(filterDef.name());
			}
		} else {
			final FilterDef filterDef = getEntityClass().getAnnotation(FilterDef.class);
			if (filterDef != null) {
				final Session session = (Session) getEntityManager().getDelegate();
				session.disableFilter(filterDef.name());
			}
		}
	}

}