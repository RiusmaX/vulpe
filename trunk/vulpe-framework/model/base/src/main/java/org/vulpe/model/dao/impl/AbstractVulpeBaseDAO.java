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
package org.vulpe.model.dao.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;

import javax.persistence.OneToMany;
import javax.sql.rowset.serial.SerialClob;

import org.apache.log4j.Logger;
import org.vulpe.audit.model.entity.AuditOccurrence;
import org.vulpe.audit.model.entity.AuditOccurrenceType;
import org.vulpe.commons.VulpeConstants.Security;
import org.vulpe.commons.helper.VulpeConfigHelper;
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
			final String userAuthenticated = (String) entity.getMap().get(Security.USER_AUTHENTICATED);
			AuditOccurrence occurrence = new AuditOccurrence(auditOccurrenceType, entity.getClass()
					.getName(), entity.getId().toString(), userAuthenticated);
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
						occurrence
								.setDataHistory(new SerialClob(entity.toXMLAudit().toCharArray()));
					}
				} catch (Exception e) {
					LOG.error(e);
				}
			}
			occurrence = merge(occurrence);
			try {
				final Field[] fields = entity.getClass().getDeclaredFields();
				for (final Field field : fields) {
					if (Collection.class.isAssignableFrom(field.getType())) {
						final OneToMany oneToMany = field.getAnnotation(OneToMany.class);
						if (oneToMany != null) {
							final String methodName = "get"
									+ field.getName().substring(0, 1).toUpperCase()
									+ field.getName().substring(1);
							final Method method = entity.getClass().getDeclaredMethod(methodName,
									new Class[] {});
							final Collection<ENTITY> collection = (Collection<ENTITY>) method
									.invoke(entity, new Object[] {});
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