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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.ejb.HibernateEntityManager;
import org.vulpe.commons.VulpeConstants.Model.Entity;
import org.vulpe.commons.util.VulpeReflectUtil;
import org.vulpe.commons.util.VulpeStringUtil;
import org.vulpe.commons.util.VulpeValidationUtil;
import org.vulpe.exception.VulpeApplicationException;
import org.vulpe.model.annotations.Like;
import org.vulpe.model.annotations.QueryConfiguration;
import org.vulpe.model.annotations.QueryConfigurations;
import org.vulpe.model.annotations.QueryParameter;
import org.vulpe.model.annotations.Relationship;
import org.vulpe.model.annotations.Relationship.RelationshipScope;
import org.vulpe.model.dao.impl.AbstractVulpeBaseDAO;
import org.vulpe.model.entity.Parameter;
import org.vulpe.model.entity.VulpeEntity;

/**
 * Default implementation of DAO with JPA
 * 
 * @author <a href="mailto:fabio.viana@vulpe.org">Fábio Viana</a>
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 */
@SuppressWarnings( { "unchecked" })
public abstract class AbstractVulpeBaseDAOJPA<ENTITY extends VulpeEntity<ID>, ID extends Serializable & Comparable>
		extends AbstractVulpeBaseDAO<ENTITY, ID> {

	private static final Logger LOG = Logger.getLogger(AbstractVulpeBaseDAOJPA.class.getName());

	@PersistenceContext
	private EntityManager entityManager;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.model.dao.VulpeDAO#merge(java.lang.Object)
	 */
	public <T> T merge(final T entity) {
		List<Field> fields = VulpeReflectUtil.getInstance().getFields(entity.getClass());
		for (final Field field : fields) {
			final OneToMany oneToMany = field.getAnnotation(OneToMany.class);
			if (oneToMany != null) {
				try {
					final List<ENTITY> entities = (List<ENTITY>) PropertyUtils.getProperty(entity,
							field.getName());
					if (VulpeValidationUtil.isNotEmpty(entities)) {
						for (final ENTITY entity2 : entities) {
							PropertyUtils.setProperty(entity2, oneToMany.mappedBy(), entity);
						}
					}
				} catch (Exception e) {
					LOG.error(e);
				}
			}
		}
		T merged = entity;
		if (((ENTITY) entity).getMap().containsKey(Entity.ONLY_UPDATE_DETAILS)) {
			final List<String> detailNames = (List<String>) ((ENTITY) entity).getMap().get(
					Entity.ONLY_UPDATE_DETAILS);
			if (VulpeValidationUtil.isNotEmpty(detailNames)) {
				for (final String detail : detailNames) {
					List<ENTITY> details = null;
					try {
						details = (List<ENTITY>) PropertyUtils.getProperty(entity, detail);
					} catch (Exception e) {
						LOG.error(e);
					}
					if (VulpeValidationUtil.isNotEmpty(details)) {
						for (final ENTITY entity2 : details) {
							loadEntityRelationships(entity2);
							merge(entity2);
						}
					}
				}
			}
		} else {
			merged = entityManager.merge(entity);
		}
		loadEntityRelationships((ENTITY) merged);
		entityManager.flush();
		return merged;
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
		final Query query = entityManager.createQuery(hql);
		setParams(query, params);
		return query.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.model.dao.VulpeDAO#executeProcedure(java.lang.String,
	 * java.util.List)
	 */
	public CallableStatement executeProcedure(final String name, final List<Parameter> parameters)
			throws VulpeApplicationException {
		return executeCallableStatement(name, null, parameters);
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
		return executeCallableStatement(name, returnType, parameters);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.model.dao.VulpeDAO#executeCallableStatement(java.lang.String,
	 * java.lang.Integer, java.util.List)
	 */
	public CallableStatement executeCallableStatement(final String name, final Integer returnType,
			final List<Parameter> parameters) throws VulpeApplicationException {
		CallableStatement cstmt = null;
		try {
			final Connection connection = ((HibernateEntityManager) entityManager).getSession()
					.connection();
			final StringBuilder call = new StringBuilder();
			call.append(returnType == null ? "{call " : "{? = call ");
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
				if (returnType == null) {
					if (returnType != 0) {
						cstmt.registerOutParameter(1, returnType);
						count = 1;
					} else {
						count = 0;
					}
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

	/**
	 * 
	 * @param entity
	 */
	protected void loadEntityRelationships(final ENTITY entity) {
		final List<ENTITY> entities = new ArrayList<ENTITY>();
		entities.add(entity);
		loadRelationships(entities, null, true);
	}

	/**
	 * 
	 * @param attribute
	 * @param parent
	 * @return
	 */
	private String mountRelationshipReference(final String attribute, final String parent) {
		final StringBuilder hqlAttribute = new StringBuilder();
		final List<String> attributeList = new ArrayList<String>();
		String first = attribute.substring(0, attribute.indexOf("["));
		if (StringUtils.isNotEmpty(first)) {
			first += "_";
		}
		separateAttributes(attributeList, attribute);
		for (String attributeName : attributeList) {
			hqlAttribute.append(", obj.").append(attributeName).append(" as ").append(first)
					.append(attributeName.replaceAll("\\.", "_"));
		}
		return hqlAttribute.toString();
	}

	/**
	 * 
	 * @param attributeList
	 * @param value
	 */
	private static void separateAttributes(List<String> attributeList, String value) {
		List<String> attributeInternalList = new ArrayList<String>();
		if (value.indexOf("[") != -1) {
			String first = VulpeValidationUtil.isEmpty(attributeList) ? "" : value.substring(0,
					value.indexOf("["));
			if (StringUtils.isNotEmpty(first)) {
				first += ".";
			}
			String subAttributes = value.substring(value.indexOf("[") + 1, value.lastIndexOf("]"));
			if (subAttributes.contains(",")) {
				while (subAttributes.contains(",")) {
					int position = subAttributes.indexOf(",");
					String subAttributesInternal = subAttributes.substring(0, position);
					if (!subAttributesInternal.contains("[")
							&& !subAttributesInternal.contains("]")) {
						attributeInternalList.add(first + subAttributes.substring(0, position));
						subAttributes = subAttributes.substring(position + 1);
						boolean last = subAttributes.substring(position + 1).indexOf(",") == -1;
						if (last) {
							attributeInternalList.add(first + subAttributes);
							subAttributes = "";
						}
					} else {
						position = subAttributes.lastIndexOf("],");
						if (position != -1) {
							attributeInternalList.add(first
									+ subAttributes.substring(0, position + 1));
							subAttributes = subAttributes.substring(position + 2);
						} else {
							position = subAttributes.indexOf(",");
							boolean last = subAttributes.substring(position + 1).indexOf(",") == -1;
							if (last) {
								attributeInternalList.add(first + subAttributes);
								subAttributes = "";
							} else {
								attributeInternalList.add(first
										+ "."
										+ subAttributes.substring(0,
												subAttributes.lastIndexOf("]") + 1));
							}
						}
					}
				}
				if (StringUtils.isNotEmpty(subAttributes)) {
					attributeList.add(first + subAttributes.replace("[", ".").replace("]", ""));
				}
			} else {
				attributeList.add(first + subAttributes);
			}
		}
		for (String string : attributeInternalList) {
			if (string.contains("[")) {
				separateAttributes(attributeList, string);
			} else {
				attributeList.add(string);
			}
		}
	}

	/**
	 * Load relationships and optimize lazy load.
	 * 
	 * @param entities
	 * @param params
	 */
	protected void loadRelationships(final List<ENTITY> entities, final Map<String, Object> params,
			final boolean onlyInCRUD) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Method loadRelationships - Start");
		}
		if (VulpeValidationUtil.isNotEmpty(entities)) {
			final ENTITY firstEntity = entities.get(0);
			final String queryConfigurationName = firstEntity.getMap().containsKey(
					Entity.QUERY_CONFIGURATION_NAME) ? (String) firstEntity.getMap().get(
					Entity.QUERY_CONFIGURATION_NAME) : "default";
			final Class<?> entityClass = entities.get(0).getClass();
			final QueryConfigurations queryConfigurations = entityClass
					.getAnnotation(QueryConfigurations.class);
			if (queryConfigurations != null && queryConfigurations.value().length > 0) {
				for (QueryConfiguration queryConfiguration : queryConfigurations.value()) {
					if (queryConfiguration != null
							&& queryConfiguration.name().equals(queryConfigurationName)
							&& queryConfiguration.relationships().length > 0) {
						final List<ID> parentIds = new ArrayList<ID>();
						for (final ENTITY parent : entities) {
							parentIds.add(parent.getId());
						}
						for (final Relationship relationship : queryConfiguration.relationships()) {
							final boolean loadAll = relationship.attributes().length == 1
									&& "*".equals(relationship.attributes()[0]);
							if ((!relationship.scope().equals(RelationshipScope.SELECT) && loadAll && !onlyInCRUD)
									|| (relationship.scope().equals(RelationshipScope.SELECT) && onlyInCRUD)) {
								continue;
							}
							try {
								final StringBuilder hql = new StringBuilder();
								final String parentName = VulpeStringUtil
										.lowerCaseFirst(entityClass.getSimpleName());
								final Class propertyType = PropertyUtils.getPropertyType(
										entityClass.newInstance(), relationship.property());
								final boolean oneToMany = VulpeReflectUtil.getInstance()
										.getAnnotationInField(OneToMany.class, entityClass,
												relationship.property()) != null;
								final Map<String, String> hqlAttributes = new HashMap<String, String>();
								final List<String> hqlJoin = new ArrayList<String>();

								if (oneToMany && loadAll) {
									hql.append("select obj ");
								} else {
									hql.append("select new map(obj.id as id");
									for (final String attribute : relationship.attributes()) {
										if ("id".equals(attribute)) {
											continue;
										}
										if (oneToMany) {
											hql.append(", ").append("obj.").append(attribute)
													.append(" as ").append(attribute);
										} else {
											if (attribute.contains("[")) {
												final StringBuilder hqlAttribute = new StringBuilder(
														"select new map(obj.id as id");
												String attributeParent = attribute.substring(0,
														attribute.indexOf("["));
												hqlAttribute.append(mountRelationshipReference(
														attribute, null));
												if (StringUtils.isEmpty(attributeParent)) {
													attributeParent = relationship.property();
													final Class attributeParentType = PropertyUtils
															.getPropertyType(firstEntity,
																	attributeParent);
													hqlAttribute.append(") from ").append(
															attributeParentType.getSimpleName())
															.append(" obj");
												} else {
													final Class attributeParentType = PropertyUtils
															.getPropertyType(propertyType
																	.newInstance(), attributeParent);
													hqlAttribute.append(") from ").append(
															attributeParentType.getSimpleName())
															.append(" obj");
													int joinCount = hqlJoin.size() + 1;
													hqlJoin.add((joinCount > 0 ? "" : ",")
															+ "left outer join obj."
															+ attributeParent + " obj" + joinCount);
													hql.append(", obj").append(joinCount).append(
															".id").append(" as ").append(
															attributeParent);
												}
												hqlAttribute.append(" where obj.id = :id");
												hqlAttributes.put(attribute, hqlAttribute
														.toString());
											} else {
												final Class attributeType = PropertyUtils
														.getPropertyType(
																propertyType.newInstance(),
																attribute);
												boolean manyToOne = attributeType
														.isAnnotationPresent(ManyToOne.class)
														|| VulpeEntity.class
																.isAssignableFrom(attributeType);
												hql.append(", ").append("obj.").append(
														attribute + (manyToOne ? ".id" : ""))
														.append(" as ").append(attribute);
											}
										}
									}
									if (oneToMany) {
										hql.append(", obj.").append(parentName).append(".id as ")
												.append(parentName);
									}
									hql.append(")");
								}
								final String className = relationship.target().equals(Class.class) ? propertyType
										.getSimpleName()
										: relationship.target().getSimpleName();
								hql.append(" from ").append(className).append(" obj ");
								for (String join : hqlJoin) {
									hql.append(join);
								}
								if (oneToMany) {
									hql.append(" where obj.").append(parentName).append(
											".id in (:parentIds)");
								} else {
									hql.append(" where obj.id in (:ids)");
								}
								if (relationship.parameters() != null) {
									for (final QueryParameter parameter : relationship.parameters()) {
										if (params.containsKey(parameter.name())) {
											hql.append(" and ");
											hql
													.append(
															StringUtils.isNotEmpty(parameter
																	.alias()) ? parameter.alias()
																	: "obj").append(".");
											hql.append(parameter.name());
											final Like like = VulpeReflectUtil
													.getInstance()
													.getAnnotationInField(Like.class,
															relationship.target(), parameter.name());
											hql.append(" ").append(
													like != null ? "like" : parameter.operator()
															.getValue()).append(" ");
											hql.append(":").append(parameter.name());
										}
									}
								}
								final Query query = entityManager.createQuery(hql.toString());
								if (oneToMany) {
									query.setParameter("parentIds", parentIds);
								} else {
									final List<ID> ids = new ArrayList<ID>();
									for (final ENTITY entity : entities) {
										final VulpeEntity<ID> propertyEntity = (VulpeEntity<ID>) PropertyUtils
												.getProperty(entity, relationship.property());
										ids.add(propertyEntity.getId());
									}
									query.setParameter("ids", ids);
								}
								if (relationship.parameters() != null) {
									for (final QueryParameter parameter : relationship.parameters()) {
										if (params.containsKey(parameter.name())) {
											query.setParameter(parameter.name(), params
													.get(parameter.name()));
										}
									}
								}
								final List<ENTITY> childs = new ArrayList<ENTITY>();
								final Map<ID, ID> relationshipIds = new HashMap<ID, ID>();
								if (oneToMany && loadAll) {
									final List<ENTITY> result = query.getResultList();
									for (final ENTITY child : result) {
										final VulpeEntity<ID> parent = (VulpeEntity<ID>) PropertyUtils
												.getProperty(child, parentName);
										relationshipIds.put(child.getId(), parent.getId());
										final List<Field> childFields = VulpeReflectUtil
												.getInstance().getFields(child.getClass());
										for (final Field field : childFields) {
											if (field.getName().equals(parentName)) {
												continue;
											}
											if (field.isAnnotationPresent(ManyToOne.class)) {
												final VulpeEntity<ID> fieldValue = (VulpeEntity<ID>) PropertyUtils
														.getProperty(child, field.getName());
												if (fieldValue != null) {
													PropertyUtils.setProperty(child, field
															.getName(), (ENTITY) getEntityManager()
															.getReference(field.getType(),
																	fieldValue.getId()));
												}
											}
										}
										childs.add(child);
									}
								} else {
									final List<Map> result = query.getResultList();
									for (final Map map : result) {
										final ENTITY child = (ENTITY) (oneToMany ? relationship
												.target().newInstance() : propertyType
												.newInstance());
										PropertyUtils.setProperty(child, "id", map.get("id"));
										relationshipIds
												.put(child.getId(), (ID) map.get(parentName));
										for (final String attribute : relationship.attributes()) {
											if (oneToMany) {
												PropertyUtils.setProperty(child, attribute, map
														.get(attribute));
											} else {
												if (hqlAttributes.containsKey(attribute)) {
													final String attributeParent = attribute
															.substring(0, attribute.indexOf("["));
													final String hqlAttribute = hqlAttributes
															.get(attribute);
													if (StringUtils.isNotEmpty(attributeParent)) {
														final Class attributeType = PropertyUtils
																.getPropertyType(propertyType
																		.newInstance(),
																		attributeParent);
														final VulpeEntity<ID> newAttribute = (VulpeEntity<ID>) attributeType
																.newInstance();
														newAttribute.setId((ID) map
																.get(attributeParent));
														final Query queryAttribute = getEntityManager()
																.createQuery(hqlAttribute)
																.setParameter("id",
																		newAttribute.getId());
														final Map attributeMap = (Map) queryAttribute
																.getSingleResult();
														mountProperties(attributeMap, newAttribute,
																attribute, null);
														PropertyUtils.setProperty(child,
																attributeParent, newAttribute);
													} else {
														final Query queryAttribute = getEntityManager()
																.createQuery(hqlAttribute)
																.setParameter("id", child.getId());
														final Map attributeMap = (Map) queryAttribute
																.getSingleResult();
														mountProperties(attributeMap, child,
																attribute, null);
													}
												} else {
													final Class attributeType = PropertyUtils
															.getPropertyType(propertyType
																	.newInstance(), attribute);
													boolean manyToOne = attributeType
															.isAnnotationPresent(ManyToOne.class)
															|| VulpeEntity.class
																	.isAssignableFrom(attributeType);
													if (manyToOne) {
														final VulpeEntity<ID> newAttribute = (VulpeEntity<ID>) attributeType
																.newInstance();
														newAttribute.setId((ID) map.get(attribute));
														PropertyUtils.setProperty(child, attribute,
																newAttribute);
													} else {
														PropertyUtils.setProperty(child, attribute,
																map.get(attribute));
													}
												}
											}
										}
										childs.add(child);
									}
								}
								for (final ENTITY parent : entities) {
									if (VulpeValidationUtil.isEmpty(childs)) {
										PropertyUtils.setProperty(parent, relationship.property(),
												null);
									} else {
										final List<ENTITY> loadedChilds = new ArrayList<ENTITY>();
										for (final ENTITY child : childs) {
											if (oneToMany) {
												final ID parentId = (ID) relationshipIds.get(child
														.getId());
												if (parent.getId().equals(parentId)) {
													PropertyUtils.setProperty(child, parentName,
															parent);
													loadedChilds.add(child);
												}
											}
											PropertyUtils.setProperty(parent, relationship
													.property(), oneToMany ? loadedChilds : child);
										}
									}
								}
							} catch (Exception e) {
								LOG.error(e);
							}
						}
					}
					if (LOG.isDebugEnabled()) {
						LOG.debug("Method loadRelationships - End");
					}
				}
			}
		}
	}

	/**
	 * 
	 * @param map
	 * @param entity
	 * @param attribute
	 * @param parent
	 */
	private void mountProperties(final Map<String, Object> map, final VulpeEntity<ID> entity,
			final String attribute, final String parent) {
		try {
			final List<String> attributeList = new ArrayList<String>();
			String first = attribute.substring(0, attribute.indexOf("["));
			if (StringUtils.isNotEmpty(first)) {
				first += "_";
			}
			separateAttributes(attributeList, attribute);
			for (String attributeName : attributeList) {
				mountPropertiesByAttributes(map, entity, attributeName, first + attributeName);
			}
		} catch (Exception e) {
			LOG.error(e);
		}
	}

	/**
	 * 
	 * @param map
	 * @param entity
	 * @param attributeName
	 * @param attributeReference
	 */
	private void mountPropertiesByAttributes(final Map<String, Object> map,
			final VulpeEntity<ID> entity, String attributeName, final String attributeReference) {
		try {
			if (attributeName.contains(".")) {
				final String attributeParent = attributeName.substring(0, attributeName
						.indexOf("."));
				attributeName = attributeName.substring(attributeName.indexOf(".") + 1);
				VulpeEntity<ID> newEntity = (VulpeEntity<ID>) PropertyUtils.getProperty(entity,
						attributeParent);
				if (newEntity == null) {
					final Class attributeType = PropertyUtils.getPropertyType(entity,
							attributeParent);
					newEntity = (VulpeEntity<ID>) attributeType.newInstance();
				}
				if (attributeName.contains(".")) {
					mountPropertiesByAttributes(map, newEntity, attributeName, attributeReference);
				} else {
					PropertyUtils.setProperty(newEntity, attributeName, map.get(attributeReference
							.replaceAll("\\.", "_")));
				}
				PropertyUtils.setProperty(entity, attributeParent, newEntity);
			} else {
				PropertyUtils.setProperty(entity, attributeName, map.get(attributeReference
						.replaceAll("\\.", "_")));
			}
		} catch (Exception e) {
			LOG.error(e);
		}
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public Object findByNamedQuery(final String name) {
		return getEntityManager().createNamedQuery(name).getSingleResult();
	}

	public Object findByNamedQueryAndNamedParams(final String name, final Map<String, Object> map) {
		final Query query = getEntityManager().createNamedQuery(name);
		for (final String parameter : map.keySet()) {
			query.setParameter(parameter, map.get(parameter));
		}
		return query.getSingleResult();
	}

	public List<?> listByNamedQuery(final String name) {
		return getEntityManager().createNamedQuery(name).getResultList();
	}

	public List<?> listByNamedQueryAndNamedParams(final String name, final Map<String, Object> map) {
		final Query query = getEntityManager().createNamedQuery(name);
		for (final String parameter : map.keySet()) {
			query.setParameter(parameter, map.get(parameter));
		}
		return query.getResultList();
	}
}