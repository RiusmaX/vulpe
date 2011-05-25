package org.jw.mmn.notices.controller;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import org.vulpe.commons.annotations.DetailConfig;

import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Select;

import org.jw.mmn.notices.model.services.NoticesService;
import org.jw.mmn.controller.ApplicationBaseController;
import org.jw.mmn.notices.model.entity.Meeting;

/**
 * Controller implementation of Meeting
 */
@Component("notices.MeetingController")
@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller(serviceClass = NoticesService.class, detailsConfig = { @DetailConfig(name = "discursos", propertyName = "entity.discursos", despiseFields = "tema", newDetails = 3) }, select = @Select(pageSize = 5))
public class MeetingController extends ApplicationBaseController<Meeting, java.lang.Long> {

	@Override
	protected Meeting prepareEntity(Operation operation) {
		final Meeting meeting = super.prepareEntity(operation);
		meeting.setCongregation(getCongregation());
		return meeting;
	}

}
