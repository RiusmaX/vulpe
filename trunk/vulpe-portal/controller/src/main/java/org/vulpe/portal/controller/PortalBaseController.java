package org.vulpe.portal.controller;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.vulpe.commons.VulpeConstants.Controller.URI;
import org.vulpe.commons.util.VulpeReflectUtil;
import org.vulpe.commons.util.VulpeValidationUtil;
import org.vulpe.controller.annotations.ExecuteAlways;
import org.vulpe.controller.commons.VulpeControllerConfig.ControllerType;
import org.vulpe.controller.struts.VulpeStrutsController;
import org.vulpe.exception.VulpeApplicationException;
import org.vulpe.model.entity.VulpeEntity;
import org.vulpe.portal.commons.ApplicationConstants.Core;
import org.vulpe.portal.commons.model.entity.Status;
import org.vulpe.portal.commons.model.entity.TextTranslate;
import org.vulpe.portal.commons.model.entity.TextTranslateLanguage;
import org.vulpe.portal.core.model.entity.BasePortal;
import org.vulpe.portal.core.model.entity.Content;
import org.vulpe.portal.core.model.entity.Download;
import org.vulpe.portal.core.model.entity.Link;
import org.vulpe.portal.core.model.entity.Menu;
import org.vulpe.portal.core.model.entity.Portal;
import org.vulpe.portal.core.model.entity.Social;
import org.vulpe.portal.core.model.services.CoreService;

@SuppressWarnings( { "serial", "unchecked" })
public class PortalBaseController<ENTITY extends VulpeEntity<ID>, ID extends Serializable & Comparable<?>>
		extends VulpeStrutsController<ENTITY, ID> {

	@Override
	protected void postConstruct() {
		super.postConstruct();
		final List<Portal> portalList = vulpe.cache().classes().getAuto(
				Portal.class.getSimpleName());
		if (portalList != null) {
			for (final Portal portal : portalList) {
				if (portal.getStatus().equals(Status.ACTIVE)) {
					ever.put(Core.VULPE_PORTAL, portal);
				}
			}
		}
	}

	protected void load() {
		if (vulpe.controller().type().equals(ControllerType.FRONTEND)
				|| vulpe.controller().type().equals(ControllerType.BACKEND)) {
			try {
				final List<Menu> menus = vulpe.service(CoreService.class).readMenu(
						new Menu(Status.ACTIVE));
				ever.put(Core.VULPE_PORTAL_MENUS, menus);
				final List<Download> downloads = vulpe.service(CoreService.class).readDownload(
						new Download(Status.ACTIVE));
				sort(downloads);
				ever.put(Core.VULPE_PORTAL_DOWNLOADS, downloads);
				final List<Link> links = vulpe.service(CoreService.class).readLink(
						new Link(Status.ACTIVE));
				sort(links);
				ever.put(Core.VULPE_PORTAL_LINKS, links);
				final List<Social> social = vulpe.service(CoreService.class).readSocial(
						new Social(Status.ACTIVE));
				sort(social);
				ever.put(Core.VULPE_PORTAL_SOCIAL, social);
				if (vulpe.controller().type().equals(ControllerType.BACKEND)) {
					final List<Content> contents = vulpe.service(CoreService.class).readContent(
							new Content(Status.ACTIVE));
					sort(contents);
					ever.put(Core.VULPE_PORTAL_CONTENTS, contents);
				}
			} catch (VulpeApplicationException e) {
				LOG.error(e.getMessage());
			}
		}
	}

	@Override
	protected ENTITY prepareEntity(Operation operation) {
		final ENTITY entity = super.prepareEntity(operation);
		final List<Field> fields = VulpeReflectUtil.getFields(entity.getClass());
		for (final Field field : fields) {
			if (field.getType().getName().equals(TextTranslate.class.getName())) {
				final TextTranslate textTranslate = VulpeReflectUtil.getFieldValue(entity, field
						.getName());
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
		if (BasePortal.class.isAssignableFrom(entity.getClass())) {
			if (operation != null) {
				if (operation.equals(Operation.CREATE_POST)) {
					VulpeReflectUtil.setFieldValue(entity, "date", new Date());
					VulpeReflectUtil.setFieldValue(entity, "status", Status.ACTIVE);
				}
				if (operation.equals(Operation.UPDATE_POST)) {
					if (VulpeReflectUtil.getFieldValue(entity, "date") == null) {
						VulpeReflectUtil.setFieldValue(entity, "date", new Date());
					}
				}
			}
		}
		return entity;
	}

	@ExecuteAlways
	public void validateSelectedConfiguration() {
		if (!vulpe.controller().currentName().contains("frontend/")
				&& !vulpe.controller().currentName().contains("core/Portal")
				&& (ever.get(Core.VULPE_PORTAL) == null || !ever
						.<Portal> getAuto(Core.VULPE_PORTAL).getStatus().equals(Status.ACTIVE))) {
			if (getRequest().getRequestURI().endsWith(URI.AJAX)) {
				vulpe.controller().ajax(true);
			}
			vulpe.controller().redirectTo("/core/Portal/select");
		}
	}

	public CoreService getCoreService() {
		return vulpe.service(CoreService.class);
	}

	protected void sort(final List<? extends BasePortal> list) {
		if (VulpeValidationUtil.isNotEmpty(list)) {
			Collections.sort(list, new Comparator<BasePortal>() {
				@Override
				public int compare(BasePortal o1, BasePortal o2) {
					return o2.getDate().compareTo(o1.getDate());
				}
			});
		}
	}
}
