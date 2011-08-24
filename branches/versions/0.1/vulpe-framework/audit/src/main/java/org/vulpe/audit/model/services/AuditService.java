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
package org.vulpe.audit.model.services;

import java.util.List;

import org.vulpe.audit.model.entity.AuditOccurrence;
import org.vulpe.commons.beans.Paging;
import org.vulpe.exception.VulpeApplicationException;
import org.vulpe.model.services.VulpeService;

public interface AuditService extends VulpeService {

	AuditOccurrence findAuditOccurrence(final AuditOccurrence occurrence0)
			throws VulpeApplicationException;

	List<AuditOccurrence> findByParentAuditOccurrence(
			final AuditOccurrence occurrence0) throws VulpeApplicationException;

	void deleteAuditOccurrence(final AuditOccurrence occurrence0)
			throws VulpeApplicationException;

	void deleteAuditOccurrence(final List<AuditOccurrence> list0)
			throws VulpeApplicationException;

	List<AuditOccurrence> readAuditOccurrence(final AuditOccurrence occurrence0)
			throws VulpeApplicationException;

	AuditOccurrence createAuditOccurrence(final AuditOccurrence occurrence0)
			throws VulpeApplicationException;

	void updateAuditOccurrence(final AuditOccurrence occurrence0)
			throws VulpeApplicationException;

	Paging<AuditOccurrence> pagingAuditOccurrence(
			final AuditOccurrence occurrence0, final Integer integer1,
			final Integer integer2) throws VulpeApplicationException;

	List<AuditOccurrence> persistAuditOccurrence(
			final List<AuditOccurrence> list0) throws VulpeApplicationException;

}