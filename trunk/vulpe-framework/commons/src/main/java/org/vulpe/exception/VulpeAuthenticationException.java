package org.vulpe.exception;

@SuppressWarnings("serial")
public class VulpeAuthenticationException extends Exception{
	public VulpeAuthenticationException() {
		super("vulpe.error.authentication.empty");
	}
}
