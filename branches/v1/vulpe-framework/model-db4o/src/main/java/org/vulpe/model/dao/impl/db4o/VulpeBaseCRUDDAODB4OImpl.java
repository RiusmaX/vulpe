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
package org.vulpe.model.dao.impl.db4o;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Transient;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.vulpe.commons.VulpeConstants;
import org.vulpe.commons.VulpeReflectUtil;
import org.vulpe.commons.VulpeValidationUtil;
import org.vulpe.commons.VulpeConstants.Model.DAO.DB4O;
import org.vulpe.commons.VulpeReflectUtil.DeclaredType;
import org.vulpe.commons.audit.AuditOccurrenceType;
import org.vulpe.commons.beans.Paging;
import org.vulpe.exception.VulpeApplicationException;
import org.vulpe.model.annotations.Param;
import org.vulpe.model.annotations.Param.OperatorType;
import org.vulpe.model.annotations.db4o.Like;
import org.vulpe.model.annotations.db4o.OrderBy;
import org.vulpe.model.annotations.db4o.OrderBy.OrderType;
import org.vulpe.model.entity.VulpeBaseEntity;
import org.vulpe.model.entity.LogicEntity;
import org.vulpe.model.entity.LogicEntity.Status;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;

/**
 * Default implementation of DAO for CRUD's with DB4O.
 * 
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 */
@SuppressWarnings("unchecked")
public class VulpeBaseCRUDDAODB4OImpl<ENTITY extends VulpeBaseEntity<ID>, ID extends Serializable & Comparable>
		extends AbstractVulpeBaseDAODB4OImpl<ENTITY, ID> {

	private static final Logger LOG = Logger.getLogger(VulpeBaseCRUDDAODB4OImpl.class.getName());

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
		if (entity instanceof LogicEntity) {
			final LogicEntity logicEntity = (LogicEntity) entity;
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
		final ENTITY deletedEntity = find(entity.getId());
		try {
			final ObjectContainer container = getObjectContainer();
			if (deletedEntity instanceof LogicEntity) {
				final LogicEntity logicEntity = (LogicEntity) deletedEntity;
				logicEntity.setStatus(Status.D);
				// make merge of entity
				merge(deletedEntity);
			} else {
				container.delete(deletedEntity);
			}
		} finally {
			commit();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.model.dao.VulpeBaseCRUDDAO#delete(java.util. List)
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
	public void update(final ENTITY entity) throws VulpeApplicationException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Updating object: ".concat(entity.toString()));
		}
		audit(entity, AuditOccurrenceType.UPDATE, null);
		if (entity instanceof LogicEntity) {
			final LogicEntity logicEntity = (LogicEntity) entity;
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
		ENTITY entity = null;
		try {
			final ObjectContainer container = getObjectContainer();
			entity = getEntityClass().newInstance();
			entity.setId(identifier);
			entity = (ENTITY) container.queryByExample(entity).get(0);
			if (entity instanceof LogicEntity) {
				final LogicEntity logicEntity = (LogicEntity) entity;
				if (Status.D.equals(logicEntity.getStatus())) {
					entity = null;
				}
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		} finally {
			rollback();
			close();
		}
		return entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.model.dao.VulpeBaseCRUDDAO#read(br.com.activethread
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
		if (VulpeValidationUtil.getInstance().isNotEmpty(value)) {
			if (value instanceof VulpeBaseEntity) {
				return VulpeValidationUtil.getInstance().isNotEmpty(
						((VulpeBaseEntity) value).getId());
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
				for (String string : orderParts) {
					orderMap.put(string, string);
				}
				if (orderParts.length == 4) {
					if (descending) {
						query.descend(orderParts[0]).descend(orderParts[1]).descend(orderParts[2])
								.descend(orderParts[3]).orderDescending();
					} else {
						query.descend(orderParts[0]).descend(orderParts[1]).descend(orderParts[2])
								.descend(orderParts[3]).orderAscending();
					}
				} else if (orderParts.length == 3) {
					if (descending) {
						query.descend(orderParts[0]).descend(orderParts[1]).descend(orderParts[2])
								.orderDescending();
					} else {
						query.descend(orderParts[0]).descend(orderParts[1]).descend(orderParts[2])
								.orderAscending();
					}
				} else if (orderParts.length == 2) {
					if (descending) {
						query.descend(orderParts[0]).descend(orderParts[1]).orderDescending();
					} else {
						query.descend(orderParts[0]).descend(orderParts[1]).orderAscending();
					}
				} else {
					if (descending) {
						query.descend(orderParts[0]).orderDescending();
					} else {
						query.descend(orderParts[0]).orderAscending();
					}
				}
			}

		}
		emptyToNull(entity);
		for (Field field : VulpeReflectUtil.getInstance().getFields(getEntityClass())) {
			final Object value = VulpeReflectUtil.getInstance().getFieldValue(entity,
					field.getName());
			if (LogicEntity.class.isAssignableFrom(entity.getClass())
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
				final Param param = field.getAnnotation(Param.class);
				if (param != null) {
					String paramName = param.name();
					if (StringUtils.isBlank(paramName)) {
						paramName = field.getName();
					}
					if (OperatorType.EQUAL.equals(param.operator())) {
						query.descend(paramName).constrain(value).equal();
					} else if (OperatorType.SMALLER.equals(param.operator())) {
						query.descend(paramName).constrain(value).smaller();
					} else if (OperatorType.GREATER.equals(param.operator().getValue())) {
						query.descend(paramName).constrain(value).greater();
					} else if (OperatorType.SMALLER_OR_EQUAL.equals(param.operator())) {
						query.descend(paramName).constrain(value).smaller().equal();
					} else if (OperatorType.GREATER_OR_EQUAL.equals(param.operator())) {
						query.descend(paramName).constrain(value).greater().equal();
					}
				}
				if (field.getModifiers() != VulpeConstants.Modifiers.TRANSIENT) {
					if (String.class.isAssignableFrom(field.getType())) {
						final Like like = field.getAnnotation(Like.class);
						if (like == null) {
							query.descend(field.getName()).constrain(value);
						} else {
							query.descend(field.getName()).constrain(value).like();
						}
					} else if (VulpeBaseEntity.class.isAssignableFrom(field.getType())) {
						if (isNotEmpty(value)) {
							query.descend(field.getName()).constrain(value);
						}
					} else if (List.class.isAssignableFrom(field.getType())) {
						final List values = (List) value;
						if (!values.isEmpty()) {
							final Query subqy = query.descend(field.getName());
							if (values.size() > 1) {
								for (Object object : values) {
									if (object instanceof Enum) {
										subqy.descend(field.getName()).constrain(object.toString());
									} else {
										subqy.constrain(object);
									}
								}
							} else {
								final Object object = values.get(0);
								if (object instanceof Enum) {
									subqy.constrain(object.toString());
								} else {
									subqy.constrain(object);
								}
							}
						}
					} else if (Object[].class.isAssignableFrom(field.getType())) {
						final Query subqy = query.descend(field.getName());
						final Object[] values = (Object[]) value;
						if (values.length > 1) {
							for (Object object : values) {
								if (object instanceof Enum) {
									subqy.constrain(object.toString());
								} else {
									subqy.constrain(object);
								}
							}
						} else {
							subqy.constrain(values[0].toString());
						}
					} else if (!field.isAnnotationPresent(Transient.class)
							&& !field.getName().equals(DB4O.SELECTED)) {
						query.descend(field.getName()).constrain(value);
					}
				}
			}
		}
		return query;
	}
}