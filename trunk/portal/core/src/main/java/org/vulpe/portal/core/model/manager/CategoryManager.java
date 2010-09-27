package org.vulpe.portal.core.model.manager;

import org.springframework.stereotype.Service;

import org.vulpe.portal.core.model.dao.CategoryDAO;
import org.vulpe.portal.core.model.entity.Category;
import org.vulpe.model.services.manager.impl.VulpeBaseManager;

/**
 * Manager implementation of Category
 */
@Service
public class CategoryManager extends VulpeBaseManager<Category, java.lang.Long, CategoryDAO<Category>> {

}
