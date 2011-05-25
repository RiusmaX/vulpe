package org.jw.mmn.commons.model.entity;

import org.jw.mmn.commons.model.entity.City;
import org.vulpe.model.annotations.db4o.Inheritance;
import org.vulpe.model.entity.impl.VulpeBaseDB4OEntity;

@Inheritance
@SuppressWarnings("serial")
public class Address extends VulpeBaseDB4OEntity<Long> {

	private String address;

	private String number;

	private String complement;

	private String district;

	private String postCode;

	private City city;

	public void setNumber(String number) {
		this.number = number;
	}

	public String getNumber() {
		return number;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	public String getComplement() {
		return complement;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getDistrict() {
		return district;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public City getCity() {
		return city;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

}
