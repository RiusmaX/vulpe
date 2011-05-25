package org.jw.mmn.publications.model.manager;

import org.springframework.stereotype.Service;

import org.jw.mmn.publications.model.dao.PublicationTypeDAO;
import org.vulpe.model.services.manager.impl.VulpeBaseManager;
import org.jw.mmn.publications.model.entity.PublicationType;

/**
 * Manager implementation of PublicationType
 */
@Service
public class PublicationTypeManager extends VulpeBaseManager<PublicationType, java.lang.Long, PublicationTypeDAO> {

}
