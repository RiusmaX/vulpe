package org.jw.mmn.ministry.model.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang.StringUtils;
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

	@VulpeText(mask = "99:99")
	private String hours;

	@VulpeText(mask = "I", size = 8)
	private Integer magazines;

	@VulpeText(mask = "I", size = 8)
	private Integer revisits;

	public Integer getTotalMinites() {
		Integer minutes = 0;
		if (StringUtils.isNotBlank(this.hours)) {
			final String[] parts = this.hours.split(":");
			final int part1 = Integer.valueOf(parts[0]);
			final int part2 = Integer.valueOf(parts[1]);
			minutes = (part1 * 60) + part2;
		}
		return minutes;
	}

}
