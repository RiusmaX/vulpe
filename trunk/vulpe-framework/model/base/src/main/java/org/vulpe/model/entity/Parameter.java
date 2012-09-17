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
package org.vulpe.model.entity;

/**
 *
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>.
 */
public class Parameter {

	private int type;

	private Object value;

	private boolean out = false;

	private String arrayType;

	private Object[] arrayValues;

	/**
	 *
	 * @param type
	 *            Tipo SQL do parametro
	 * @param value
	 *            Valor do Parametro (no caso de um Array, deve-se deixar nulo)
	 */
	public Parameter(final int type, final Object value) {
		this.type = type;
		this.value = value;
	}

	/**
	 *
	 * @param type
	 *            Tipo SQL do parametro
	 * @param value
	 *            Valor do Parametro (no caso de um Array, deve-se deixar nulo)
	 * @param out
	 *            Indica se o parametro tamb�m � de saida (in/out)
	 */
	public Parameter(final int type, final Object value, final boolean out) {
		this.type = type;
		this.value = value;
		this.out = out;
	}

	/**
	 *
	 * @param type
	 *            Tipo SQL do parametro
	 * @param value
	 *            Valor do Parametro (no caso de um Array, deve-se deixar nulo)
	 * @param arrayType
	 *            Tipo do Array no banco - Exemplo: T_LISTA_NUMEROS
	 * @param arrayValues
	 *            Array de objetos que represetam os valores a serem passados no
	 *            SQL Array
	 */
	public Parameter(final int type, final Object value, final String arrayType,
			final Object[] arrayValues) {
		this.type = type;
		this.value = value;
		this.arrayType = arrayType;
		this.arrayValues = arrayValues;
	}

	/**
	 *
	 * @param type
	 *            Tipo SQL do parametro
	 * @param value
	 *            Valor do Parametro (no caso de um Array, deve-se deixar nulo)
	 * @param out
	 *            Indica se o parametro tamb�m � de saida (in/out)
	 * @param arrayType
	 *            Tipo do Array no banco - Exemplo: T_LISTA_NUMEROS
	 * @param arrayValues
	 *            Array de objetos que represetam os valores a serem passados no
	 *            SQL Array
	 */
	public Parameter(final int type, final Object value, final boolean out, final String arrayType,
			final Object[] arrayValues) {
		this.type = type;
		this.value = value;
		this.out = out;
		this.arrayType = arrayType;
		this.arrayValues = arrayValues;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public boolean isOut() {
		return out;
	}

	public void setOut(boolean out) {
		this.out = out;
	}

	public String getArrayType() {
		return arrayType;
	}

	public void setArrayType(String arrayType) {
		this.arrayType = arrayType;
	}

	public Object[] getArrayValues() {
		return arrayValues;
	}

	public void setArrayValues(Object[] arrayValues) {
		this.arrayValues = arrayValues;
	}

}
