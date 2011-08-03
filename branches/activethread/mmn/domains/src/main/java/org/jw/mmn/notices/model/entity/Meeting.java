package org.jw.mmn.notices.model.entity;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.jw.mmn.core.model.entity.Congregation;
import org.jw.mmn.core.model.entity.Member;
import org.vulpe.model.entity.impl.VulpeBaseDB4OEntity;
import org.vulpe.view.annotations.input.VulpeDate;
import org.vulpe.view.annotations.input.VulpeSelect;
import org.vulpe.view.annotations.input.VulpeSelectPopup;
import org.vulpe.view.annotations.logic.main.Detail;
import org.vulpe.view.annotations.output.VulpeColumn;

//@CodeGenerator(controller = @Controller(select = @Select(pageSize = 5), detailsConfig = { @DetailConfig(name = "discursos", propertyName = "entity.discursos", despiseFields = "tema", quantity = @Quantity(type = QuantityType.ONE_OR_MORE), newDetails = 3) }), manager = true, view = @View(viewType = {
//		ViewType.SELECT, ViewType.MAIN }))
@SuppressWarnings("serial")
@Getter
@Setter
public class Meeting extends VulpeBaseDB4OEntity<Long> {

	@VulpeColumn(sortable = true, attribute = "nome")
	@VulpeSelectPopup(identifier = "id", description = "nome", action = "/core/Member/select", popupWidth = 420, argument = true, required = true, autocomplete = true)
	private Member president;

	@VulpeColumn
	@VulpeDate(required = true)
	private Date date;

	@VulpeSelect
	private MeetingType type;

	private Integer initialSong;

	private Integer finalSong;

	@Detail(target = Discourse.class)
	private List<Discourse> discourses;

	private Congregation congregation;

}
