package org.jw.mmn.core.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jw.mmn.commons.model.entity.Gender;
import org.jw.mmn.commons.model.entity.MinistryType;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.commons.annotations.DetailConfig;
import org.vulpe.commons.beans.ValueBean;
import org.vulpe.commons.util.VulpeStringUtil;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Select;
import org.vulpe.controller.commons.VulpeControllerConfig.ControllerType;

import org.jw.mmn.core.model.services.CoreService;
import org.jw.mmn.commons.ApplicationConstants.Core;
import org.jw.mmn.controller.ApplicationBaseController;
import org.jw.mmn.core.model.entity.Member;

@Component("core.MemberController")
@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller(type = ControllerType.MAIN, serviceClass = CoreService.class, select = @Select(pageSize = 5, requireOneFilter = true), detailsConfig = {
		@DetailConfig(name = "addresses", propertyName = "entity.addresses", despiseFields = "address", startNewDetails = 1, newDetails = 1),
		@DetailConfig(name = "phones", propertyName = "entity.phones", despiseFields = "number", startNewDetails = 2, newDetails = 1),
		@DetailConfig(name = "emails", propertyName = "entity.emails", despiseFields = "address", startNewDetails = 2, newDetails = 1),
		@DetailConfig(name = "urgentContacts", propertyName = "entity.urgentContacts", despiseFields = "name", startNewDetails = 2, newDetails = 1) })
public class MemberController extends ApplicationBaseController<Member, Long> {

	public List<ValueBean> ministryTypeList = new ArrayList<ValueBean>();
	public List<ValueBean> simpleMinistryTypeList = new ArrayList<ValueBean>();

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.vulpe.controller.AbstractVulpeBaseSimpleController#postConstruct()
	 */
	protected void postConstruct() {
		super.postConstruct();
		final String className = MinistryType.class.getName();
		if (ministryTypeList.isEmpty()) {
			ministryTypeList.add(new ValueBean(MinistryType.PUBLISHER.toString(), vulpe.controller().text(className
					+ "." + MinistryType.PUBLISHER.toString())));
			ministryTypeList.add(new ValueBean(MinistryType.AUXILIARY_PIONEER.toString(),
					vulpe.controller().text(className + "." + MinistryType.AUXILIARY_PIONEER.toString())));
			ministryTypeList.add(new ValueBean(MinistryType.REGULAR_PIONEER.toString(),
					vulpe.controller().text(className + "." + MinistryType.REGULAR_PIONEER.toString())));
			ministryTypeList.add(new ValueBean(MinistryType.AWAY.toString(), vulpe.controller().text(className
					+ "." + MinistryType.AWAY.toString())));
		}
		if (simpleMinistryTypeList.isEmpty()) {
			simpleMinistryTypeList.add(new ValueBean(MinistryType.PUBLISHER.toString(),
					vulpe.controller().text(className + "." + MinistryType.PUBLISHER.toString())));
			simpleMinistryTypeList.add(new ValueBean(MinistryType.STUDENT.toString(),
					vulpe.controller().text(className + "." + MinistryType.STUDENT.toString())));
			simpleMinistryTypeList.add(new ValueBean(MinistryType.AWAY.toString(),
					vulpe.controller().text(className + "." + MinistryType.AWAY.toString())));
		}
	}

	@Override
	protected void createPostBefore() {
		super.updatePostBefore();
		cleanPrivileges();
	}

	@Override
	protected void createPostAfter() {
		List<Member> members = ever.getSelf(Core.MEMBERS_OF_SELECTED_CONGREGATION);
		if (members == null) {
			members = new ArrayList<Member>();
		}
		members.add(entity);
		ever.put(Core.MEMBERS_OF_SELECTED_CONGREGATION, members);
	}

	@Override
	protected void updatePostBefore() {
		super.updatePostBefore();
		cleanPrivileges();
	}

	@Override
	protected void updatePostAfter() {
		super.updatePostAfter();
		List<Member> members = ever.getSelf(Core.MEMBERS_OF_SELECTED_CONGREGATION);
		if (members == null) {
			members = new ArrayList<Member>();
			members.add(entity);
		} else {
			for (Member member : members) {
				if (member.getId().equals(entity.getId())) {
					member = entity;
					break;
				}
			}
		}
		ever.put(Core.MEMBERS_OF_SELECTED_CONGREGATION, members);
	}

	@Override
	protected void deleteAfter() {
		super.deleteAfter();
		final List<Member> members = vulpe.sessionAttribute(Core.MEMBERS_OF_SELECTED_CONGREGATION);
		for (final Iterator<Member> iterator = members.iterator(); iterator.hasNext();) {
			final Member publicador = iterator.next();
			if (publicador.getId().equals(entity.getId())) {
				iterator.remove();
				break;
			}
		}
		vulpe.sessionAttribute(Core.MEMBERS_OF_SELECTED_CONGREGATION, members);
	}

	private void cleanPrivileges() {
		if (!entity.getBaptized()) {
			entity.setMinistryType(null);
			entity.setResponsibility(null);
			entity.setAdditionalPrivileges(null);
		} else if (entity.getGender().equals(Gender.FEMALE)) {
			entity.setResponsibility(null);
			entity.setAdditionalPrivileges(null);
		}
	}

	@Override
	public List<Member> autocompleteList() {
		final List<Member> members = ever.getSelf(Core.MEMBERS_OF_SELECTED_CONGREGATION);
		final List<Member> filteredMembers = new ArrayList<Member>();
		for (final Member member : members) {
			if (VulpeStringUtil.normalize(member.getName().toLowerCase()).contains(
					entitySelect.getName().toLowerCase())) {
				filteredMembers.add(member);
			}
		}
		return filteredMembers;
	}

	@Override
	protected Member prepareEntity(Operation operation) {
		final Member member = super.prepareEntity(operation);
		member.setCongregation(getCongregation());
		return member;
	}
}
