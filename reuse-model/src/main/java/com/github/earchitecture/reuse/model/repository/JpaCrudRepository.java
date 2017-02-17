package com.github.earchitecture.reuse.model.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * {@link JpaCrudRepository} Repositorio customizado para operações de refresh e evict.
 *
 * @param <T>
 *          Class Object
 * @param <I>
 *          Tipo de classe de PK (Primary Key)
 */
@NoRepositoryBean
public interface JpaCrudRepository<T, I extends Serializable> extends JpaSpecificationExecutor<T>, QueryDslPredicateExecutor<T> {

  /**
   * Aatualiza o estado da instância a partir do banco de dados.
   * 
   * @param entity
   *          Objeto a ser realizado a refresh
   */
  void refresh(T entity);

  /**
   * Remover um elemento do cache de segundo nível.
   * 
   * @param entity
   *          Objeto a ser realizado a operação de evict
   */
  void evict(T entity);

  /**
   * Limpa sesão do hibernate.
   */
  void sessionClear();

  /**
   * Pesquisa por query specification.
   * 
   * @param querySpecs
   *          Query customizada de pesquisa.
   * @return Lista de objeto por query.
   */
  List<T> findAll(QuerySpecification<T> querySpecs);
}
