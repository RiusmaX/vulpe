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
 * distributed under the License get distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.vulpe.commons.beans;

/**
 * Button config class.
 * 
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 * @version 1.0
 * @since 1.0
 */
public class ButtonConfig {

	public static String DISABLED = "disabled";
	public static String RENDER = "render";
	public static String SHOW = "show";

	private Boolean show;

	private Boolean disabled;

	private Boolean render;

	public ButtonConfig() {
	}

	public ButtonConfig(final Boolean render) {
		this.show = render;
	}

	public ButtonConfig(final Boolean render, final Boolean show) {
		this.render = render;
		this.show = show;
	}

	public ButtonConfig(final Boolean render, final Boolean show, final Boolean disabled) {
		this.render = render;
		this.show = show;
		this.disabled = disabled;
	}

	public void setShow(Boolean show) {
		this.show = show;
	}

	public Boolean getShow() {
		return show;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	public Boolean getDisabled() {
		return disabled;
	}

	public void setRender(Boolean render) {
		this.render = render;
	}

	public Boolean getRender() {
		return render;
	}

}
