package org.vulpe.portal.commons.model.entity;

import java.util.Date;

import org.vulpe.model.annotations.db4o.Inheritance;
import org.vulpe.model.entity.impl.VulpeBaseDB4OAuditEntity;
import org.vulpe.security.model.entity.User;
import org.vulpe.view.annotations.input.VulpeSelect;
import org.vulpe.view.annotations.output.VulpeColumn;

@Inheritance
@SuppressWarnings("serial")
public class BasePortal extends VulpeBaseDB4OAuditEntity<Long> {

	private Date date;

	private Date expire;

	private User user;

	@VulpeColumn
	@VulpeSelect(required = true)
	private Status status;

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDate() {
		return date;
	}

	public void setExpire(Date expire) {
		this.expire = expire;
	}

	public Date getExpire() {
		return expire;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

}
