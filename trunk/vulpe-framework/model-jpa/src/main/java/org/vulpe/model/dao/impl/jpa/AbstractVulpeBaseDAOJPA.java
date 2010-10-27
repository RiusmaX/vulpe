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
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.ejb.HibernateEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.JpaTemplate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.vulpe.commons.util.VulpeReflectUtil;
import org.vulpe.commons.util.VulpeStringUtil;
import org.vulpe.commons.util.VulpeValidationUtil;
import org.vulpe.exception.VulpeApplicationException;
import org.vulpe.model.annotations.Like;
import org.vulpe.model.annotations.QueryConfiguration;
import org.vulpe.model.annotations.QueryParameter;
import org.vulpe.model.annotations.Relationship;
import org.vulpe.model.dao.impl.AbstractVulpeBaseDAO;
import org.vulpe.model.entity.Parameter;
import org.vulpe.model.entity.VulpeEntity;

/**
 * Default implementation of DAO with JPA
 * 
 * @author <a href="mailto:fabio.viana@activethread.com.br">Fábio Viana</a>
 */
@SuppressWarnings( { "unchecked" })
public abstract class AbstractVulpeBaseDAOJPA<ENTITY extends VulpeEntity<ID>, ID extends Serializable & Comparable>
		extends AbstractVulpeBaseDAO<ENTITY, ID> {

	private static final Logger LOG = Logger.getLogger(AbstractVulpeBaseDAOJPA.class.getName());

	private EntityManagerFactory entityManagerFactory;

	private JpaTemplate jpaTemplate;

	@Autowired
	private TransactionTemplate transactionTemplate;

	public final void setEntityManagerFactory(final EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
		this.jpaTemplate = new JpaTemplate(this.entityManagerFactory);
	}

	public TransactionTemplate getTransactionTemplate() {
		return transactionTemplate;
	}

	public void setTransactionTemplate(final TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}

	public JpaTemplate getJpaTemplate() {
		return jpaTemplate;
	}

	public void setJpaTemplate(final JpaTemplate jpaTemplate) {
		this.jpaTemplate = jpaTemplate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.model.dao.VulpeDAO#merge(java.lang.Object)
	 */
	public <T> T merge(final T entity) {
		final List<Field> fields = VulpeReflectUtil.getInstance().getFields(entity.getClass());
		for (Field field : fields) {
			final OneToMany oneToMany = field.getAnnotation(OneToMany.class);
			if (oneToMany != null) {
				try {
					final List<ENTITY> entities = (List<ENTITY>) PropertyUtils.getProperty(entity,
							field.getName());
					if (VulpeValidationUtil.isNotEmpty(entities)) {
						for (ENTITY entity2 : entities) {
							PropertyUtils.setProperty(entity2, oneToMany.mappedBy(), entity);
						}
					}
				} catch (Exception e) {
					LOG.error(e);
				}
			}
		}
		return (T) getTransactionTemplate().execute(new TransactionCallback() {
			public Object doInTransaction(final TransactionStatus status) {
				final T merged = getJpaTemplate().merge(entity);
				getJpaTemplate().flush();
				return merged;
			}
		});
	}

	/**
	 * Execute HQL query.
	 * 
	 * @param <T>
	 * @param hql
	 * @param params
	 * @return
	 * @throws VulpeApplicationException
	 */
	protected <T> List<T> execute(final String hql, final Map<String, Object> params)
			throws VulpeApplicationException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Reading object: ".concat(hql));
		}
		return (List<T>) getJpaTemplate().execute(new JpaCallback() {
			public Object doInJpa(final EntityManager entityManager) throws PersistenceException {
				final Query query = entityManager.createQuery(hql);
				setParams(query, params);
				return query.getResultList();
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.model.dao.VulpeDAO#executeProcedure(java.lang.String,
	 * java.util.List)
	 */
	public CallableStatement executeProcedure(final String name, final List<Parameter> parameters)
			throws VulpeApplicationException {
		CallableStatement cstmt = null;
		try {
			final EntityManager entityManager = getJpaTemplate().getEntityManagerFactory()
					.createEntityManager();
			final Connection connection = ((HibernateEntityManager) entityManager).getSession()
					.connection();
			final StringBuilder call = new StringBuilder();
			call.append("{call ");
			call.append(name);
			int count = 0;
			if (parameters != null) {
				call.append("(");
				do {
					if (count > 0) {
						call.append(",");
					}
					call.append("?");
					count++;
				} while (count < parameters.size());
				call.append(")");
			}
			call.append("}");
			if (parameters != null) {
				cstmt = connection.prepareCall(call.toString());
				count = 0;
				for (Parameter parameter : parameters) {
					count++;
					if (parameter.getType() == Types.ARRAY) {
						// Connection nativeConnection =
						// cstmt.getConnection().getMetaData().getConnection();
						// ArrayDescriptor objectArrayDescriptor =
						// ArrayDescriptor.createDescriptor(
						// parameter.getArrayType(), nativeConnection);
						// Array array = new ARRAY(objectArrayDescriptor,
						// nativeConnection, parameter
						// .getArrayValues());
						// cstmt.setArray(count, array);
					} else {
						cstmt.setObject(count, parameter.getValue(), parameter.getType());
					}
					if (parameter.isOut()) {
						if (parameter.getType() == Types.ARRAY) {
							cstmt
									.registerOutParameter(count, Types.ARRAY, parameter
											.getArrayType());
						} else {
							cstmt.registerOutParameter(count, parameter.getType());
						}
					}
				}
			}
			cstmt.execute();
		} catch (SQLException e) {
			throw new VulpeApplicationException(e.getMessage());
		}
		return cstmt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.model.dao.VulpeDAO#executeFunction(java.lang.String, int,
	 * java.util.List)
	 */
	@Override
	public CallableStatement executeFunction(final String name, final int returnType,
			final List<Parameter> parameters) throws VulpeApplicationException {
		CallableStatement cstmt = null;
		try {
			final EntityManager entityManager = getJpaTemplate().getEntityManagerFactory()
					.createEntityManager();
			final Connection connection = ((HibernateEntityManager) entityManager).getSession()
					.connection();
			final StringBuilder call = new StringBuilder();
			call.append("{? = call ");
			call.append(name);
			int count = 0;
			if (parameters != null) {
				call.append("(");
				do {
					if (count > 0) {
						call.append(",");
					}
					call.append("?");
					count++;
				} while (count < parameters.size());
				call.append(")");
			}
			call.append("}");
			if (parameters != null) {
				cstmt = connection.prepareCall(call.toString());
				if (returnType != 0) {
					cstmt.registerOutParameter(1, returnType);
					count = 1;
				} else {
					count = 0;
				}
				for (Parameter parameter : parameters) {
					count++;
					if (parameter.getType() == Types.ARRAY) {
						// Connection nativeConnection =
						// cstmt.getConnection().getMetaData().getConnection();
						// ArrayDescriptor objectArrayDescriptor =
						// ArrayDescriptor.createDescriptor(
						// parameter.getArrayType(), nativeConnection);
						// Array array = new ARRAY(objectArrayDescriptor,
						// nativeConnection, parameter
						// .getArrayValues());
						// cstmt.setArray(count, array);
					} else {
						cstmt.setObject(count, parameter.getValue(), parameter.getType());
					}
					if (parameter.isOut()) {
						if (parameter.getType() == Types.ARRAY) {
							cstmt
									.registerOutParameter(count, Types.ARRAY, parameter
											.getArrayType());
						} else {
							cstmt.registerOutParameter(count, parameter.getType());
						}
					}
				}
			}
			cstmt.execute();
		} catch (SQLException e) {
			throw new VulpeApplicationException(e.getMessage());
		}
		return cstmt;
	}

	/**
	 * Prepares object Query, setting parameters
	 */
	protected void setParams(final Query query, final Map<String, Object> params) {
		if (params != null) {
			for (String name : params.keySet()) {
				final Object value = params.get(name);
				query.setParameter(name, value);
			}
		}
	}

	/**
	 * Returns NamedQuery defined in entity
	 */
	protected NamedQuery getNamedQuery(final Class<?> entityClass, final String nameQuery) {
		if (entityClass.isAnnotationPresent(NamedQueries.class)) {
			final NamedQueries namedQueries = entityClass.getAnnotation(NamedQueries.class);
			for (NamedQuery namedQuery : namedQueries.value()) {
				if (namedQuery.name().equals(nameQuery)) {
					return namedQuery;
				}
			}
		} else if (entityClass.isAnnotationPresent(NamedQuery.class)) {
			final NamedQuery namedQuery = entityClass.getAnnotation(NamedQuery.class);
			if (namedQuery.name().equals(nameQuery)) {
				return namedQuery;
			}
		}
		return null;
	}

	public EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	/**
	 * 
	 * @param entity
	 */
	protected void loadEntityRelationships(final ENTITY entity, final boolean load) {
		final List<ENTITY> entities = new ArrayList<ENTITY>();
		entities.add(entity);
		loadRelationships(entities, null, load);
	}

	/**
	 * Load relationships and optimize lazy load.
	 * 
	 * @param entities
	 * @param params
	 */
	protected void loadRelationships(final List<ENTITY> entities, final Map<String, Object> params,
			final boolean load) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Method loadRelationships - Start");
		}
		final Class<?> entityClass = entities.get(0).getClass();
		final QueryConfiguration queryConfiguration = entityClass
				.getAnnotation(QueryConfiguration.class);
		if (queryConfiguration != null && queryConfiguration.relationships().length > 0) {
			final List<ID> parentIds = new ArrayList<ID>();
			for (final ENTITY parent : entities) {
				parentIds.add(parent.getId());
			}
			for (final Relationship relationship : queryConfiguration.relationships()) {
				try {
					final StringBuilder hql = new StringBuilder();
					final String parentName = VulpeStringUtil.lowerCaseFirst(entityClass
							.getSimpleName());
					final Class propertyType = PropertyUtils.getPropertyType(entityClass
							.newInstance(), relationship.property());
					final boolean oneToMany = Collection.class.isAssignableFrom(propertyType);
					if (oneToMany && load) {
						hql.append("select obj ");
					} else {
						hql.append("select new map(obj.id as id");
						for (final String attribute : relationship.attributes()) {
							if ("id".equals(attribute)) {
								continue;
							}
							if (oneToMany) {
								hql.append(", ").append("obj.").append(attribute).append(" as ")
										.append(attribute);
							} else {
								final Class attributeType = PropertyUtils.getPropertyType(
										entityClass.newInstance(), attribute);
								boolean vulpeEntity = VulpeEntity.class
										.isAssignableFrom(attributeType);
								hql.append(", ").append("obj.").append(
										attribute + (vulpeEntity ? ".id" : "")).append(" as ")
										.append(attribute);
							}
						}
						if (oneToMany) {
							hql.append(", obj.").append(parentName).append(".id as ").append(
									parentName);
						}
						hql.append(")");
					}
					final String className = relationship.target().equals(Class.class) ? propertyType
							.getSimpleName()
							: relationship.target().getSimpleName();
					hql.append(" from ").append(className).append(" obj");
					if (oneToMany) {
						hql.append(" where obj.").append(parentName).append(".id in (:parentIds)");
					} else {
						hql.append(" where obj.id in (:ids)");
					}
					if (relationship.parameters() != null) {
						for (QueryParameter parameter : relationship.parameters()) {
							if (params.containsKey(parameter.name())) {
								hql.append(" and ");
								hql.append(
										StringUtils.isNotEmpty(parameter.alias()) ? parameter
												.alias() : "obj").append(".");
								hql.append(parameter.name());
								final Like like = VulpeReflectUtil.getInstance()
										.getAnnotationInField(Like.class, relationship.target(),
												parameter.name());
								hql.append(" ").append(
										like != null ? "like" : parameter.operator().getValue())
										.append(" ");
								hql.append(":").append(parameter.name());
							}
						}
					}
					final Query query = getJpaTemplate().getEntityManager().createQuery(
							hql.toString());
					if (oneToMany) {
						query.setParameter("parentIds", parentIds);
					} else {
						final List<ID> ids = new ArrayList<ID>();
						for (ENTITY entity : entities) {
							final VulpeEntity<ID> propertyEntity = (VulpeEntity<ID>) PropertyUtils
									.getProperty(entity, relationship.property());
							ids.add(propertyEntity.getId());
						}
						query.setParameter("ids", ids);
					}
					if (relationship.parameters() != null) {
						for (QueryParameter parameter : relationship.parameters()) {
							if (params.containsKey(parameter.name())) {
								query.setParameter(parameter.name(), params.get(parameter.name()));
							}
						}
					}
					final List<ENTITY> childs = new ArrayList<ENTITY>();
					final Map<ID, ID> relationshipIds = new HashMap<ID, ID>();
					if (oneToMany && load) {
						final List<ENTITY> result = query.getResultList();
						for (final ENTITY child : result) {
							final VulpeEntity<ID> parent = (VulpeEntity<ID>) PropertyUtils
									.getProperty(child, parentName);
							relationshipIds.put(child.getId(), parent.getId());
							childs.add(child);
						}
					} else {
						final List<Map> result = query.getResultList();
						for (final Map map : result) {
							final ENTITY child = (ENTITY) (oneToMany ? relationship.target()
									.newInstance() : propertyType.newInstance());
							PropertyUtils.setProperty(child, "id", map.get("id"));
							relationshipIds.put(child.getId(), (ID) map.get(parentName));
							for (final String attribute : relationship.attributes()) {
								if (oneToMany) {
									PropertyUtils.setProperty(child, attribute, map.get(attribute));
								} else {
									final Class attributeType = PropertyUtils.getPropertyType(
											entityClass.newInstance(), attribute);
									boolean vulpeEntity = VulpeEntity.class
											.isAssignableFrom(attributeType);
									if (vulpeEntity) {
										VulpeEntity<ID> parent = (VulpeEntity<ID>) attributeType
												.newInstance();
										parent.setId((ID) map.get(attribute));
										PropertyUtils.setProperty(child, attribute, parent);
									} else {
										PropertyUtils.setProperty(child, attribute, map
												.get(attribute));
									}
								}
							}
							childs.add(child);
						}
					}
					for (final ENTITY parent : entities) {
						if (VulpeValidationUtil.isEmpty(childs)) {
							PropertyUtils.setProperty(parent, relationship.property(), null);
						} else {
							final List<ENTITY> loadedChilds = new ArrayList<ENTITY>();
							for (final ENTITY child : childs) {
								if (oneToMany) {
									final ID parentId = (ID) relationshipIds.get(child.getId());
									if (parent.getId().equals(parentId)) {
										PropertyUtils.setProperty(child, parentName, parent);
										loadedChilds.add(child);
									}
								}
								PropertyUtils.setProperty(parent, relationship.property(),
										oneToMany ? loadedChilds : child);
							}
						}
					}
				} catch (Exception e) {
					LOG.error(e);
				}
			}
			if (LOG.isDebugEnabled()) {
				LOG.debug("Method loadRelationships - End");
			}
		}
	}
}