package org.vulpe.model.entity;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.vulpe.audit.model.annotations.IgnoreAuditAttribute;
import org.vulpe.audit.model.annotations.IgnoreAuditEntity;
import org.vulpe.audit.model.annotations.IgnoreAuditHistory;
import org.vulpe.common.ReflectUtil;
import org.vulpe.common.helper.VulpeConfigHelper;
import org.vulpe.common.xml.XMLDateConversor;
import org.vulpe.config.annotations.VulpeDomains;
import org.vulpe.model.entity.validate.EntityAttributeValidate;
import org.vulpe.view.annotations.input.VulpeDate;
import org.vulpe.view.annotations.input.VulpeSelectPopup;
import org.vulpe.view.annotations.input.VulpeText;

import com.thoughtworks.xstream.XStream;

@SuppressWarnings( { "unchecked", "serial" })
public abstract class AbstractVulpeBaseEntityImpl<ID extends Serializable & Comparable>
		implements VulpeBaseEntity<ID> {

	private static final Logger LOG = Logger
			.getLogger(AbstractVulpeBaseEntityImpl.class);

	@IgnoreAuditAttribute
	private transient boolean selected;

	@IgnoreAuditAttribute
	private transient String orderBy;

	public AbstractVulpeBaseEntityImpl() {
		if (!VulpeConfigHelper.get(VulpeDomains.class).useDB4O()) {
			this.orderBy = "obj.id";
		}
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
		final AbstractVulpeBaseEntityImpl<ID> entity = (AbstractVulpeBaseEntityImpl<ID>) obj;
		if ((obj == null || obj.getClass() != this.getClass())
				|| (entity.getId() == null || getId() == null)) {
			return false;
		}

		return this == entity || getId().equals(entity.getId());
	}

	@Override
	public String toString() {
		return getClass().getSimpleName().concat(
				this.getId() == null ? "" : ".id: ".concat(this.getId()
						.toString()));
	}

	public int compareTo(final VulpeBaseEntity<ID> entity) {
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
		return this.getClass().getAnnotation(IgnoreAuditEntity.class) == null;
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
			for (Field attribute : ReflectUtil.getInstance().getFields(
					this.getClass())) {
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
		if (attribute.getAnnotation(IgnoreAuditAttribute.class) != null) {
			return false;
		}
		if (attribute.getType().isPrimitive()
				|| attribute.getType() == String.class
				|| attribute.getType() == Character.class
				|| attribute.getType() == Integer.class
				|| attribute.getType() == Short.class
				|| attribute.getType() == Long.class
				|| attribute.getType() == Double.class
				|| attribute.getType() == Date.class
				|| attribute.getType() == java.sql.Date.class
				|| attribute.getType() == java.sql.Timestamp.class) {
			return true;
		}
		return false;
	}

	private String getModuleName() {
		final String className = this.getClass().getName();
		final String classSimpleName = this.getClass().getSimpleName();
		final String moduleName = className.substring(0, className
				.indexOf(".model.entity.".concat(classSimpleName)));
		final char dot = ".".charAt(0);
		return moduleName.substring(moduleName.lastIndexOf(dot) + 1);
	}

	private String getLabel(final String label, final String name,
			final String type) {
		final String classSimpleName = this.getClass().getSimpleName();
		return StringUtils.isNotBlank(label) ? label : "label.".concat(
				VulpeConfigHelper.getProjectName()).concat(".").concat(
				getModuleName()).concat(".").concat(classSimpleName)
				.concat(".").concat(type).concat(".").concat(name);
	}

	public List<EntityAttributeValidate> getAttributesToValidateInCRUD() {
		final List<EntityAttributeValidate> list = new ArrayList<EntityAttributeValidate>();
		for (Field attribute : ReflectUtil.getInstance().getFields(
				this.getClass())) {
			final EntityAttributeValidate eav = new EntityAttributeValidate(
					attribute.getName(), attribute.getType().getSimpleName());
			String type = "crud";
			final VulpeText text = attribute.getAnnotation(VulpeText.class);
			final VulpeSelectPopup selectPopup = attribute
					.getAnnotation(VulpeSelectPopup.class);
			if (text != null && text.validate()) {
				eav.setLabel(getLabel(text.label(), eav.getName(), type));
				if (VulpeConfigHelper.get(VulpeDomains.class).useDB4O()) {
					eav.setSize(text.size());
				} else {
					final Column column = attribute.getAnnotation(Column.class);
					final JoinColumn joinColumn = attribute
							.getAnnotation(JoinColumn.class);
					if ((column != null && !column.nullable())
							|| (joinColumn != null && !joinColumn.nullable())) {
						eav.setSize(column == null ? text.size() : column
								.length());
					}
				}
			}
			if (selectPopup != null && selectPopup.validate()) {
				eav.setLabel(getLabel(selectPopup.label(), eav.getName(),
						type));
				if (VulpeConfigHelper.get(VulpeDomains.class).useDB4O()) {
					if (StringUtils.isNotBlank(selectPopup.identifier())) {
						eav.setIdentifier(selectPopup.identifier());
					}
					if (StringUtils.isNotBlank(selectPopup.description())) {
						eav.setDescription(selectPopup.description());
					}
				} else {
					final Column column = attribute.getAnnotation(Column.class);
					final JoinColumn joinColumn = attribute
							.getAnnotation(JoinColumn.class);
					if ((column != null && !column.nullable())
							|| (joinColumn != null && !joinColumn.nullable())) {
						if (joinColumn != null) {
							if (StringUtils
									.isNotBlank(selectPopup.identifier())) {
								eav.setIdentifier(selectPopup.identifier());
							}
							if (StringUtils.isNotBlank(selectPopup
									.description())) {
								eav.setDescription(selectPopup.description());
							}
						}
					}
				}
			}

			if (StringUtils.isNotBlank(eav.getLabel())) {
				list.add(eav);
			}
		}
		return list;
	}

	public List<EntityAttributeValidate> getAttributesToValidateInSelect() {
		final List<EntityAttributeValidate> list = new ArrayList<EntityAttributeValidate>();
		for (Field attribute : ReflectUtil.getInstance().getFields(
				this.getClass())) {
			String type = "select";
			final EntityAttributeValidate eav = new EntityAttributeValidate(
					attribute.getName(), attribute.getType().getSimpleName());
			final VulpeText text = attribute.getAnnotation(VulpeText.class);
			if (text != null && text.argument() && text.validate()) {
				eav.setLabel(getLabel(text.label(), eav.getName(), type));
				eav.setSize(text.size());
			}
			final VulpeDate date = attribute.getAnnotation(VulpeDate.class);
			if (date != null && date.argument() && date.validate()) {
				eav.setLabel(getLabel(date.label(), eav.getName(), type));
				eav.setSize(date.size());
			}
			if (StringUtils.isNotBlank(eav.getLabel())) {
				list.add(eav);
			}
		}
		return list;
	}

}