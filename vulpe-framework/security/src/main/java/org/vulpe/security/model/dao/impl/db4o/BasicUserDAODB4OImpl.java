package org.vulpe.security.model.dao.impl.db4o;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.vulpe.model.dao.impl.db4o.VulpeBaseCRUDDAODB4OImpl;
import org.vulpe.security.model.dao.BasicUserDAO;
import org.vulpe.security.model.entity.BasicUser;

@Repository("BasicUserDAO")
@Transactional
public class BasicUserDAODB4OImpl<ENTITY_CLASS extends BasicUser> extends
		VulpeBaseCRUDDAODB4OImpl<ENTITY_CLASS, Long> implements
		BasicUserDAO<ENTITY_CLASS> {

}