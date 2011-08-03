package org.jw.mmn.commons.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.jw.mmn.commons.model.entity.State;
import org.vulpe.model.entity.impl.VulpeBaseDB4OEntity;

@SuppressWarnings("serial")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class City extends VulpeBaseDB4OEntity<Long> {

	private String name;

	private String abbreviation;

	private State state;

}
