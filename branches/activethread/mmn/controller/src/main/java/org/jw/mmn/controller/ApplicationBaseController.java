package org.jw.mmn.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jw.mmn.commons.ApplicationConstants.Core;
import org.jw.mmn.core.controller.CongregationController;
import org.jw.mmn.core.model.entity.Congregation;
import org.jw.mmn.core.model.entity.Member;
import org.jw.mmn.core.model.entity.Year;
import org.jw.mmn.core.model.services.CoreService;
import org.jw.mmn.ministry.model.services.MinistryService;
import org.vulpe.commons.VulpeConstants.View;
import org.vulpe.commons.VulpeConstants.Controller.URI;
import org.vulpe.commons.util.VulpeValidationUtil;
import org.vulpe.controller.annotations.ExecuteAlways;
import org.vulpe.controller.struts.VulpeStrutsController;
import org.vulpe.exception.VulpeApplicationException;
import org.vulpe.model.entity.VulpeEntity;
import org.vulpe.security.model.entity.User;

@SuppressWarnings( { "serial", "unchecked" })
public class ApplicationBaseController<ENTITY extends VulpeEntity<ID>, ID extends Serializable & Comparable>
		extends VulpeStrutsController<ENTITY, ID> {

	@Override
	protected void postConstruct() {
		super.postConstruct();
		if (!ever.containsKey("years")) {
			final List<Year> years = new ArrayList<Year>();
			for (int i = 0; i <= 200; ++i) {
				years.add(new Year(1990 + i));
			}
			ever.put("years", years);
		}

	}

	public Congregation getCongregation() {
		return ever.<Congregation> getAuto(Core.SELECTED_CONGREGATION);
	}

	@ExecuteAlways
	public void validateSelectedCongregation() {
		if (!vulpe.controller().currentName().contains("frontend/Index")
				&& !vulpe.controller().currentName().contains("backend/Index")
				&& !ever.containsKey(Core.SELECTED_CONGREGATION)
				&& !this.getClass().equals(CongregationController.class)) {
			if (getRequest().getRequestURI().endsWith(URI.AJAX)) {
				vulpe.controller().ajax(true);
			}
			final String currentLayout = ever.getAuto(View.CURRENT_LAYOUT);
			final String url = "FRONTEND".equals(currentLayout) ? "/frontend/Index"
					: "/backend/Index";
			vulpe.controller().redirectTo(url, vulpe.controller().ajax());
		} else if ((vulpe.controller().currentName().contains("frontend/Index") || vulpe
				.controller().currentName().contains("backend/Index"))
				&& ever.containsKey(Core.SELECTED_CONGREGATION)) {
//			vulpe.controller().redirectTo("/ministry/MemberPersonalReport/update",
//					vulpe.controller().ajax());
		}
	}
	
	public CoreService coreService() {
		return vulpe.service(CoreService.class);
	}
	
	public MinistryService ministryService() {
		return vulpe.service(MinistryService.class);
	}
	
	protected Member retrieveMember() {
		Member member = new Member(new User(vulpe.securityContext().getUser().getId()));
		try {
			final List<Member> members = vulpe.service(CoreService.class).readMember(member);
			if (VulpeValidationUtil.isNotEmpty(members)) {
				member = members.get(0);
			} else {
				member = null;
			}
		} catch (VulpeApplicationException e) {
			LOG.error(e);
		}
		return member;
	}

}
