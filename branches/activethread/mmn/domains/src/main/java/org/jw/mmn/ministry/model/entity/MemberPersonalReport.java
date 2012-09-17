package org.jw.mmn.ministry.model.entity;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.jw.mmn.commons.model.entity.MinistryType;
import org.jw.mmn.core.model.entity.Member;
import org.vulpe.commons.util.VulpeDateUtil;
import org.vulpe.commons.util.VulpeValidationUtil;
import org.vulpe.controller.commons.MultipleResourceBundle;
import org.vulpe.model.annotations.Parameter;
import org.vulpe.model.annotations.Parameter.OperatorType;
import org.vulpe.model.annotations.QueryParameter;
import org.vulpe.model.entity.VulpeEntity;
import org.vulpe.model.entity.impl.VulpeBaseDB4OEntity;
import org.vulpe.view.annotations.input.VulpeDate;
import org.vulpe.view.annotations.input.VulpeSelect;
import org.vulpe.view.annotations.input.VulpeText;
import org.vulpe.view.annotations.logic.main.Detail;
import org.vulpe.view.annotations.output.VulpeColumn;

//@CodeGenerator(controller = @Controller(detailsConfig = @DetailConfig(name = "reports", despiseFields = "hours", propertyName = "entity.reports")), view = @View(viewType = ViewType.MAIN))
@SuppressWarnings("serial")
@Getter
@Setter
public class MemberPersonalReport extends VulpeBaseDB4OEntity<Long> {

	private Member member;

	@VulpeColumn(sortable = true)
	@VulpeDate
	private Date date;

	@QueryParameter(equals = @Parameter(name = "date", operator = OperatorType.GREATER_OR_EQUAL))
	private transient Date initialDate;

	@QueryParameter(equals = @Parameter(name = "date", operator = OperatorType.SMALLER_OR_EQUAL))
	private transient Date finalDate;

	@VulpeSelect
	private Month month;

	private Integer year;

	@VulpeSelect
	private MinistryType ministryType;

	@VulpeText(mask = "I", size = 8)
	private Integer studies;

	@Detail(target = PersonalReport.class)
	private List<PersonalReport> reports;

	private boolean sended;

	private boolean delivered;

	private transient Integer totalBooks;

	private transient Integer totalBrochures;

	private transient Integer totalMagazines;

	private transient Integer totalRevisits;

	private transient String totalHours;

	private transient String totalPioneer;

	private transient String totalPioneerRemain;

	private transient String totalPioneerRemainPerDay;

	private transient String totalAveragePerDay;

	private transient Integer totalLeftDays;

	private transient Integer totalMinutes;

	public String getMonthI18n() {
		return MultipleResourceBundle.getInstance().getI18NEnum(this.month);
	}

	public int getOrdinalMonth() {
		return this.month.ordinal();
	}

	public void sum() {
		if (VulpeValidationUtil.isNotEmpty(this.getReports())) {
			int books = 0;
			int brochures = 0;
			int minutes = 0;
			int magazines = 0;
			int revisits = 0;
			for (final PersonalReport personalReport : this.getReports()) {
				if (personalReport != null) {
					books += personalReport.getBooks() == null ? 0 : personalReport.getBooks();
					brochures += personalReport.getBrochures() == null ? 0 : personalReport
							.getBrochures();
					minutes += personalReport.getTotalMinites();
					magazines += personalReport.getMagazines() == null ? 0 : personalReport
							.getMagazines();
					revisits += personalReport.getRevisits() == null ? 0 : personalReport
							.getRevisits();
				}
			}
			this.setTotalBooks(books);
			this.setTotalBrochures(brochures);
			this.setTotalMinutes(minutes);
			this.setTotalHours(VulpeDateUtil.getFormatedTime(minutes));
			this.setTotalMagazines(magazines);
			this.setTotalRevisits(revisits);
			int totalDaysOfMonth = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
			int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
			this.setTotalAveragePerDay(VulpeDateUtil.getFormatedTime(minutes / currentDay));
			this.setTotalLeftDays((totalDaysOfMonth - currentDay));
			if (this.getMinistryType().equals(MinistryType.AUXILIARY_PIONEER)) {
				final int totalMinutesAuxiliary = 50 * 60;
				if (minutes < totalMinutesAuxiliary) {
					this.setTotalPioneer(" (-"
							+ VulpeDateUtil.getFormatedTime(totalMinutesAuxiliary - minutes) + ")");
				}
				int totalRemain = (totalMinutesAuxiliary - minutes);
				int totalRemainPerDay = 0;
				if (this.getTotalLeftDays() > 0) {
					totalRemainPerDay = totalRemain / this.getTotalLeftDays();
				} else {
					totalRemain = 0;
				}
				this.setTotalPioneerRemain(VulpeDateUtil.getFormatedTime(totalRemain));
				this.setTotalPioneerRemainPerDay(VulpeDateUtil.getFormatedTime(totalRemainPerDay));

			} else if (this.getMinistryType().equals(MinistryType.REGULAR_PIONEER)) {
				final int totalMinutesRegular = 70 * 60;
				if (minutes < totalMinutesRegular) {
					this.setTotalPioneer(" (-"
							+ VulpeDateUtil.getFormatedTime(totalMinutesRegular - minutes) + ")");
				}
				int totalRemain = (totalMinutesRegular - minutes);
				int totalRemainPerDay = 0;
				if (this.getTotalLeftDays() > 0) {
					totalRemainPerDay = totalRemain / this.getTotalLeftDays();
				} else {
					totalRemain = 0;
				}
				this.setTotalPioneerRemain(VulpeDateUtil.getFormatedTime(totalRemain));
				this.setTotalPioneerRemainPerDay(VulpeDateUtil.getFormatedTime(totalRemainPerDay));
			}
		}
	}

	public void turnZero() {
		if (VulpeValidationUtil.isNotEmpty(this.getReports())) {
			for (final PersonalReport personalReport : this.getReports()) {
				if (personalReport != null) {
					if (personalReport.getBooks() == null) {
						personalReport.setBooks(0);
					}
					if (personalReport.getBrochures() == null) {
						personalReport.setBrochures(0);
					}
					if (personalReport.getMagazines() == null) {
						personalReport.setMagazines(0);
					}
					if (personalReport.getRevisits() == null) {
						personalReport.setRevisits(0);
					}
				}
			}
		}
	}

	@Override
	public int compareTo(VulpeEntity<Long> entity) {
		final MemberPersonalReport memberPersonalReport = (MemberPersonalReport) entity;
		return Integer.valueOf(this.month.ordinal()).compareTo(
				Integer.valueOf(memberPersonalReport.getMonth().ordinal()))
				+ this.year.compareTo(memberPersonalReport.getYear());
	}
}
