package org.vulpe.security.model.dao.impl.db4o;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.vulpe.model.dao.impl.db4o.VulpeBaseCRUDDAODB4OImpl;
import org.vulpe.security.model.dao.UserDAO;
import org.vulpe.security.model.entity.User;

@Repository("UserDAO")
@Transactional
public class UserDAODB4OImpl extends VulpeBaseCRUDDAODB4OImpl<User, Long> implements
		UserDAO {

}