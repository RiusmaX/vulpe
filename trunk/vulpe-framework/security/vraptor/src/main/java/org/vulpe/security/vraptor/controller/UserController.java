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
package org.vulpe.security.vraptor.controller;

import org.apache.commons.lang.StringUtils;
import org.vulpe.commons.annotations.DetailConfig;
import org.vulpe.commons.annotations.Quantity;
import org.vulpe.commons.annotations.Quantity.QuantityType;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.vraptor.VulpeVRaptorController;
import org.vulpe.security.model.entity.User;
import org.vulpe.security.model.services.SecurityService;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;

@Resource
@Path("/security/User")
@Controller(serviceClass = SecurityService.class, detailsConfig = { @DetailConfig(name = "userRoles", propertyName = "entity.userRoles", despiseFields = "role", startNewDetails = 1, newDetails = 1, quantity = @Quantity(type = QuantityType.ONE_OR_MORE)) })
@SuppressWarnings("serial")
public class UserController extends VulpeVRaptorController<User, Long> {

	@Override
	public void createPost() {
		if ((StringUtils.isNotBlank(entity.getPassword()) && StringUtils
				.isNotBlank(entity.getPasswordConfirm()))
				&& (!entity.getPassword().equals(entity.getPasswordConfirm()))) {
			showError("{vulpe.security.user.password.not.match}");
		}
		setPassword(entity.getPassword());
		super.createPost();
	}

	@Override
	protected void onUpdate() {
		super.onUpdate();
		setPassword(entity.getPassword());
	}

	@Override
	protected boolean onUpdatePost() {
		if (StringUtils.isBlank(entity.getPassword())
				&& StringUtils.isBlank(entity.getPasswordConfirm())) {
			entity.setPassword(getPassword());
		} else {
			setPassword(entity.getPassword());
		}
		if ((StringUtils.isNotBlank(entity.getPassword()) && StringUtils
				.isNotBlank(entity.getPasswordConfirm()))
				&& (!entity.getPassword().equals(entity.getPasswordConfirm()))) {
			addActionError("{vulpe.security.user.password.not.match}");
			return false;
		}
		return super.onUpdatePost();
	}

	public String getPassword() {
		return (String) getRequest().getAttribute("password");
	}

	public void setPassword(final String password) {
		getRequest().setAttribute("password", password);
	}
}
