/**
 * Vulpe Framework - Copyright (c) Active Thread
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
	 *            Indica se o parametro também é de saida (in/out)
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
	 *            Indica se o parametro também é de saida (in/out)
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
