package com.github.earchitecture.reuse.view.spring.controller;

import com.github.earchitecture.reuse.view.spring.exeption.ValidationControllerException;

import java.io.Serializable;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author <a href="mailto:barcelos.cbc@gmail.com">Cleber Barcelos</a>
 * @version 0.1.0
 */
public interface CrudController<E, I extends Serializable> extends ListController<E, I> {
  interface AttributeCrud {
    String PAGE_FORM = "form";
    String OBJECT = "object";

    interface Path {
      String INDEX = "index";
    }
  }

  /**
   * Mapeia a url /{entidade}/create. Para tela de criação.
   * 
   * @param ss
   *          Formulário de pesquisa serializado
   * @param model
   *          atributos do model nas requisições httprequest.
   * @return form view
   * @throws ValidationControllerException
   *           Erro de valicação de controlador de interface
   */
  @RequestMapping(value = "/create", method = RequestMethod.GET)
  String create(@RequestParam(defaultValue = "") String ss, Model model) throws ValidationControllerException;
}
