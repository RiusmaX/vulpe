package org.jw.mmn.notices.model.manager;

import org.springframework.stereotype.Service;

import org.jw.mmn.notices.model.dao.MeetingDAO;
import org.vulpe.model.services.manager.impl.VulpeBaseManager;
import org.jw.mmn.notices.model.entity.Meeting;

/**
 * Manager implementation of Meeting
 */
@Service
public class MeetingManager extends VulpeBaseManager<Meeting, java.lang.Long, MeetingDAO> {

}
