package org.jw.mmn.commons.model.entity;

import lombok.Getter;
import lombok.Setter;

import org.vulpe.model.annotations.db4o.Inheritance;
import org.vulpe.model.entity.impl.VulpeBaseDB4OEntity;

@Inheritance
@SuppressWarnings("serial")
@Getter
@Setter
public class Phone extends VulpeBaseDB4OEntity<Long> {

	private String number;

	private PhoneType type;

	private boolean principal;

}
