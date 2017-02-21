package com.github.earchitecture.reuse.service.log;

import java.util.List;

import com.github.earchitecture.reuse.exception.ValidationServiceException;

/**
 * 
 * @author ccosta
 *
 * @param <T> Tipo da entidade a ser logada
 */
public interface LogService<T> {
  /**
   * Registra log de insert.
   * @param entity entidade a ser logada
   */
  void logInsert(T entity) throws ValidationServiceException;

  /**
   * Registra log de atualização.
   * @param entity entidade a ser logada
   */
  void logUpdate(T entity) throws ValidationServiceException;

  /**
   * Registra log ao excluir.
   * @param entity entidade a ser logada.
   */
  void logDelete(T entity) throws ValidationServiceException;

  /**
   * Registra log de exclusão em bat
   * @param entities entidade a ser logada
   */
  void logBatchDelete(List<T> entities) throws ValidationServiceException;
}
