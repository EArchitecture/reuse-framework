package com.github.earchitecture.reuse.exception;

/**
 * Validação generica de regras de negocio.
 * 
 * @author <a href="mailto:barcelos.cbc@gmail.com">Cleber Barcelos</a>
 * @version 0.1.0
 */
public class ValidationServiceException extends Exception {
  private static final long serialVersionUID = 1L;
  private String message;
  private Object[] params;

  /**
   * Aramagem informações das mensagems.
   * 
   * @param menssage
   *          Chave de mapeamento do propertie.
   * @param params
   *          parametros a serem adicionados na mensagem.
   */
  public ValidationServiceException(String menssage, Object... params) {
    this.message = menssage;
    this.params = params;
  }

  /**
   * @return the message
   */
  public String getMessage() {
    return message;
  }

  /**
   * @param message
   *          the message to set
   */
  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * @return the params
   */
  public Object[] getParams() {
    return params;
  }

  /**
   * @param params
   *          the params to set
   */
  public void setParams(Object[] params) {
    this.params = params;
  }
}
