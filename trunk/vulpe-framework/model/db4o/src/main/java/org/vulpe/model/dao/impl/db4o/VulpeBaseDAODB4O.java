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
package org.vulpe.model.dao.impl.db4o;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.CallableStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Transient;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.vulpe.audit.model.entity.AuditOccurrenceType;
import org.vulpe.commons.VulpeConstants.Model.DAO.DB4O;
import org.vulpe.commons.beans.Paging;
import org.vulpe.commons.util.VulpeReflectUtil;
import org.vulpe.commons.util.VulpeValidationUtil;
import org.vulpe.commons.util.VulpeReflectUtil.DeclaredType;
import org.vulpe.exception.VulpeApplicationException;
import org.vulpe.model.annotations.SkipAutoFilter;
import org.vulpe.model.annotations.Like;
import org.vulpe.model.annotations.NotExistEquals;
import org.vulpe.model.annotations.OrderBy;
import org.vulpe.model.annotations.QueryParameter;
import org.vulpe.model.annotations.OrderBy.OrderType;
import org.vulpe.model.annotations.Parameter.OperatorType;
import org.vulpe.model.db4o.annotations.FindBy;
import org.vulpe.model.entity.Parameter;
import org.vulpe.model.entity.VulpeEntity;
import org.vulpe.model.entity.VulpeLogicEntity;
import org.vulpe.model.entity.VulpeLogicEntity.Status;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;

