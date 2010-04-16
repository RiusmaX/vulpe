package org.vulpe.config.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PACKAGE)
public @interface VulpeView {

	int messageSlideUpTime() default 10000;
	
	boolean showButtonAsImage() default false;

	boolean showButtonIcon() default false;

	boolean showButtonText() default true;

	int widthButtonIcon() default 16;

	int widthMobileButtonIcon() default 32;

	int heightButtonIcon() default 16;

	int heightMobileButtonIcon() default 32;
	
	boolean backendCenteredLayout() default false;
	
	boolean frontendCenteredLayout() default false;

}
