package org.jw.mmn.notices.model.entity;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.vulpe.model.entity.impl.VulpeBaseDB4OEntity;
import org.vulpe.view.annotations.input.VulpeDate;
import org.vulpe.view.annotations.input.VulpeSelect;
import org.vulpe.view.annotations.input.VulpeSelectPopup;
import org.vulpe.view.annotations.input.VulpeText;

import org.jw.mmn.notices.model.entity.DiscourseType;
import org.jw.mmn.core.model.entity.CharacteristicOratory;
import org.jw.mmn.core.model.entity.Member;
import org.jw.mmn.notices.model.entity.Meeting;

//@CodeGenerator(controller = @Controller(select = @Select(pageSize = 5), detailsConfig = { @DetailConfig(name = "discursos", propertyName = "entity.discursos", despiseFields = "tema", quantity = @Quantity(type = QuantityType.ONE_OR_MORE), newDetails = 3) }), manager = true, view = @View(viewType = {
//ViewType.SELECT, ViewType.MAIN }))
@SuppressWarnings("serial")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Discourse extends VulpeBaseDB4OEntity<Long> {

	private Meeting meeting;

	@VulpeDate
	private Date date;

	@VulpeSelectPopup(identifier = "id", description = "nome", action = "/core/Member/select", popupWidth = 420, argument = true, required = true, autocomplete = true)
	private Member speaker;

	@VulpeSelectPopup(identifier = "id", description = "nome", action = "/core/Member/select", popupWidth = 420, argument = true, required = true, autocomplete = true)
	private Member assistant;

	@VulpeText(required = true, size = 40, maxlength = 100)
	private String guestSpeaker;

	@VulpeText(required = true, size = 40, maxlength = 100)
	private String topic;

	@VulpeText(mask = "I", required = true)
	private Integer time;

	@VulpeText(required = true, size = 40, maxlength = 100)
	private String scene;

	@VulpeText(required = true, size = 40, maxlength = 100)
	private String source;

	@VulpeSelect
	private DiscourseType type;
	
	private CharacteristicOratory characteristicOratory;
	
}
