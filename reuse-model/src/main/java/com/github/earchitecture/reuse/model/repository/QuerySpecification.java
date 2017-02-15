package com.github.earchitecture.reuse.model.repository;

import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

/**
 * Descreve operações de query specificação.
 * 
 * @author <a href="mailto:barcelos.cbc@gmail.com">Cleber Barcelos</a>
 * @version 0.1.0
 * @param <T>
 *          Tipo da entidade
 */
public interface QuerySpecification<T> {
  /**
   * Configura query a ser aplicada.
   * 
   * @param query
   *          Query a ser aplicada.
   * @param cb
   *          operadores
   * @return Tuplas retornadas.
   */
  CriteriaQuery<Tuple> toPredicate(CriteriaQuery<Tuple> query, CriteriaBuilder cb);

  /**
   * Converte a tupla em objeto.
   * 
   * @param tuple
   *          tupla a ser convertida
   * @return Objeto transformado.
   */
  T toObject(Tuple tuple);
}
