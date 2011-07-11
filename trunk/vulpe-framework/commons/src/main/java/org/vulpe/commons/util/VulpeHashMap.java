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

import java.util.HashMap;

/**
 * 
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings("serial")
public class VulpeHashMap<KEY extends Object, VALUE extends Object> extends HashMap<KEY, VALUE> {

	/**
	 * Retrieves the value referenced by key making automatic conversion.
	 * 
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getSelf(final KEY key) {
		return (T) get(key);
	}

	public boolean getBoolean(final KEY key) {
		Boolean value = Boolean.FALSE;
		Object object = get(key);
		if (object != null) {
			if (object instanceof Boolean) {
				value = (Boolean) object;
			} else if (object instanceof String) {
				String string = (String) object;
				if (string.equals("true")) {
					value = Boolean.TRUE;
				} else {
					value = Boolean.FALSE;
				}
			}
		}
		return value;
	}

	@SuppressWarnings("unchecked")
	public <T> T getSelf(final KEY key, final T defaultValue) {
		return containsKey(key) && VulpeValidationUtil.isNotEmpty(get(key)) ? (T) getSelf(key)
				: defaultValue;
	}

	/**
	 * Remove and returns the value referenced by key making automatic
	 * conversion.
	 * 
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T removeSelf(final KEY key) {
		return (T) remove(key);
	}

	/**
	 * Add object on the map and returns himself making automatic conversion.
	 * 
	 * @param <T>
	 * @param key
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T putSelf(final KEY key, final VALUE value) {
		return (T) super.put(key, value);
	}

}
