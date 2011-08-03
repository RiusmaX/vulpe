package org.jw.mmn.core.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.vulpe.model.entity.impl.VulpeBaseDB4OEntity;

@SuppressWarnings("serial")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CharacteristicOratory extends VulpeBaseDB4OEntity<Long> {

	private Integer number;

	private String name;

	private String description;

}
