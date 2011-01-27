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
package org.vulpe.controller.vraptor;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.vulpe.controller.AbstractVulpeBaseSimpleController;
import org.vulpe.controller.commons.VulpeControllerConfig;
import org.vulpe.controller.commons.VulpeControllerConfig.ControllerType;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.core.RequestInfo;

/**
 * Controller base for VRaptor
 *
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings( { "serial" })
public class VulpeVRaptorSimpleController extends AbstractVulpeBaseSimpleController {

	protected static final Logger LOG = Logger.getLogger(VulpeVRaptorSimpleController.class);

	@Autowired
	protected RequestInfo requestInfo;
	@Autowired
	protected Result result;
	@Autowired
	protected Validator validator;

	/*
	 * (non-Javadoc)
	 *
	 * @see org.vulpe.controller.VulpeSimpleController#getActionConfig()
	 */
	public VulpeControllerConfig getControllerConfig() {
		return controllerUtil.getControllerConfig(this);
	}

	public void addActionMessage(final String message) {
		result.include("notice", message);
	}

	public void addActionError(final String message) {
		result.include("notice", message);
	}

	public void addActionError(final String key, final Object... args) {
		result.include("notice", getText(key));
	}

	@Path("/")
	public void vraptor() {
		if (getControllerType().equals(ControllerType.FRONTEND)) {
			frontend();
		} else if (getControllerType().equals(ControllerType.BACKEND)) {
			backend();
		}
	}

}