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

/**
 * Annotation to configure application properties.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PACKAGE)
public @interface VulpeApplication {

	/**
	 * Project Name.
	 */
	String name();

	/**
	 * Project Package.
	 */
	String applicationPackage();

	/**
	 * Character Encoding.
	 */
	String characterEncoding() default "UTF-8";

	/**
	 * Default Date pattern.
	 */
	String datePattern() default "dd/MM/yyyy";

	/**
	 * Default Date Time pattern.
	 */
	String dateTimePattern() default "dd/MM/yyyy HH:mm:ss";

	/**
	 * Default Locale Code.
	 */
	String localeCode() default "en_US";

	/**
	 * I18N message files name.
	 */
	String[] i18n() default { "VulpeResources", "VulpeSecurityResources", "ApplicationResources" };

	/**
	 * I18N Manager.
	 */
	String i18nManager() default "org.vulpe.controller.commons.MultipleResourceBundle";

	/**
	 * Configure View properties.
	 */
	VulpeView view() default @VulpeView;

	/**
	 * Configure File Upload properties.
	 */
	VulpeUpload upload() default @VulpeUpload;

	/**
	 * Configure Mobile properties.
	 */
	VulpeMobile mobile() default @VulpeMobile;

	/**
	 * Configure Code Generator properties
	 */
	VulpeCodeGenerator codeGenerator() default @VulpeCodeGenerator;
	
	/**
	 * Configure Hot Keys properties
	 */
	VulpeHotKeys hotKeys() default @VulpeHotKeys;

}
