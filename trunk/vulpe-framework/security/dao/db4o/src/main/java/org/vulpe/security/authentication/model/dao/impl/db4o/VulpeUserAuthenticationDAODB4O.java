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
package org.vulpe.security.authentication.model.dao.impl.db4o;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.vulpe.model.dao.impl.db4o.VulpeBaseDAODB4O;
import org.vulpe.security.authentication.data.VulpeAuthenticationResponse;
import org.vulpe.security.authentication.model.dao.VulpeUserAuthenticationDAO;
import org.vulpe.security.commons.VulpeSecurityConstants;
import org.vulpe.security.exception.VulpeSecurityException;
import org.vulpe.security.exception.VulpeSecurityInactiveUserException;
import org.vulpe.security.exception.VulpeSecurityInvalidPasswordException;
import org.vulpe.security.exception.VulpeSecurityUserNotFoundException;
import org.vulpe.security.model.entity.User;

/**
 * 
 * This <code>VulpeUserAuthenticationDAO</code> implementation uses data
 * available in memory to authenticate the user.
 * 
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 * @version 1.0
 * @since 1.0
 * 
 */
@Repository("VulpeUserAuthenticationDAO")
@Transactional
public class VulpeUserAuthenticationDAODB4O extends VulpeBaseDAODB4O<User, Long> implements
		VulpeUserAuthenticationDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.vulpe.security.authentication.model.dao.UserAuthenticationDAO#
	 * authenticateUser(java.lang.String, java.lang.String)
	 */
	public VulpeAuthenticationResponse authenticateUser(final String username, final String password)
			throws VulpeSecurityException {
		final User user = getObject(new User(username));
		if (user == null) {
			throw new VulpeSecurityUserNotFoundException(username);
		}
		if (!password.equals(user.getPassword())) {
			throw new VulpeSecurityInvalidPasswordException();
		}
		if (!user.isActive()) {
			throw new VulpeSecurityInactiveUserException(username);
		}
		return new VulpeAuthenticationResponse(VulpeSecurityConstants.AUTHENTICATION_SUCCESS, user);
	}

}
