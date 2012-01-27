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
package org.vulpe.security.commons;

/**
 * Contains common constants related to application security implementation.
 *
 */
public final class VulpeSecurityConstants {

	/**
	 * A default private constructor to avoid instance creation of
	 * <code>VulpeSecurityConstants</code> class.
	 *
	 */
	private VulpeSecurityConstants() {
	}

	public static final String SPRING_SECURITY_USERNAME_FIELD = "j_username";
	public static final String SPRING_SECURITY_PASSWORD_FIELD = "j_password";

	/**
	 * A return code for authentication success.
	 */
	public static final int AUTHENTICATION_SUCCESS = 0;
	/**
	 * A return code for authentication failure.
	 */
	public static final int AUTHENTICATION_FAILED = 1;

	/**
	 *
	 */
	public static final String AUTHENTICATION_LOGIN_BYPASS = "AuthenticationLoginBypass";

	public final class Context {
		public static final String ANONYMOUS_USER = "anonymousUser";
		public static final String ANONYMOUS_ROLE = "anonymousRole";
		public static final String ANONYMOUS_ROLE_DESCRIPTION = "anonymousRoleDescription";

		public static final String ADMINISTRATOR_USER = "administratorUser";
		public static final String ADMINISTRATOR_PASSWORD = "administratorPassword";
		public static final String ADMINISTRATOR_NAME = "administratorName";
		public static final String ADMINISTRATOR_EMAIL = "administratorEmail";
		public static final String ADMINISTRATOR_ROLE = "administratorRole";
		public static final String ADMINISTRATOR_ROLE_DESCRIPTION = "administratorRoleDescription";

		public static final String SECURE_RESOURCES_PATH = "secureResourcesPath";
		public final class ContextDefaults {
			public static final String ANONYMOUS_USER = "anonymous";
			public static final String ANONYMOUS_ROLE = "ROLE_ANONYMOUS";
			public static final String ANONYMOUS_ROLE_DESCRIPTION = "ANONYMOUS";

			public static final String ADMINISTRATOR_USER = "admin";
			public static final String ADMINISTRATOR_PASSWORD = "vulpe";
			public static final String ADMINISTRATOR_NAME = "Administrator";
			public static final String ADMINISTRATOR_EMAIL = "admin@localhost";
			public static final String ADMINISTRATOR_ROLE = "ROLE_ADMINISTRATOR";
			public static final String ADMINISTRATOR_ROLE_DESCRIPTION = "Administrators";

			public static final String SECURE_RESOURCES_PATH = "/audit/**,/security/**";
		}

	}
}
