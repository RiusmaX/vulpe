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
package org.vulpe.security.vraptor.commons;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.vulpe.commons.factory.AbstractVulpeBeanFactory;
import org.vulpe.security.commons.VulpeSecurityUtil;

import br.com.caelum.vraptor.core.RequestInfo;

public class VulpeSecurityVRaptorCallbackUtil extends VulpeSecurityUtil {

	/**
	 * Returns current HTTP Session.
	 *
	 * @return Http Session
	 */
	public HttpSession getSession() {
		return getRequestInfo().getRequest().getSession();
	}

	/**
	 * Returns current HTTP Request.
	 *
	 * @return Http Servlet Request
	 */
	public HttpServletRequest getRequest() {
		return getRequestInfo().getRequest();
	}

	/**
	 * Returns current HTTP Response.
	 *
	 * @return Http Servlet Reponse
	 */
	public HttpServletResponse getResponse() {
		return getRequestInfo().getResponse();
	}

	private static RequestInfo getRequestInfo() {
		final RequestInfo requestInfo = AbstractVulpeBeanFactory.getInstance().getBean(
				"requestInfo");
		return requestInfo;
	}

}
