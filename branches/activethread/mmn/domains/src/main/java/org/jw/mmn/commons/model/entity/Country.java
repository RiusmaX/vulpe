package org.jw.mmn.commons.model.entity;

import lombok.Getter;
import lombok.Setter;

import org.vulpe.model.entity.impl.VulpeBaseDB4OEntity;

@SuppressWarnings("serial")
@Getter
@Setter
public class Country extends VulpeBaseDB4OEntity<Long> {

	private String name;

	private String abbreviation;

	private String code;

}
