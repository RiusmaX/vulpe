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
package org.vulpe.audit.model.entity;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Clob;
import java.sql.SQLException;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.vulpe.audit.model.annotations.SkipAudit;
import org.vulpe.commons.xml.XMLAttribute;
import org.vulpe.commons.xml.XMLDateConversor;
import org.vulpe.commons.xml.XMLReader;
import org.vulpe.model.entity.VulpeEntity;
import org.vulpe.model.entity.impl.AbstractVulpeBaseEntity;

import com.thoughtworks.xstream.XStream;

@NamedQueries( { @NamedQuery(name = "AuditOccurrence.findByParent", query = "select obj from AuditOccurrence obj where obj.parent = :parent") })
@SuppressWarnings( { "serial", "rawtypes" })
@Entity
@Table(name = "VulpeAuditOccurrence")
@SkipAudit
public class AuditOccurrence extends AbstractVulpeBaseEntity<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Long parent;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private AuditOccurrenceType occurrenceType;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateTime;

	@Column(length = 150)
	private String entity;

	@Column(length = 50)
	private String primaryKey;

	@Column(length = 30)
	private String username;

	private Clob dataHistory;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public Clob getDataHistory() {
		return dataHistory;
	}

	public void setDataHistory(final Clob dataHistory) {
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

	@Transient
	public VulpeEntity fromXMLHistory() {
		final XStream xstream = new XStream();
		xstream.registerConverter(new XMLDateConversor(), 1);
		return (VulpeEntity) xstream.fromXML(getDataHistory().toString());
	}

	public List<XMLAttribute> getHistoryAttributes() {
		return new XMLReader().reader(getDataHistory().toString());
	}

	public AuditOccurrence() {
		super();
	}

	public AuditOccurrence(final Long parent) {
		super();
		this.parent = parent;
	}

	public AuditOccurrence(final AuditOccurrenceType occurrenceType, final String entity,
			final String primaryKey, final String username) {
		super();
		this.occurrenceType = occurrenceType;
		this.entity = entity;
		this.primaryKey = primaryKey;
		this.username = username;
		this.dateTime = Calendar.getInstance().getTime();
	}

	public AuditOccurrence(final Long parent, final AuditOccurrenceType occurrenceType,
			final String entity, final String primaryKey, final String username) {
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
		final StringBuilder history = new StringBuilder();
		try {
			String aux = "";
			final BufferedReader buffer = new BufferedReader(getDataHistory().getCharacterStream());
			while ((aux = buffer.readLine()) != null) {
				history.append(aux);
			}
		} catch (SQLException e) {
			LOG.error(e);
		} catch (IOException e) {
			LOG.error(e);
		}
		return new XMLReader().reader(history.toString());
	}

}