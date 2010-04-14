package org.vulpe.security.providers.anonymous;

import java.util.Properties;

import org.springframework.security.providers.anonymous.AnonymousAuthenticationProvider;

/**
 *
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 *
 */
public class VulpeAnonymousAuthenticationProvider extends
		AnonymousAuthenticationProvider {

	private Properties anonymousConfig;

	public Properties getAnonymousConfig() {
		return anonymousConfig;
	}

	public void setAnonymousConfig(final Properties anonymousConfig) {
		this.anonymousConfig = anonymousConfig;
		if (anonymousConfig != null) {
			setKey(anonymousConfig.getProperty("key"));
		}
	}

}
