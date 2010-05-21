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
package org.vulpe.common;

import java.util.Collection;

import org.apache.commons.lang.ArrayUtils;
import org.vulpe.common.cache.VulpeCacheHelper;
import org.vulpe.model.entity.VulpeBaseEntity;

/**
 * Utility class to validation.
 * 
 */
public class ValidationUtil {

	/**
	 * Returns instance of ValidationUtil
	 */
	public static ValidationUtil getInstance() {
		final VulpeCacheHelper cache = VulpeCacheHelper.getInstance();
		if (!cache.contains(ValidationUtil.class)) {
			cache.put(ValidationUtil.class, new ValidationUtil());
		}
		return cache.get(ValidationUtil.class);
	}

	protected ValidationUtil() {
		//
	}

	/**
	 * Validate if value is not empty
	 * 
	 * @param value
	 * @return returns true if is not empty
	 */
	@SuppressWarnings("unchecked")
	public boolean isNotEmpty(final Object value) {
		if (value == null) {
			return false;
		}

		if (value instanceof String) {
			return !value.toString().trim().equals("");
		}

		if (value instanceof Collection) {
			return !((Collection) value).isEmpty();
		}

		if (value instanceof VulpeBaseEntity<?>) {
			return ((VulpeBaseEntity<?>) value).getId() != null;
		}

		if (value.getClass().isArray()) {
			return ArrayUtils.isNotEmpty((Object[]) value);
		}

		return true;
	}

	/**
	 * Validate if value is empty.
	 * 
	 * @param value
	 * @return returns true if is empty
	 */
	public boolean isEmpty(final Object value) {
		return !isNotEmpty(value);
	}
}