package org.vulpe.security.model.dao.impl.db4o;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.vulpe.model.dao.impl.db4o.VulpeBaseCRUDDAODB4OImpl;
import org.vulpe.security.model.dao.RoleDAO;
import org.vulpe.security.model.entity.Role;

@Repository("RoleDAO")
@Transactional
public class RoleDAODB4OImpl extends VulpeBaseCRUDDAODB4OImpl<Role, Long> implements
		RoleDAO {

}