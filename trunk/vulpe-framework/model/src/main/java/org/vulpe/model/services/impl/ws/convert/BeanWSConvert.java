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
package org.vulpe.model.services.impl.ws.convert;

import org.vulpe.exception.VulpeSystemException;
import org.vulpe.model.entity.VulpeEntity;
import org.vulpe.model.entity.impl.AbstractVulpeBaseEntityDelegate;

/**
 * 
 * 
 * @author <a href="mailto:fabio.viana@activethread.com.br">Fábio Viana</a>
 */
@SuppressWarnings({ "unchecked" })
public class BeanWSConvert<ENTITY extends VulpeEntity, VODELEGATE extends AbstractVulpeBaseEntityDelegate>
		implements WSConvert<ENTITY, VODELEGATE> {

	public ENTITY toBean(final VODELEGATE wsBean) {
		if (wsBean != null) {
			return (ENTITY) wsBean.getBean();
		}
		return null;
	}

	public VODELEGATE toWSBean(final ENTITY bean) {
		if (bean == null) {
			return null;
		}

		final String className = bean.getClass().getName() + "Delegate";
		try {
			final Class classe = Class.forName(className);
			return (VODELEGATE) classe.getConstructor(bean.getClass()).newInstance(bean);
		} catch (Exception e) {
			throw new VulpeSystemException(e);
		}
	}
}