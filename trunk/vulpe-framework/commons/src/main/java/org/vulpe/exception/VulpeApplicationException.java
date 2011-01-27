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
package org.vulpe.exception;

/**
 * Exception to report business rules error.
 * 
 * @author <a href="mailto:fabio.viana@vulpe.org">Fábio Viana</a>
 */
@SuppressWarnings("serial")
public class VulpeApplicationException extends Exception {
	private String args[];
	private String message;

	public VulpeApplicationException(final Throwable throwable, final String message,
			final String... args) {
		super(message, throwable);
		this.message = message;
		this.args = args;
	}

	public VulpeApplicationException(final String message, final String... args) {
		super(message);
		this.message = message;
		this.args = args;
	}

	public String[] getArgs() {
		return args;
	}

	public String getMessage() {
		return this.message;
	}

	public void setArgs(final String[] args) {
		this.args = args;
	}

	public void setMessage(final String message) {
		this.message = message;
	}
}