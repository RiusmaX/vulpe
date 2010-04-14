package org.vulpe.config.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.vulpe.model.entity.VulpeBaseEntity;


@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PACKAGE)
public @interface VulpeDomains {
	
	boolean useDB4O() default false;
	
	Class<? extends VulpeBaseEntity<?>>[] cachedClass() default {};
	
	@SuppressWarnings("unchecked")
	Class[] cachedEnum() default {};
	
}
