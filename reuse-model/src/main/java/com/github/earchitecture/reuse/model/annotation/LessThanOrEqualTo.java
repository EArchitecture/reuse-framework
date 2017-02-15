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
@Target({ METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface LessThanOrEqualTo {
    /**
     * 
     * @return nome do campo
     */
    String field();
}
