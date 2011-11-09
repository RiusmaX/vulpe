package org.jw.mmn.controller;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.jw.mmn.commons.ApplicationConstants.Core;
import org.jw.mmn.core.model.entity.Congregation;
import org.jw.mmn.frontend.model.entity.Index;
import org.jw.mmn.ministry.model.entity.MemberPersonalReport;
import org.vulpe.commons.util.VulpeValidationUtil;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.ExecuteAlways;
import org.vulpe.controller.commons.VulpeControllerConfig.ControllerType;
import org.vulpe.exception.VulpeApplicationException;

@SuppressWarnings( { "serial" })
@Controller(type = ControllerType.FRONTEND)
public class IndexBaseController extends ApplicationBaseController<Index, Long> {

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
			now.put("chartYears", "'" + year + "', '" + (year + 1) + "'");
			final List<MemberPersonalReport> memberPersonalReports = ministryService()
					.readMemberPersonalReport(memberPersonalReport);
			if (VulpeValidationUtil.isNotEmpty(memberPersonalReports)) {
				Collections.sort(memberPersonalReports);
				for (final MemberPersonalReport personalReport : memberPersonalReports) {
					personalReport.sum();
				}
				now.put(MEMBER_PERSONAL_REPORTS, memberPersonalReports);
				int initialYearMinutes = 0;
				int finalYearMinutes = 0;
				for (final MemberPersonalReport personalReport : memberPersonalReports) {
					if (personalReport.getYear() == serviceYear) {
						initialYearMinutes += personalReport.getTotalMinutes();
					} else {
						finalYearMinutes += personalReport.getTotalMinutes();
					}
				}
				now.put("initialYearPercent", (initialYearMinutes * 100d)
						/ (initialYearMinutes + finalYearMinutes));
				now.put("finalYearPercent", (finalYearMinutes * 100d)
						/ (initialYearMinutes + finalYearMinutes));
			}
		} catch (VulpeApplicationException e) {
			LOG.error(e);
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
