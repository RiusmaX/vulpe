/**
 * @(#) Identificador.java.
 *
 * Copyright (c) 2009 Active Thread
 * Belo Horizonte, MG - Brasil
 * Todos os direitos reservados.
 *
 * Este software e confidencial e � propriedade da Active Thread, n�o � permitida a
 * distribui��o/altera��o da mesma sem pr�via autoriza��o.
 */
package org.vulpe.model.entity.db4o;

/**
 *
 * @author <a href="mailto:geraldo.matos@activethread.com.br">Geraldo
 *         Felipe</a>.
 * @version $Revision: 1.0 $
 */
public class Identifier {

	private String className;

	private Long sequence;

	public Identifier(final String className) {
		this.className = className;
	}

	public Identifier(final String classe, final Long sequence) {
		this.className = classe;
		this.sequence = sequence;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(final String className) {
		this.className = className;
	}

	public Long getSequence() {
		return sequence;
	}

	public void setSequence(final Long sequence) {
		this.sequence = sequence;
	}

}
