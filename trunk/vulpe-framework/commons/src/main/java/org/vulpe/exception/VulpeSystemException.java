/**
 * Vulpe Framework - Quick and Smart ;)
 * Copyright (C) 2011 Active Thread
 *
 * Este programa é software livre; você pode redistribuí-lo e/ou
 * modificá-lo sob os termos da Licença Pública Geral GNU, conforme
 * publicada pela Free Software Foundation; tanto a versão 2 da
 * Licença como (a seu critério) qualquer versão mais nova.
 *
 * Este programa é distribuído na expectativa de ser útil, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia implícita de
 * COMERCIALIZAÇÃO ou de ADEQUAÇÃO A QUALQUER PROPÓSITO EM
 * PARTICULAR. Consulte a Licença Pública Geral GNU para obter mais
 * detalhes.
 *
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU
 * junto com este programa; se não, escreva para a Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307, USA.
 */
/**
 * Vulpe Framework - Quick and Smart ;)
 * Copyright (C) 2011 Active Thread
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.vulpe.exception;

/**
 * Exception to report infra error.
 *
 * @author <a href="mailto:fabio.viana@vulpe.org">Fábio Viana</a>
 */
@SuppressWarnings("serial")
public class VulpeSystemException extends RuntimeException {

	private String args[];
	private String message;

	public VulpeSystemException(final Throwable throwable, final String message, final String... args) {
		super(message, throwable);
		this.message = message;
		this.args = args;
	}

	public VulpeSystemException(final String message, final String... args) {
		super(message);
		this.message = message;
		this.args = args;
	}

	public VulpeSystemException(final String message, final Throwable throwable) {
		super(message, throwable);
		this.message = message;
	}

	public VulpeSystemException(final Throwable throwable) {
		this("vulpe.error.general", throwable);
	}

	public String[] getArgs() {
		return args;
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
