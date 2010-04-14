package org.vulpe.audit.model.services.manager;

import java.util.List;

import javax.ejb.TransactionAttributeType;

import org.springframework.stereotype.Service;
import org.vulpe.audit.model.dao.AuditOccurrenceDAO;
import org.vulpe.audit.model.entity.AuditOccurrence;
import org.vulpe.exception.VulpeApplicationException;
import org.vulpe.model.annotations.TransactionType;
import org.vulpe.model.services.manager.VulpeBaseCRUDManagerImpl;


@Service
public class AuditOccurrenceManager extends
		VulpeBaseCRUDManagerImpl<AuditOccurrence, Long, AuditOccurrenceDAO> {
	@TransactionType(TransactionAttributeType.NOT_SUPPORTED)
	public List<AuditOccurrence> findByParent(final AuditOccurrence parent)
			throws VulpeApplicationException {
		return getDAO().findByParent(parent);
	}

}
