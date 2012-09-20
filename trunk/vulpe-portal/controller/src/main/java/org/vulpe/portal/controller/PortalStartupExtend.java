package org.vulpe.portal.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletContextEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;
import org.vulpe.commons.VulpeConstants.Context;
import org.vulpe.commons.VulpeServiceLocator;
import org.vulpe.commons.helper.VulpeCacheHelper;
import org.vulpe.commons.util.VulpeValidationUtil;
import org.vulpe.controller.listener.VulpeStartupExtend;
import org.vulpe.exception.VulpeApplicationException;
import org.vulpe.model.services.VulpeService;
import org.vulpe.portal.commons.ApplicationConstants.PortalInit;
import org.vulpe.portal.commons.model.entity.Status;
import org.vulpe.portal.commons.model.entity.TextTranslate;
import org.vulpe.portal.core.model.entity.Language;
import org.vulpe.portal.core.model.entity.Portal;
import org.vulpe.portal.core.model.services.CoreService;

@Component(Context.STARTUP_EXTEND)
public class PortalStartupExtend implements VulpeStartupExtend {

	private static final Logger LOG = LoggerFactory.getLogger(PortalStartupExtend.class);

	@Override
	public void contextInitialized(ServletContextEvent evt) {
		final CoreService coreService = getService(CoreService.class);
		try {
			final Language language = new Language();
			final List<Language> languageList = coreService.readLanguage(language);
			if (VulpeValidationUtil.isEmpty(languageList)) {
				language.setDate(new Date());
				language.setLocaleCode(PortalInit.LANGUAGE_CODE);
				language.setName(PortalInit.LANGUAGE_NAME);
				language.setDefaultLanguage(true);
				language.setStatus(Status.ACTIVE);
				coreService.createLanguage(language);
			}
			final Portal portal = new Portal();
			final List<Portal> portalList = coreService.readPortal(portal);
			if (VulpeValidationUtil.isEmpty(portalList)) {
				portal.setDate(new Date());
				TextTranslate name = new TextTranslate(language, PortalInit.NAME);
				portal.setName(name);
				TextTranslate title = new TextTranslate(language, PortalInit.TITLE);
				portal.setTitle(title);
				TextTranslate description = new TextTranslate(language, PortalInit.DESCRIPTION);
				portal.setDescription(description);
				TextTranslate copyright = new TextTranslate(language, PortalInit.COPYRIGHT);
				portal.setCopyright(copyright);
				TextTranslate offlineMessage = new TextTranslate(language,
						PortalInit.OFFLINE_MESSAGE);
				portal.setOfflineMessage(offlineMessage);
				portal.setStatus(Status.ACTIVE);
				coreService.createPortal(portal);
			} else {
				VulpeCacheHelper.getInstance().put(Portal.class.getSimpleName(), portalList);
			}
		} catch (VulpeApplicationException e) {
			LOG.error(e.getMessage());
		}
	}

	/**
	 * Method find specific service returns POJO or EJB implementation.
	 *
	 * @param serviceClass
	 * @return Service Implementation.
	 * @since 1.0
	 * @see VulpeService
	 */
	public <T extends VulpeService> T getService(final Class<T> serviceClass) {
		return VulpeServiceLocator.getInstance().getService(serviceClass);
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {

	}
}
