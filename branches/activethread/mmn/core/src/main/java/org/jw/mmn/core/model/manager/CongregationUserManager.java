package org.jw.mmn.core.model.manager;

import org.springframework.stereotype.Service;
import org.vulpe.model.services.manager.impl.VulpeBaseManager;

import org.jw.mmn.core.model.dao.CongregationUserDAO;
import org.jw.mmn.core.model.entity.CongregationUser;

/**
 * Manager implementation of CongregationUser
 */
@Service
public class CongregationUserManager extends VulpeBaseManager<CongregationUser, java.lang.Long, CongregationUserDAO> {

}

