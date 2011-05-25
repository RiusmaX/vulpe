package org.jw.mmn.ministry.model.manager;

import org.springframework.stereotype.Service;

import org.jw.mmn.ministry.model.dao.MemberReportDAO;
import org.vulpe.model.services.manager.impl.VulpeBaseManager;
import org.jw.mmn.ministry.model.entity.MemberReport;

/**
 * Manager implementation of MemberReport
 */
@Service
public class MemberReportManager extends VulpeBaseManager<MemberReport, java.lang.Long, MemberReportDAO> {

}
