package org.jw.mmn.core.model.entity;

import lombok.Getter;
import lombok.Setter;

import org.jw.mmn.commons.model.entity.Email;
import org.vulpe.model.db4o.annotations.Inheritance;

@Inheritance
@SuppressWarnings("serial")
@Getter
@Setter
public class MemberEmail extends Email {

	private Member member;

}
