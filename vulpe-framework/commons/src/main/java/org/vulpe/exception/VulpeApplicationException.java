package org.vulpe.exception;


/**
 * Exceção utilizada para reportar erros em regras de negócio.
 * @author <a href="mailto:fabio.viana@activethread.com.br">Fábio Viana</a>
 */
@SuppressWarnings("serial")
public class VulpeApplicationException extends Exception {
	private String args[];
	private String message;

	public VulpeApplicationException(final Throwable throwable, final String message, final String...args){
		super(message, throwable);
		this.message = message;
		this.args = args;
	}

	public VulpeApplicationException(final String message, final String...args){
		super(message);
		this.message = message;
		this.args = args;
	}

	public String[] getArgs() {
		return args.clone();
	}

	public String getMessage() {
		return this.message;
	}

	public void setArgs(final String[] args) {
		this.args = args.clone();
	}

	public void setMessage(final String message) {
		this.message = message;
	}
}