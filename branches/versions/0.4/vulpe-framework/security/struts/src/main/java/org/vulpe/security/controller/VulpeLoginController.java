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
package org.vulpe.security.controller;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.stereotype.Component;
import org.vulpe.commons.VulpeConstants.View;
import org.vulpe.commons.VulpeConstants.Controller.Result;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.ExecuteAlways;
import org.vulpe.controller.commons.VulpeControllerConfig.ControllerType;
import org.vulpe.controller.struts.VulpeStrutsController;
import org.vulpe.model.entity.impl.VulpeBaseSimpleEntity;

@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component("security.authenticator")
@Controller(type = ControllerType.NONE)
public class VulpeLoginController extends VulpeStrutsController<VulpeBaseSimpleEntity, Long> {

	private Integer loginError;

	private boolean accessDenied;

	/**
	 * 
	 * @return
	 */
	public void define() {
		if (accessDenied) {
			vulpe.controller().resultName(Result.ACCESS_DENIED);
		} else if (loginError != null && loginError == 1) {
			vulpe.controller().resultName(Result.ERRORS);
		}
	}

	public Integer getLoginError() {
		return loginError;
	}

	public void setLoginError(final Integer loginError) {
		this.loginError = loginError;
	}

	public boolean isAccessDenied() {
		return accessDenied;
	}

	public void setAccessDenied(final boolean accessDenied) {
		this.accessDenied = accessDenied;
	}

	@ExecuteAlways
	public void layout() {
		final DefaultSavedRequest savedRequest = vulpe
				.sessionAttribute(WebAttributes.SAVED_REQUEST);
		if (!ever.containsKey(View.CURRENT_LAYOUT)) {
			ever.put(View.CURRENT_LAYOUT, "FRONTEND");	
		}
		if (savedRequest != null && savedRequest.getRedirectUrl().contains("/backend")) {
			ever.put(View.CURRENT_LAYOUT, "BACKEND");
		}
	}
}
