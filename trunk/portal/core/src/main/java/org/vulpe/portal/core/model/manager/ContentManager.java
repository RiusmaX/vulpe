package org.vulpe.portal.core.model.manager;

import org.springframework.stereotype.Service;

import org.vulpe.portal.core.model.dao.ContentDAO;
import org.vulpe.portal.core.model.entity.Content;
import org.vulpe.model.services.manager.impl.VulpeBaseManager;

/**
 * Manager implementation of Content
 */
@Service
public class ContentManager extends VulpeBaseManager<Content, java.lang.Long, ContentDAO<Content>> {

}