/**
 * Default implementation of DAO for MAIN's with DB4O.
 *
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 */
@SuppressWarnings( { "unchecked", "rawtypes" })
public class VulpeBaseDAODB4O<ENTITY extends VulpeEntity<ID>, ID extends Serializable & Comparable<?>>
		extends AbstractVulpeBaseDAODB4O<ENTITY, ID> {

	/*
	 * (non-Javadoc)
	 *
	 * @seeorg.vulpe.model.dao.VulpeBaseCRUDDAO#create(br.com.
	 * activethread.framework.model.entity.BaseEntity)
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
	 * @seeorg.vulpe.model.dao.VulpeBaseCRUDDAO#delete(br.com.
	 * activethread.framework.model.entity.BaseEntity)
	 */
	public void delete(final ENTITY entity) throws VulpeApplicationException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Deleting object: ".concat(entity.toString()));
		}
		audit(entity, AuditOccurrenceType.DELETE, null);
		// persistent entity
		try {
			if (entity instanceof VulpeLogicEntity) {
				final VulpeLogicEntity logicEntity = (VulpeLogicEntity) entity;
				logicEntity.setStatus(Status.D);
				// make merge of entity
				merge(entity);
			} else {
				final ObjectContainer container = getObjectContainer();
				container.delete(load(container, entity));
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new VulpeApplicationException(e.getMessage());
		} finally {
			commit();
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.vulpe.model.dao.VulpeDAO#delete(java.util. List)
	 */
	public void delete(final List<ENTITY> entities) throws VulpeApplicationException {
		for (ENTITY entity : entities) {
			delete(entity);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @seeorg.vulpe.model.dao.VulpeBaseCRUDDAO#update(br.com.
	 * activethread.framework.model.entity.BaseEntity)
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
		ENTITY newEntity = null;
		try {
			final ObjectContainer container = getObjectContainer();
			newEntity = getEntityClass().newInstance();
			newEntity.setId(entity.getId());
			newEntity = (ENTITY) container.queryByExample(newEntity).get(0);
			if (newEntity instanceof VulpeLogicEntity) {
				final VulpeLogicEntity logicEntity = (VulpeLogicEntity) newEntity;
				if (Status.D.equals(logicEntity.getStatus())) {
					newEntity = null;
				}
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new VulpeApplicationException(e.getMessage());
		} finally {
			rollback();
			close();
		}
		return newEntity;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.vulpe.model.dao.VulpeDAO#read(br.com.activethread
	 * .framework.model.entity.BaseEntity)
	 */
	public List<ENTITY> read(final ENTITY entity) throws VulpeApplicationException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Reading object: ".concat(entity.toString()));
		}
		try {
			final ObjectSet<ENTITY> objectSet = getQuery(entity).execute();
			final List<ENTITY> list = objectSet.hasNext() ? new ArrayList<ENTITY>() : null;
			while (objectSet.hasNext()) {
				list.add(objectSet.next());
			}
			return list;
		} finally {
			rollback();
			close();
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @seeorg.vulpe.model.dao.VulpeBaseCRUDDAO#paging(br.com.
	 * activethread.framework.model.entity.BaseEntity, java.lang.Integer,
	 * java.lang.Integer)
	 */
	public Paging<ENTITY> paging(final ENTITY entity, final Integer pageSize, final Integer page)
			throws VulpeApplicationException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Paging object: ".concat(entity.toString()));
		}
		try {
			// getting total records
			final ObjectSet<ENTITY> objectSet = getQuery(entity).execute();
			final List<ENTITY> list = new ArrayList<ENTITY>();
			while (objectSet.hasNext()) {
				list.add(objectSet.next());
			}
			final int size = list.size();

			final Paging paging = new Paging<ENTITY>(size, pageSize, page);
			if (size > 0) {
				// getting list by size of page
				final List<ENTITY> listPaging = new ArrayList<ENTITY>();
				for (int i = 0; i < (size - paging.getFromIndex()); i++) {
					if (i >= pageSize) {
						break;
					}
					listPaging.add(list.get(paging.getFromIndex() + i));
				}
				paging.setList(listPaging);
			}
			return paging;
		} finally {
			rollback();
			close();
		}
	}

	@Transient
	private transient Class<ENTITY> entityClass;
	{
		getEntityClass();
	}

	/**
	 * Returns entity class from DAO
	 */
	protected Class<ENTITY> getEntityClass() {
		if (entityClass == null) {
			final DeclaredType declaredType = VulpeReflectUtil.getDeclaredType(getClass(),
					getClass().getGenericSuperclass());
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
				return VulpeValidationUtil.isNotEmpty(((VulpeEntity) value).getId());
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	/**
	 *
	 * @param entity
	 * @return
	 */
	public Query getQuery(final ENTITY entity) {
		final Query query = getObjectContainer().query();
		query.constrain(entity.getClass());
		final Map<String, String> orderMap = new HashedMap();
		if (StringUtils.isNotBlank(entity.getOrderBy())) {
			final String[] orderArray = entity.getOrderBy().split(",");
			for (int i = 0; i < orderArray.length; i++) {
				String order = orderArray[i].trim();
				boolean descending = false;
				if (order.contains("desc")) {
					descending = true;
					order = order.replaceAll(" desc", "");
				} else {
					order = order.replaceAll(" asc", "");
				}
				final String[] orderParts = order.split("\\.");
				Query subQuery = query;
				int count = 1;
				for (String orderPart : orderParts) {
					orderMap.put(orderPart, orderPart);
					if (count == orderParts.length) {
						if (descending) {
							subQuery.descend(orderPart).orderDescending();
						} else {
							subQuery.descend(orderPart).orderAscending();
						}
					} else {
						subQuery = subQuery.descend(orderPart);
					}
				}
			}
		}
		emptyToNull(entity);
		for (Field field : VulpeReflectUtil.getFields(getEntityClass())) {
			if (field.isAnnotationPresent(SkipAutoFilter.class)) {
				continue;
			}
			final Object value = VulpeReflectUtil.getFieldValue(entity, field.getName());
			if (VulpeLogicEntity.class.isAssignableFrom(entity.getClass())
					&& field.getName().equals(DB4O.STATUS)) {
				query.descend(field.getName()).constrain(Status.D).not();
			}
			final OrderBy orderBy = field.getAnnotation(OrderBy.class);
			if (orderBy != null && !orderMap.containsKey(field.getName())) {
				if (orderBy.type().equals(OrderType.DESC)) {
					query.descend(field.getName()).orderDescending();
				} else {
					query.descend(field.getName()).orderAscending();
				}
			}
			if (value != null) {
				final QueryParameter queryParameter = field.getAnnotation(QueryParameter.class);
				if (queryParameter != null) {
					String paramName = queryParameter.equals().name();
					if (StringUtils.isBlank(paramName)) {
						paramName = field.getName();
					}
					final String[] relations = paramName.split("\\.");
					if (relations != null && relations.length > 1) {
						int count = 1;
						Query subQuery = query;
						for (String relation : relations) {
							if (count == relations.length) {
								subQuery.descend(relation).constrain(value).equal();
							} else {
								subQuery = subQuery.descend(relation);
							}
							++count;
						}
					} else {
						if (OperatorType.EQUAL.equals(queryParameter.equals().operator())) {
							query.descend(paramName).constrain(value).equal();
						} else if (OperatorType.SMALLER.equals(queryParameter.equals().operator())) {
							query.descend(paramName).constrain(value).smaller();
						} else if (OperatorType.GREATER.equals(queryParameter.equals().operator()
								.getValue())) {
							query.descend(paramName).constrain(value).greater();
						} else if (OperatorType.SMALLER_OR_EQUAL.equals(queryParameter.equals()
								.operator())) {
							query.descend(paramName).constrain(value).smaller().equal();
						} else if (OperatorType.GREATER_OR_EQUAL.equals(queryParameter.equals()
								.operator())) {
							query.descend(paramName).constrain(value).greater().equal();
						}
					}
				} else {
					if (!Modifier.isTransient(field.getModifiers())) {
						final FindBy findBy = field.getType().getAnnotation(FindBy.class);
						if (findBy != null) {
							Query subquery = query.descend(field.getName());
							final String[] findParts = findBy.value().split("\\.");
							for (final String find : findParts) {
								subquery = subquery.descend(find);
							}
							if (findBy.like()) {
								subquery.constrain(value.toString()).like();
							} else {
								subquery.constrain(value.toString());
							}
						} else if (String.class.isAssignableFrom(field.getType())) {
							final Like like = field.getAnnotation(Like.class);
							if (like == null) {
								query.descend(field.getName()).constrain(value);
							} else {
								query.descend(field.getName()).constrain(value).like();
							}
						} else if (VulpeEntity.class.isAssignableFrom(field.getType())) {
							if (isNotEmpty(value)) {
								query.descend(field.getName()).constrain(value);
							}
						} else if (List.class.isAssignableFrom(field.getType())) {
							final List values = (List) value;
							if (!values.isEmpty()) {
								final Query subqy = query.descend(field.getName());
								for (Object object : values) {
									subqy.constrain(object);
								}
							}
						} else if (Object[].class.isAssignableFrom(field.getType())) {
							final Query subqy = query.descend(field.getName());
							final Object[] values = (Object[]) value;
							for (Object object : values) {
								subqy.constrain(object);
							}
						} else if (!field.isAnnotationPresent(Transient.class)
								&& !field.getName().equals(DB4O.SELECTED)) {
							query.descend(field.getName()).constrain(value);
						}
					}
				}
			}
		}
		return query;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.vulpe.model.dao.VulpeDAO#executeProcedure(java.lang.String,
	 * java.util.List)
	 */
	@Override
	public CallableStatement executeProcedure(String name, List<Parameter> parameters)
			throws VulpeApplicationException {
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.vulpe.model.dao.VulpeDAO#exists(org.vulpe.model.entity.VulpeEntity)
	 */
	@Override
	public boolean exists(ENTITY entity) throws VulpeApplicationException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Exists object: ".concat(entity.toString()));
		}
		final Query query = getObjectContainer().query();
		query.constrain(entity.getClass());
		query.descend("id").constrain(entity.getId());
		final int size = query.execute().size();
		return size == 1;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @seeorg.vulpe.model.dao.VulpeDAO#notExistEquals(org.vulpe.model.entity.
	 * VulpeEntity)
	 */
	@Override
	public boolean notExistEquals(ENTITY entity) throws VulpeApplicationException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Not Exists object: ".concat(entity.toString()));
		}
		final NotExistEquals notExistEqual = entity.getClass().getAnnotation(NotExistEquals.class);
		if (notExistEqual != null) {
			final QueryParameter[] parameters = notExistEqual.parameters();
			final Query query = getObjectContainer().query();
			query.constrain(entity.getClass());
			query.descend("id").constrain(entity.getId()).not();
			for (QueryParameter queryParameter : parameters) {
				try {
					query.descend(queryParameter.equals().name()).constrain(
							PropertyUtils.getProperty(entity, queryParameter.equals().name()));
				} catch (Exception e) {
					LOG.error(e.getMessage());
				}
			}
			// getting total records
			final int size = query.execute().size();
			return size > 0;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.vulpe.model.dao.VulpeDAO#executeFunction(java.lang.String, int,
	 * java.util.List)
	 */
	@Override
	public CallableStatement executeFunction(String name, int returType, List<Parameter> parameters)
			throws VulpeApplicationException {
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.vulpe.model.dao.VulpeDAO#executeCallableStatement(java.lang.String,
	 * java.lang.Integer, java.util.List)
	 */
	@Override
	public CallableStatement executeCallableStatement(String name, Integer returnType,
			List<Parameter> parameters) throws VulpeApplicationException {
		return null;
	}

	@Override
	public void updateSomeAttributes(final ENTITY entity) {
	}

	@Override
	public void updateSomeAttributes(final ENTITY entity, final List<ID> ids) {
	}

	@Override
	public void updateSomeAttributes(List<ENTITY> list) {
	}
}