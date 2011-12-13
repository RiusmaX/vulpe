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
package org.vulpe.audit.model.services.impl.pojo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.vulpe.audit.model.entity.AuditOccurrence;
import org.vulpe.audit.model.services.AuditService;
import org.vulpe.audit.model.services.manager.AuditOccurrenceManager;
import org.vulpe.commons.beans.Paging;
import org.vulpe.exception.VulpeApplicationException;

@Service("AuditService")
@Transactional
public class AuditServicePOJO implements AuditService {

	@Autowired
	private transient AuditOccurrenceManager occurrenceManager;

	@Transactional(readOnly = true)
	public AuditOccurrence findAuditOccurrence(final AuditOccurrence occurrence0)
			throws VulpeApplicationException {
		return occurrenceManager.find(occurrence0);
	}

	@Transactional(readOnly = true)
	public List<AuditOccurrence> findByParentAuditOccurrence(
			final AuditOccurrence occurrence0) throws VulpeApplicationException {
		return occurrenceManager.findByParent(occurrence0);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteAuditOccurrence(final AuditOccurrence occurrence0)
			throws VulpeApplicationException {
		occurrenceManager.delete(occurrence0);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteAuditOccurrence(final List<AuditOccurrence> list0)
			throws VulpeApplicationException {
		occurrenceManager.delete(list0);
	}

	@Transactional(readOnly = true)
	public List<AuditOccurrence> readAuditOccurrence(
			final AuditOccurrence occurrence0) throws VulpeApplicationException {
		return occurrenceManager.read(occurrence0);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public AuditOccurrence createAuditOccurrence(
			final AuditOccurrence occurrence0) throws VulpeApplicationException {
		return occurrenceManager.create(occurrence0);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateAuditOccurrence(final AuditOccurrence occurrence0)
			throws VulpeApplicationException {
		occurrenceManager.update(occurrence0);
	}

	@Transactional(readOnly = true)
	public Paging<AuditOccurrence> pagingAuditOccurrence(
			final AuditOccurrence occurrence0, final Integer integer1,
			final Integer integer2) throws VulpeApplicationException {
		return occurrenceManager.paging(occurrence0, integer1, integer2);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<AuditOccurrence> persistAuditOccurrence(
			final List<AuditOccurrence> list0) throws VulpeApplicationException {
		return occurrenceManager.persist(list0);
	}

}