package com.github.earchitecture.reuse.model.annotation;

import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Pesquisa por menor ou igual.
 * 
 * @author <a href="mailto:barcelos.cbc@gmail.com">Cleber Barcelos</a>
 * @version 0.1.0
 */
@Target({ METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface GreaterThan {
  /**
   * 
   * @return Nome do campo
   */
  String field();
}
