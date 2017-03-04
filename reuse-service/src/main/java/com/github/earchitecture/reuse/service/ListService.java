package com.github.earchitecture.reuse.service;

import com.github.earchitecture.reuse.exception.ValidationServiceException;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Define operações de negocio de listagem dos sistemas.
 * 
 * @author <a href="mailto:barcelos.cbc@gmail.com">Cleber Barcelos</a>
 * @version 0.1.0
 * @param <E>
 *          Tipo da entidade a ser referenciada.
 * @param <I>
 *          Tipo do id a ser referenciado.
 */
public interface ListService<E, I extends Serializable> extends BaseService<E, I> {
  /**
   * Recupera entidade por id.
   * 
   * @param id
   *          id da entidade a ser localizado.
   * @return Caso exista algum objeto com o id, será retorndo o mesmo, caso contrario retorna null.
   * @throws ValidationServiceException
   *           validações de negocio.
   */
  E get(I id) throws ValidationServiceException;

  /**
   * Retorna a quantidade de registro no banco de dados.
   * 
   * @return Quantidade de registro
   */
  long count();

  /**
   * Retorna a quantidade de registro por filtro by example
   * 
   * @param entity
   *          entidade a ser pesquisada
   * @return quantidade de registros.
   */
  long count(E entity);

  /**
   * Retorna uma lista de objeto, conforme a pesquisa por exemplo.
   * 
   * @param entity
   *          Parametros de entidade a ser pesquisado.
   * @return Lista de objeto, caso encontre alguma relação com os parametros.
   * @throws ValidationServiceException
   *           validações de negocio.
   */
  List<E> find(E entity) throws ValidationServiceException;

  /**
   * Retorna um objeto, conforme a pesquisa por exemplo.
   * 
   * @param entity
   *          Parametros de entidade a ser pesquisado.
   * @return Objeto pesquisado ou null caso não encontre correspondencia.
   * @throws ValidationServiceException
   *           validações de negocio.
   */
  E findOne(E entity) throws ValidationServiceException;

  /**
   * Pesquisa uma lista de objetos, conforme a pesquisa por exemplo.
   * 
   * @param entity
   *          Parametros de entidade a ser pesquisado.
   * @param sort
   *          Coluna de ordenação na pesquisa.
   * @return Lista de Objetos ordenada, ou null caso não encontre correspondencia.
   * @throws ValidationServiceException
   *           validações de negocio.
   */
  List<E> findSort(E entity, Sort sort) throws ValidationServiceException;

  /**
   * 
   * @param entity
   *          Parametros de entidade a ser pesquisado.
   * @param page
   *          Paginação a ser filtrada.
   * @return Pagina conforme o filtro de paginação.
   * @throws ValidationServiceException
   *           validações de negocio.
   */
  Page<E> findPage(E entity, Pageable page) throws ValidationServiceException;

  /**
   * Pesquisa todos os objeto.
   * 
   * @return Lista completa de objetos.
   * @throws ValidationServiceException
   *           validações de negocio.
   */
  List<E> findAll() throws ValidationServiceException;

  /**
   * Pesquisa todos objeto com ordenação.
   * 
   * @param sort
   *          Coluna de ordenação na pesquisa.
   * @return Lista completa de objetos ordenada.
   * @throws ValidationServiceException
   *           validações de negocio.
   */
  List<E> findAll(Sort sort) throws ValidationServiceException;

  /**
   * Pesquisa todos os registro por pagina.
   * 
   * @param page
   *          Pagina a ser retornada.
   * @return Pagina com objetos correspondente.
   * @throws ValidationServiceException
   *           validações de negocio.
   */
  Page<E> findAllPage(Pageable page) throws ValidationServiceException;
}
