package org.vulpe.security.model.services.manager;

import org.springframework.stereotype.Service;
import org.vulpe.model.services.manager.VulpeBaseCRUDManagerImpl;
import org.vulpe.security.model.dao.RoleDAO;
import org.vulpe.security.model.entity.Role;


@Service
public class RoleManager extends VulpeBaseCRUDManagerImpl<Role, Long, RoleDAO> {

}
