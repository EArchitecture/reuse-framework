package com.github.earchitecture.reuse.service;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.ValidationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.github.earchitecture.reuse.exception.ValidationServiceException;
import com.github.earchitecture.reuse.model.entity.Identifiable;
import com.github.earchitecture.reuse.service.log.LogService;

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
public abstract class CrudServiceImpl<E extends Identifiable<I>, I extends Serializable> extends ListServiceImpl<E, I> implements CrudService<E, I> {

  private final Log logger = LogFactory.getLog(getClass());

  /*
   * (non-Javadoc)
   * 
   * @see com.github.earchitecture.reuse.service.CrudService#saveTransactionReadOnly(java.lang.Object)
   */
  @Override
  @Transactional
  public E saveTransactionReadOnly(E entity) throws ValidationServiceException {
    // se o id está preenchido é update, chama o log antes da atualização
    // para os casos onde precisa-se saber o que foi alterado
    boolean update = false;
    if (entity.getId() != null) {
      getLogService().logUpdate(entity);
      update = true;
    }
    E savedEntity = getRepository().save(entity);
    getRepository().flush();
    getRepository().sessionClear();
    if (!update) {
      getLogService().logInsert(savedEntity);
    }
    return savedEntity;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.github.earchitecture.reuse.service.CrudService#save(java.lang.Iterable)
   */
  @Override
  @Transactional
  public List<E> save(Iterable<E> entities) throws ValidationServiceException {
    return getRepository().save(entities);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.github.earchitecture.reuse.service.CrudService#save(java.lang.Object)
   */
  @Override
  @Transactional
  public E save(E entity) throws ValidationServiceException {
    // se o id está preenchido é update, chama o log antes da atualização
    // para os casos onde precisa-se saber o que foi alterado
    boolean update = false;
    if (entity.getId() != null) {
      getLogService().logUpdate(entity);
      update = true;
    }
    E savedEntity = getRepository().save(entity);
    getRepository().flush();
    getRepository().sessionClear();
    if (!update) {
      getLogService().logInsert(savedEntity);
    }
    return savedEntity;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.github.earchitecture.reuse.service.CrudService#delete(java.io.Serializable)
   */
  @Override
  @Transactional
  public void delete(I id) throws ValidationServiceException {

    E entity = get(id);
    getLogService().logDelete(entity);
    getRepository().delete(id);

  }

  /*
   * (non-Javadoc)
   * 
   * @see com.github.earchitecture.reuse.service.CrudService#deleteByExample(java.lang.Object)
   */
  @Override
  @Transactional
  public void deleteByExample(E entity) throws ValidationServiceException {
    List<E> entities = find(entity);
    getLogService().logBatchDelete(entities);
    getRepository().deleteInBatch(entities);
  }

  /**
   * Recupera login conectado
   * 
   * @return Usuario autenticado
   * @throws ValidationServiceException
   */
  public String getUsername() throws ValidationServiceException {
    // TODO IMPLEMENTAR RECUPERAR USUÁRIO
    return "";
  }

  /**
   * Função para registro de log.
   * 
   * @return Retorna uma instancia do log.
   * @throws ValidationServiceException
   *           Caso ocorra algum erro , será lançando exception
   */
  protected LogService<E> getLogService() throws ValidationServiceException {
    return new SimpleLogService();
  }

  private class SimpleLogService implements LogService<E> {
    @Override
    public void logInsert(E entity) throws ValidationServiceException {
      logger.debug("Usuário: " + getUsername() + " - Inserindo: " + entity);
    }

    @Override
    public void logUpdate(E entity) throws ValidationServiceException {
      logger.debug("Usuário: " + getUsername() + " - Atualizando: " + entity);
    }

    @Override
    public void logDelete(E entity) throws ValidationServiceException {
      logger.debug("Usuário: " + getUsername() + " - Excluindo: " + entity);
    }

    @Override
    public void logBatchDelete(List<E> entities) throws ValidationServiceException {
      for (E e : entities) {
        logDelete(e);
      }
    }
  }
}
