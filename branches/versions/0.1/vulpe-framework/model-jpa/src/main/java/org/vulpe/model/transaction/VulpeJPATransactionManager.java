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
			treatContraintViolationException(e);
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

	protected void treatContraintViolationException(TransactionSystemException e) {
		final Throwable constraintViolation = getConstraintViolationCause(e);
		if (constraintViolation != null) {
			final Throwable first = getFirstCause(e);
			final Throwable firstNext = VulpeReflectUtil.getInstance().getFieldValue(first, "next");
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
