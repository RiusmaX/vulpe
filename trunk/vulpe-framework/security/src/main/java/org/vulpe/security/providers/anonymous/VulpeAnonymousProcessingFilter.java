package org.vulpe.security.providers.anonymous;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.security.core.userdetails.memory.UserAttribute;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.util.StringUtils;

/**
 *
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 *
 */
public class VulpeAnonymousProcessingFilter extends AnonymousAuthenticationFilter {

	private Properties anonymousConfig;


	public UserAttribute setAsText(final String string)
			throws IllegalArgumentException {
		if (StringUtils.hasText(string)) {
			final String[] tokens = StringUtils
					.commaDelimitedListToStringArray(string);
			final UserAttribute userAttribute = new UserAttribute();

			final List<String> authorities = new ArrayList<String>();

			for (int i = 0; i < tokens.length; i++) {
				final String currentToken = tokens[i].trim();

				if (i == 0) {
					userAttribute.setPassword(currentToken);
				} else {
					if (currentToken.equalsIgnoreCase("enabled")) {
						userAttribute.setEnabled(true);
					} else if (currentToken.equalsIgnoreCase("disabled")) {
						userAttribute.setEnabled(false);
					} else {
						authorities.add(currentToken);
					}
				}
			}
			userAttribute.setAuthoritiesAsString(authorities);

			if (userAttribute.isValid()) {
				return userAttribute;
			}
		}
		return null;
	}

	public Properties getAnonymousConfig() {
		return anonymousConfig;
	}

	public void setAnonymousConfig(final Properties anonymousConfig) {
		this.anonymousConfig = anonymousConfig;
		if (anonymousConfig != null) {
			setUserAttribute(setAsText(anonymousConfig.getProperty(
					"anonymousUser").concat(",").concat(
					anonymousConfig.getProperty("anonymousRole"))));
			setKey(anonymousConfig.getProperty("key"));
		}
	}
}
