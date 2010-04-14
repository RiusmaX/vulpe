package org.vulpe.exception;


/**
 * Exceção utilizada para reportar erros de infra-estrutura.
 * @author <a href="mailto:fabio.viana@activethread.com.br">Fábio Viana</a>
 */
@SuppressWarnings("serial")
public class VulpeSystemException extends RuntimeException {

	private String args[];
	private String message;

	public VulpeSystemException(final Throwable throwable, final String message, final String...args){
		super(message, throwable);
		this.message = message;
		this.args = args;
	}

	public VulpeSystemException(final String message, final String...args){
		super(message);
		this.message = message;
		this.args = args;
	}

	public VulpeSystemException(final String message, final Throwable throwable){
		super(message, throwable);
		this.message = message;
	}

	public VulpeSystemException(final Throwable throwable){
		this("vulpe.error.general", throwable);
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
