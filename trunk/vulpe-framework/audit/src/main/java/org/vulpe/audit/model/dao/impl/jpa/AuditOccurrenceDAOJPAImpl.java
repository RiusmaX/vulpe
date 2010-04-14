package org.vulpe.audit.model.dao.impl.jpa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;
import org.vulpe.audit.model.dao.AuditOccurrenceDAO;
import org.vulpe.audit.model.entity.AuditOccurrence;
import org.vulpe.exception.VulpeApplicationException;
import org.vulpe.model.dao.impl.jpa.VulpeBaseCRUDDAOJPAImpl;


//@Repository("AuditOccurrenceDAO")
@Transactional
public class AuditOccurrenceDAOJPAImpl extends
		VulpeBaseCRUDDAOJPAImpl<AuditOccurrence, Long> implements AuditOccurrenceDAO {

	@SuppressWarnings("unchecked")
	public List<AuditOccurrence> findByParent(final AuditOccurrence parent)
			throws VulpeApplicationException {
		final Map<String, Object> map = new HashMap<String, Object>();
		map.put("parent", parent);
		return (List<AuditOccurrence>) getJpaTemplate()
				.findByNamedQueryAndNamedParams("AuditOccurrence.findByParent",
						map);
	}

}