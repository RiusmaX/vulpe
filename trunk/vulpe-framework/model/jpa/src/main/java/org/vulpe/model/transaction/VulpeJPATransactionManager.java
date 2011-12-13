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
package org.vulpe.model.transaction;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.support.DefaultTransactionStatus;
import org.vulpe.commons.util.VulpeReflectUtil;
import org.vulpe.exception.VulpeSystemException;

@SuppressWarnings("serial")
public class VulpeJPATransactionManager extends JpaTransactionManager {

	private static final Logger LOG = Logger.getLogger(VulpeJPATransactionManager.class);

	@Override
	protected void doCommit(DefaultTransactionStatus status) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Vulpe JPA Transaction Manager - override doComit start");
		}
		try {
			super.doCommit(status);
		} catch (TransactionSystemException e) {
			treatConstraintViolationException(e);
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("Vulpe JPA Transaction Manager - override doComit end");
		}
	}

	protected Throwable getFirstCause(Throwable e) {
		Throwable throwable = e.getCause();
		if (throwable.getCause() != null) {
			throwable = getFirstCause(throwable);
		}
		return throwable;
	}

	private Throwable getConstraintViolationCause(final Throwable e) {
		Throwable throwable = e.getCause();
		if (throwable != null && !(throwable instanceof ConstraintViolationException)) {
			throwable = getConstraintViolationCause(throwable);
		}
		return throwable;
	}

	protected void treatConstraintViolationException(TransactionSystemException e) {
		final Throwable constraintViolation = getConstraintViolationCause(e);
		if (constraintViolation != null) {
			final Throwable first = getFirstCause(e);
			final Throwable firstNext = VulpeReflectUtil.getFieldValue(first, "next");
			String key = "vulpe.error.sql.constraint.violation.exception";
			if (firstNext != null) {
				String message = firstNext.getMessage().toUpperCase();
				message = message.substring(message.indexOf(":") + 1).replaceAll("\"", "");
				String constraint = "";
				if (message.indexOf("FK_") != -1) {
					constraint = message.substring(message.indexOf("FK_"));
				} else if (message.indexOf("AK_") != -1) {
					constraint = message.substring(message.indexOf("AK_"));
				} else if (message.indexOf("PK_") != -1) {
					constraint = message.substring(message.indexOf("PK_"));
				}
				constraint = constraint.substring(0, constraint.indexOf(" "));
				key += ".".concat(constraint);
			}
			throw new VulpeSystemException(constraintViolation, key, new String[] {});
		}
	}
}
