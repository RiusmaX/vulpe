package org.vulpe.security.model.dao;

import org.vulpe.model.dao.VulpeBaseCRUDDAO;
import org.vulpe.security.model.entity.BasicUser;


public interface BasicUserDAO<ENTITY_CLASS extends BasicUser> extends VulpeBaseCRUDDAO<ENTITY_CLASS, Long> {

}
