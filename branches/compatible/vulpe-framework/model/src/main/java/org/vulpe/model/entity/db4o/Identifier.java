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
package org.vulpe.model.entity.db4o;

/**
 * 
 * @author <a href="mailto:geraldo.matos@activethread.com.br">Geraldo
 *         Felipe</a>.
 * @version $Revision: 1.0 $
 */
public class Identifier {

	private String className;

	private Long sequence;

	public Identifier(final String className) {
		this.className = className;
	}

	public Identifier(final String classe, final Long sequence) {
		this.className = classe;
		this.sequence = sequence;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(final String className) {
		this.className = className;
	}

	public Long getSequence() {
		return sequence;
	}

	public void setSequence(final Long sequence) {
		this.sequence = sequence;
	}

}
