package org.vulpe.config.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PACKAGE)
public @interface VulpeProject {

	String name();

	VulpeView view() default @VulpeView;

	VulpeUpload upload() default @VulpeUpload;

	boolean mobileEnabled() default false;

	VulpeMobile mobile() default @VulpeMobile;

	String theme() default "default";

	boolean audit() default true;

	boolean security() default true;

	MenuType menuType() default MenuType.DROPPY;

	enum MenuType {
		DROPPY, NONE
	}
}
