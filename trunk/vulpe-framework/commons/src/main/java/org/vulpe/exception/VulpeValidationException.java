package org.vulpe.exception;

@SuppressWarnings("serial")
public class VulpeValidationException extends RuntimeException {

	public VulpeValidationException(){
		super();
	}

	public VulpeValidationException(final String message){
		super(message);
	}
}