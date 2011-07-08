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
package org.vulpe.model.services;

import java.util.List;

import org.vulpe.model.entity.VulpeEntity;

/**
 *
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 */
public interface GenericService extends VulpeService {

	/**
	 *
	 * @param <T>
	 * @param entity
	 * @return
	 */
	<T extends VulpeEntity<?>> List<T> getList(final T entity);

	<T extends VulpeEntity<?>> boolean exists(final T entity);
	
	<T extends VulpeEntity<?>> boolean notExistEquals(final T entity);

}
