package org.jw.mmn.core.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Year {

	private Integer number;

	public Year(Integer number){
		this.number = number;
	}
}
