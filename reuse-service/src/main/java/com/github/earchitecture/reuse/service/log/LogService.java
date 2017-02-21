package com.github.earchitecture.reuse.service.log;

import java.util.List;

import com.github.earchitecture.reuse.exception.ValidationServiceException;

/**
 * 
 * @author ccosta
 *
 * @param <T>
 */
public interface LogService<T> {
  /**
   * 
   * @param entity
   */
  void logInsert(T entity) throws ValidationServiceException;

  /**
   * 
   * @param entity
   */
  void logUpdate(T entity) throws ValidationServiceException;

  /**
   * 
   * @param entity
   */
  void logDelete(T entity) throws ValidationServiceException;

  /**
   * 
   * @param entities
   */
  void logBatchDelete(List<T> entities) throws ValidationServiceException;
}
