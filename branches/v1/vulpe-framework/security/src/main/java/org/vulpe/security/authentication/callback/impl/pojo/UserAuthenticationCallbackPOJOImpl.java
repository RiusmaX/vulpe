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
package org.vulpe.security.authentication.callback.impl.pojo;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.vulpe.security.authentication.callback.AfterUserAuthenticationCallback;
import org.vulpe.security.authentication.callback.UserAuthenticationCallback;
import org.vulpe.security.commons.VulpeSecurityUtil;

@Service("UserAuthenticationCallback")
public class UserAuthenticationCallbackPOJOImpl extends VulpeSecurityUtil implements UserAuthenticationCallback {

	@Override
	public boolean isAuthenticated() {
		final Authentication authentication = getAuthentication();
		final boolean autenticated = authentication.isAuthenticated()
				&& !(authentication instanceof AnonymousAuthenticationToken);
		if (autenticated) {
			AfterUserAuthenticationCallback afterUserAuthentication = getBean(AfterUserAuthenticationCallback.class);
			if (afterUserAuthentication != null) {
				afterUserAuthentication.execute();
			}
		}
		return autenticated;
	}

}
