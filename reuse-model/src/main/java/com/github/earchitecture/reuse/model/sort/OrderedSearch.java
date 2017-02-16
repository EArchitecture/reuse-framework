package com.github.earchitecture.reuse.model.sort;

import org.springframework.data.domain.Sort;

/**
 * Define ordenações de pesquisa
 * 
 * @author <a href="mailto:barcelos.cbc@gmail.com">Cleber Barcelos</a>
 * @version 0.1.0
 */
public interface OrderedSearch {

  /**
   * Direção da ordenação
   * 
   * @return Sort.Direction
   */
  Sort.Direction getDirection();

  /**
   * Propriedade de ordenação.
   * 
   * @return String
   */
  String getSortedProperty();
}
