package org.jw.mmn.core.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.vulpe.model.entity.impl.VulpeBaseDB4OEntity;
import org.vulpe.security.model.entity.User;

import org.jw.mmn.core.model.entity.Congregation;

@SuppressWarnings("serial")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CongregationUser extends VulpeBaseDB4OEntity<Long> {

	private Congregation congregation;

	private User user;

}
