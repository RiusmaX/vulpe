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
package org.vulpe.security.web.authentication;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.memory.UserAttribute;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.util.StringUtils;

/**
 * 
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 * 
 */
public class VulpeAnonymousProcessingFilter extends AnonymousAuthenticationFilter {

	private Properties anonymousConfig;

	public UserAttribute setAsText(final String string) throws IllegalArgumentException {
		if (StringUtils.hasText(string)) {
			final String[] tokens = StringUtils.commaDelimitedListToStringArray(string);
			final UserAttribute userAttribute = new UserAttribute();

			final List<String> authorities = new ArrayList<String>();

			for (int i = 0; i < tokens.length; i++) {
				final String currentToken = tokens[i].trim();

				if (i == 0) {
					userAttribute.setPassword(currentToken);
				} else {
					if (currentToken.equalsIgnoreCase("enabled")) {
						userAttribute.setEnabled(true);
					} else if (currentToken.equalsIgnoreCase("disabled")) {
						userAttribute.setEnabled(false);
					} else {
						authorities.add(currentToken);
					}
				}
			}
			userAttribute.setAuthoritiesAsString(authorities);

			if (userAttribute.isValid()) {
				return userAttribute;
			}
		}
		return null;
	}

	public Properties getAnonymousConfig() {
		return anonymousConfig;
	}

	public void setAnonymousConfig(final Properties anonymousConfig) {
		this.anonymousConfig = anonymousConfig;
	}

	public VulpeAnonymousProcessingFilter(final Properties anonymousConfig) {
		super(anonymousConfig.getProperty("key"), "anonymousUser", AuthorityUtils
				.createAuthorityList(anonymousConfig.getProperty("anonymousRole")));
	}

}
