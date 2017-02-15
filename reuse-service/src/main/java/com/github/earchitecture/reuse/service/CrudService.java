package com.github.earchitecture.reuse.service;

import com.github.earchitecture.reuse.exception.ValidationServiceException;

import java.io.Serializable;
import java.util.List;

/**
 * Define operações de salvar, excluir, editar e listagem para entidades de crud.
 * 
 * @author <a href="mailto:barcelos.cbc@gmail.com">Cleber Barcelos</a>
 * @version 0.1.0
 * @param <E>
 *          Tipo da entidade a ser referenciada.
 * @param <I>
 *          Tipo do id a ser referenciado.
 */
public interface CrudService<E, I extends Serializable> extends ListService<E, I> {

  /**
   * Salva entidade com transação read only.
   * 
   * @param entity
   *          Entidade a ser persistida.
   * @return Entidade persistida.
   * @throws ValidationServiceException
   *           validações de negocio.
   */
  E saveTransactionReadOnly(E entity) throws ValidationServiceException;

  /**
   * Persiste uma lista de entidades
   * 
   * @param entities
   *          Lista de entidades a serem persistidas
   * @return Lista de entidade persistida.
   * @throws ValidationServiceException
   *           validações de negocio.
   */
  List<E> save(Iterable<E> entities) throws ValidationServiceException;

  /**
   * Persiste entidade.
   * 
   * @param entity
   *          Entidade a ser persistida.
   * @return Entidade persistida no banco
   * @throws ValidationServiceException
   *           validações de negocio.
   */
  E save(E entity) throws ValidationServiceException;

  /**
   * Remove objeto pelo id.
   * 
   * @param id
   *          id do objeto a ser removido.
   * @throws ValidationServiceException
   *           validações de negocio.
   */
  void delete(I id) throws ValidationServiceException;

  /**
   * Remove objeto por exemplo.
   * 
   * @param entity
   *          Objeto a ser removido.
   * @throws ValidationServiceException
   *           validações de negocio.
   */
  void deleteByExample(E entity) throws ValidationServiceException;
}
