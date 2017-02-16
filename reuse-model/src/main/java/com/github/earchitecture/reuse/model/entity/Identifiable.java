package com.github.earchitecture.reuse.model.entity;

import java.io.Serializable;

/**
 * Descreve metodo e operação para primary key.
 *
 * @param <I>
 *          Tipo da PK (Primary Key)
 */
public interface Identifiable<I extends Serializable> extends Serializable {
  /**
   * Retorna o valor do objeto de Primary Key do objeto.
   * 
   * @return Objeto que implementa {@link Serializable}
   */
  I getId();

  /**
   * Atribui o valor do id do objeto.
   * 
   * @param id
   *          id do objeto.
   */
  void setId(I id);
}