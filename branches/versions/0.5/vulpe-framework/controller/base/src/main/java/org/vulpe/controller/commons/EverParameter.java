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
package org.vulpe.controller.commons;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.vulpe.commons.VulpeConstants;
import org.vulpe.commons.util.VulpeHashMap;

/**
 * Utility class to access and control global parameters.
 * 
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings("serial")
public class EverParameter extends VulpeHashMap<String, Object> {

	public static final String WEAK_REFERENCE = "WeakReference_";

	/**
	 * Add object on the map to use in current case. When the flow is changed or
	 * the case is finished the object is released from the map.
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Object putWeakRef(String key, Object value) {
		return super.put(WEAK_REFERENCE + key, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.HashMap#get(java.lang.Object)
	 */
	@Override
	public Object get(Object key) {
		Object obj = super.get(WEAK_REFERENCE + key);
		if (obj == null) {
			obj = super.get(key);
		}
		return obj;
	}

	/**
	 * Remove all keys from the map with 'WeakRef'.
	 */
	public void removeWeakRef() {
		List<String> keys = new ArrayList<String>();
		for (String key : this.keySet()) {
			if (key.startsWith(WEAK_REFERENCE)) {
				keys.add(key);
			}
		}
		for (String key : keys) {
			this.remove(key);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.HashMap#remove(java.lang.Object)
	 */
	@Override
	public Object remove(Object key) {
		Object obj = super.remove(WEAK_REFERENCE + key);
		if (obj == null) {
			obj = super.remove(key);
		}
		return obj;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.HashMap#containsKey(java.lang.Object)
	 */
	@Override
	public boolean containsKey(Object key) {
		boolean contains = super.containsKey(WEAK_REFERENCE + key);
		if (!contains) {
			contains = super.containsKey(key);
		}
		return contains;
	}
	
	/**
	 * 
	 * @param session
	 * @return
	 */
	public static EverParameter getInstance(final HttpSession session) {
		EverParameter ever = (EverParameter) session.getAttribute(VulpeConstants.Session.EVER);
		if (ever == null) {
			ever = new EverParameter();
			session.setAttribute(VulpeConstants.Session.EVER, ever);
		}
		return ever;
	}
}
