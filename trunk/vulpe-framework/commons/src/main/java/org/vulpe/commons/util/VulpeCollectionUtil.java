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
package org.vulpe.commons.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.apache.log4j.Logger;

/**
 * Vulpe Collection Utility class.
 * 
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 * @version 1.0
 * @since 1.0
 */
public class VulpeCollectionUtil {

	protected static final Logger LOG = Logger.getLogger(VulpeCollectionUtil.class);

	public static <C extends Collection<?>> boolean exists(final C collection,
			final String property, final Object value) {
		for (final Object object : collection) {
			final Object objectValue = VulpeReflectUtil.getFieldValue(object, property);
			if (objectValue.equals(value)) {
				return true;
			}
		}
		return false;
	}

	public static <C extends Collection<?>> Collection<?> check(final C one, final C two,
			final String property) {
		final Collection<Object> collection = new ArrayList<Object>();
		for (final Object cOne : one) {
			final Object valueOne = VulpeReflectUtil.getFieldValue(cOne, property);
			if (!exists(two, property, valueOne)) {
				collection.add(cOne);
			}
		}
		return collection;
	}

	public static Collection<?> asCollection(final String value) {
		if (value.startsWith("[") && value.endsWith("]")) {
			String newValue = value.substring(1, value.length() - 1);
			final String[] parts = newValue.split(",");
			return Arrays.asList(parts);
		}
		return null;
	}
}