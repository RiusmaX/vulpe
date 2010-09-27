package org.vulpe.portal.core.model.manager;

import org.springframework.stereotype.Service;

import org.vulpe.portal.core.model.dao.BasePortalDAO;
import org.vulpe.model.services.manager.impl.VulpeBaseManager;
import org.vulpe.portal.core.model.entity.BasePortal;

/**
 * Manager implementation of BasePortal
 */
@Service
public class BasePortalManager<ENTITY_CLASS extends BasePortal, ENTITY_DAO extends BasePortalDAO<ENTITY_CLASS>> extends VulpeBaseManager<ENTITY_CLASS, java.lang.Long, ENTITY_DAO> {

}
