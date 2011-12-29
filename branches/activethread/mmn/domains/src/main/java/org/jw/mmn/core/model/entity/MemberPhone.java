package org.jw.mmn.core.model.entity;

import lombok.Getter;
import lombok.Setter;

import org.jw.mmn.commons.model.entity.Phone;
import org.vulpe.model.db4o.annotations.Inheritance;

@Inheritance
@SuppressWarnings("serial")
@Getter
@Setter
public class MemberPhone extends Phone {

	private Member member;

}
