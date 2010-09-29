package org.vulpe.portal.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletContextEvent;

import org.apache.log4j.Logger;
import org.vulpe.commons.VulpeServiceLocator;
import org.vulpe.commons.helper.VulpeCacheHelper;
import org.vulpe.commons.util.VulpeValidationUtil;
import org.vulpe.controller.VulpeStartupListener;
import org.vulpe.exception.VulpeApplicationException;
import org.vulpe.model.services.VulpeService;
import org.vulpe.portal.commons.ApplicationConstants.PortalInit;
import org.vulpe.portal.commons.model.entity.Status;
import org.vulpe.portal.commons.model.entity.TextTranslate;
import org.vulpe.portal.core.model.entity.Language;
import org.vulpe.portal.core.model.entity.Portal;
import org.vulpe.portal.core.model.services.CoreService;

public class PortalStartupListerner extends VulpeStartupListener {

	private static final Logger LOG = Logger.getLogger(PortalStartupListerner.class);

	@Override
	public void contextInitialized(ServletContextEvent evt) {
		final CoreService coreService = getService(CoreService.class);
		try {
			final Language language = new Language();
			final List<Language> languageList = coreService.readLanguage(language);
			if (VulpeValidationUtil.isEmpty(languageList)) {
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
			LOG.error(e);
		}
		super.contextInitialized(evt);
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
}
