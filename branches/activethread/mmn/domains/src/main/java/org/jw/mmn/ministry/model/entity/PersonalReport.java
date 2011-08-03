package org.jw.mmn.ministry.model.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import org.vulpe.model.annotations.CodeGenerator;
import org.vulpe.model.entity.impl.VulpeBaseDB4OEntity;
import org.vulpe.view.annotations.input.VulpeText;

@CodeGenerator(dao = false, manager = false)
@SuppressWarnings("serial")
@Getter
@Setter
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

}
