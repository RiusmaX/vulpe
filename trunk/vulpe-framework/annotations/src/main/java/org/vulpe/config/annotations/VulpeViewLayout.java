/**
 * Vulpe Framework - Quick and Smart ;)
 * Copyright (C) 2011 Active Thread
 *
 * Este programa é software livre; você pode redistribuí-lo e/ou
 * modificá-lo sob os termos da Licença Pública Geral GNU, conforme
 * publicada pela Free Software Foundation; tanto a versão 2 da
 * Licença como (a seu critério) qualquer versão mais nova.
 *
 * Este programa é distribuído na expectativa de ser útil, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia implícita de
 * COMERCIALIZAÇÃO ou de ADEQUAÇÃO A QUALQUER PROPÓSITO EM
 * PARTICULAR. Consulte a Licença Pública Geral GNU para obter mais
 * detalhes.
 *
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU
 * junto com este programa; se não, escreva para a Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307, USA.
 */
/**
 * Vulpe Framework - Quick and Smart ;)
 * Copyright (C) 2011 Active Thread
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
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
public @interface VulpeViewLayout {

	/**
	 * Menu Type to backend.
	 */
	MenuType backendMenuType() default MenuType.SUPERFISH;

	boolean breakLabel() default true;

	boolean displaySpecificMessagesWhenLoading() default false;

	/**
	 * Menu Type to frontend.
	 */
	MenuType frontendMenuType() default MenuType.SUPERFISH;

	String jQueryUI() default JQUERYUI_SMOOTHNESS;

	int iconHeight() default 32;

	int iconWidth() default 32;

	boolean showLoadingAsModal() default false;

	boolean showButtonsAsImage() default false;

	boolean showIconErrorMessage() default true;

	boolean showIconOfButton() default false;

	boolean showTextOfButton() default true;

	boolean showCopyright() default true;

	boolean showButtonDeleteThis() default true;

	boolean showButtonUpdate() default false;

	boolean showButtonsDelete() default true;

	boolean showButtonClone() default true;

	boolean showRows() default true;

	boolean showSliderPanel() default false;

	boolean showSliderPanelOnlyIfAuthenticated() default true;

	boolean showReportInNewWindow() default true;

	boolean showPoweredBy() default true;

	boolean useBackendLayer() default false;

	boolean useFrontendLayer() default true;

	/**
	 * Menu Type.
	 */
	enum MenuType {
		VULPE, DROPPY, SUPERFISH, NONE
	}

	String JQUERYUI_SMOOTHNESS = "smoothness";
	String JQUERYUI_REDMOND = "redmond";

}
