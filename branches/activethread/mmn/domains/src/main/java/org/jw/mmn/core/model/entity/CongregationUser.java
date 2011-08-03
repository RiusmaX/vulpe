package org.jw.mmn.core.model.entity;

import lombok.Getter;
import lombok.Setter;

import org.vulpe.model.entity.impl.VulpeBaseDB4OEntity;
import org.vulpe.security.model.entity.User;

@SuppressWarnings("serial")
@Getter
@Setter
public class CongregationUser extends VulpeBaseDB4OEntity<Long> {

	private Congregation congregation;

	private User user;

}
