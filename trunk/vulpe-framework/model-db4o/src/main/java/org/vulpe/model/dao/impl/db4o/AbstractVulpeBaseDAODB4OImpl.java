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
import org.vulpe.commons.VulpeReflectUtil;
import org.vulpe.commons.db4o.DB4OUtil;
import org.vulpe.exception.VulpeSystemException;
import org.vulpe.model.annotations.Param;
import org.vulpe.model.dao.impl.AbstractVulpeBaseDAO;
import org.vulpe.model.entity.VulpeBaseEntity;
import org.vulpe.model.entity.db4o.Identifier;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

/**
 * Default implementation of DAO for CRUD's with DB4O.
 *
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 */
@SuppressWarnings( { "unchecked" })
public abstract class AbstractVulpeBaseDAODB4OImpl<ENTITY extends VulpeBaseEntity<ID>, ID extends Serializable & Comparable>
		extends AbstractVulpeBaseDAO<ENTITY, ID> {

	protected static final Logger LOG = Logger.getLogger(AbstractVulpeBaseDAODB4OImpl.class
			.getName());

	/*
	 * (non-Javadoc)
	 *
	 * @see org.vulpe.model.dao.VulpeBaseDAO#merge(java.lang.Object)
	 */
	public <T> T merge(final T entity) {
		final ObjectContainer container = getObjectContainer();
		try {
			final Long identifier = ((VulpeBaseEntity<Long>) entity).getId();
			VulpeBaseEntity<Long> entityReferenced = null;
			if (VulpeBaseEntity.class.isAssignableFrom(entity.getClass()) && identifier == null) {
				((VulpeBaseEntity<Long>) entity).setId(getId(entity));
			} else {
				entityReferenced = (VulpeBaseEntity<Long>) entity.getClass().newInstance();
				entityReferenced.setId(identifier);
				entityReferenced = (VulpeBaseEntity<Long>) container.queryByExample(
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
	public <T extends VulpeBaseEntity<Long>> Long getId(final Object entity) {
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
	public <T extends VulpeBaseEntity<Long>> T getObject(final T entity) {
		final List<T> list = getList(entity);
		return list == null ? null : list.get(0);
	}

	/**
	 * Get list of entity by example.
	 *
	 * @param entity
	 * @return
	 */
	public <T extends VulpeBaseEntity<Long>> List<T> getList(final T entity) {
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
						&& !field.isAnnotationPresent(Param.class)) {
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
						} else if (VulpeBaseEntity.class.isAssignableFrom(value.getClass())) {
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
				if (VulpeBaseEntity.class.isAssignableFrom(field.getType())) {
					try {
						final VulpeBaseEntity<Long> valueEntity = (VulpeBaseEntity) value;
						if (valueEntity.getId() != null) {
							final VulpeBaseEntity<Long> newEntity = (VulpeBaseEntity<Long>) field
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
						if (VulpeBaseEntity.class.isAssignableFrom(object.getClass())) {
							try {
								String attributeName = entity.getClass().getSimpleName();
								attributeName = attributeName.substring(0, 1).toLowerCase()
										+ attributeName.substring(1);
								final VulpeBaseEntity<Long> detail = (VulpeBaseEntity<Long>) object;
								repair(detail, container);
								if (detail.getId() == null) {
									detail.setId(getId(detail));
									PropertyUtils.setProperty(detail, attributeName, entity);
								} else {
									final VulpeBaseEntity<Long> valueEntity = (VulpeBaseEntity) entity;
									final VulpeBaseEntity<Long> newEntity = (VulpeBaseEntity<Long>) entity
											.getClass().newInstance();
									newEntity.setId(valueEntity.getId());
									final VulpeBaseEntity<Long> newDetailEntity = (VulpeBaseEntity<Long>) object
											.getClass().newInstance();
									newDetailEntity.setId(detail.getId());
									PropertyUtils.setProperty(newDetailEntity, attributeName,
											newEntity);
									final ObjectSet objectSet = getObjectContainer()
											.queryByExample(newDetailEntity);
									if (objectSet.hasNext()) {
										final VulpeBaseEntity<Long> persitedDetail = (VulpeBaseEntity<Long>) objectSet
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
			if (VulpeBaseEntity.class.isAssignableFrom(field.getType())) {
				final VulpeBaseEntity<Long> value = VulpeReflectUtil.getInstance().getFieldValue(
						detail, field.getName());
				if (value != null) {
					try {
						if (value.getId() != null) {
							final VulpeBaseEntity<Long> newObject = (VulpeBaseEntity<Long>) value
									.getClass().newInstance();
							newObject.setId(value.getId());
							final ObjectSet objectSet = getObjectContainer().queryByExample(
									newObject);
							if (objectSet.hasNext()) {
								final VulpeBaseEntity<Long> objectPersisted = (VulpeBaseEntity<Long>) objectSet
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
		return DB4OUtil.getInstance().getObjectContainer();
	}

	/**
	 * Close DB4O instance.
	 */
	public void close() {
		DB4OUtil.getInstance().close();
	}

	/**
	 * Commit data.
	 */
	public void commit() {
		DB4OUtil.getInstance().commit();
	}

	/**
	 * Rollback data.
	 */
	public void rollback() {
		DB4OUtil.getInstance().rollback();
	}

}