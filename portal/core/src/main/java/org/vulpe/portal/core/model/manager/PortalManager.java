package org.vulpe.portal.core.model.manager;

import org.springframework.stereotype.Service;

import org.vulpe.portal.core.model.dao.PortalDAO;
import org.vulpe.portal.core.model.entity.Portal;
import org.vulpe.model.services.manager.impl.VulpeBaseManager;

/**
 * Manager implementation of Portal
 */
@Service
public class PortalManager extends VulpeBaseManager<Portal, java.lang.Long, PortalDAO<Portal>> {

}

