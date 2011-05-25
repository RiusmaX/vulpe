package org.jw.mmn.ministry.model.entity;

import java.util.Date;

import org.vulpe.model.annotations.CodeGenerator;
import org.vulpe.model.entity.impl.VulpeBaseDB4OEntity;
import org.vulpe.view.annotations.input.VulpeText;

@CodeGenerator(dao = false, manager = false)
@SuppressWarnings("serial")
public class PersonalReport extends VulpeBaseDB4OEntity<Long> {

	private MemberPersonalReport memberPersonalReport;

	private Date date;

	@VulpeText(mask = "I", size = 8)
	private Integer books;

	@VulpeText(mask = "I", size = 8)
	private Integer brochures;

	@VulpeText(mask = "I", size = 8)
	private double hours;

	@VulpeText(mask = "I", size = 8)
	private Integer magazines;

	@VulpeText(mask = "I", size = 8)
	private Integer revisits;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getBooks() {
		return books;
	}

	public void setBooks(Integer books) {
		this.books = books;
	}

	public Integer getBrochures() {
		return brochures;
	}

	public void setBrochures(Integer brochures) {
		this.brochures = brochures;
	}

	public double getHours() {
		return hours;
	}

	public void setHours(double hours) {
		this.hours = hours;
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

	public void setMemberPersonalReport(MemberPersonalReport memberPersonalReport) {
		this.memberPersonalReport = memberPersonalReport;
	}

	public MemberPersonalReport getMemberPersonalReport() {
		return memberPersonalReport;
	}

}
