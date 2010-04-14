package org.vulpe.audit.controller.action;

import static org.vulpe.controller.struts.action.VulpeBaseAction.BaseActionButtons.CREATE;

import org.vulpe.audit.model.entity.AuditOccurrence;
import org.vulpe.audit.model.services.AuditServices;
import org.vulpe.common.audit.AuditOccurrenceType;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Controller.ControllerType;
import org.vulpe.controller.struts.action.VulpeBaseAction;


@Controller(controllerType = ControllerType.SELECT, serviceClass = AuditServices.class, pageSize = 5)
@SuppressWarnings("serial")
public class OccurrenceSelectAction extends VulpeBaseAction<AuditOccurrence, Long> {

	@Override
	public String prepare() {
		final String prepare = super.prepare();
		hideButton(CREATE);
		return prepare;
	}

	@Override
	public String read() {
		final String read = super.read();
		hideButton(CREATE);
		return read;
	}

	public AuditOccurrenceType[] getListOccurrenceType() {
		return AuditOccurrenceType.values();
	}

}
