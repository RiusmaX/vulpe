package org.jw.mmn.core.model.entity;

import lombok.Getter;
import lombok.Setter;

import org.jw.mmn.commons.model.entity.Address;
import org.vulpe.model.db4o.annotations.Inheritance;

@Inheritance
@SuppressWarnings("serial")
@Getter
@Setter
public class MemberAddress extends Address {

	private Member member;

}
