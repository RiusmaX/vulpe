package org.jw.mmn.notices.model.entity;

import java.util.Date;

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
public class Discourse extends VulpeBaseDB4OEntity<Long> {

	private Meeting meeting;

	@VulpeDate
	private Date data;

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

	public Meeting getMeeting() {
		return meeting;
	}

	public void setMeeting(Meeting meeting) {
		this.meeting = meeting;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Member getSpeaker() {
		return speaker;
	}

	public void setSpeaker(Member speaker) {
		this.speaker = speaker;
	}

	public String getGuestSpeaker() {
		return guestSpeaker;
	}

	public void setGuestSpeaker(String guestSpeaker) {
		this.guestSpeaker = guestSpeaker;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public String getScene() {
		return scene;
	}

	public void setScene(String scene) {
		this.scene = scene;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public DiscourseType getType() {
		return type;
	}

	public void setType(DiscourseType type) {
		this.type = type;
	}

	public void setAssistant(Member assistant) {
		this.assistant = assistant;
	}

	public Member getAssistant() {
		return assistant;
	}

	public void setCharacteristicOratory(CharacteristicOratory characteristicOratory) {
		this.characteristicOratory = characteristicOratory;
	}

	public CharacteristicOratory getCharacteristicOratory() {
		return characteristicOratory;
	}

}
