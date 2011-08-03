package org.jw.mmn.commons.model.entity;

import lombok.Getter;
import lombok.Setter;

import org.vulpe.model.entity.impl.VulpeBaseDB4OEntity;

@Getter
@Setter
@SuppressWarnings("serial")
public class State extends VulpeBaseDB4OEntity<Long> {

	private String name;

	private String abbreviation;

	private Country country;

}
