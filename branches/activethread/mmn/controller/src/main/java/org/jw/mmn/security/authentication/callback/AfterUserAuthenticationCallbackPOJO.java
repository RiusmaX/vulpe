package org.jw.mmn.security.authentication.callback;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.jw.mmn.commons.ApplicationConstants.Core;
import org.jw.mmn.commons.ApplicationConstants.Publications;
import org.jw.mmn.core.model.entity.Congregation;
import org.jw.mmn.core.model.entity.CongregationUser;
import org.jw.mmn.core.model.entity.Group;
import org.jw.mmn.core.model.entity.Member;
import org.jw.mmn.core.model.services.CoreService;
import org.jw.mmn.publications.model.entity.Publication;
import org.jw.mmn.publications.model.services.PublicationsService;
import org.springframework.stereotype.Component;
import org.vulpe.commons.VulpeConstants.Security;
import org.vulpe.commons.util.VulpeHashMap;
import org.vulpe.commons.util.VulpeValidationUtil;
import org.vulpe.exception.VulpeApplicationException;
import org.vulpe.security.authentication.callback.AfterUserAuthenticationCallback;
import org.vulpe.security.commons.VulpeSecurityStrutsCallbackUtil;
import org.vulpe.security.context.VulpeSecurityContext;
import org.vulpe.security.model.entity.User;

@Component(Security.AFTER_USER_AUTHENTICATION_CALLBACK)
public class AfterUserAuthenticationCallbackPOJO extends VulpeSecurityStrutsCallbackUtil implements
		AfterUserAuthenticationCallback {

	protected static final Logger LOG = Logger.getLogger(AfterUserAuthenticationCallbackPOJO.class);

	@Override
	public void execute() {
		final VulpeSecurityContext securityContext = getBean(VulpeSecurityContext.class);
		if (securityContext != null) {
			final Long userId = securityContext.getUser().getId();
			try {
				final CongregationUser congregationUser = new CongregationUser();
				final User usser = new User();
				usser.setId(userId);
				congregationUser.setUser(usser);
				final List<CongregationUser> congregationUsers = getService(CoreService.class).readCongregationUser(
						congregationUser);
				final List<Congregation> congregations = new ArrayList<Congregation>();
				for (CongregationUser congregationUser2 : congregationUsers) {
					congregations.add(congregationUser2.getCongregation());
				}
				if (congregations.size() == 1) {
					final Congregation congregation = congregations.get(0);
					getEver().put(Core.SELECTED_CONGREGATION, congregation);
					final Group group = new Group();
					group.setCongregation(congregation);
					final List<Group> groups = getService(CoreService.class).readGroup(group);
					final List<Member> members = new ArrayList<Member>();
					for (Group group2 : groups) {
						final Member member = new Member();
						member.setGroup(group2);
						final List<Member> membersByGroup = getService(CoreService.class).readMember(member);
						if (VulpeValidationUtil.isNotEmpty(membersByGroup)) {
							members.addAll(membersByGroup);
						}
					}
					getEver().put(Core.MEMBERS_OF_SELECTED_CONGREGATION, members);
					final List<VulpeHashMap<String, Object>> values = new ArrayList<VulpeHashMap<String, Object>>();
					for (Member member : members) {
						final VulpeHashMap<String, Object> map = new VulpeHashMap<String, Object>();
						map.put("id", member.getId());
						map.put("value", member.getName());
						map.put("ministryType", member.getMinistryType().name());
						values.add(map);
					}
					getEver().put(Core.MEMBERS_AUTOCOMPLETE_VALUE_LIST, new JSONArray(values));
					getEver().put(Core.GROUPS_OF_SELECTED_CONGREGATION, groups);
				}
				getEver().put(Core.CONGREGATIONS_OF_USER, congregations);
				final List<Publication> publications = getService(PublicationsService.class).readPublication(
						new Publication());
				final List<VulpeHashMap<String, Object>> values = new ArrayList<VulpeHashMap<String, Object>>();
				for (Publication publication : publications) {
					final VulpeHashMap<String, Object> map = new VulpeHashMap<String, Object>();
					map.put("id", publication.getId());
					map.put("value", publication.getName());
					values.add(map);
				}
				getEver().put(Publications.PUBLICATIONS_AUTOCOMPLETE_VALUE_LIST, new JSONArray(values));
			} catch (VulpeApplicationException e) {
				LOG.error(e);
			}
		}
	}

}
