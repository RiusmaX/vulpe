package org.vulpe.audit.model.dao;

import java.util.List;

import org.vulpe.audit.model.entity.AuditOccurrence;
import org.vulpe.exception.VulpeApplicationException;
import org.vulpe.model.dao.VulpeBaseCRUDDAO;


public interface AuditOccurrenceDAO extends VulpeBaseCRUDDAO<AuditOccurrence, Long> {
	List<AuditOccurrence> findByParent(AuditOccurrence parent) throws VulpeApplicationException;

}
