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

	/**
	 * Add news details on top of list.
	 */
	boolean addNewDetailsOnTop() default false;

	/**
	 * Menu Type to backend.
	 */
	MenuType backendMenuType() default MenuType.SUPERFISH;

	boolean breakLabel() default true;

	String dateMask() default "99/99/9999";

	boolean focusFirst() default true;

	/**
	 * Menu Type to frontend.
	 */
	MenuType frontendMenuType() default MenuType.SUPERFISH;

	int globalPageSize() default 5;

	String jQueryUI() default JQUERYUI_SMOOTHNESS;

	int iconHeight() default 32;

	int iconWidth() default 32;

	boolean messageSlideUp() default false;

	int messageSlideUpTime() default 10000;

	PagingButtonStyle pagingButtonStyle() default PagingButtonStyle.CSS;

	PagingStyle pagingStyle() default PagingStyle.NUMERIC;

	boolean showButtonsAsImage() default false;

	boolean showIconOfButton() default false;

	boolean showTextOfButton() default true;

	boolean showCopyright() default true;

	boolean showButtonDeleteThis() default true;

	boolean showButtonUpdate() default false;

	boolean showButtonsDelete() default true;

	boolean showButtonClone() default true;

	boolean showLine() default true;

	boolean showReportInNewWindow() default true;

	boolean showPoweredBy() default true;

	boolean showWarningBeforeClear() default false;

	boolean showWarningBeforeDelete() default true;

	boolean showWarningBeforeUpdatePost() default false;

	boolean showModificationWarning() default true;

	SortType sortType() default SortType.ALL;

	boolean useBackendLayer() default false;

	boolean useFrontendLayer() default true;

	public enum SortType {
		ALL, LAST_COLUMN
	}

	public enum PagingStyle {
		NUMERIC, PAGE_OF
	}

	public enum PagingButtonStyle {
		CSS, JQUERY_UI
	}

	/**
	 * Menu Type.
	 */
	enum MenuType {
		DROPPY, SUPERFISH, NONE
	}

	public final String JQUERYUI_SMOOTHNESS = "smoothness";
	public final String JQUERYUI_REDMOND = "redmond";

}