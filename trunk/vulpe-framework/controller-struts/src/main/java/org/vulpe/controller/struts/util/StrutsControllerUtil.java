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
package org.vulpe.controller.struts.util;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.vulpe.commons.VulpeConstants.View;
import org.vulpe.commons.VulpeConstants.View.Logic;
import org.vulpe.commons.cache.VulpeCacheHelper;
import org.vulpe.controller.util.ControllerUtil;

import com.opensymphony.xwork2.ActionContext;

/**
 * Utility class to controller
 * 
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 * 
 */
public class StrutsControllerUtil extends ControllerUtil {

	private static final Logger LOG = Logger.getLogger(StrutsControllerUtil.class);

	/**
	 * Returns instance of StrutsControllerUtil
	 */
	public static StrutsControllerUtil getInstance() {
		final VulpeCacheHelper cache = VulpeCacheHelper.getInstance();
		if (!cache.contains(StrutsControllerUtil.class)) {
			cache.put(ControllerUtil.class, new StrutsControllerUtil());
		}
		return cache.get(ControllerUtil.class);
	}

	/**
	 *
	 */
	protected StrutsControllerUtil() {
		// default constructor
	}

	/**
	 * 
	 * @return
	 */
	public String getCurrentActionName() {
		String base = StringUtils.replace(ActionContext.getContext().getName(), "/", ".");
		if (base.contains(Logic.AJAX)) {
			base = base.replace(Logic.AJAX, "");
		}
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
			method = ActionContext.getContext().getActionInvocation().getProxy().getMethod();
		} catch (Exception e) {
			LOG.error(e);
		}
		if (StringUtils.isEmpty(method)) {
			method = ActionContext.getContext().getName();
			method = method.substring(StringUtils.lastIndexOf(method, '.') + 1);
		}
		return method;
	}

}