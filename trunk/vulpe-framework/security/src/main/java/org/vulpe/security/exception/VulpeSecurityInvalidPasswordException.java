package org.vulpe.security.exception;

/**
 * This exception is thrown when passwords do not match.
 *
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 * @version 1.0
 * @since 1.0
 *
 */
public class VulpeSecurityInvalidPasswordException extends VulpeSecurityException {

	private static final long serialVersionUID = 1L;

	public VulpeSecurityInvalidPasswordException() {
		super();
	}

	public VulpeSecurityInvalidPasswordException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public VulpeSecurityInvalidPasswordException(final String message) {
		super(message);
	}

	public VulpeSecurityInvalidPasswordException(final Throwable cause) {
		super(cause);
	}
}
