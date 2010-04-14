package org.vulpe.security.authorization;

import java.util.Collection;
import java.util.List;

import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.ConfigAttributeEditor;
import org.springframework.security.intercept.web.FilterInvocation;
import org.springframework.security.intercept.web.FilterInvocationDefinitionSource;
import org.vulpe.security.authorization.model.services.AuthorizationService;
import org.vulpe.security.model.entity.Role;
import org.vulpe.security.model.entity.SecureResource;

/**
 * This <code>FilterInvocationDefinitionSource</code> implementation uses
 * database and in turn is used for dynamic authorization.
 *
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 * @version 1.0
 * @since 1.0
 *
 */
public class DatabaseDrivenFilterInvocationDefinitionSource implements
		FilterInvocationDefinitionSource {

	private AuthorizationService authorizationService;

	public ConfigAttributeDefinition lookupAttributes(final String url) {
		String newUrl = url;
		// Strip anything after a question mark symbol, as per SEC-161. See also
		// SEC-321
		final int firstQuestionMarkIndex = newUrl.indexOf("?");

		if (firstQuestionMarkIndex != -1) {
			newUrl = newUrl.substring(0, firstQuestionMarkIndex);
		}
		final SecureResource secureObject = authorizationService
				.getSecureObject(newUrl);
		if (secureObject == null) {// if secure object not exist in database
			return null;
		}
		// retrieving roles associated with this secure object
		final List<Role> secureObjectRoles = authorizationService
				.getSecureObjectRoles(secureObject);
		// creating ConfigAttributeDefinition
		if (secureObjectRoles != null && !secureObjectRoles.isEmpty()) {
			final ConfigAttributeEditor cfe = new ConfigAttributeEditor();
			final StringBuffer roles = new StringBuffer();
			for (Role sor : secureObjectRoles) {
				roles.append(sor.getName()).append(",");
			}
			cfe.setAsText(roles.toString().substring(0, roles.length() - 1));
			return (ConfigAttributeDefinition) cfe.getValue();
		}
		return null;

	}

	/*
	 * (non-Javadoc)
	 *
	 * @seeorg.springframework.security.intercept.ObjectDefinitionSource#
	 * getConfigAttributeDefinitions()
	 */
	@SuppressWarnings("unchecked")
	public Collection getConfigAttributeDefinitions() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.springframework.security.intercept.ObjectDefinitionSource#getAttributes
	 * (java.lang.Object)
	 */
	@Override
	public ConfigAttributeDefinition getAttributes(final Object object)
			throws IllegalArgumentException {
		if ((object == null) || !this.supports(object.getClass())) {
			throw new IllegalArgumentException(
					"Object must be a FilterInvocation");
		}

		return this.lookupAttributes(((FilterInvocation) object)
				.getRequestUrl());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.springframework.security.intercept.ObjectDefinitionSource#supports
	 * (java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean supports(final Class clazz) {
		return true;
	}

	public AuthorizationService getAuthorizationService() {
		return authorizationService;
	}

	public void setAuthorizationService(
			final AuthorizationService authorizationService) {
		this.authorizationService = authorizationService;
	}

}
