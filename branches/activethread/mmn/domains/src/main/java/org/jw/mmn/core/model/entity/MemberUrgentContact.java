package org.jw.mmn.core.model.entity;

import lombok.Getter;
import lombok.Setter;

import org.vulpe.model.entity.impl.VulpeBaseDB4OEntity;

@SuppressWarnings("serial")
@Getter
@Setter
public class MemberUrgentContact extends VulpeBaseDB4OEntity<Long> {

	private Member member;

	private String name;

	private String phone;

}
