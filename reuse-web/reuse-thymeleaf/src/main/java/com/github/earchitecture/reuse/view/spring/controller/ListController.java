package com.github.earchitecture.reuse.view.spring.controller;

import com.github.earchitecture.reuse.view.spring.controller.AbstractListController.DataJson;

import java.io.Serializable;

import javax.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Defini implementação de metodos padrão para listagem, pesquisa de itens.
 * 
 * @author <a href="mailto:barcelos.cbc@gmail.com">Cleber Barcelos</a>
 * @version 0.1.0
 */
public interface ListController<E, I extends Serializable> extends Serializable {
  interface AttributeList {
    String IS_SEARCH = "isSearch";
    String PAGE_LIST = "list";
    String ELEMENTOS = "elementos";

    interface Path {
      String INDEX = "index";
    }
  }

  /**
   * Monta tela de listagem, para requisições index e / para pasta raiz.
   * 
   * @param model
   *          Attributos de requisição
   * @return Pagina a ser redirecionada.
   * @throws Exception
   *           Levanta exceções não tratadas.
   */
  @RequestMapping(value = { "/index", "/", "" }, method = RequestMethod.GET, params = "!ss")
  String listGet(Model model) throws Exception;

  /**
   * Metodo para executar a tela de pesquisa, via GET com os campos preenchidos pelo parametro ss, que contem o formulário de pesquisa serializado. É mapeado
   * para a url /{entidade}/index?ss=.
   * 
   * @param ss
   *          entidade de pesquisa (searchDTO) serializada.
   * @param model
   *          Attributos de requisição
   * @return view Pagina a ser redirecionada.
   * @throws Exception
   *           Levanta exceções não tratadas.
   */
  @RequestMapping(value = { "/index", "/", "" }, method = RequestMethod.GET, params = "ss")
  String listGetFilter(@RequestParam String ss, Model model) throws Exception;

  /**
   * Metodo para executar a tela de pesquisa, via POST recebe o formulário de pesquisa e executa a validação É mapeado para a url /{entidade}/index.
   * 
   * @param search
   *          Objeto que mapeia formulário de pesquisa
   * @param result
   *          Resultado da validação, que é acrescentado ao model para exibição de mensagens de erro na tela
   * @param model
   *          Attributos de requisição
   * @return o identificado da tela de pesquisa. * @throws Exception Levanta exceções não tratadas.
   */
  @RequestMapping(value = { "/index", "/", "" }, method = RequestMethod.POST)
  String listPost(@Valid E search, BindingResult result, Model model) throws Exception;

  @RequestMapping(value = { "/index", "/", "" }, method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
  DataJson listPostJson(@Valid E search, BindingResult result, Model model) throws Exception;

  /**
   * Operação REST de get por id.
   * 
   * @param id
   *          id da entidade
   * @return Retorna o json com a entidade selecionada pelo id
   * @throws Exception
   *           Caso o id não seja encontrado
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  E get(@PathVariable I id) throws Exception;
}
