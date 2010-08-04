package org.vulpe.model.entity.impl;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.MappedSuperclass;

import org.apache.commons.lang.StringUtils;
import org.vulpe.model.annotations.IgnoreAutoFilter;

@MappedSuperclass
@SuppressWarnings( { "serial", "unchecked" })
public abstract class AbstractVulpeBaseAuditEntity<ID extends Serializable & Comparable> extends
		AbstractVulpeBaseEntity<ID> {

	@IgnoreAutoFilter
	private String userOfLastUpdate;

	@IgnoreAutoFilter
	private Date dateOfLastUpdate;


	public AbstractVulpeBaseAuditEntity() {
		super();
	}

	@Override
	public String getOrderBy() {
		if (StringUtils.isBlank(super.getOrderBy())) {
			super.setOrderBy("obj.id");
		}
		return super.getOrderBy();
	}

	public void setDateOfLastUpdate(Date dateOfLastUpdate) {
		this.dateOfLastUpdate = dateOfLastUpdate;
	}

	public Date getDateOfLastUpdate() {
		return dateOfLastUpdate;
	}

	public void setUserOfLastUpdate(String userOfLastUpdate) {
		this.userOfLastUpdate = userOfLastUpdate;
	}

	public String getUserOfLastUpdate() {
		return userOfLastUpdate;
	}

}
