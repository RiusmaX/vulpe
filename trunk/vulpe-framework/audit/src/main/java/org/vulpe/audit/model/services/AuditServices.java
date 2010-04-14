package org.vulpe.audit.model.services;

import java.util.List;

import org.vulpe.audit.model.entity.AuditOccurrence;
import org.vulpe.common.beans.Paging;
import org.vulpe.exception.VulpeApplicationException;
import org.vulpe.model.services.Services;

public interface AuditServices extends Services {

	AuditOccurrence findAuditOccurrence(final Long long0)
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
