/**
 * Vulpe Framework - Copyright (c) Active Thread
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.vulpe.security.web.authentication;

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

	public UserAttribute setAsText(final String string) throws IllegalArgumentException {
		if (StringUtils.hasText(string)) {
			final String[] tokens = StringUtils.commaDelimitedListToStringArray(string);
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
			setUserAttribute(setAsText(anonymousConfig.getProperty("anonymousUser").concat(",")
					.concat(anonymousConfig.getProperty("anonymousRole"))));
			setKey(anonymousConfig.getProperty("key"));
		}
	}
}
