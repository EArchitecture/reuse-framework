package com.github.earchitecture.reuse.model.annotation;

import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author ccosta
 *
 */
@Target({METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface StartsWith {
	/**
	 * 
	 * @return valor default true
	 */
	boolean lowercase() default true;
	/**
	 * 
	 * @return valor default true
	 */
	boolean trim() default true;
}
