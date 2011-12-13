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
package org.vulpe.view.annotations.input;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation represent input validation on view.
 *
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.ANNOTATION_TYPE, ElementType.FIELD })
public @interface VulpeValidate {

	/**
	 * Type of validation on input
	 */
	VulpeValidateType type() default VulpeValidateType.NONE;

	/**
	 * Scope of validation on input
	 */
	VulpeValidateScope[] scope() default { VulpeValidateScope.ALL };

	/**
	 * Scope of required validation on input
	 */
	VulpeValidateScope[] requiredScope() default { VulpeValidateScope.ALL };

	/**
	 * Minimum value to input
	 */
	int min() default 0;

	/**
	 * Maximum value to input
	 */
	int max() default 0;

	/**
	 * Minimum length to input
	 */
	int minlength() default 0;

	/**
	 * Maximum length to input
	 */
	int maxlength() default 0;

	/**
	 * Range to numbers
	 */
	int[] range() default { 0, 0 };

	/**
	 * Mask to input.<br>
	 * Example: 99/99/9999
	 */
	String mask() default "";

	/**
	 * Date pattern to input.<br>
	 * Example: dd/MM/yyyy
	 */
	String datePattern() default "dd/MM/yyyy";

	/**
	 * Types for input validation
	 */
	enum VulpeValidateType {
		ARRAY, DATE, DOUBLE, EMAIL, FLOAT, INTEGER, LONG, NONE, STRING
	}

	/**
	 * Scope for input validation
	 */
	enum VulpeValidateScope {
		ALL, MAIN, DETAIL, SELECT, TABULAR
	}
}