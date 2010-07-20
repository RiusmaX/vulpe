package org.vulpe.model.entity.impl;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import org.apache.commons.lang.StringUtils;

@MappedSuperclass
@SuppressWarnings( { "serial", "unchecked" })
public abstract class AbstractVulpeBaseJPAEntity<ID extends Serializable & Comparable> extends
		AbstractVulpeBaseEntity<ID> {

	public AbstractVulpeBaseJPAEntity() {
		super();
	}

	@Override
	public String getOrderBy() {
		if (StringUtils.isBlank(super.getOrderBy())) {
			super.setOrderBy("obj.id");
		}
		return super.getOrderBy();
	}
}
