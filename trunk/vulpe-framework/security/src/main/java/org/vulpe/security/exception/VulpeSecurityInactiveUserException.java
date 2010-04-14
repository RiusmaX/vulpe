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
