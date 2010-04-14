package org.vulpe.audit.model.dao.impl.db4o;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.vulpe.audit.model.dao.AuditOccurrenceDAO;
import org.vulpe.audit.model.entity.AuditOccurrence;
import org.vulpe.exception.VulpeApplicationException;
import org.vulpe.model.dao.impl.db4o.VulpeBaseCRUDDAODB4OImpl;


@Repository("AuditOccurrenceDAO")
@Transactional
public class AuditOccurrenceDAODB4OImpl extends
		VulpeBaseCRUDDAODB4OImpl<AuditOccurrence, Long> implements AuditOccurrenceDAO {

	public List<AuditOccurrence> findByParent(final AuditOccurrence parent)
			throws VulpeApplicationException {
		return getList(parent);
	}

}