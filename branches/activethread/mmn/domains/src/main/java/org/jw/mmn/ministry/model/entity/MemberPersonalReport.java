package org.jw.mmn.ministry.model.entity;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.jw.mmn.commons.model.entity.MinistryType;
import org.jw.mmn.core.model.entity.Member;
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

}
