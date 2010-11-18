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
package org.vulpe.model.entity.impl;

import org.apache.log4j.Logger;
import org.vulpe.model.entity.VulpeSimpleEntity;

/**
 *
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 *
 */
@SuppressWarnings("serial")
public class VulpeBaseSimpleEntity implements VulpeSimpleEntity {

	private static final Logger LOG = Logger.getLogger(VulpeBaseSimpleEntity.class);

	@Override
	public int compareTo(VulpeSimpleEntity entity) {
		return 0;
	}

	@Override
	public VulpeSimpleEntity clone() {
		VulpeSimpleEntity entity = null;
		try {
			entity = (VulpeSimpleEntity) super.clone();
		} catch (CloneNotSupportedException e) {
			LOG.error(e);
		}
		return entity;
	}
}
