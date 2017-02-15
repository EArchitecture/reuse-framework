package com.github.earchitecture.reuse.view.spring.impl.controller;

import com.github.earchitecture.reuse.exception.ValidationServiceException;
import com.github.earchitecture.reuse.model.impl.entity.Todo;
import com.github.earchitecture.reuse.service.CrudService;
import com.github.earchitecture.reuse.view.spring.controller.AbstractListController;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Testa implementação do abstract list para operações de listagem, filtro.
 * 
 * @author <a href="mailto:barcelos.cbc@gmail.com">Cleber Barcelos</a>
 * @version 0.1.0
 */
@Controller
@RequestMapping("/todo")
public class TodoListController extends AbstractListController<Todo, Long> {
  private static final long serialVersionUID = 1L;

  /*
   * (non-Javadoc)
   * 
   * @see com.github.earchitecture.reuse.view.spring.controller.AbstractListController#getService()
   */
  @Override
  protected CrudService<Todo, Long> getService() {
    return new CrudService<Todo, Long>() {

      @Override
      public Todo get(Long id) throws ValidationServiceException {
        return null;
      }

      @Override
      public List<Todo> find(Todo entity) throws ValidationServiceException {
        List<Todo> todo = new ArrayList<Todo>();
        todo.add(new Todo());
        return todo;
      }

      @Override
      public Todo findOne(Todo entity) throws ValidationServiceException {
        return null;
      }

      @Override
      public List<Todo> findSort(Todo entity, Sort sort) throws ValidationServiceException {
        // TODO Auto-generated method stub
        return null;
      }

      @Override
      public Page<Todo> findPage(Todo entity, Pageable page) throws ValidationServiceException {
        // TODO Auto-generated method stub
        return null;
      }

      @Override
      public List<Todo> findAll() throws ValidationServiceException {
        // TODO Auto-generated method stub
        return null;
      }

      @Override
      public List<Todo> findAll(Sort sort) throws ValidationServiceException {
        // TODO Auto-generated method stub
        return null;
      }

      @Override
      public Page<Todo> findAll(Pageable page) throws ValidationServiceException {
        // TODO Auto-generated method stub
        return null;
      }

      @Override
      public Todo saveTransactionReadOnly(Todo entity) throws ValidationServiceException {
        // TODO Auto-generated method stub
        return null;
      }

      @Override
      public List<Todo> save(Iterable<Todo> entities) throws ValidationServiceException {
        // TODO Auto-generated method stub
        return null;
      }

      @Override
      public Todo save(Todo entity) throws ValidationServiceException {
        // TODO Auto-generated method stub
        return null;
      }

      @Override
      public void delete(Long id) throws ValidationServiceException {
        // TODO Auto-generated method stub

      }

      @Override
      public void deleteByExample(Todo entity) throws ValidationServiceException {
        // TODO Auto-generated method stub

      }
    };
  }

}
