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
package org.vulpe.audit.model.services.manager;

import java.util.List;

import javax.ejb.TransactionAttributeType;

import org.springframework.stereotype.Service;
import org.vulpe.audit.model.dao.AuditOccurrenceDAO;
import org.vulpe.audit.model.entity.AuditOccurrence;
import org.vulpe.exception.VulpeApplicationException;
import org.vulpe.model.annotations.TransactionType;
import org.vulpe.model.services.manager.impl.VulpeBaseManager;


@Service
public class AuditOccurrenceManager extends
		VulpeBaseManager<AuditOccurrence, Long, AuditOccurrenceDAO> {
	@TransactionType(TransactionAttributeType.NOT_SUPPORTED)
	public List<AuditOccurrence> findByParent(final AuditOccurrence parent)
			throws VulpeApplicationException {
		return getDAO().findByParent(parent);
	}

}
