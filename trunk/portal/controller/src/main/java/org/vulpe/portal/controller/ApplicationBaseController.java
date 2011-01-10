package org.vulpe.portal.controller;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.vulpe.commons.VulpeConstants.Controller.URI;
import org.vulpe.commons.util.VulpeReflectUtil;
import org.vulpe.commons.util.VulpeValidationUtil;
import org.vulpe.controller.struts.VulpeStrutsController;
import org.vulpe.model.entity.VulpeEntity;
import org.vulpe.portal.commons.ApplicationConstants.Core;
import org.vulpe.portal.commons.model.entity.Status;
import org.vulpe.portal.commons.model.entity.TextTranslate;
import org.vulpe.portal.commons.model.entity.TextTranslateLanguage;
import org.vulpe.portal.core.controller.PortalController;
import org.vulpe.portal.core.model.entity.BasePortal;
import org.vulpe.portal.core.model.entity.Portal;

@SuppressWarnings( { "serial", "unchecked" })
public class ApplicationBaseController<ENTITY extends VulpeEntity<ID>, ID extends Serializable & Comparable>
		extends VulpeStrutsController<ENTITY, ID> {

	protected static final Logger LOG = Logger.getLogger(ApplicationBaseController.class);

	@Override
	protected void postConstruct() {
		super.postConstruct();
		final List<Portal> portalList = getCachedClasses().getSelf(Portal.class.getSimpleName());
		if (portalList != null) {
			for (Portal portal : portalList) {
				if (portal.getStatus().equals(Status.ACTIVE)) {
					setSessionAttribute(Core.VULPE_PORTAL, portal);
				}
			}
		}
	}

	@Override
	protected ENTITY prepareEntity(Operation operation) {
		final ENTITY entity = super.prepareEntity(operation);
		final List<Field> fields = VulpeReflectUtil.getFields(entity.getClass());
		for (final Field field : fields) {
			if (field.getType().getName().equals(TextTranslate.class.getName())) {
				final TextTranslate textTranslate = VulpeReflectUtil.getFieldValue(
						entity, field.getName());
				if (textTranslate != null
						&& VulpeValidationUtil.isNotEmpty(textTranslate.getLanguages())) {
					for (final Iterator<TextTranslateLanguage> iterator = textTranslate
							.getLanguages().iterator(); iterator.hasNext();) {
						final TextTranslateLanguage textTranslateLanguage = iterator.next();
						if (StringUtils.isEmpty(textTranslateLanguage.getText())) {
							iterator.remove();
						}
					}
				}
			}
		}
		if (entity instanceof BasePortal) {
			final BasePortal portal = (BasePortal) entity;
			if (operation != null) {
				if (operation.equals(Operation.CREATE_POST)) {
					portal.setDate(new Date());
					portal.setStatus(Status.ACTIVE);
				}
				if (operation.equals(Operation.UPDATE_POST)) {
					if (portal.getDate() == null) {
						portal.setDate(new Date());
					}
				}
			}
		}
		return entity;
	}

	@Override
	public String select() {
		final String redirect = validateSelectedConfiguration();
		if (StringUtils.isNotEmpty(redirect)) {
			return redirect;
		}
		return StringUtils.isNotEmpty(redirect) ? redirect : super.select();
	}

	@Override
	public String create() {
		final String redirect = validateSelectedConfiguration();
		return StringUtils.isNotEmpty(redirect) ? redirect : super.create();
	}

	@Override
	public String update() {
		final String redirect = validateSelectedConfiguration();
		return StringUtils.isNotEmpty(redirect) ? redirect : super.update();
	}

	@Override
	public String tabular() {
		final String redirect = validateSelectedConfiguration();
		return StringUtils.isNotEmpty(redirect) ? redirect : super.tabular();
	}

	private String validateSelectedConfiguration() {
		if (getSessionAttribute(Core.VULPE_PORTAL) == null
				&& !this.getClass().getName().equals(PortalController.class.getName())) {
			if (getRequest().getRequestURI().endsWith(URI.AJAX)) {
				setAjax(true);
			}
			return redirectTo("/core/Portal/select", isAjax());
		}
		return null;
	}
}
