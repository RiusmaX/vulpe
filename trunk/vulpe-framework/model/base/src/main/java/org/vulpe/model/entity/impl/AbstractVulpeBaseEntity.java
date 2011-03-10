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
package org.vulpe.model.entity.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.apache.log4j.Logger;
import org.vulpe.audit.model.annotations.IgnoreAudit;
import org.vulpe.audit.model.annotations.IgnoreAuditHistory;
import org.vulpe.commons.VulpeConstants.Model.Entity;
import org.vulpe.commons.util.VulpeReflectUtil;
import org.vulpe.commons.xml.XMLDateConversor;
import org.vulpe.model.entity.VulpeEntity;
import org.vulpe.model.entity.VulpeSimpleEntity;

import com.thoughtworks.xstream.XStream;

@MappedSuperclass
@SuppressWarnings( { "unchecked", "serial" })
public abstract class AbstractVulpeBaseEntity<ID extends Serializable & Comparable> extends VulpeBaseSimpleEntity
		implements VulpeEntity<ID> {

	private static final Logger LOG = Logger.getLogger(AbstractVulpeBaseEntity.class);

	@IgnoreAudit
	private transient Map<String, Object> map = new HashMap<String, Object>();

	public AbstractVulpeBaseEntity() {
	}

	public boolean isSelected() {
		return this.map.containsKey(Entity.SELECTED) ? (Boolean) this.map.get(Entity.SELECTED) : false;
	}

	public void setSelected(final boolean selected) {
		this.map.put(Entity.SELECTED, selected);
	}

	public String getOrderBy() {
		return this.map.containsKey(Entity.ORDER_BY) ? (String) this.map.get(Entity.ORDER_BY) : null;
	}

	public void setOrderBy(final String orderBy) {
		this.map.put(Entity.ORDER_BY, orderBy);
	}

	@Override
	public boolean equals(final Object obj) {
		final AbstractVulpeBaseEntity<ID> entity = (AbstractVulpeBaseEntity<ID>) obj;
		if ((obj == null || obj.getClass() != this.getClass()) || (entity.getId() == null || getId() == null)) {
			return false;
		}

		return this == entity || getId().equals(entity.getId());
	}

	@Override
	public String toString() {
		return getClass().getSimpleName().concat(this.getId() == null ? "" : ".id: ".concat(this.getId().toString()));
	}

	public int compareTo(final VulpeSimpleEntity entity) {
		if (this.equals(entity)) {
			return 0;
		}

		if (getId() == null) {
			return 999999999;
		}

		if (entity instanceof VulpeEntity) {
			final VulpeEntity persistentEntity = (VulpeEntity) entity;
			if (persistentEntity.getId() == null) {
				return -999999999;
			}

			return getId().compareTo(persistentEntity.getId());
		}
		return 0;
	}

	@Transient
	public boolean isAuditable() {
		return this.getClass().getAnnotation(IgnoreAudit.class) == null;
	}

	@Transient
	public boolean isHistoryAuditable() {
		return this.getClass().getAnnotation(IgnoreAuditHistory.class) == null;
	}

	@Transient
	public String toXMLAudit() {
		String strXml = "";
		final XStream xstream = new XStream();
		try {
			for (Field attribute : VulpeReflectUtil.getFields(this.getClass())) {
				if (!isConvertible(attribute)) {
					xstream.omitField(this.getClass(), attribute.getName());
				}
			}
		} catch (Exception e) {
			LOG.error(e);
		}
		xstream.registerConverter(new XMLDateConversor(), 1);
		strXml = xstream.toXML(this);
		return strXml;
	}

	protected boolean isConvertible(final Field attribute) {
		if (attribute.getAnnotation(IgnoreAudit.class) != null) {
			return false;
		}
		if (attribute.getType().isPrimitive() || attribute.getType() == String.class
				|| attribute.getType() == Character.class || attribute.getType() == Integer.class
				|| attribute.getType() == Short.class || attribute.getType() == Long.class
				|| attribute.getType() == Double.class || attribute.getType() == Date.class
				|| attribute.getType() == java.sql.Date.class || attribute.getType() == java.sql.Timestamp.class) {
			return true;
		}
		return false;
	}

	@Override
	public VulpeEntity<ID> clone() {
		return (VulpeEntity<ID>) super.clone();
	}

	public void setAutocomplete(final String autocomplete) {
		this.map.put(Entity.AUTOCOMPLETE, autocomplete);
	}

	public String getAutocomplete() {
		return this.map.containsKey(Entity.AUTOCOMPLETE) ? (String) this.map.get(Entity.AUTOCOMPLETE) : null;
	}

	public void setMap(final Map<String, Object> map) {
		this.map = map;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public String getQueryConfigurationName() {
		return this.map.containsKey(Entity.QUERY_CONFIGURATION_NAME) ? (String) this.map
				.get(Entity.QUERY_CONFIGURATION_NAME) : "default";
	}

	public void setQueryConfigurationName(final String queryConfigurationName) {
		this.map.put(Entity.QUERY_CONFIGURATION_NAME, queryConfigurationName);
	}

	public void setFakeId(final boolean fakeId) {
		this.map.put(Entity.FAKE_ID, fakeId);
	}

	public boolean isFakeId() {
		return this.map.containsKey(Entity.FAKE_ID) ? (Boolean) this.map.get(Entity.FAKE_ID) : false;
	}

	public List<VulpeEntity<?>> getDeletedDetails() {
		final List<VulpeEntity<?>> deleted = new ArrayList<VulpeEntity<?>>();
		if (this.map.containsKey(Entity.DELETED_DETAILS)) {
			deleted.addAll((List<VulpeEntity<?>>) this.map.get(Entity.DELETED_DETAILS));
		} else {
			setDeletedDetails(deleted);
		}
		return deleted;
	}

	public void setDeletedDetails(final List<VulpeEntity<?>> deletedDetails) {
		this.map.put(Entity.DELETED_DETAILS, deletedDetails);
	}

}