package org.jw.mmn.ministry.controller;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import org.vulpe.commons.util.VulpeDateUtil;
import org.vulpe.commons.util.VulpeValidationUtil;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Report;
import org.vulpe.exception.VulpeApplicationException;

/**
 * Controller implementation of MemberPersonalReport
 */
@Component("ministry.MemberPersonalReportController")
@SuppressWarnings({ "serial", "unchecked" })
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller(serviceClass = MinistryService.class, detailsConfig = { @DetailConfig(name = "reports", propertyName = "entity.reports", despiseFields = "despise") }, showInTabs = false, report = @Report(subReports = { "Reports" }))
public class MemberPersonalReportController extends
		ApplicationBaseController<MemberPersonalReport, java.lang.Long> {

	private static final Logger LOG = LoggerFactory.getLogger(MemberPersonalReportController.class);

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
	protected void createPostBefore() {
		super.createPostBefore();
		mountDate();
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
					// MemberPersonalReport mpr = new MemberPersonalReport();
					// mpr.setId(id);
					// mpr = ministryService().findMemberPersonalReport(mpr);
					// boolean remove = false;
					// for (Iterator<PersonalReport> iterator =
					// mpr.getReports().iterator(); iterator.hasNext();) {
					// PersonalReport personalReport = iterator
					// .next();
					// if (personalReport == null) {
					// iterator.remove();
					// remove = true;
					// }
					// }
					// if (remove) {
					// ministryService().updateMemberPersonalReport(mpr);
					// }
				} else {
					ever.putWeakRef("memberPersonalReport", memberPersonalReport);
					create();
					return;
				}
			}
		} catch (VulpeApplicationException e) {
			LOG.error(e.getMessage());
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
	protected void updatePostBefore() {
		super.updatePostBefore();
		mountDate();
		entity.turnZero();
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

	private void mountDate() {
		if (entity.getYear() != null && entity.getMonth() != null) {
			int month = entity.getMonth().ordinal() + 1;
			try {
				entity.setDate(VulpeDateUtil.convertStringToDateTime("01/"
						+ (month < 10 ? "0" + month : month) + "/" + entity.getYear() + " 00:00:00"));
			} catch (ParseException e) {
				LOG.error(e.getMessage());
			}
		}
	}

	@Override
	protected DownloadInfo doReportLoad() {
		if (VulpeValidationUtil.isNotEmpty(entities)) {
			Collections.sort(entities);
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

	@Override
	protected void onRead() {
		if (entitySelect.getMonth() == null) {
			int year = calendar.get(Calendar.YEAR);
			int serviceYear = year;
			if (calendar.get(Calendar.MONTH) < 8) {
				--serviceYear;
			}
			final Calendar currentCalendar = Calendar.getInstance();
			currentCalendar.set(Calendar.HOUR, 0);
			currentCalendar.set(Calendar.MINUTE, 0);
			currentCalendar.set(Calendar.MILLISECOND, 0);
			currentCalendar.set(Calendar.YEAR, serviceYear);
			currentCalendar.set(Calendar.MONTH, Calendar.SEPTEMBER);
			currentCalendar.set(Calendar.DAY_OF_MONTH, 1);
			final Date initialDate = currentCalendar.getTime();
			entitySelect.setInitialDate(initialDate);
			currentCalendar.set(Calendar.YEAR, serviceYear);
			currentCalendar.set(Calendar.MONTH, Calendar.DECEMBER);
			currentCalendar.set(Calendar.DAY_OF_MONTH, 31);
			final Date finalDate = currentCalendar.getTime();
			entitySelect.setFinalDate(finalDate);
		}
		super.onRead();
	}
}
