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
package org.vulpe.commons.helper;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.vulpe.exception.VulpeSystemException;

/**
 * Utility class to control objects in cache.
 * 
 * @author <a href="mailto:fabio.viana@vulpe.org">Fábio Viana</a>
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 */
@SuppressWarnings( { "unchecked" })
public final class VulpeCacheHelper {
	private static final VulpeCacheHelper INSTANCE = new VulpeCacheHelper();

	public static VulpeCacheHelper getInstance() {
		return INSTANCE;
	}

	private VulpeCacheHelper() {
	}

	private final transient Map<Object, Object> cache = Collections
			.synchronizedMap(new HashMap<Object, Object>());

	public void put(final Object key, final Object instance) {
		cache.put(key, instance);
	}

	public <T> T get(final Object key) {
		return (T) cache.get(key);
	}

	public <T> T getInstance(final Class<T> classe) {
		if (!contains(classe)) {
			try {
				put(classe, classe.newInstance());
			} catch (Exception e) {
				throw new VulpeSystemException(e);
			}
		}
		return (T) get(classe);
	}

	public boolean contains(final Object key) {
		return cache.containsKey(key);
	}

	public void remove(final Object key) {
		cache.remove(key);
	}
}