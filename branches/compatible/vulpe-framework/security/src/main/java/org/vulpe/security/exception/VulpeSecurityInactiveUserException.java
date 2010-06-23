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
package org.vulpe.security.exception;

/**
 * This exception is thrown when user not found.
 * 
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 * @version 1.0
 * @since 1.0
 * 
 */
public class VulpeSecurityInactiveUserException extends VulpeSecurityException {

	private static final long serialVersionUID = 1L;

	public VulpeSecurityInactiveUserException() {
		super();
	}

	public VulpeSecurityInactiveUserException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public VulpeSecurityInactiveUserException(final String message) {
		super(message);
	}

	public VulpeSecurityInactiveUserException(final Throwable cause) {
		super(cause);
	}
}
