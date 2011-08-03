package org.jw.mmn.core.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.jw.mmn.commons.model.entity.Address;

import org.jw.mmn.core.model.entity.Member;
import org.vulpe.model.annotations.db4o.Inheritance;

@Inheritance
@SuppressWarnings("serial")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class MemberAddress extends Address {

	private Member member;

}
