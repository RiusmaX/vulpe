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

	String projectPackage();
	
	String[] i18n() default { "VulpeResources", "VulpeSecurityResources", "ApplicationResources" };

	String i18nClass() default "org.vulpe.controller.common.MultipleResourceBundle";

	VulpeView view() default @VulpeView;

	VulpeUpload upload() default @VulpeUpload;

	boolean mobileEnabled() default false;

	VulpeMobile mobile() default @VulpeMobile;

	String theme() default "default";

	boolean audit() default true;

	boolean security() default true;

	MenuType frontendMenuType() default MenuType.DROPPY;

	MenuType backendMenuType() default MenuType.DROPPY;

	enum MenuType {
		DROPPY, NONE
	}
}
