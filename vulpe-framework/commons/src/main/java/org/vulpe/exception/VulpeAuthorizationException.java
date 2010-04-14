package org.vulpe.exception;

@SuppressWarnings("serial")
public class VulpeAuthorizationException extends Exception {
	public VulpeAuthorizationException() {
		super("vulpe.error.authorization.not");
	}
}