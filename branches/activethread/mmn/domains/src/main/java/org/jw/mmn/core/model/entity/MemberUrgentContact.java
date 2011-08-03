package org.jw.mmn.core.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.vulpe.model.entity.impl.VulpeBaseDB4OEntity;

import org.jw.mmn.core.model.entity.Member;

@SuppressWarnings("serial")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class MemberUrgentContact extends VulpeBaseDB4OEntity<Long> {

	private Member member;

	private String name;

	private String phone;

}
