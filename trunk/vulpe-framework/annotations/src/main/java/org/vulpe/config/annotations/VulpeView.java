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
public @interface VulpeView {

	boolean backendCenteredLayout() default false;

	boolean breakLabel() default false;

	boolean focusFirst() default true;

	boolean frontendCenteredLayout() default false;

	int heightButtonIcon() default 32;

	int heightMobileButtonIcon() default 32;

	boolean messageSlideUp() default false;

	int messageSlideUpTime() default 10000;

	PagingButtonStyle pagingButtonStyle() default PagingButtonStyle.CSS;

	PagingStyle pagingStyle() default PagingStyle.NUMERIC;

	boolean showButtonAsImage() default false;

	boolean showButtonIcon() default false;

	boolean showButtonText() default true;

	boolean showCopyright() default true;

	boolean showPoweredBy() default true;

	boolean showWarningBeforeUpdatePost() default false;
	
	boolean showModificationWarning() default true;
	
	boolean useBackendLayer() default false;

	boolean useFrontendLayer() default true;
	
	int widthButtonIcon() default 32;

	int widthMobileButtonIcon() default 32;


	public enum PagingStyle {
		NUMERIC, PAGE_OF
	}

	public enum PagingButtonStyle {
		CSS, JQUERY_UI
	}

}
