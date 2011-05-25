package org.jw.mmn.core.model.manager;

import org.springframework.stereotype.Service;

import org.jw.mmn.core.model.dao.MemberDAO;
import org.vulpe.model.services.manager.impl.VulpeBaseManager;
import org.jw.mmn.core.model.entity.Member;

/**
 * Manager implementation of Member
 */
@Service
public class MemberManager extends VulpeBaseManager<Member, java.lang.Long, MemberDAO> {

}
