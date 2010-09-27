package org.vulpe.portal.core.model.manager;

import org.springframework.stereotype.Service;

import org.vulpe.portal.core.model.dao.LanguageDAO;
import org.vulpe.portal.core.model.entity.Language;
import org.vulpe.model.services.manager.impl.VulpeBaseManager;

/**
 * Manager implementation of Language
 */
@Service
public class LanguageManager extends VulpeBaseManager<Language, java.lang.Long, LanguageDAO<Language>> {

}

