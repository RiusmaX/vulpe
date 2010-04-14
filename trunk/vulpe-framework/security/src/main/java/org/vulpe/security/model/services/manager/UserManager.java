package org.vulpe.security.model.services.manager;

import org.springframework.stereotype.Service;
import org.vulpe.model.services.manager.VulpeBaseCRUDManagerImpl;
import org.vulpe.security.model.dao.UserDAO;
import org.vulpe.security.model.entity.User;


@Service
public class UserManager extends VulpeBaseCRUDManagerImpl<User, Long, UserDAO> {

}
