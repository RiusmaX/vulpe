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
package org.vulpe.model.dao.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

import javax.persistence.OneToMany;
import javax.sql.rowset.serial.SerialClob;

import org.apache.log4j.Logger;
import org.vulpe.audit.model.entity.AuditOccurrence;
import org.vulpe.audit.model.entity.AuditOccurrenceType;
import org.vulpe.commons.VulpeConstants.Security;
import org.vulpe.commons.VulpeConstants.Model.Entity;
import org.vulpe.commons.helper.VulpeConfigHelper;
import org.vulpe.commons.util.VulpeBeanComparatorUtil;
import org.vulpe.commons.util.VulpeReflectUtil;
import org.vulpe.exception.VulpeApplicationException;
import org.vulpe.model.dao.VulpeDAO;
import org.vulpe.model.entity.VulpeEntity;

/**
 * Abstract Base to implementation of DAO.
 * 
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 * 
 */
@SuppressWarnings( { "unchecked" })
public abstract class AbstractVulpeBaseDAO<ENTITY extends VulpeEntity<ID>, ID extends Serializable & Comparable>
		implements VulpeDAO<ENTITY, ID> {

	protected static final Logger LOG = Logger.getLogger(AbstractVulpeBaseDAO.class);

	/**
	 * Make audit.
	 * 
	 * @param entity
	 * @param auditOccurrenceType
	 * @param occurrenceParent
	 * @throws VulpeApplicationException
	 */
	protected void audit(final ENTITY entity, final AuditOccurrenceType auditOccurrenceType,
			final Long occurrenceParent) throws VulpeApplicationException {
		if (VulpeConfigHelper.isAuditEnabled() && entity.isAuditable()) {
			final ENTITY entityBeforeUpdate = (ENTITY) entity.map().get(Entity.BEFORE_UPDATE);
			if (VulpeBeanComparatorUtil.isDifferent(entityBeforeUpdate, entity)) {
				final String userAuthenticated = (String) entity.map().get(
						Security.USER_AUTHENTICATED);
				AuditOccurrence occurrence = new AuditOccurrence(auditOccurrenceType, entity
						.getClass().getName(), entity.getId().toString(), userAuthenticated);
				if (occurrenceParent != null) {
					occurrence = new AuditOccurrence(occurrenceParent, auditOccurrenceType, entity
							.getClass().getName(), entity.getId().toString(), userAuthenticated);
				}

				if (LOG.isDebugEnabled()) {
					LOG.debug("Auditing object" + (occurrenceParent == null ? " son" : "")
							+ ": ".concat(entity.toString()));
				}
				if (entity.isHistoryAuditable()) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("Auditing history of object"
								+ (occurrenceParent == null ? " son" : "")
								+ ": ".concat(entity.toString()));
					}
					try {
						if (auditOccurrenceType.equals(AuditOccurrenceType.UPDATE)) {
							final ENTITY entityAudit = (ENTITY) find(entity);
							occurrence.setDataHistory(new SerialClob(entityAudit.toXMLAudit()
									.toCharArray()));
						} else {
							occurrence.setDataHistory(new SerialClob(entity.toXMLAudit()
									.toCharArray()));
						}
					} catch (Exception e) {
						LOG.error(e);
					}
				}
				occurrence = merge(occurrence);
				try {
					List<Field> fields = VulpeReflectUtil.getFields(entity.getClass());
					for (final Field field : fields) {
						if (Collection.class.isAssignableFrom(field.getType())) {
							final OneToMany oneToMany = field.getAnnotation(OneToMany.class);
							if (oneToMany != null) {
								final Collection<ENTITY> collection = VulpeReflectUtil
										.getFieldValue(entity, field.getName());
								if (collection != null) {
									for (final ENTITY entityAudit : collection) {
										audit(entityAudit, auditOccurrenceType, occurrence.getId());
									}
								}
							}
						}
					}
				} catch (Exception e) {
					LOG.error(e);
				}
			}
		}
	}
}
