package org.vulpe.audit.model.entity;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.validator.Length;
import org.vulpe.audit.model.annotations.IgnoreAudit;
import org.vulpe.common.audit.AuditOccurrenceType;
import org.vulpe.common.xml.XMLAttribute;
import org.vulpe.common.xml.XMLDateConversor;
import org.vulpe.common.xml.XMLReader;
import org.vulpe.model.entity.AbstractVulpeBaseEntityImpl;
import org.vulpe.model.entity.VulpeBaseEntity;

import com.thoughtworks.xstream.XStream;

@NamedQueries( { @NamedQuery(name = "AuditOccurrence.findByParent", query = "select obj from AuditOccurrence obj where obj.parent = :parent") })
@SuppressWarnings("serial")
@Entity
@MappedSuperclass
@IgnoreAudit
public class AuditOccurrence extends AbstractVulpeBaseEntityImpl<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auditoccurrence_seq")
	@SequenceGenerator(name = "auditoccurrence_seq", sequenceName = "auditoccurrence_seq", allocationSize = 1)
	private Long id;

	private Long parent;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private AuditOccurrenceType occurrenceType;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateTime;

	@Length(max = 150)
	@Column(length = 150)
	private String entity;

	@Length(max = 50)
	@Column(length = 50)
	private String primaryKey;

	@Length(max = 30)
	@Column(length = 30)
	private String username;

	@Lob
	private String dataHistory;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getDataHistory() {
		return dataHistory;
	}

	public void setDataHistory(final String dataHistory) {
		this.dataHistory = dataHistory;
	}

	public AuditOccurrenceType getOccurrenceType() {
		return occurrenceType;
	}

	public void setOccurrenceType(final AuditOccurrenceType occurrenceType) {
		this.occurrenceType = occurrenceType;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(final Date dateTime) {
		this.dateTime = dateTime;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(final String entity) {
		this.entity = entity;
	}

	public String getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(final String primaryKey) {
		this.primaryKey = primaryKey;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	@SuppressWarnings("unchecked")
	@Transient
	public VulpeBaseEntity fromXMLHistory() {
		final XStream xstream = new XStream();
		xstream.registerConverter(new XMLDateConversor(), 1);
		return (VulpeBaseEntity) xstream.fromXML(getDataHistory());
	}

	public List<XMLAttribute> getHistoryAttributes() {
		return new XMLReader().reader(getDataHistory());
	}

	public AuditOccurrence() {
		super();
	}

	public AuditOccurrence(final Long parent) {
		super();
		this.parent = parent;
	}

	public AuditOccurrence(final AuditOccurrenceType occurrenceType,
			final String entity, final String primaryKey, final String username) {
		super();
		this.occurrenceType = occurrenceType;
		this.entity = entity;
		this.primaryKey = primaryKey;
		this.username = username;
		this.dateTime = Calendar.getInstance().getTime();
	}

	public AuditOccurrence(final Long parent,
			final AuditOccurrenceType occurrenceType, final String entity,
			final String primaryKey, final String username) {
		super();
		this.parent = parent;
		this.occurrenceType = occurrenceType;
		this.entity = entity;
		this.primaryKey = primaryKey;
		this.username = username;
		this.dateTime = Calendar.getInstance().getTime();
	}

	public Long getParent() {
		return parent;
	}

	public void setParent(final Long parent) {
		this.parent = parent;
	}

	public List<XMLAttribute> getDataHistoryAttributes() {
		return new XMLReader().reader(getDataHistory());
	}

}