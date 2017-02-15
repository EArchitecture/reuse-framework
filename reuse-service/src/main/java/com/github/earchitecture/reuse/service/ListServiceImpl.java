package com.github.earchitecture.reuse.service;

import com.github.earchitecture.reuse.exception.ValidationServiceException;

import java.awt.print.Pageable;
import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

/**
 * Lista de serviço de operações de listagem, filtro e paginação.
 * 
 * @author <a href="mailto:barcelos.cbc@gmail.com">Cleber Barcelos</a>
 * @version 0.1.0
 * 
 * @param <E>
 *          Tipo da entidade a ser referenciada.
 * @param <I>
 *          Tipo do id a ser referenciado.
 */
public abstract class ListServiceImpl<E, I extends Serializable> extends BaseServiceImpl<E, I> implements ListService<E, I> {

  /*
   * (non-Javadoc)
   * 
   * @see com.github.earchitecture.reuse.service.ListService#get(java.lang.Object)
   */
  @Override
  @Transactional(readOnly = true)
  public E get(I id) throws ValidationServiceException {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.github.earchitecture.reuse.service.ListService#find(java.lang.Object)
   */
  @Override
  @Transactional(readOnly = true)
  public List<E> find(E entity) throws ValidationServiceException {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.github.earchitecture.reuse.service.ListService#findOne(java.lang.Object)
   */
  @Override
  @Transactional(readOnly = true)
  public E findOne(E entity) throws ValidationServiceException {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.github.earchitecture.reuse.service.ListService#findSort(java.lang.Object, org.springframework.data.domain.Sort)
   */
  @Override
  @Transactional(readOnly = true)
  public List<E> findSort(E entity, Sort sort) throws ValidationServiceException {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.github.earchitecture.reuse.service.ListService#findPage(java.lang.Object, java.awt.print.Pageable)
   */
  @Override
  @Transactional(readOnly = true)
  public Page<E> findPage(E entity, Pageable page) throws ValidationServiceException {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.github.earchitecture.reuse.service.ListService#findAll()
   */
  @Override
  @Transactional(readOnly = true)
  public List<E> findAll() throws ValidationServiceException {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.github.earchitecture.reuse.service.ListService#findAll(org.springframework.data.domain.Sort)
   */
  @Override
  @Transactional(readOnly = true)
  public List<E> findAll(Sort sort) throws ValidationServiceException {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.github.earchitecture.reuse.service.ListService#findAll(java.awt.print.Pageable)
   */
  @Override
  @Transactional(readOnly = true)
  public Page<E> findAll(Pageable page) throws ValidationServiceException {
    // TODO Auto-generated method stub
    return null;
  }

}
