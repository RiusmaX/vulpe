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
import org.apache.log4j.Logger;
import org.hibernate.ejb.HibernateEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.JpaTemplate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.vulpe.commons.util.VulpeReflectUtil;
import org.vulpe.exception.VulpeApplicationException;
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
					List<ENTITY> entities = (List<ENTITY>) PropertyUtils.getProperty(entity, field
							.getName());
					for (ENTITY entity2 : entities) {
						PropertyUtils.setProperty(entity2, oneToMany.mappedBy(), entity);
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

}