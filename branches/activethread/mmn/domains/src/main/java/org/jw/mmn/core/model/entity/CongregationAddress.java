package org.jw.mmn.core.model.entity;

import lombok.Getter;
import lombok.Setter;

import org.jw.mmn.commons.model.entity.Address;
import org.vulpe.model.annotations.db4o.Inheritance;

@Inheritance
@SuppressWarnings("serial")
@Getter
@Setter
public class CongregationAddress extends Address {

	private Congregation congregation;

}
