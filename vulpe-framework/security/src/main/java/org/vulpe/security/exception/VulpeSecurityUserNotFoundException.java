package org.vulpe.security.exception;

/**
 * This exception is thrown when user not found.
 *
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 * @version 1.0
 * @since 1.0
 *
 */
public class VulpeSecurityUserNotFoundException extends VulpeSecurityException {

	private static final long serialVersionUID = 1L;

	public VulpeSecurityUserNotFoundException() {
		super();
	}

	public VulpeSecurityUserNotFoundException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public VulpeSecurityUserNotFoundException(final String message) {
		super(message);
	}

	public VulpeSecurityUserNotFoundException(final Throwable cause) {
		super(cause);
	}
}
