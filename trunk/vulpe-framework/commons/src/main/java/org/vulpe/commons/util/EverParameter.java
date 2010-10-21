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
import java.util.List;

/**
 * Utility class to access and control global parameters.
 *
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings("serial")
public class EverParameter extends VulpeHashMap<String, Object> {

	public static final String WEAK_REFERENCE = "WeakReference_";

	/**
	 * Add object on the map to use in current case. When the flow is changed or
	 * the case is finished the object is released from the map.
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public Object putWeakRef(String key, Object value) {
		return super.put(WEAK_REFERENCE + key, value);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.HashMap#get(java.lang.Object)
	 */
	@Override
	public Object get(Object key) {
		Object obj = super.get(WEAK_REFERENCE + key);
		if (obj == null) {
			obj = super.get(key);
		}
		return obj;
	}

	/**
	 * Remove all keys from the map with 'WeakRef'.
	 */
	public void removeWeakRef() {
		List<String> keys = new ArrayList<String>();
		for (String key : this.keySet()) {
			if (key.startsWith(WEAK_REFERENCE)) {
				keys.add(key);
			}
		}
		for (String key : keys) {
			this.remove(key);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.HashMap#remove(java.lang.Object)
	 */
	@Override
	public Object remove(Object key) {
		Object obj = super.remove(WEAK_REFERENCE + key);
		if (obj == null) {
			obj = super.remove(key);
		}
		return obj;
	}
}
