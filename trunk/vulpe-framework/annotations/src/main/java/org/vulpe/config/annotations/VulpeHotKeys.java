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
public @interface VulpeHotKeys {

	VulpeHotKey[] defaultKeys() default { @VulpeHotKey(name = "Clear", keys = "Alt+Ctrl+Shift+del"),
			@VulpeHotKey(name = "Create", keys = "Ctrl+f8"),
			@VulpeHotKey(name = "CreatePost", keys = "Ctrl+f10", putSameOnReturnKey = true, returnKeyDontFireInText = true),
			@VulpeHotKey(name = "Clone", keys = "Ctrl+f11"),
			@VulpeHotKey(name = "Delete", keys = "Ctrl+del"), @VulpeHotKey(name = "Prepare", keys = "Ctrl+backspace"),
			@VulpeHotKey(name = "Read", keys = "Ctrl+f9", putSameOnReturnKey = true),
			@VulpeHotKey(name = "Report", keys = "Ctrl+f12"),
			@VulpeHotKey(name = "UpdatePost", keys = "Ctrl+f10", putSameOnReturnKey = true, returnKeyDontFireInText = true),
			@VulpeHotKey(name = "TabularFilter", keys = "Ctrl+f7"),
			@VulpeHotKey(name = "TabularPost", keys = "Ctrl+f10", putSameOnReturnKey = true, returnKeyDontFireInText = true),
			@VulpeHotKey(name = "[addDetail]", keys = "Alt+f8"),
			@VulpeHotKey(name = "[deleteDetail]", keys = "Alt+del"),
			@VulpeHotKey(name = "[items]", keys = "Ctrl+Shift+[numbers]"),
			@VulpeHotKey(name = "[tabs]", keys = { "Alt+Shift+left", "Alt+Shift+right" }) };

	VulpeHotKey[] value() default {};
}
