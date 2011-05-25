package org.jw.mmn.core.model.manager;

import org.springframework.stereotype.Service;

import org.jw.mmn.core.model.dao.CongregationDAO;
import org.vulpe.model.services.manager.impl.VulpeBaseManager;
import org.jw.mmn.core.model.entity.Congregation;

/**
 * Manager implementation of Congregation
 */
@Service
public class CongregationManager extends VulpeBaseManager<Congregation, java.lang.Long, CongregationDAO> {

}
