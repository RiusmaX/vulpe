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
package org.vulpe.controller.vraptor.util;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.vulpe.commons.VulpeConstants.View;
import org.vulpe.commons.VulpeConstants.View.Logic;
import org.vulpe.commons.cache.VulpeCacheHelper;
import org.vulpe.controller.util.ControllerUtil;

import br.com.caelum.vraptor.core.RequestInfo;

/**
 * Utility class to controller
 * 
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 * 
 */
public class VRaptorControllerUtil extends ControllerUtil {

	private static final Logger LOG = Logger.getLogger(VRaptorControllerUtil.class);

	private RequestInfo requestInfo;

	/**
	 * Returns instance of VRaptorControllerUtil
	 */
	public static VRaptorControllerUtil getInstance(RequestInfo requestInfo) {
		final VulpeCacheHelper cache = VulpeCacheHelper.getInstance();
		VRaptorControllerUtil controllerUtil = cache.get(ControllerUtil.class);
		if (controllerUtil == null) {
			controllerUtil = new VRaptorControllerUtil();
			cache.put(ControllerUtil.class, controllerUtil);
		}
		controllerUtil.setRequestInfo(requestInfo);
		return controllerUtil;
	}

	/**
	 *
	 */
	protected VRaptorControllerUtil() {
		// default constructor
	}

	/**
	 * 
	 * @return
	 */
	public String getCurrentActionName() {
		final String uri = requestInfo.getRequestedUri().startsWith("/") ? requestInfo
				.getRequestedUri().substring(1) : requestInfo.getRequestedUri();
		String base = StringUtils.replace(uri, "/", ".");
		if (base.contains(Logic.AJAX)) {
			base = base.replace(Logic.AJAX, "");
		}
		final String[] parts = uri.split("/");
		requestInfo.getRequest().setAttribute("vulpeModuleURI", parts[0] + "/");
		return (base.contains(Logic.BACKEND) || base.contains(Logic.FRONTEND) || base
				.contains(View.AUTHENTICATOR)) ? base : base.substring(0, StringUtils.lastIndexOf(
				base, '.'));
	}

	/**
	 * 
	 * @return
	 */
	public String getCurrentMethod() {
		String method = null;
		try {
			method = "";// ActionContext.getContext().getActionInvocation().getProxy().getMethod();
		} catch (Exception e) {
			LOG.error(e);
		}
		if (StringUtils.isEmpty(method)) {
			// method = ActionContext.getContext().getName();
			method = method.substring(StringUtils.lastIndexOf(method, '.') + 1);
		}
		return method;
	}

	public void setRequestInfo(RequestInfo requestInfo) {
		this.requestInfo = requestInfo;
	}

	public RequestInfo getRequestInfo() {
		return requestInfo;
	}
}