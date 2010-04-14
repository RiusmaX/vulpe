package org.vulpe.model.annotations.db4o;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface SODAQueryAttribute {

	String name();

	String type();

}