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
package org.vulpe.security.authorization.model.services.impl.pojo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.vulpe.security.authorization.model.dao.VulpeAuthorizationDAO;
import org.vulpe.security.authorization.model.services.VulpeAuthorizationService;
import org.vulpe.security.model.entity.Role;
import org.vulpe.security.model.entity.SecureResource;

@Service("VulpeAuthorizationService")
public class VulpeAuthorizationServicePOJO implements VulpeAuthorizationService {

	@Qualifier("VulpeAuthorizationDAO")
	@Autowired
	private VulpeAuthorizationDAO authorizationDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.vulpe.security.authorization.model.services.
	 * VulpeAuthorizationService#getSecureObject(java.lang.String)
	 */
	public SecureResource getSecureObject(final String secObjectName) {
		return authorizationDAO.getSecureObject(secObjectName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.vulpe.security.authorization.model.services. VulpeAuthorizationService
	 * #getSecureObjectRoles(org.vulpe.security.authentication .model.entity.SecureResource)
	 */
	public List<Role> getSecureObjectRoles(final SecureResource secureObject) {
		return authorizationDAO.getSecureObjectRoles(secureObject);
	}

	public VulpeAuthorizationDAO getAuthorizationDAO() {
		return authorizationDAO;
	}

	public void setAuthorizationDAO(final VulpeAuthorizationDAO authorizationDAO) {
		this.authorizationDAO = authorizationDAO;
	}

}
