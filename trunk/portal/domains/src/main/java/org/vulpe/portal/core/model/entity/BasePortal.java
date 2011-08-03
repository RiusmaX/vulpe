package org.vulpe.portal.core.model.entity;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.vulpe.model.annotations.db4o.Inheritance;
import org.vulpe.model.entity.impl.VulpeBaseDB4OAuditEntity;
import org.vulpe.portal.commons.model.entity.Status;
import org.vulpe.security.model.entity.User;
import org.vulpe.view.annotations.input.VulpeSelect;
import org.vulpe.view.annotations.output.VulpeColumn;

//@CodeGenerator
@Inheritance
@SuppressWarnings("serial")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BasePortal extends VulpeBaseDB4OAuditEntity<Long> {

	private Date date;

	private Date expire;

	private User user;

	@VulpeColumn
	@VulpeSelect(required = true)
	private Status status;

}
