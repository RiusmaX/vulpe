package org.vulpe.portal.core.controller;

import java.util.Date;

import org.vulpe.portal.commons.model.entity.Status;
import org.vulpe.portal.controller.ApplicationBaseController;
import org.vulpe.portal.core.model.entity.BasePortal;

/**
 * Controller implementation of Portal
 */
@SuppressWarnings("serial")
public class BasePortalController<ENTITY_PORTAL extends BasePortal> extends
		ApplicationBaseController<ENTITY_PORTAL, java.lang.Long> {

	@Override
	protected ENTITY_PORTAL prepareEntity(Operation operation) {
		final ENTITY_PORTAL portal = super.prepareEntity(operation);
		if (getOperation().equals(Operation.CREATE_POST)) {
			getEntity().setDate(new Date());
			portal.setStatus(Status.ACTIVE);
		}
		if (getOperation().equals(Operation.UPDATE_POST)) {
			if (getEntity().getDate() == null) {
				getEntity().setDate(new Date());
			}
		}
		return portal;
	}

}
