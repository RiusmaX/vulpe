package org.vulpe.model.entity;

public interface LogicEntity {
	public enum Status {
		C, U, D
	}

	void setStatus(Status status);

	Status getStatus();
}