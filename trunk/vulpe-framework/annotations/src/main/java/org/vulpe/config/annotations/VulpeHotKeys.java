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
package org.vulpe.config.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface VulpeHotKeys {

	VulpeHotKey[] defaultKeys() default { @VulpeHotKey(name = "AddDetail", keys = "Alt+f8"),
			@VulpeHotKey(name = "Clear", keys = "Alt+Ctrl+Shift+del"), @VulpeHotKey(name = "Create", keys = "Ctrl+f8"),
			@VulpeHotKey(name = "CreatePost", keys = "Ctrl+f10", putSameOnReturnKey = true, dontFireOnText = true),
			@VulpeHotKey(name = "Delete", keys = "Ctrl+del"), @VulpeHotKey(name = "Prepare", keys = "Ctrl+backspace"),
			@VulpeHotKey(name = "Read", keys = "Ctrl+f9", putSameOnReturnKey = true),
			@VulpeHotKey(name = "Report", keys = "Ctrl+f12"),
			@VulpeHotKey(name = "UpdatePost", keys = "Ctrl+f10", putSameOnReturnKey = true, dontFireOnText = true),
			@VulpeHotKey(name = "TabularFilter", keys = "Ctrl+f7"),
			@VulpeHotKey(name = "TabularPost", keys = "Ctrl+f10", putSameOnReturnKey = true, dontFireOnText = true),
			@VulpeHotKey(name = "[items]", keys = "Ctrl+Shift+[numbers]"),
			@VulpeHotKey(name = "[tabs]", keys = { "Alt+Shift+left", "Alt+Shift+right" }) };

	VulpeHotKey[] value() default {};
}
