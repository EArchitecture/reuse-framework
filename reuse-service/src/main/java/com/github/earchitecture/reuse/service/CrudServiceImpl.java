package com.github.earchitecture.reuse.service;

import com.github.earchitecture.reuse.exception.ValidationServiceException;

import java.io.Serializable;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

/**
 * Define operações de alteração, exclusão e persistencia para entidades de crud.
 * 
 * @author <a href="mailto:barcelos.cbc@gmail.com">Cleber Barcelos</a>
 * @version 0.1.0
 * @param <E>
 *          Tipo da entidade a ser referenciada.
 * @param <I>
 *          Tipo do id a ser referenciado.
 */
public abstract class CrudServiceImpl<E, I extends Serializable> extends ListServiceImpl<E, I> implements CrudService<E, I> {

  /*
   * (non-Javadoc)
   * 
   * @see com.github.earchitecture.reuse.service.CrudService#saveTransactionReadOnly(java.lang.Object)
   */
  @Override
  @Transactional
  public E saveTransactionReadOnly(E entity) throws ValidationServiceException {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.github.earchitecture.reuse.service.CrudService#save(java.lang.Iterable)
   */
  @Override
  @Transactional
  public List<E> save(Iterable<E> entities) throws ValidationServiceException {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.github.earchitecture.reuse.service.CrudService#save(java.lang.Object)
   */
  @Override
  @Transactional
  public E save(E entity) throws ValidationServiceException {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.github.earchitecture.reuse.service.CrudService#delete(java.io.Serializable)
   */
  @Override
  @Transactional
  public void delete(I id) throws ValidationServiceException {
    // TODO Auto-generated method stub

  }

  /*
   * (non-Javadoc)
   * 
   * @see com.github.earchitecture.reuse.service.CrudService#deleteByExample(java.lang.Object)
   */
  @Override
  @Transactional
  public void deleteByExample(E entity) throws ValidationServiceException {
    // TODO Auto-generated method stub

  }
}
