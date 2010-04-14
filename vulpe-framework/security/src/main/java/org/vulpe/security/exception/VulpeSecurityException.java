package org.vulpe.security.exception;

/**
 * This exception is thrown when some security related problem occurs in the
 * application.
 *
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 * @version 1.0
 * @since 1.0
 *
 */
public class VulpeSecurityException extends Exception {

	private static final long serialVersionUID = 1L;

	public VulpeSecurityException() {
		super();
	}

	public VulpeSecurityException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public VulpeSecurityException(final String message) {
		super(message);
	}

	public VulpeSecurityException(final Throwable cause) {
		super(cause);
	}
}
