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
@Target(ElementType.PACKAGE)
public @interface VulpeProject {

	String name();

	String projectPackage();

	String[] i18n() default { "VulpeResources", "VulpeSecurityResources", "ApplicationResources" };

	String i18nManager() default "org.vulpe.controller.commons.MultipleResourceBundle";

	VulpeView view() default @VulpeView;

	VulpeUpload upload() default @VulpeUpload;

	boolean mobileEnabled() default false;

	VulpeMobile mobile() default @VulpeMobile;

	String theme() default "default";

	boolean audit() default true;

	boolean security() default true;

	MenuType frontendMenuType() default MenuType.DROPPY;

	MenuType backendMenuType() default MenuType.DROPPY;

	enum MenuType {
		DROPPY, NONE
	}
}
