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
import org.vulpe.model.annotations.db4o.IgnoreEmpty;
import org.vulpe.model.entity.VulpeEntity;

import com.thoughtworks.xstream.XStream;

@MappedSuperclass
@SuppressWarnings( { "unchecked", "serial" })
public abstract class AbstractVulpeBaseEntity<ID extends Serializable & Comparable> implements
		VulpeEntity<ID>, Cloneable {

	protected static final Logger LOG = Logger.getLogger(AbstractVulpeBaseEntity.class);

	@IgnoreEmpty
	@IgnoreAudit
	private transient Map<String, Object> map = new HashMap<String, Object>();

	public AbstractVulpeBaseEntity() {
	}

	public boolean isSelected() {
		return getMap().containsKey(Entity.SELECTED) ? (Boolean) getMap().get(Entity.SELECTED)
				: false;
	}

	public void setSelected(final boolean selected) {
		getMap().put(Entity.SELECTED, selected);
	}

	public boolean isUsed() {
		return getMap().containsKey(Entity.USED) ? (Boolean) getMap().get(Entity.USED) : false;
	}

	public void setUsed(final boolean used) {
		getMap().put(Entity.USED, used);
	}

	public boolean isConditional() {
		return getMap().containsKey(Entity.CONDITIONAL) ? (Boolean) getMap()
				.get(Entity.CONDITIONAL) : false;
	}

	public void setConditional(final boolean conditional) {
		getMap().put(Entity.CONDITIONAL, conditional);
	}

	public Integer getRowNumber() {
		return getMap().containsKey(Entity.ROW_NUMBER) ? (Integer) getMap().get(Entity.ROW_NUMBER)
				: 0;
	}

	public void setRowNumber(final Integer rowNumber) {
		getMap().put(Entity.ROW_NUMBER, rowNumber);
	}

	public String getOrderBy() {
		return getMap().containsKey(Entity.ORDER_BY) ? (String) getMap().get(Entity.ORDER_BY)
				: null;
	}

	public void setOrderBy(final String orderBy) {
		getMap().put(Entity.ORDER_BY, orderBy);
	}

	@Override
	public boolean equals(final Object obj) {
		final AbstractVulpeBaseEntity<ID> entity = (AbstractVulpeBaseEntity<ID>) obj;
		if ((obj == null || obj.getClass() != this.getClass())
				|| (entity.getId() == null || getId() == null)) {
			return false;
		}

		return this == entity || getId().equals(entity.getId());
	}

	@Override
	public String toString() {
		return getClass().getSimpleName().concat(
				this.getId() == null ? "" : ".id: ".concat(this.getId().toString()));
	}

	public int compareTo(final VulpeEntity<ID> entity) {
		if (this.equals(entity)) {
			return 0;
		}

		if (getId() == null) {
			return 999999999;
		}

		if (entity.getId() == null) {
			return -999999999;
		}

		return getId().compareTo(entity.getId());
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
				|| attribute.getType() == java.sql.Date.class
				|| attribute.getType() == java.sql.Timestamp.class) {
			return true;
		}
		return false;
	}

	@Override
	public VulpeEntity<ID> clone() {
		try {
			return (VulpeEntity<ID>) super.clone();
		} catch (CloneNotSupportedException e) {
			LOG.error(e);
		}
		return null;
	}

	public void setAutocomplete(final String autocomplete) {
		getMap().put(Entity.AUTOCOMPLETE, autocomplete);
	}

	public String getAutocomplete() {
		return getMap().containsKey(Entity.AUTOCOMPLETE) ? (String) getMap().get(
				Entity.AUTOCOMPLETE) : null;
	}

	public void setAutocompleteTerm(final String autocompleteTerm) {
		getMap().put(Entity.AUTOCOMPLETE_TERM, autocompleteTerm);
	}

	public String getAutocompleteTerm() {
		return getMap().containsKey(Entity.AUTOCOMPLETE_TERM) ? (String) getMap().get(
				Entity.AUTOCOMPLETE_TERM) : null;
	}

	public void setMap(final Map<String, Object> map) {
		this.map = map;
	}

	public Map<String, Object> getMap() {
		if (map == null) {
			map = new HashMap<String, Object>();
		}
		return map;
	}

	public String getQueryConfigurationName() {
		return getMap().containsKey(Entity.QUERY_CONFIGURATION_NAME) ? (String) this.map
				.get(Entity.QUERY_CONFIGURATION_NAME) : "default";
	}

	public void setQueryConfigurationName(final String queryConfigurationName) {
		getMap().put(Entity.QUERY_CONFIGURATION_NAME, queryConfigurationName);
	}

	public void setFakeId(final boolean fakeId) {
		getMap().put(Entity.FAKE_ID, fakeId);
	}

	public boolean isFakeId() {
		return getMap().containsKey(Entity.FAKE_ID) ? (Boolean) getMap().get(Entity.FAKE_ID)
				: false;
	}

	public List<VulpeEntity<?>> getDeletedDetails() {
		final List<VulpeEntity<?>> deleted = new ArrayList<VulpeEntity<?>>();
		if (getMap().containsKey(Entity.DELETED_DETAILS)) {
			deleted.addAll((List<VulpeEntity<?>>) getMap().get(Entity.DELETED_DETAILS));
		} else {
			setDeletedDetails(deleted);
		}
		return deleted;
	}

	public void setDeletedDetails(final List<VulpeEntity<?>> deletedDetails) {
		getMap().put(Entity.DELETED_DETAILS, deletedDetails);
	}

}