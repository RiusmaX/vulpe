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
package org.vulpe.commons;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class VulpeContext {

	public static final String LOCALE = "locale";

	private Map<String, Object> map = new HashMap<String, Object>();

	public void setLocale(final Locale locale) {
		map.put(LOCALE, locale);
	}

	public Locale getLocale() {
		return (Locale) map.get(LOCALE);
	}

	public Object get(final String key) {
		return map.get(key);
	}

	public void set(final String key, final Object value) {
		map.put(key, value);
	}
}
