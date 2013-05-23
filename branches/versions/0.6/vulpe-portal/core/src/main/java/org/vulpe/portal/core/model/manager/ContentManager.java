package org.vulpe.portal.core.model.manager;

import org.springframework.stereotype.Service;
import org.vulpe.portal.core.model.dao.ContentDAO;
import org.vulpe.portal.core.model.entity.Content;

/**
 * Manager implementation of Content
 */
@Service
public class ContentManager extends org.vulpe.portal.core.model.manager.BasePortalManager<Content, ContentDAO<Content>> {

}
