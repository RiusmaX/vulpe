package org.vulpe.audit.model.services.impl.pojo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.vulpe.audit.model.entity.AuditOccurrence;
import org.vulpe.audit.model.services.AuditServices;
import org.vulpe.audit.model.services.manager.AuditOccurrenceManager;
import org.vulpe.common.beans.Paging;
import org.vulpe.exception.VulpeApplicationException;

@Service("AuditServices")
@Transactional
public class AuditServicesPOJOImpl implements AuditServices {

	@Autowired
	private transient AuditOccurrenceManager occurrenceManager;

	@Transactional(readOnly = true)
	public AuditOccurrence findAuditOccurrence(final Long long0)
			throws VulpeApplicationException {
		return occurrenceManager.find(long0);
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