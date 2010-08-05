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
import java.util.Date;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.apache.log4j.Logger;
import org.vulpe.audit.model.annotations.IgnoreAudit;
import org.vulpe.audit.model.annotations.IgnoreAuditHistory;
import org.vulpe.commons.util.VulpeReflectUtil;
import org.vulpe.commons.xml.XMLDateConversor;
import org.vulpe.model.entity.VulpeEntity;
import org.vulpe.model.entity.VulpeSimpleEntity;

import com.thoughtworks.xstream.XStream;

@MappedSuperclass
@SuppressWarnings( { "unchecked", "serial" })
public abstract class AbstractVulpeBaseEntity<ID extends Serializable & Comparable> extends VulpeBaseSimpleEntity implements
		VulpeEntity<ID> {

	private static final Logger LOG = Logger.getLogger(AbstractVulpeBaseEntity.class);

	@IgnoreAudit
	private transient boolean selected;

	@IgnoreAudit
	private transient String orderBy;

	@IgnoreAudit
	private transient String autoComplete;

	public AbstractVulpeBaseEntity() {
	}

	public boolean isSelected() {
		return this.selected;
	}

	public void setSelected(final boolean selected) {
		this.selected = selected;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(final String orderBy) {
		this.orderBy = orderBy;
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
			for (Field attribute : VulpeReflectUtil.getInstance().getFields(this.getClass())) {
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
		return (VulpeEntity<ID>) super.clone();
	}

	public void setAutoComplete(String autoComplete) {
		this.autoComplete = autoComplete;
	}

	public String getAutoComplete() {
		return autoComplete;
	}

}