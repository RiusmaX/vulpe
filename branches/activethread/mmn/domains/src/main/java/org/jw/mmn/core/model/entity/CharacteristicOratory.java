package org.jw.mmn.core.model.entity;

import lombok.Getter;
import lombok.Setter;

import org.vulpe.model.entity.impl.VulpeBaseDB4OEntity;

@SuppressWarnings("serial")
@Getter
@Setter
public class CharacteristicOratory extends VulpeBaseDB4OEntity<Long> {

	private Integer number;

	private String name;

	private String description;

}
