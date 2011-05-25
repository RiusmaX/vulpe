package org.jw.mmn.ministry.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.jw.mmn.commons.ApplicationConstants.Core;
import org.jw.mmn.commons.model.entity.MinistryType;
import org.jw.mmn.controller.ApplicationBaseController;
import org.jw.mmn.core.model.commons.MemberGroupComparator;
import org.jw.mmn.core.model.commons.MemberReportGroupComparator;
import org.jw.mmn.core.model.entity.Member;
import org.jw.mmn.ministry.model.entity.MemberReport;
import org.jw.mmn.ministry.model.entity.Month;
import org.jw.mmn.ministry.model.services.MinistryService;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.commons.beans.DownloadInfo;
import org.vulpe.commons.util.VulpeDateUtil;
import org.vulpe.commons.util.VulpeValidationUtil;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Report;
import org.vulpe.controller.annotations.Select;

/**
 * Controller implementation of MemberReport
 */
@Component("ministry.MemberReportController")
@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller(serviceClass = MinistryService.class, select = @Select(pageSize = 5, showReport = true, requireOneFilter = true), report = @Report(subReports = {
		"MemberReports", "PendingMemberReports" }), newOnPost = true)
public class MemberReportController extends ApplicationBaseController<MemberReport, java.lang.Long> {

	@Override
	protected void selectAfter() {
		super.selectAfter();
		final Calendar calendar = Calendar.getInstance();
		int month = calendar.get(Calendar.MONTH);
		getEntitySelect().setMonth(Month.getMonth(month == 0 ? 11 : month - 1));
		int year = calendar.get(Calendar.YEAR);
		getEntitySelect().setYear(year);
	}

	@Override
	protected MemberReport prepareEntity(Operation operation) {
		MemberReport memberReport = super.prepareEntity(operation);
		memberReport.setCongregation(getCongregation());
		final Calendar calendar = Calendar.getInstance();
		if (memberReport.getYear() == null) {
			int year = calendar.get(Calendar.YEAR);
			memberReport.setYear(year);
		}
		if (memberReport.getMonth() == null) {
			int month = calendar.get(Calendar.MONTH);
			memberReport.setMonth(Month.getMonth(month == 0 ? 11 : month - 1));
		}
		return memberReport;
	}

	@Override
	protected void createAfter() {
		super.createAfter();
		getEntity().setDate(new Date());
		final Calendar calendar = Calendar.getInstance();
		int month = calendar.get(Calendar.MONTH);
		getEntity().setMonth(Month.getMonth(month == 0 ? 11 : month - 1));
		int year = calendar.get(Calendar.YEAR);
		getEntity().setYear(year);
	}

	@Override
	protected DownloadInfo doReportLoad() {
		final Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		if (getEntitySelect().getMonth() != null) {
			calendar.set(Calendar.MONTH, getEntitySelect().getMonth().ordinal());
		}
		final String period = VulpeDateUtil.getDate(calendar.getTime(), "MMMM 'de' yyyy");
		getReportParameters().put("period", period);
		final List<String> collection = new ArrayList<String>();
		collection.add("report");
		setReportCollection(collection);
		final List<MemberReport> membersReport = new ArrayList<MemberReport>();
		final List<Member> pendingMembers = new ArrayList<Member>();
		final List<Member> members = getSessionAttribute(Core.MEMBERS_OF_SELECTED_CONGREGATION);
		if (VulpeValidationUtil.isNotEmpty(getEntities())) {
			for (MemberReport memberReport : getEntities()) {
				if (memberReport.getMinistryType().equals(MinistryType.PUBLISHER)) {
					membersReport.add(memberReport);
				}
			}
			final List<MemberReport> auxiliaryPioneers = new ArrayList<MemberReport>();
			for (MemberReport memberReport : getEntities()) {
				if (memberReport.getMinistryType().equals(MinistryType.AUXILIARY_PIONEER)) {
					auxiliaryPioneers.add(memberReport);
				}
			}
			final List<MemberReport> regularPioneers = new ArrayList<MemberReport>();
			for (MemberReport memberReport : getEntities()) {
				if (memberReport.getMinistryType().equals(MinistryType.REGULAR_PIONEER)) {
					regularPioneers.add(memberReport);
				}
			}
			Collections.sort(membersReport);
			Collections.sort(membersReport, new MemberReportGroupComparator());
			getReportParameters().put("publishers", membersReport.isEmpty() ? null : membersReport);
			Collections.sort(auxiliaryPioneers);
			getReportParameters().put("auxiliaryPioneers",
					auxiliaryPioneers.isEmpty() ? null : auxiliaryPioneers);
			Collections.sort(regularPioneers);
			getReportParameters().put("regularPioneers",
					regularPioneers.isEmpty() ? null : regularPioneers);
			for (Member member : members) {
				if (!member.getMinistryType().equals(MinistryType.STUDENT)
						&& !member.getMinistryType().equals(MinistryType.AWAY)) {
					boolean delivered = false;
					for (MemberReport relatorio : getEntities()) {
						if (relatorio.getMember().getId().equals(member.getId())) {
							delivered = true;
						}
					}
					if (!delivered) {
						pendingMembers.add(member);
					}
				}
			}
		} else {
			for (Member member : members) {
				if (!member.getMinistryType().equals(MinistryType.STUDENT)
						&& !member.getMinistryType().equals(MinistryType.AWAY)) {
					pendingMembers.add(member);
				}
			}
		}
		Collections.sort(pendingMembers);
		Collections.sort(pendingMembers, new MemberGroupComparator());
		getReportParameters().put("pendingMembers",
				pendingMembers.isEmpty() ? null : pendingMembers);
		return super.doReportLoad();
	}

}
