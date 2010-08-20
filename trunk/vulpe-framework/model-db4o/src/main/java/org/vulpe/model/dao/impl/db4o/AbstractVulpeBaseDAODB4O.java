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
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Transient;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.vulpe.commons.util.VulpeDB4OUtil;
import org.vulpe.commons.util.VulpeReflectUtil;
import org.vulpe.exception.VulpeSystemException;
import org.vulpe.model.annotations.QueryParameter;
import org.vulpe.model.dao.impl.AbstractVulpeBaseDAO;
import org.vulpe.model.entity.VulpeEntity;
import org.vulpe.model.entity.db4o.Identifier;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

/**
 * Default implementation of DAO for CRUD's with DB4O.
 *
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 */
@SuppressWarnings( { "unchecked" })
public abstract class AbstractVulpeBaseDAODB4O<ENTITY extends VulpeEntity<ID>, ID extends Serializable & Comparable>
		extends AbstractVulpeBaseDAO<ENTITY, ID> {

	protected static final Logger LOG = Logger.getLogger(AbstractVulpeBaseDAODB4O.class
			.getName());

	/*
	 * (non-Javadoc)
	 *
	 * @see org.vulpe.model.dao.VulpeDAO#merge(java.lang.Object)
	 */
	public <T> T merge(final T entity) {
		final ObjectContainer container = getObjectContainer();
		try {
			final Long identifier = ((VulpeEntity<Long>) entity).getId();
			VulpeEntity<Long> entityReferenced = null;
			if (VulpeEntity.class.isAssignableFrom(entity.getClass()) && identifier == null) {
				((VulpeEntity<Long>) entity).setId(getId(entity));
			} else {
				entityReferenced = (VulpeEntity<Long>) entity.getClass().newInstance();
				entityReferenced.setId(identifier);
				entityReferenced = (VulpeEntity<Long>) container.queryByExample(
						entityReferenced).get(0);
				container.ext().bind(entity, container.ext().getID(entityReferenced));
			}
			repairRelationship(entity, container);
			container.store(entity);
		} catch (Exception e) {
			rollback();
			LOG.error(e);
		} finally {
			close();
		}
		return entity;
	}

	/**
	 * Get identifier to entity.
	 *
	 * @param <T>
	 * @param entity
	 * @return
	 */
	public <T extends VulpeEntity<Long>> Long getId(final Object entity) {
		final String entityName = entity.getClass().getSimpleName();
		final List<Identifier> retorno = getObjectContainer().queryByExample(
				new Identifier(entityName));
		Identifier identifier = new Identifier(entityName, Long.valueOf(1));
		if (retorno != null && !retorno.isEmpty()) {
			identifier = retorno.get(0);
			identifier.setSequence(identifier.getSequence() + 1L);
		}
		getObjectContainer().store(identifier);
		return identifier.getSequence();
	}

	/**
	 * Get entity by example.
	 *
	 * @param entity
	 * @return
	 */
	public <T extends VulpeEntity<Long>> T getObject(final T entity) {
		final List<T> list = getList(entity);
		return list == null ? null : list.get(0);
	}

	/**
	 * Get list of entity by example.
	 *
	 * @param entity
	 * @return
	 */
	public <T extends VulpeEntity<Long>> List<T> getList(final T entity) {
		final ObjectContainer container = getObjectContainer();
		try {
			final ObjectSet<T> objectSet = container.queryByExample(entity);
			final List<T> list = objectSet.hasNext() ? new ArrayList<T>() : null;
			while (objectSet.hasNext()) {
				list.add(objectSet.next());
			}
			return list;
		} finally {
			rollback();
		}
	}

	/**
	 * Verified if entity property is empty and set to null;
	 *
	 * @param object
	 */
	public void emptyToNull(final Object object) {
		final List<Field> fields = VulpeReflectUtil.getInstance().getFields(object.getClass());
		for (Field field : fields) {
			try {
				if ((Modifier.isTransient(field.getModifiers()) || field
						.isAnnotationPresent(Transient.class))
						&& !field.isAnnotationPresent(QueryParameter.class)) {
					if (!field.getType().isPrimitive()) {
						PropertyUtils.setProperty(object, field.getName(), null);
					}
				} else {
					final Object value = PropertyUtils.getProperty(object, field.getName());
					if (value != null) {
						if (String.class.isAssignableFrom(field.getType())) {
							if (StringUtils.isEmpty(value.toString()) || "obj.id".equals(value)
									|| "null".equals(value) || "%".equals(value)) {
								PropertyUtils.setProperty(object, field.getName(), null);
							}
						} else if (VulpeEntity.class.isAssignableFrom(value.getClass())) {
							emptyToNull(value);
						}
					}
				}
			} catch (NoSuchMethodException e) {
				LOG.debug("Method not found.", e);
			} catch (Exception e) {
				throw new VulpeSystemException(e);
			}
		}
	}

	/**
	 * Repair relationship of entity.
	 *
	 * @param <T>
	 * @param entity
	 */
	protected <T> void repairRelationship(final T entity, final ObjectContainer container) {
		emptyToNull(entity);
		for (Field field : VulpeReflectUtil.getInstance().getFields(entity.getClass())) {
			final Object value = VulpeReflectUtil.getInstance().getFieldValue(entity,
					field.getName());
			if (value != null) {
				if (VulpeEntity.class.isAssignableFrom(field.getType())) {
					try {
						final VulpeEntity<Long> valueEntity = (VulpeEntity) value;
						if (valueEntity.getId() != null) {
							final VulpeEntity<Long> newEntity = (VulpeEntity<Long>) field
									.getType().newInstance();
							newEntity.setId(valueEntity.getId());
							final ObjectSet objectSet = getObjectContainer().queryByExample(
									newEntity);
							if (objectSet.hasNext()) {
								PropertyUtils
										.setProperty(entity, field.getName(), objectSet.next());
							}
						} else {
							PropertyUtils.setProperty(entity, field.getName(), null);
						}
					} catch (Exception e) {
						LOG.error(e);
					}
				} else if (Collection.class.isAssignableFrom(field.getType())) {
					final Collection details = (Collection) value;
					for (Object object : details) {
						if (VulpeEntity.class.isAssignableFrom(object.getClass())) {
							try {
								String attributeName = entity.getClass().getSimpleName();
								attributeName = attributeName.substring(0, 1).toLowerCase()
										+ attributeName.substring(1);
								final VulpeEntity<Long> detail = (VulpeEntity<Long>) object;
								repair(detail, container);
								if (detail.getId() == null) {
									detail.setId(getId(detail));
									PropertyUtils.setProperty(detail, attributeName, entity);
								} else {
									final VulpeEntity<Long> valueEntity = (VulpeEntity) entity;
									final VulpeEntity<Long> newEntity = (VulpeEntity<Long>) entity
											.getClass().newInstance();
									newEntity.setId(valueEntity.getId());
									final VulpeEntity<Long> newDetailEntity = (VulpeEntity<Long>) object
											.getClass().newInstance();
									newDetailEntity.setId(detail.getId());
									PropertyUtils.setProperty(newDetailEntity, attributeName,
											newEntity);
									final ObjectSet objectSet = getObjectContainer()
											.queryByExample(newDetailEntity);
									if (objectSet.hasNext()) {
										final VulpeEntity<Long> persitedDetail = (VulpeEntity<Long>) objectSet
												.get(0);
										container.ext().bind(detail,
												container.ext().getID(persitedDetail));
										container.store(detail);
									}
								}
							} catch (Exception e) {
								LOG.error(e);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Repair entity.
	 *
	 * @param <T>
	 * @param detail
	 * @param container
	 * @throws Exception
	 */
	public <T> void repair(final T detail, final ObjectContainer container)
			throws VulpeSystemException {
		for (Field field : VulpeReflectUtil.getInstance().getFields(detail.getClass())) {
			if (VulpeEntity.class.isAssignableFrom(field.getType())) {
				final VulpeEntity<Long> value = VulpeReflectUtil.getInstance().getFieldValue(
						detail, field.getName());
				if (value != null) {
					try {
						if (value.getId() != null) {
							final VulpeEntity<Long> newObject = (VulpeEntity<Long>) value
									.getClass().newInstance();
							newObject.setId(value.getId());
							final ObjectSet objectSet = getObjectContainer().queryByExample(
									newObject);
							if (objectSet.hasNext()) {
								final VulpeEntity<Long> objectPersisted = (VulpeEntity<Long>) objectSet
										.get(0);
								PropertyUtils.setProperty(detail, field.getName(), objectPersisted);
							}
						} else {
							PropertyUtils.setProperty(detail, field.getName(), null);
						}
					} catch (Exception e) {
						throw new VulpeSystemException(e);
					}
					repair(value, container);
				}
			}
		}

	}

	/**
	 * Get instance of Object Container.
	 *
	 * @return returns ObjectContainer.
	 */
	public ObjectContainer getObjectContainer() {
		return VulpeDB4OUtil.getInstance().getObjectContainer();
	}

	/**
	 * Close DB4O instance.
	 */
	public void close() {
		VulpeDB4OUtil.getInstance().close();
	}

	/**
	 * Commit data.
	 */
	public void commit() {
		VulpeDB4OUtil.getInstance().commit();
	}

	/**
	 * Rollback data.
	 */
	public void rollback() {
		VulpeDB4OUtil.getInstance().rollback();
	}

}