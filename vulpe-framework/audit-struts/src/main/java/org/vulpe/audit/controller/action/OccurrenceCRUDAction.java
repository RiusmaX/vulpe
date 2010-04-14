package org.vulpe.audit.controller.action;

import static org.vulpe.controller.struts.action.VulpeBaseAction.BaseActionButtons.DELETE;
import static org.vulpe.controller.struts.action.VulpeBaseAction.BaseActionButtons.UPDATE_POST;

import java.util.List;

import org.jfree.util.Log;
import org.vulpe.audit.model.entity.AuditOccurrence;
import org.vulpe.audit.model.services.AuditServices;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Controller.ControllerType;
import org.vulpe.controller.struts.action.VulpeBaseAction;
import org.vulpe.exception.VulpeApplicationException;

@Controller(controllerType = ControllerType.CRUD, serviceClass = AuditServices.class)
@SuppressWarnings("serial")
public class OccurrenceCRUDAction extends VulpeBaseAction<AuditOccurrence, Long> {

	private List<AuditOccurrence> childOccurrences = null;

	@Override
	public String update() {
		final String update = super.update();
		hideButton(new BaseActionButtons[] { UPDATE_POST, DELETE });
		return update;
	}

	@Override
	protected void onUpdate() {
		super.onUpdate();
		try {
			childOccurrences = getService(AuditServices.class)
					.findByParentAuditOccurrence(
							new AuditOccurrence(getEntity().getId()));
		} catch (VulpeApplicationException e) {
			Log.error(e);
		}
	}

	public List<AuditOccurrence> getChildOccurrences() {
		return childOccurrences;
	}

	public void setChildOccurrences(final List<AuditOccurrence> childOccurrences) {
		this.childOccurrences = childOccurrences;
	}

}
