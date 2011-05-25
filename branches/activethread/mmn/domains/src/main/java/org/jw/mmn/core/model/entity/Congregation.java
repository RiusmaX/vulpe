package org.jw.mmn.core.model.entity;

import java.util.List;

import org.vulpe.commons.enumeration.DaysOfWeek;
import org.vulpe.model.annotations.CachedClass;
import org.vulpe.model.entity.impl.VulpeBaseDB4OEntity;
import org.vulpe.view.annotations.input.VulpeText;
import org.vulpe.view.annotations.input.VulpeValidate;
import org.vulpe.view.annotations.input.VulpeValidate.VulpeValidateType;
import org.vulpe.view.annotations.logic.main.Detail;
import org.vulpe.view.annotations.output.VulpeColumn;

@CachedClass
//@CodeGenerator(controller = @Controller(select = @Select(pageSize = 5), tabular = @Tabular(despiseFields = "nome", startNewRecords = 5, newRecords = 1), controllerType = ControllerType.MAIN, detailsConfig = {
//		@DetailConfig(name = "grupos", propertyName = "entity.grupos", despiseFields = "nome", startNewDetails = 3, newDetails = 1, quantity = @Quantity(type = QuantityType.ONE)),
//		@DetailConfig(name = "usuarios", propertyName = "entity.usuarios", despiseFields = "usuario", startNewDetails = 3, newDetails = 1, quantity = @Quantity(type = QuantityType.ONE)) }), manager = true, view = @View(viewType = {
//		ViewType.MAIN, ViewType.SELECT, ViewType.TABULAR }))
@SuppressWarnings("serial")
public class Congregation extends VulpeBaseDB4OEntity<Long> {

	@VulpeColumn(sortable = true)
	@VulpeValidate(type = VulpeValidateType.STRING, minlength = 5, maxlength = 60)
	@VulpeText(argument = true, size = 40, maxlength = 60, required = true)
	private String name;

	private DaysOfWeek firstMeetingDay;

	private String firstMeetingHour;

	private DaysOfWeek secondMeetingDay;

	private String secondMeetingHour;

	@Detail(target = Group.class)
	private List<Group> groups;

	@Detail(target = CongregationUser.class)
	private List<CongregationAddress> addresses;

	@Detail(target = CongregationUser.class)
	private List<CongregationUser> users;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setUsers(List<CongregationUser> users) {
		this.users = users;
	}

	public List<CongregationUser> getUsers() {
		return users;
	}

	public void setAddresses(List<CongregationAddress> addresses) {
		this.addresses = addresses;
	}

	public List<CongregationAddress> getAddresses() {
		return addresses;
	}

	public void setFirstMeetingDay(DaysOfWeek firstMeetingDay) {
		this.firstMeetingDay = firstMeetingDay;
	}

	public DaysOfWeek getFirstMeetingDay() {
		return firstMeetingDay;
	}

	public void setFirstMeetingHour(String firstMeetingHour) {
		this.firstMeetingHour = firstMeetingHour;
	}

	public String getFirstMeetingHour() {
		return firstMeetingHour;
	}

	public void setSecondMeetingDay(DaysOfWeek secondMeetingDay) {
		this.secondMeetingDay = secondMeetingDay;
	}

	public DaysOfWeek getSecondMeetingDay() {
		return secondMeetingDay;
	}

	public void setSecondMeetingHour(String secondMeetingHour) {
		this.secondMeetingHour = secondMeetingHour;
	}

	public String getSecondMeetingHour() {
		return secondMeetingHour;
	}

}
