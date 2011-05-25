package org.jw.mmn.publications.model.manager;

import org.springframework.stereotype.Service;

import org.jw.mmn.publications.model.dao.PublicationDAO;
import org.vulpe.model.services.manager.impl.VulpeBaseManager;
import org.jw.mmn.publications.model.entity.Publication;

/**
 * Manager implementation of Publication
 */
@Service
public class PublicationManager extends VulpeBaseManager<Publication, java.lang.Long, PublicationDAO> {

}
