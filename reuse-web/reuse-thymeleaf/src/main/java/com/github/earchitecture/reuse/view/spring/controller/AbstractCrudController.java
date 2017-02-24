package com.github.earchitecture.reuse.view.spring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.earchitecture.reuse.view.spring.exeption.ValidationControllerException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Base64;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author <a href="mailto:barcelos.cbc@gmail.com">Cleber Barcelos</a>
 * @version 0.1.0
 */
public abstract class AbstractCrudController<E, I extends Serializable> extends AbstractListController<E, I> implements CrudController<E, I> {
  private static final long serialVersionUID = 1L;

  private final Class<E> entityClass;

  /**
   * Inicializa o parametro de class tipo do objeto de pesquisa.
   */
  @SuppressWarnings("unchecked")
  public AbstractCrudController() {
    this.entityClass = (Class<E>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
  }

  protected E fromString(String s) {
    E entity = null;
    if (StringUtils.isNoneBlank(s)) {
      byte[] data = Base64.getDecoder().decode(s.getBytes());
      ObjectInputStream ois;
      try {
        ois = new ObjectInputStream(new ByteArrayInputStream(data));
        String valueJson = (String) ois.readObject();
        ObjectMapper obj = new ObjectMapper();
        entity = obj.readValue(valueJson, this.getEntityClass());
        ois.close();
      } catch (IOException exp) {
        // TODO Auto-generated catch block
        exp.printStackTrace();
      } catch (ClassNotFoundException exp) {
        // TODO Auto-generated catch block
        exp.printStackTrace();
      }

    }
    return entity;
  }

  /**
   * Mapeia a url /{entidade}/edit/{id}. Para tela de edição.
   * 
   * @param id
   *          Id da entidade
   * @param ss
   *          Formulário de pesquisa serializado
   * @param model
   *          atributos de request.
   * @return form view
   * @throws ValidationControllerException
   *           Erro de valicação de controlador de interface
   */
  @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
  public String edit(@PathVariable I id, @RequestParam(defaultValue = "") String ss, Model model) throws Exception {
    E entity = get(id);
    E search = getSearchObject();
    if (StringUtils.isNotBlank(ss)) {
      search = fromString(ss);
    }
    setTypeTela(model, TypeTela.EDIT);
    return edit(model, entity, search);
  }

  /**
   * Método para preencher o model do formulário de edição
   * 
   * @param model
   *          atributos do model nas requisições httprequest.
   * @param entity
   *          Entidade a ser editada
   * @param search
   *          Objeto mapeando formulário de pesquisa
   * @return form view
   * @throws ValidationControllerException
   *           Erro de valicação de controlador de interface
   */
  protected String edit(Model model, E entity, E search) throws ValidationControllerException {
    parametrosCrudDefault(model, entity, search);
    formModelFiller(model);
    return this.getFormView();
  }

  /**
   * Mapeia a url /{entidade}/create. Para tela de criação.
   * 
   * @param ss
   *          Formulário de pesquisa serializado
   * @param model
   *          atributos do model nas requisições httprequest.
   * @return form view
   * @see #create(Object, Model)
   * @throws ValidationControllerException
   *           Erro de valicação de controlador de interface
   */
  @Override
  @RequestMapping(value = "/create", method = RequestMethod.GET)
  public String create(@RequestParam(defaultValue = "") String ss, Model model) throws ValidationControllerException {
    E search = fromString(ss);
    setTypeTela(model, TypeTela.CREATE);
    return create(search, model);
  }

  /**
   * Metodo que preenche o model com os dados para criação de novo elemento.
   * 
   * @param search
   *          Objeto com parametros de pesquisa da tela anterior
   * @param model
   *          atributos do model nas requisições httprequest.
   * @return form view
   * @throws ValidationControllerException
   *           Erro de valicação de controlador de interface
   */
  protected String create(E search, Model model) throws ValidationControllerException {
    E entity = newEntityFillerHook(search);
    return edit(model, entity, search);
  }

  /**
   * Sobrescreva este método caso seja necessário acrescentar elementos ao model da tela de criação e edição, como por exemplo listas de combos.
   * 
   * @param model
   *          atributos do model nas requisições httprequest.
   * @throws ValidationControllerException
   *           Erro de valicação de controlador de interface
   */
  protected void formModelFiller(Model model) throws ValidationControllerException {
  }

  /**
   * Sobrescreva este metodo caso a entidade nova tenha algum valor préviamente definido, para ser utilizado na tela de criação.
   * 
   * @param entity
   *          Entidade preenchida com os dados da pesquisa
   * @return Entidade com os valores preenchidos
   * @throws ValidationControllerException
   *           Erro de valicação de controlador de interface
   */
  protected E newEntityFillerHook(E entity) throws ValidationControllerException {
    try {
      return this.getEntityClass().newInstance();
    } catch (InstantiationException | IllegalAccessException exp) {
      exp.printStackTrace();
    }
    return null;
  }

  /**
   * Metodo executado no post do formulário de edição/criação da entidade, para persisti-la
   * 
   * @param ss
   *          Formulário da tela de pesquisa serializado
   * @param entidade
   *          Entidade preenchida com os dados do formulário de edição
   * @param bindingResult
   *          bind de resultados.
   * @param modelRedirect
   *          Atributos de retorno da requisição.
   * @param model
   *          atributos do model nas requisições httprequest.
   * @return Retorna form view em caso de erro e list view em caso de sucesso
   * @throws ValidationControllerException
   *           Erro de valicação de controlador de interface
   */
  @RequestMapping(value = "/save", method = RequestMethod.POST)
  public String save(@RequestParam(defaultValue = "") String ss, @Valid E entidade, BindingResult bindingResult, RedirectAttributes modelRedirect, Model model) throws ValidationControllerException {
    // F search = getSearchObject();
    // boolean isNew = false;
    // try {
    // if (StringUtils.isNotBlank(ss)) {
    // search = (F) fromString(ss);
    // }
    // if (bindingResult.hasErrors() || !preSaveValidation(entidade, search, model)) {
    // if (bindingResult.hasErrors()) {
    // processErroMens(bindingResult, model);
    // }
    // model.addAttribute(getEntityClassName(), entidade);
    // model.addAttribute(SEARCH_STRING, toString(search));
    // model.addAttribute(OBJECT, entidade);
    // model.addAttribute(SEARCH_OBJECT, search);
    // model.addAttribute(BindingResult.MODEL_KEY_PREFIX + OBJECT, bindingResult);
    // this.setTypeTela(model, TypeTela.CREATE);
    // formModelFiller(model);
    // return formView;
    // }
    // if (entidade.getId() == null) {
    // isNew = true;
    // }
    // entityPreSaveFillerHook(entidade, search);
    // saveOrUpdate(entidade, model);
    // addInfo(modelRedirect, new Object[] { isNew ? getCreateMessage() : getEditMessage() });
    // return listPostFromAction(search, entidade, isNew ? ACTION.CREATE : ACTION.EDIT, modelRedirect);
    // } catch (DataIntegrityViolationException e) {
    // LOGGER.warn("Ocorreu alguma falha no banco e somente os casos de unique violation foi tratado.", e);
    // if (e.getCause() instanceof ConstraintViolationException) {
    // ConstraintViolationException conse = (ConstraintViolationException) e.getCause();
    // if (conse.getErrorCode() == 2627) {
    // String messageError = "msg." + this.getEntityClassName().trim() + ".unique.error";
    // try {
    // this.addError(model, new Object[] { this.getMessageSource().getMessage(messageError, null, LOCALE_BRASIL) });
    // } catch (NoSuchMessageException m) {
    // this.addError(model, new Object[] { uniqueErrorMessage });
    // }
    // }
    // parametrosDefault(model, getEntityClassName(), entidade, search);
    // formModelFiller(model);
    // return formView;
    // }
    // parametrosDefault(model, getEntityClassName(), entidade, search);
    // }
    // return formView;
    return null;
  }

  protected boolean preSaveValidation(E entidade, E search, Model model) throws ValidationControllerException {
    return true;
  }

  /**
   * Metodo que pode ser sobrescrito caso o comportamento da lista após salvar o objeto seja diferente, por exemplo alterar o redirecionamento da lista para
   * outra tela
   * 
   * @param search
   *          Entidade representando o formulário de pesquisa
   * @param model
   *          atributos do model nas requisições httprequest.
   * @return list view
   * @throws ValidationControllerException
   *           Erro de valicação de controlador de interface
   */
  protected String listPostFromSave(E search, RedirectAttributes model) throws ValidationControllerException {
    // return "redirect:" + getListPath() + "?ss=" + toEncodedString(search);
    return null;
  }

  /**
   * Metodo que pode ser sobrescrito caso o comportamento da lista após salvar/deletar o objeto seja diferente, por exemplo alterar o redirecionamento da lista
   * para outra tela
   * 
   * @param search
   *          Entidade representando o formulário de pesquisa
   * @param action
   *          informa se o elemento sendo salvo é novo ou está sendo editado
   * @param model
   *          atributos do model nas requisições httprequest.
   * @return list view
   * @throws ValidationControllerException
   *           Erro de valicação de controlador de interface
   */
  protected String listPostFromAction(E search, Action action, RedirectAttributes model) throws ValidationControllerException {
    return listPostFromSave(search, model);
  }

  protected void parametrosCrudDefault(Model model, E entity, E search) throws ValidationControllerException {
    model.addAttribute(ParamObject.OBJECT, entity);
  }

  /**
   * Retorna a tela tela do form.
   * 
   * @return Nome da view da tela de form
   */
  public String getFormView() {
    return super.getBasePath() + AttributeCrud.PAGE_FORM;
  }

  @ModelAttribute("savePath")
  public final String getSavePath() {
    return getBasePath() + TypeTela.SAVE + "/";
  }

  @ModelAttribute("editPath")
  public final String getEditPath() {
    return getBasePath() + TypeTela.EDIT + "/";
  }

  @ModelAttribute("createPath")
  public final String getCreatePath() {
    return getBasePath() + TypeTela.CREATE + "/";
  }

  @ModelAttribute("deletePath")
  public String getDeletePath() {
    return getBasePath() + TypeTela.DELETE + "/";
  }

  @ModelAttribute("showPath")
  public String getShowPath() {
    return getBasePath() + TypeTela.SHOW + "/";
  }

  protected Class<E> getEntityClass() {
    return entityClass;
  }
}
