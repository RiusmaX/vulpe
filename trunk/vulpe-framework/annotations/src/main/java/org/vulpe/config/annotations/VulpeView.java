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
public @interface VulpeView {

	boolean messageSlideUp() default true;

	int messageSlideUpTime() default 10000;

	boolean showButtonAsImage() default false;

	boolean showButtonIcon() default false;

	boolean showButtonText() default true;

	int widthButtonIcon() default 16;

	int widthMobileButtonIcon() default 32;

	int heightButtonIcon() default 16;

	int heightMobileButtonIcon() default 32;

	boolean backendCenteredLayout() default false;

	boolean frontendCenteredLayout() default false;

	boolean breakLabel() default false;
}
