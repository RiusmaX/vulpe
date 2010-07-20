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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.JpaTemplate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.vulpe.commons.VulpeReflectUtil;
import org.vulpe.exception.VulpeApplicationException;
import org.vulpe.model.dao.impl.AbstractVulpeBaseDAO;
import org.vulpe.model.entity.VulpeEntity;

/**
 * Default implementation of DAO for CRUD's with JPA
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

	/**
	 * Prepares object Query, setting parameters
	 */
	protected void setParams(final Query query, final Map<String, Object> params) {
		if (params != null) {
			for (String name : params.keySet()) {
				Object value = params.get(name);
				if (value instanceof String) {
					value = "%".concat(value.toString()).concat("%");
				}
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