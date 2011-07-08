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
package org.vulpe.security.authentication;

import java.util.Properties;

import org.springframework.security.authentication.AnonymousAuthenticationProvider;

/**
 * 
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 * 
 */
public class VulpeAnonymousAuthenticationProvider extends AnonymousAuthenticationProvider {

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
