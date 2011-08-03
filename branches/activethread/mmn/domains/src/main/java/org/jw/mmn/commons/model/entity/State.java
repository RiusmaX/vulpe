package org.jw.mmn.commons.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.jw.mmn.commons.model.entity.Country;
import org.vulpe.model.entity.impl.VulpeBaseDB4OEntity;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuppressWarnings("serial")
public class State extends VulpeBaseDB4OEntity<Long> {

	private String name;

	private String abbreviation;

	private Country country;

}
