package org.jw.mmn.ministry.model.entity;

import java.util.Date;

import org.jw.mmn.commons.model.entity.MinistryType;
import org.jw.mmn.core.model.entity.Congregation;
import org.jw.mmn.core.model.entity.Member;
import org.vulpe.commons.util.VulpeStringUtil;
import org.vulpe.model.annotations.NotExistEquals;
import org.vulpe.model.annotations.Parameter;
import org.vulpe.model.annotations.QueryParameter;
import org.vulpe.model.annotations.Parameter.OperatorType;
import org.vulpe.model.entity.VulpeEntity;
import org.vulpe.model.entity.impl.VulpeBaseDB4OEntity;
import org.vulpe.view.annotations.input.VulpeDate;
import org.vulpe.view.annotations.input.VulpeSelect;
import org.vulpe.view.annotations.input.VulpeSelectPopup;
import org.vulpe.view.annotations.input.VulpeText;
import org.vulpe.view.annotations.output.VulpeColumn;

@NotExistEquals(parameters = { @QueryParameter(equals = @Parameter(name = "member")),
		@QueryParameter(equals = @Parameter(name = "month")) })
@SuppressWarnings("serial")
public class MemberReport extends VulpeBaseDB4OEntity<Long> {

	@VulpeColumn(sortable = true, attribute = "name")
	@VulpeSelectPopup(identifier = "id", description = "name", action = "/core/Member/select", popupWidth = 420, argument = true, autocomplete = true)
	private Member member;

	@VulpeColumn(sortable = true)
	@VulpeDate
	private Date date;

	@VulpeSelect
	private Month month;

	private Integer year;

	@QueryParameter(equals = @Parameter(name = "data", operator = OperatorType.GREATER_OR_EQUAL))
	@VulpeDate(argument = true)
	private transient Date initialDate;

	@QueryParameter(equals = @Parameter(name = "data", operator = OperatorType.SMALLER_OR_EQUAL))
	@VulpeDate(argument = true)
	private transient Date finalDate;

	@VulpeText(mask = "I", size = 8)
	private Integer books;

	@VulpeText(mask = "I", size = 8)
	private Integer brochures;

	@VulpeText(mask = "I", size = 8)
	private Integer hours;

	@VulpeText(mask = "I", size = 8)
	private Integer magazines;

	@VulpeText(mask = "I", size = 8)
	private Integer revisits;

	@VulpeText(mask = "I", size = 8)
	private Integer studies;

	@VulpeSelect
	private MinistryType ministryType;

	private Congregation congregation;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getHours() {
		return hours;
	}

	public void setHours(Integer hours) {
		this.hours = hours;
	}

	public Integer getBooks() {
		return books;
	}

	public void setBooks(Integer books) {
		this.books = books;
	}

	public Integer getMagazines() {
		return magazines;
	}

	public void setMagazines(Integer magazines) {
		this.magazines = magazines;
	}

	public Integer getRevisits() {
		return revisits;
	}

	public void setRevisits(Integer revisits) {
		this.revisits = revisits;
	}

	public Integer getStudies() {
		return studies;
	}

	public void setStudies(Integer studies) {
		this.studies = studies;
	}

	public MinistryType getMinistryType() {
		return ministryType;
	}

	public void setMinistryType(MinistryType ministryType) {
		this.ministryType = ministryType;
	}

	public void setInitialDate(Date initialDate) {
		this.initialDate = initialDate;
	}

	public Date getInitialDate() {
		return initialDate;
	}

	public void setFinalDate(Date finalDate) {
		this.finalDate = finalDate;
	}

	public Date getFinalDate() {
		return finalDate;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public void setBrochures(Integer brochures) {
		this.brochures = brochures;
	}

	public Integer getBrochures() {
		return brochures;
	}

	public void setMonth(Month month) {
		this.month = month;
	}

	public Month getMonth() {
		return month;
	}

	@Override
	public String getOrderBy() {
		return "publicador.nome";
	}

	public void setCongregation(Congregation congregation) {
		this.congregation = congregation;
	}

	public Congregation getCongregation() {
		return congregation;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getYear() {
		return year;
	}

	@Override
	public int compareTo(VulpeEntity<Long> entity) {
		final MemberReport memberReport = (MemberReport) entity;
		return VulpeStringUtil.normalize(member.getName()).compareTo(
				VulpeStringUtil.normalize(memberReport.getMember().getName()));
	}
}
