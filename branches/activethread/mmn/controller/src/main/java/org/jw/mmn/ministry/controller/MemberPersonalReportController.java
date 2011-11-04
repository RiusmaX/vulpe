package org.jw.mmn.ministry.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.jw.mmn.controller.ApplicationBaseController;
import org.jw.mmn.ministry.model.entity.MemberPersonalReport;
import org.jw.mmn.ministry.model.entity.Month;
import org.jw.mmn.ministry.model.entity.PersonalReport;
import org.jw.mmn.ministry.model.services.MinistryService;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.commons.VulpeConstants.Controller.Button;
import org.vulpe.commons.annotations.DetailConfig;
import org.vulpe.commons.beans.DownloadInfo;
import org.vulpe.commons.util.VulpeValidationUtil;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Report;
import org.vulpe.exception.VulpeApplicationException;

/**
 * Controller implementation of MemberPersonalReport
 */
@Component("ministry.MemberPersonalReportController")
@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller(serviceClass = MinistryService.class, detailsConfig = { @DetailConfig(name = "reports", propertyName = "entity.reports", despiseFields = "despise") }, showInTabs = false, report = @Report(subReports = { "Reports" }))
public class MemberPersonalReportController extends
		ApplicationBaseController<MemberPersonalReport, java.lang.Long> {

	private static final Logger LOG = Logger.getLogger(MemberPersonalReportController.class);

	private void monthAndYear(final MemberPersonalReport entity) {
		entity.setMonth(Month.getMonth(calendar.get(Calendar.MONTH)));
		entity.setYear(calendar.get(Calendar.YEAR));
	}

	@Override
	protected void createAfter() {
		super.createAfter();
		monthAndYear(entity);
		final MemberPersonalReport memberPersonalReport = ever.getAuto("memberPersonalReport");
		if (memberPersonalReport != null) {
			entity.setMonth(memberPersonalReport.getMonth());
			entity.setYear(memberPersonalReport.getYear());
		}
		entity.setDate(new Date());
		entity.setMember(retrieveMember());
		if (entity.getMember() != null) {
			entity.setMinistryType(entity.getMember().getMinistryType());
		}
	}

	@Override
	protected void createPostAfter() {
		super.createPostAfter();
		entity.sum();
	}

	@Override
	public void addDetail() {
		super.addDetail();
		checksDate();
	}

	@Override
	public void update() {
		try {
			final MemberPersonalReport memberPersonalReport = new MemberPersonalReport();
			memberPersonalReport.setMember(retrieveMember());
			if (VulpeValidationUtil.isEmpty(memberPersonalReport.getMember())) {
				addActionError("{app.message.error.ministry.MemberPersonalReport.main.member.not.found}");
				vulpe.view()
						.notRenderButtons(Button.CREATE_POST, Button.UPDATE_POST, Button.REPORT);
				controlResultForward();
				return;
			} else {
				memberPersonalReport.setSended(false);
				monthAndYear(memberPersonalReport);
				if (entity != null) {
					if (entity.getMonth() != null) {
						memberPersonalReport.setMonth(entity.getMonth());
					}
					if (entity.getYear() != null) {
						memberPersonalReport.setYear(entity.getYear());
					}
				}
				final List<MemberPersonalReport> list = vulpe.service(MinistryService.class)
						.readMemberPersonalReport(memberPersonalReport);
				if (VulpeValidationUtil.isNotEmpty(list)) {
					id = list.get(0).getId();
				} else {
					ever.putWeakRef("memberPersonalReport", memberPersonalReport);
					create();
					return;
				}
			}
		} catch (VulpeApplicationException e) {
			LOG.error(e);
		}
		super.update();
	}

	@Override
	protected void updateAfter() {
		super.updateAfter();
		checksDate();
		entity.sum();
		if (entity.getYear() == null) {
			entity.setYear(calendar.get(Calendar.YEAR));
		}
		vulpe.view().renderButtons(Button.REPORT);
	}

	@Override
	protected void updatePostAfter() {
		super.updatePostAfter();
		entity.sum();
		vulpe.view().renderButtons(Button.REPORT);
	}

	@Override
	public void manageButtons(Operation operation) {
		super.manageButtons(operation);
		vulpe.view().hideButtons(Button.CREATE, Button.DELETE, Button.BACK, Button.CLONE);
	}

	private void checksDate() {
		for (final PersonalReport personalReport : entity.getReports()) {
			if (personalReport.getDate() == null) {
				personalReport.setDate(new Date());
			}
		}
	}

	@Override
	protected DownloadInfo doReportLoad() {
		if (VulpeValidationUtil.isNotEmpty(entities)) {
			for (final MemberPersonalReport memberPersonalReport : entities) {
				memberPersonalReport.sum();
			}
		}
		return super.doReportLoad();
	}

	@Override
	protected void addDetailAfter() {
		super.addDetailAfter();
		entity.sum();
	}

	@Override
	protected void deleteDetailAfter() {
		super.deleteDetailAfter();
		entity.sum();
	}
}
