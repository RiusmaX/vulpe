/**
 * Vulpe Framework - Quick and Smart ;)
 * Copyright (C) 2011 Active Thread
 * 
 * Este programa é software livre; você pode redistribuí-lo e/ou
 * modificá-lo sob os termos da Licença Pública Geral GNU, conforme
 * publicada pela Free Software Foundation; tanto a versão 2 da
 * Licença como (a seu critério) qualquer versão mais nova.
 * 
 * Este programa é distribuído na expectativa de ser útil, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia implícita de
 * COMERCIALIZAÇÃO ou de ADEQUAÇÃO A QUALQUER PROPÓSITO EM
 * PARTICULAR. Consulte a Licença Pública Geral GNU para obter mais
 * detalhes.
 * 
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU
 * junto com este programa; se não, escreva para a Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307, USA.
 */
/**
 * Vulpe Framework - Quick and Smart ;)
 * Copyright (C) 2011 Active Thread
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
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
import org.vulpe.model.db4o.annotations.IgnoreEmpty;
import org.vulpe.model.entity.VulpeEntity;

import com.thoughtworks.xstream.XStream;

@MappedSuperclass
@SuppressWarnings( { "unchecked", "serial" })
public abstract class AbstractVulpeBaseEntity<ID extends Serializable & Comparable> implements VulpeEntity<ID>,
		Cloneable {

	protected static final Logger LOG = Logger.getLogger(AbstractVulpeBaseEntity.class);

	@IgnoreEmpty
	@IgnoreAudit
	private transient Map<String, Object> map = new HashMap<String, Object>();

	public AbstractVulpeBaseEntity() {
	}

	public boolean isSelected() {
		return map().containsKey(Entity.SELECTED) ? (Boolean) map().get(Entity.SELECTED) : false;
	}

	public void setSelected(final boolean selected) {
		map().put(Entity.SELECTED, selected);
	}

	public boolean isUsed() {
		return map().containsKey(Entity.USED) ? (Boolean) map().get(Entity.USED) : false;
	}

	public void setUsed(final boolean used) {
		map().put(Entity.USED, used);
	}

	public boolean isConditional() {
		return map().containsKey(Entity.CONDITIONAL) ? (Boolean) map().get(Entity.CONDITIONAL) : false;
	}

	public void setConditional(final boolean conditional) {
		map().put(Entity.CONDITIONAL, conditional);
	}

	public Integer getRowNumber() {
		return map().containsKey(Entity.ROW_NUMBER) ? (Integer) map().get(Entity.ROW_NUMBER) : 0;
	}

	public void setRowNumber(final Integer rowNumber) {
		map().put(Entity.ROW_NUMBER, rowNumber);
	}

	public String getOrderBy() {
		return map().containsKey(Entity.ORDER_BY) ? (String) map().get(Entity.ORDER_BY) : null;
	}

	public void setOrderBy(final String orderBy) {
		map().put(Entity.ORDER_BY, orderBy);
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
				|| attribute.getType() == java.sql.Date.class || attribute.getType() == java.sql.Timestamp.class) {
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
		map().put(Entity.AUTOCOMPLETE, autocomplete);
	}

	public String getAutocomplete() {
		return map().containsKey(Entity.AUTOCOMPLETE) ? (String) map().get(Entity.AUTOCOMPLETE) : null;
	}

	public void setAutocompleteTerm(final String autocompleteTerm) {
		map().put(Entity.AUTOCOMPLETE_TERM, autocompleteTerm);
	}

	public String getAutocompleteTerm() {
		return map().containsKey(Entity.AUTOCOMPLETE_TERM) ? (String) map().get(Entity.AUTOCOMPLETE_TERM) : null;
	}

	public void map(final Map<String, Object> map) {
		this.map = map;
	}

	public Map<String, Object> map() {
		if (map == null) {
			map = new HashMap<String, Object>();
		}
		return map;
	}

	public String getQueryConfigurationName() {
		return map().containsKey(Entity.QUERY_CONFIGURATION_NAME) ? (String) this.map
				.get(Entity.QUERY_CONFIGURATION_NAME) : "default";
	}

	public void setQueryConfigurationName(final String queryConfigurationName) {
		map().put(Entity.QUERY_CONFIGURATION_NAME, queryConfigurationName);
	}

	public Map<String, String> fieldColumnMap() {
		Map<String, String> fieldColumnMap = (Map<String, String>) map().get(Entity.FIELD_COLUMN_MAP);
		if (fieldColumnMap == null) {
			fieldColumnMap = new HashMap<String, String>();
		}
		map().put(Entity.FIELD_COLUMN_MAP, fieldColumnMap);
		return fieldColumnMap;
	}
	
	public void setFakeId(final boolean fakeId) {
		map().put(Entity.FAKE_ID, fakeId);
	}

	public boolean isFakeId() {
		return map().containsKey(Entity.FAKE_ID) ? (Boolean) map().get(Entity.FAKE_ID) : false;
	}

	public List<VulpeEntity<?>> getDeletedDetails() {
		final List<VulpeEntity<?>> deleted = new ArrayList<VulpeEntity<?>>();
		if (map().containsKey(Entity.DELETED_DETAILS)) {
			deleted.addAll((List<VulpeEntity<?>>) map().get(Entity.DELETED_DETAILS));
		} else {
			setDeletedDetails(deleted);
		}
		return deleted;
	}

	public void setDeletedDetails(final List<VulpeEntity<?>> deletedDetails) {
		map().put(Entity.DELETED_DETAILS, deletedDetails);
	}

	public <T> T simple() {
		T simple = null;
		try {
			simple = (T) this.getClass().newInstance();
			((VulpeEntity<ID>) simple).setId(getId());
		} catch (Exception e) {
			LOG.error(e);
		}
		return simple;
	}

	public <T> T simple(final String... properties) {
		T simple = (T) simple();
		for (final String property : properties) {
			VulpeReflectUtil.setFieldValue(simple, property, VulpeReflectUtil.getFieldValue(this, property));
		}
		return simple;
	}
}