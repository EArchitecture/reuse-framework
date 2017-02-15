package com.github.earchitecture.reuse.view.spring.controller;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * O Controller deve herdar esta classe caso ele apenas implemente operações de pesquisa, e não altere os dados no banco. Nos casos onde o formulário de
 * pesquisa possuir os mesmos campos e validações que a entidade mapeada, utilize o {@link AbstractSimpleListController}
 * 
 * @author <a href="mailto:barcelos.cbc@gmail.com">Cleber Barcelos</a>
 * 
 * @param <E>
 *          Entidade mapeada JPA
 * @param <I>
 *          Tipo do objeto da PK ( Primary Key )
 * @version 0.1.0
 */
public abstract class ListController<E, I extends Serializable> extends BasicController<E, I> implements Controller {
  protected static final String ELEMENTOS = "elementos";
  private final Log logger = LogFactory.getLog(getClass());
  private E searchObject;
  private E entity;
  private Boolean autosearch = false;
  private Boolean paged = true;
  protected static final String SEARCH_STRING = "ss";
  protected static final String SEARCH_STRING_ENCODED = "ssEncoded";
  protected static final String OBJECT = "object";
  protected static final String SEARCH_OBJECT = "searchobject";
  protected static final String MSGS = "msgs";
  protected static final String ERRORS = "errors";

}
