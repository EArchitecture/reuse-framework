package com.github.earchitecture.reuse.view.spring.controller;

import com.github.earchitecture.reuse.model.sort.MultiOrderedSearch;
import com.github.earchitecture.reuse.model.sort.OrderedSearch;
import com.github.earchitecture.reuse.service.CrudService;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * O Controller deve herdar esta classe caso ele apenas implemente operações de pesquisa, e não altere os dados no banco. Nos casos onde o formulário de
 * pesquisa possuir os mesmos campos e validações que a entidade mapeada, utilize o {@link BasicController}
 * 
 * @author <a href="mailto:barcelos.cbc@gmail.com">Cleber Barcelos</a>
 * 
 * @param <E>
 *          Entidade mapeada JPA
 * @param <I>
 *          Tipo do objeto da PK ( Primary Key )
 * @version 0.1.0
 */
public abstract class AbstractListController<E, I extends Serializable> extends BasicController implements ListController<E, I> {
  private static final long serialVersionUID = 1L;
  private boolean autoSearch = false;
  private boolean pagination = false;
  private E searchObject;
  private final Class<E> searchClass;

  /**
   * Inicializa o parametro de class tipo do objeto de pesquisa.
   */
  @SuppressWarnings("unchecked")
  public AbstractListController() {
    this.searchClass = (Class<E>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
  }

  protected abstract CrudService<E, I> getService();

  /*
   * (non-Javadoc)
   * 
   * @see com.github.earchitecture.reuse.view.spring.controller.ListController#listGet(org.springframework.ui.Model)
   */
  @Override
  public String listGet(Model model) throws Exception {
    BindingResult result = new BeanPropertyBindingResult(this.getSearchObject(), this.getSearchClass().getName());
    if (isAutoSearch()) {
      model.addAttribute(AttributeList.IS_SEARCH, false);
      return listPost(searchObject, result, model);
    } else {
      validateList(result);
      model.addAttribute(AttributeList.ELEMENTOS, new ArrayList<Object>());
      parametrosListDefault(model, "");
      if (!result.hasErrors()) {
        listModelFiller(model);
      } else {
        processErroMens(result, model);
      }
    }
    model.addAttribute(AttributeList.IS_SEARCH, false);
    setTypeTela(model, TypeTela.LIST);
    return this.getViewPage();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.github.earchitecture.reuse.view.spring.controller.ListController#listPost(java.lang.Object, org.springframework.validation.BindingResult,
   * org.springframework.ui.Model)
   */
  @Override
  public String listPost(@Valid E search, BindingResult result, Model model) throws Exception {
    this.validateList(result);
    // addParameters(searchDTO);
    this.parametrosListDefault(model, search);
    setTypeTela(model, TypeTela.LIST);
    if (result.hasErrors()) {
      model.addAttribute(BindingResult.MODEL_KEY_PREFIX + "object", result);
      processErroMens(result, model);
      return this.getViewPage();
    } else {
      listModelFiller(model);
      try {
        if (this.isPagination()) {
          doListPost(search, model);
        } else {
          model.addAttribute(AttributeList.ELEMENTOS, getBySearchObject(search, null, null));
        }
      } catch (Exception exp) {
        result.addError(new ObjectError("", "Erro ao realizar pesquisa."));
      }
    }
    return this.getViewPage();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.github.earchitecture.reuse.view.spring.controller.ListController#listGet(java.lang.String, org.springframework.ui.Model)
   */
  @Override
  public String listGetFilter(String ss, Model model) throws Exception {
    return null;
  }

  /**
   * Metodo para ser reescrito caso seja necessário acrescentar elementos adicionais ao model da tela de pesquisa.
   * 
   * @param model
   *          Attributos de requisição
   */
  protected void listModelFiller(Model model) throws Exception {
  }

  /**
   * Implementa validações especifica na listagem.
   * 
   * @param result
   *          bind result
   */
  protected void validateList(BindingResult result) {
  }

  /**
   * Seta parametros default para a aplicação.
   * 
   * @param model
   *          Atributos da requisição
   * @param search
   *          objeto de pesquisa
   */
  protected void parametrosListDefault(Model model, Object search) {
    if (search instanceof String) {
      model.addAttribute(ParamObject.SEARCH_STRING, search);
      model.addAttribute(ParamObject.SEARCH_OBJECT, search);
    } else {
      model.addAttribute(ParamObject.SEARCH_STRING, super.toString(search));
      model.addAttribute(ParamObject.SEARCH_OBJECT, search);
    }
  }

  /**
   * Metodo que executa a pesquisa efetivamente, pode ser sobrescrito caso seja necessária alguma customização Para a ordenação, verifica primeiro se o
   * controller implementa {@link OrderedSearch} ou {@link MultiOrderedSearch}. Caso não implemente, verifica se a entidade de pesquisa implementa uma dessas
   * interfaces, caso contrário, executa a pesquisa sem ordenação
   * 
   * @param search
   *          Entidade contendo os parametros do formulário de pesquisa
   * @param model
   *          Atributos da requisição
   */
  protected void doListPost(E search, Model model) {
    try {
      if (this instanceof OrderedSearch) {
        // doOrderedSearch((OrderedSearch) this, search, model);
      } else if (this instanceof MultiOrderedSearch) {
        // model.addAttribute(AttributeList.ELEMENTOS, get(search, ((MultiOrderedSearch) this).getDirections()));
      } else if (search instanceof OrderedSearch) {
        // doOrderedSearch((OrderedSearch) search, search, model);
      } else if (search instanceof MultiOrderedSearch) {
        // model.addAttribute(AttributeList.ELEMENTOS, get(search, ((MultiOrderedSearch) search).getDirections()));
      } else {
        model.addAttribute(AttributeList.ELEMENTOS, getBySearchObject(search, null, null));
      }
    } catch (Exception exp) {
      throw new RuntimeException(exp);
    }
  }

  protected List<E> getBySearchObject(@ModelAttribute E search, String orderByAsc, String orderByDesc) throws Exception {
    List<E> response;
    if (orderByAsc != null) {
      Sort sort = new Sort(Sort.Direction.ASC, orderByAsc);
      sort.getOrderFor(orderByAsc).ignoreCase();
      response = getService().findSort(search, sort);
    } else if (orderByDesc != null) {
      Sort sort = new Sort(Sort.Direction.DESC, orderByDesc);
      sort.getOrderFor(orderByDesc).ignoreCase();
      response = getService().findSort(search, sort);
    } else {
      response = getService().find(search);
    }
    return response;
  }

  /**
   * Retorna a situação da auto pesquisa.
   * 
   * @return Se true habilita pesquisa na tela quando inicia, false, espera o usuario realizar pesquisa
   */
  protected boolean isAutoSearch() {
    return autoSearch;
  }

  /**
   * Defini que a sempre que entrar na tela de pesquisa vai executar uma consulta no banco.
   */
  protected void enableAutoSearch() {
    this.autoSearch = true;
  }

  /**
   * Defini que quando disabilitado, para retornar dados e necessario pesquisar.
   */
  protected void disableAutoSearch() {
    this.autoSearch = false;
  }

  /**
   * Habilita paginação.
   */
  protected void enablePagination() {
    this.pagination = true;
  }

  /**
   * Desabilita paginação.
   */
  protected void disabledePagination() {
    this.pagination = false;
  }

  /**
   * Retorna a pagina de listagem.
   * 
   * @return listagem page
   */
  protected String getViewPage() {
    return super.getBasePath() + AttributeList.PAGE_LIST;
  }

  /**
   * Retorna o path de listagem padrão.
   * 
   * @return path da listagem.
   */
  @ModelAttribute("listPath")
  public final String getListPath() {
    return getBasePath() + AttributeList.Path.INDEX;
  }

  /**
   * Recupera o objeto de pesquisa.
   * 
   * @return the searchObject
   */
  protected E getSearchObject() {
    return searchObject;
  }

  /**
   * Atribui o valor no objeto de pesquisa.
   * 
   * @param searchObject
   *          the searchObject to set
   */
  protected void setSearchObject(E searchObject) {
    this.searchObject = searchObject;
  }

  /**
   * Retorna a classe de pesquisa.
   * 
   * @return the searchClass
   */
  protected Class<E> getSearchClass() {
    return searchClass;
  }

  /**
   * Retorna se esta habilitado a paginação ou não.
   * 
   * @return the pagination
   */
  protected boolean isPagination() {
    return pagination;
  }
}
