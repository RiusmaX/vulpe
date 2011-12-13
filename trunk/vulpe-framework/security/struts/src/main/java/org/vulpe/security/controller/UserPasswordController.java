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

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.commons.VulpeConstants.Controller.Button;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.struts.VulpeStrutsController;
import org.vulpe.security.model.entity.User;
import org.vulpe.security.model.services.SecurityService;

@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component("security.UserPasswordController")
@Controller(serviceClass = SecurityService.class)
@SuppressWarnings("serial")
public class UserPasswordController extends VulpeStrutsController<User, Long> {

	public static final String PASSWORD = "vulpeUserPassword";

	@Override
	public void update() {
		entity = (User) vulpe.securityContext().getUser().clone();
		entity.setPassword(null);
		super.update();
	}

	@Override
	protected void onUpdate() {

	}

	@Override
	public boolean validateEntity() {
		final User user = vulpe.securityContext().getUser();
		boolean valid = super.validateEntity();
		if (StringUtils.isBlank(entity.getCurrentPassword())) {
			addActionError("{vulpe.security.user.error.empty.current.password}");
			valid = false;
		} else {
			if (user.getPassword().equals(entity.getCurrentPassword())) {
				if (StringUtils.isBlank(entity.getPassword())) {
					addActionError("{vulpe.security.user.error.empty.password}");
					entity = user;
					return false;
				}
				if ((StringUtils.isNotBlank(entity.getPassword()) && StringUtils
						.isNotBlank(entity.getPasswordConfirm()))
						&& (!entity.getPassword().equals(entity.getPasswordConfirm()))) {
					addActionError("{vulpe.security.user.error.new.password.not.match}");
					valid = false;
				}
				if (entity.getPassword().equals(entity.getCurrentPassword())) {
					addActionError("{vulpe.security.user.error.must.be.different.new.password.and.old.password}");
					entity = user;
					return false;
				}
			} else {
				addActionError("{vulpe.security.user.error.empty.current.password.not.match}");
				valid = false;
			}
		}
		if (!valid) {
			entity = user;
		}
		return valid;
	}

	@Override
	protected boolean onUpdatePost() {
		final User user = vulpe.securityContext().getUser();
		user.setPasswordEncrypted(entity.getPassword());
		user.setPasswordConfirmEncrypted(entity.getPasswordConfirm());
		entity = user;
		vulpe.controller().defaultMessage(Operation.UPDATE_POST, "{vulpe.security.msg.user.password.changed}");
		vulpe.controller().redirectTo("/j_spring_security_logout", false);
		return super.onUpdatePost();
	}

	public String getPassword() {
		return ever.<String> getAuto(PASSWORD);
	}

	public void setPassword(final String password) {
		ever.putWeakRef(PASSWORD, password);
	}

	@Override
	public void manageButtons(Operation operation) {
		super.manageButtons(operation);
		vulpe.view().hideButtons(Button.CLEAR, Button.CREATE, Button.DELETE, Button.BACK,
				Button.CLONE);
	}
}
