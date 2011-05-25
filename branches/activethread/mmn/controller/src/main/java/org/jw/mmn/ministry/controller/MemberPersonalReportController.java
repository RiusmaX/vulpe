package org.jw.mmn.ministry.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.jw.mmn.controller.ApplicationBaseController;
import org.jw.mmn.core.model.entity.Member;
import org.jw.mmn.core.model.services.CoreService;
import org.jw.mmn.ministry.model.entity.MemberPersonalReport;
import org.jw.mmn.ministry.model.entity.Month;
import org.jw.mmn.ministry.model.entity.PersonalReport;
import org.jw.mmn.ministry.model.services.MinistryService;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.commons.VulpeConstants.Controller.Button;
import org.vulpe.commons.annotations.DetailConfig;
import org.vulpe.commons.util.VulpeValidationUtil;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.exception.VulpeApplicationException;

/**
 * Controller implementation of MemberPersonalReport
 */
@Component("ministry.MemberPersonalReportController")
@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller(serviceClass = MinistryService.class, detailsConfig = { @DetailConfig(name = "reports", propertyName = "entity.reports", despiseFields = "hours") }, showInTabs = false)
public class MemberPersonalReportController extends ApplicationBaseController<MemberPersonalReport, java.lang.Long> {

	private static final Logger LOG = Logger.getLogger(MemberPersonalReportController.class);

	@Override
	protected void createAfter() {
		super.createAfter();
		final Calendar calendar = Calendar.getInstance();
		int month = calendar.get(Calendar.MONTH);
		getEntity().setMonth(Month.getMonth(month - 1));
		int year = calendar.get(Calendar.YEAR);
		getEntity().setYear(year);
		getEntity().setDate(new Date());
		try {
			getSecurityContext().getUser();
			getEntity().setMember(getService(CoreService.class).findMember(new Member()));
			if (getEntity().getMember() != null) {
				getEntity().setMinistryType(getEntity().getMember().getMinistryType());
			}
		} catch (VulpeApplicationException e) {
			LOG.error(e);
		}
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
			memberPersonalReport.setSended(false);
			final List<MemberPersonalReport> list = getService(MinistryService.class).readMemberPersonalReport(
					memberPersonalReport);
			if (VulpeValidationUtil.isNotEmpty(list)) {
				setId(list.get(0).getId());
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
	}

	@Override
	public void manageButtons(Operation operation) {
		super.manageButtons(operation);
		hideButtons(Button.CREATE, Button.DELETE, Button.BACK);
	}

	private void checksDate() {
		for (PersonalReport personalReport : getEntity().getReports()) {
			if (personalReport.getDate() == null) {
				personalReport.setDate(new Date());
			}
		}
	}
}
