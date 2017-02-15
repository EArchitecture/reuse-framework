package com.github.earchitecture.reuse.view.spring.controller;

import java.io.Serializable;

/**
 * @author <a href="mailto:barcelos.cbc@gmail.com">Cleber Barcelos</a>
 * @version 0.1.0
 */
public abstract class AbstractCrudController<E, I extends Serializable> extends AbstractListController<E, I> implements CrudController<E, I> {
  private static final long serialVersionUID = 1L;

}
