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
public @interface GreaterThanOrEqualTo {
    /**
     * Retorna o nome do campo Field
     * 
     * @return Nome do campo field
     */
    String field();
}
