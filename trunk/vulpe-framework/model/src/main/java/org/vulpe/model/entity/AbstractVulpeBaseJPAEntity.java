package org.vulpe.model.entity;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

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
