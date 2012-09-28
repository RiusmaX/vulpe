package org.jw.mmn.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jw.mmn.commons.ApplicationConstants.Core;
import org.jw.mmn.core.model.entity.Congregation;
import org.jw.mmn.frontend.model.entity.Index;
import org.jw.mmn.ministry.model.entity.MemberPersonalReport;
import org.vulpe.commons.util.VulpeValidationUtil;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.ExecuteAlways;
import org.vulpe.controller.commons.VulpeControllerConfig.ControllerType;
import org.vulpe.exception.VulpeApplicationException;

@SuppressWarnings({ "serial", "unchecked" })
@Controller(type = ControllerType.FRONTEND)
public class IndexBaseController extends ApplicationBaseController<Index, Long> {

	public static final String MEMBER_PERSONAL_REPORTS_YEARS = "memberPersonalReportsYears";
	public static final String MEMBER_PERSONAL_REPORTS = "memberPersonalReports";

	@Override
	protected void postConstruct() {
		super.postConstruct();
		final MemberPersonalReport memberPersonalReport = new MemberPersonalReport();
		memberPersonalReport.setMember(retrieveMember());
		try {
			int year = calendar.get(Calendar.YEAR);
			int serviceYear = year;
			if (calendar.get(Calendar.MONTH) < 8) {
				--serviceYear;
			}
			now.put("serviceYear", serviceYear);
			final List<MemberPersonalReport> memberPersonalReports = ministryService()
					.readMemberPersonalReport(memberPersonalReport);
			if (VulpeValidationUtil.isNotEmpty(memberPersonalReports)) {
				Collections.sort(memberPersonalReports);
				final Map<Integer, List<MemberPersonalReport>> years = new HashMap<Integer, List<MemberPersonalReport>>();
				for (final MemberPersonalReport personalReport : memberPersonalReports) {
					personalReport.sum();
					int currentYear = personalReport.getYear();
					if (personalReport.getOrdinalMonth() < 8) {
						--currentYear;
					}
					List<MemberPersonalReport> reports = years.get(currentYear);
					if (reports == null) {
						reports = new ArrayList<MemberPersonalReport>();
					}
					reports.add(personalReport);
					years.put(currentYear, reports);
				}
				now.put(MEMBER_PERSONAL_REPORTS, years);
				now.put(MEMBER_PERSONAL_REPORTS_YEARS, years.keySet());
			}
		} catch (VulpeApplicationException e) {
			LOG.error(e.getMessage());
		}
	}

	@ExecuteAlways
	public void init() {
		final Congregation congregation = ever.getAuto(Core.SELECTED_CONGREGATION);
		if (congregation != null) {
			entity = new Index();
			entity.setCongregation(congregation);
		}
	}

}
