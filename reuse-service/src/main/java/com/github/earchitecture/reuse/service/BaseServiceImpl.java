package com.github.earchitecture.reuse.service;

import com.github.earchitecture.reuse.model.repository.JpaCrudRepository;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Classe de implementação de serviços de negocio.
 * 
 * @author <a href="mailto:barcelos.cbc@gmail.com">Cleber Barcelos</a>
 * @version 0.1.0
 * @param <E>
 *          Tipo da entidade a ser referenciada.
 * @param <I>
 *          Tipo do id a ser referenciado.
 */
public abstract class BaseServiceImpl<E, I extends Serializable> implements BaseService<E, I> {
  private final Logger logger = LoggerFactory.getLogger(getClass());
  private final Class<E> entityClass;
  private final Class<I> idClass;

  protected abstract JpaCrudRepository<E, I> getRepository();

  /**
   * Inicializa a instanciação do objeto e carrega informações de manipulação dde objetos base.
   */
  @SuppressWarnings("unchecked")
  public BaseServiceImpl() {
    this.entityClass = (Class<E>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    this.idClass = (Class<I>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    logger.debug("inicializando informações de base de entidades");
  }

  /**
   * Retorna o class do tipo id.
   * 
   * @return the idClass
   */
  protected Class<I> getIdClass() {
    return idClass;
  }

  /**
   * Retorna o classe da entidade mapeada na classe.
   * 
   * @return the entityClass
   */
  protected Class<E> getEntityClass() {
    return entityClass;
  }

}
