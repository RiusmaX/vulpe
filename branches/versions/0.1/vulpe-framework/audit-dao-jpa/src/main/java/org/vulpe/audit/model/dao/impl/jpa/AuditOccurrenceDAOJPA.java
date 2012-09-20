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
package org.vulpe.audit.model.dao.impl.jpa;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.vulpe.audit.model.dao.AuditOccurrenceDAO;
import org.vulpe.audit.model.entity.AuditOccurrence;
import org.vulpe.exception.VulpeApplicationException;
import org.vulpe.model.dao.impl.jpa.VulpeBaseDAOJPA;

@Repository("AuditOccurrenceDAO")
@Transactional
public class AuditOccurrenceDAOJPA extends VulpeBaseDAOJPA<AuditOccurrence, Long> implements
		AuditOccurrenceDAO {

	@SuppressWarnings("unchecked")
	public List<AuditOccurrence> findByParent(final AuditOccurrence auditOccurrence)
			throws VulpeApplicationException {
		final Query query = getEntityManager().createNamedQuery("AuditOccurrence.findByParent");
		query.setParameter("parent", auditOccurrence.getParent());
		return (List<AuditOccurrence>) query.getResultList();
	}
}