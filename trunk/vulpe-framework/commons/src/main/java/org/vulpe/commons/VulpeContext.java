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
package org.vulpe.commons;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.vulpe.commons.factory.AbstractVulpeBeanFactory;

@Component
public class VulpeContext {

	public static final String LOCALE = "locale";
	public static final String HTTP_REQUEST = "httpRequest";
	public static final String HTTP_RESPONSE = "httpSession";
	public static final String HTTP_SESSION = "httpSession";
	public static final String CURRENT_CONTROLLER = "currentController";

	private Map<String, Object> map = new HashMap<String, Object>();

	public static VulpeContext getInstance() {
		return AbstractVulpeBeanFactory.getInstance().getBean(VulpeConstants.CONTEXT);
	}

	public void setLocale(final Locale locale) {
		map.put(LOCALE, locale);
	}

	public Locale getLocale() {
		return (Locale) map.get(LOCALE);
	}

	public HttpServletRequest getRequest() {
		return (HttpServletRequest) map.get(HTTP_REQUEST);
	}

	public void setRequest(final HttpServletRequest request) {
		map.put(HTTP_REQUEST, request);
	}

	public HttpServletResponse getResponse() {
		return (HttpServletResponse) map.get(HTTP_RESPONSE);
	}

	public void setResponse(final HttpServletResponse response) {
		map.put(HTTP_RESPONSE, response);
	}

	public HttpSession getSession() {
		return (HttpSession) map.get(HTTP_SESSION);
	}

	public void setSession(final HttpSession session) {
		map.put(HTTP_SESSION, session);
	}

	public String getCurrentController() {
		return (String) map.get(CURRENT_CONTROLLER);
	}

	public void setCurrentController(final String currentController) {
		map.put(CURRENT_CONTROLLER, currentController);
	}

	public Object get(final String key) {
		return map.get(key);
	}

	public void set(final String key, final Object value) {
		map.put(key, value);
	}

}
