package org.vulpe.config.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PACKAGE)
public @interface VulpeMobile {

	String viewportWidth() default "320";

	String viewportHeight() default "480";

	String viewportUserScalable() default "yes";

	String viewportInitialScale() default "2.5";

	String viewportMaximumScale() default "5.0";

	String viewportMinimumScale() default "1.0";
}
