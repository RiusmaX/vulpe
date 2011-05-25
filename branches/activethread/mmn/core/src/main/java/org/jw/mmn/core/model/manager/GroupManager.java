package org.jw.mmn.core.model.manager;

import org.springframework.stereotype.Service;

import org.jw.mmn.core.model.dao.GroupDAO;
import org.vulpe.model.services.manager.impl.VulpeBaseManager;
import org.jw.mmn.core.model.entity.Group;

/**
 * Manager implementation of Group
 */
@Service
public class GroupManager extends VulpeBaseManager<Group, java.lang.Long, GroupDAO> {

}
