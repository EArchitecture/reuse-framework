package com.github.earchitecture.reuse.view.spring.controller;

import java.util.List;
import java.util.Locale;

/**
 * Define operações e atributos do controller.
 */
public interface Controller {
  /**
   * Parametro do tipo de tela
   */
  String TYPE_TELA = "typeTela";
  /**
   * Locale brasil
   */
  Locale LOCALE_BRASIL = new Locale("pt", "BR");

  /**
   * Descreve os tipo de path de tela.
   */
  interface TypeTela {
    String SAVE = "save";
    String EDIT = "edit";
    String CREATE = "create";
    String DELETE = "delete";
    String PAGE = "page";
    String LIST = "index";
    String SHOW = "show";
  }

  /**
   * Descreve parametros de tela.
   */
  interface ParamObject {
    String SEARCH_STRING = "ss";
    String SEARCH_STRING_ENCODED = "ssEncoded";
    String OBJECT = "object";
    String SEARCH_OBJECT = "searchobject";
    String ELEMENTOS = "elementos";
  }

  /**
   * Tipo de mensagens a serem tratados nas telas
   */
  interface Mensagem {
    String INFOS = "infos";
    String ERRORS = "errors";
  }

  /**
   * Retorna o path da tela de save
   * 
   * @return path tela save
   */
  String getSavePath();

  /**
   * Retorna o path da tela de edit
   * 
   * @return path tela edit
   */
  String getEditPath();

  /**
   * Retorna o path da tela de create
   * 
   * @return path tela create
   */
  String getCreatePath();

  /**
   * Retorna o path da tela de delete
   * 
   * @return path tela delete
   */
  String getDeletePath();

  /**
   * Retorna o path da tela de page
   * 
   * @return path tela page
   */
  String getPagePath();

  /**
   * Retorna o path da tela de list
   * 
   * @return path tela list
   */
  String getListPath();

  /**
   * Retorna o base do path
   * 
   * @return base path
   */
  String getBasePath();

  /**
   * Retorna o path da tela de show
   * 
   * @return path tela show
   */
  String getShowPath();

  /**
   * Cria lista de mensagens, tratando properties
   * 
   * @param mensagemCode
   *          uma ou mais mensagens com parametros.
   * 
   *          <pre>
   * Exemplo
   * <code>createMensagem( new Object[]{"msg.erro.login.not.mapping",param1,param2}, new Object[]{"msg.erro.login.not.mapping"}); </code>
   *          </pre>
   * 
   * @return Lista de mensagens configuradas.
   */
  List<String> createMensagem(Object[]... mensagemCode);
}
