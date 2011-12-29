package org.jw.mmn.commons.model.entity;

import lombok.Getter;
import lombok.Setter;

import org.vulpe.model.db4o.annotations.Inheritance;
import org.vulpe.model.entity.impl.VulpeBaseDB4OEntity;

@Inheritance
@SuppressWarnings("serial")
@Getter
@Setter
public class Address extends VulpeBaseDB4OEntity<Long> {

	private String address;

	private String number;

	private String complement;

	private String district;

	private String postCode;

	private City city;
	
}
