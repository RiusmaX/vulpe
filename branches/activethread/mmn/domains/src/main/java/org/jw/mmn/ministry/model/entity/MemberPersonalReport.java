package org.jw.mmn.ministry.model.entity;

import java.util.Date;
import java.util.List;

import org.jw.mmn.commons.model.entity.MinistryType;
import org.jw.mmn.core.model.entity.Member;
import org.vulpe.commons.annotations.DetailConfig;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.model.annotations.CodeGenerator;
import org.vulpe.model.entity.impl.VulpeBaseDB4OEntity;
import org.vulpe.view.annotations.View;
import org.vulpe.view.annotations.View.ViewType;
import org.vulpe.view.annotations.input.VulpeDate;
import org.vulpe.view.annotations.input.VulpeSelect;
import org.vulpe.view.annotations.input.VulpeText;
import org.vulpe.view.annotations.logic.main.Detail;
import org.vulpe.view.annotations.output.VulpeColumn;

@CodeGenerator(controller = @Controller(detailsConfig = @DetailConfig(name = "reports", despiseFields = "hours", propertyName = "entity.reports")), view = @View(viewType = ViewType.MAIN))
@SuppressWarnings("serial")
public class MemberPersonalReport extends VulpeBaseDB4OEntity<Long> {

	private Member member;

	@VulpeColumn(sortable = true)
	@VulpeDate
	private Date date;

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

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Month getMonth() {
		return month;
	}

	public void setMonth(Month month) {
		this.month = month;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public MinistryType getMinistryType() {
		return ministryType;
	}

	public void setMinistryType(MinistryType ministryType) {
		this.ministryType = ministryType;
	}

	public List<PersonalReport> getReports() {
		return reports;
	}

	public void setReports(List<PersonalReport> reports) {
		this.reports = reports;
	}

	public void setDelivered(boolean delivered) {
		this.delivered = delivered;
	}

	public boolean isDelivered() {
		return delivered;
	}

	public void setSended(boolean sended) {
		this.sended = sended;
	}

	public boolean isSended() {
		return sended;
	}

	public void setStudies(Integer studies) {
		this.studies = studies;
	}

	public Integer getStudies() {
		return studies;
	}

}
