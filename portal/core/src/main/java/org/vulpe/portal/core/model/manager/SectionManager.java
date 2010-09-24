package org.vulpe.portal.core.model.manager;

import org.springframework.stereotype.Service;

import org.vulpe.portal.core.model.dao.SectionDAO;
import org.vulpe.portal.core.model.entity.Section;
import org.vulpe.model.services.manager.impl.VulpeBaseManager;

/**
 * Manager implementation of Section
 */
@Service
public class SectionManager extends VulpeBaseManager<Section, java.lang.Long, SectionDAO<Section>> {

}
