package org.jw.mmn.commons.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.jw.mmn.commons.model.entity.City;
import org.vulpe.model.annotations.db4o.Inheritance;
import org.vulpe.model.entity.impl.VulpeBaseDB4OEntity;

@Inheritance
@SuppressWarnings("serial")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Address extends VulpeBaseDB4OEntity<Long> {

	private String address;

	private String number;

	private String complement;

	private String district;

	private String postCode;

	private City city;
	
}
