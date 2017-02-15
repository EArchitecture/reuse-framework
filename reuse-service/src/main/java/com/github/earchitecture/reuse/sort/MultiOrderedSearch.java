package com.github.earchitecture.reuse.sort;

import java.util.List;

import org.springframework.data.domain.Sort.Order;

/**
 * 
 * @author <a href="mailto:barcelos.cbc@gmail.com">Cleber Barcelos</a>
 * @version 0.1.0
 */
public interface MultiOrderedSearch {
  /**
   * Retorna uma lista de ordenaçãoes.
   * 
   * @return List order
   */
  List<Order> getDirections();
}
