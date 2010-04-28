package org.vulpe.security.model.dao.impl.db4o;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.vulpe.model.dao.impl.db4o.VulpeBaseCRUDDAODB4OImpl;
import org.vulpe.security.model.dao.SecureResourceDAO;
import org.vulpe.security.model.entity.SecureResource;

@Repository("SecureResourceDAO")
@Transactional
public class SecureResourceDAODB4OImpl extends
		VulpeBaseCRUDDAODB4OImpl<SecureResource, Long> implements SecureResourceDAO {

}