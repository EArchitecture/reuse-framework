package com.github.earchitecture.reuse.view.spring.controller;

import com.github.earchitecture.reuse.core.version.Manifest;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 
 * @author <a href="mailto:barcelos.cbc@gmail.com">Cleber Barcelos</a>
 * @version 0.1.0
 * @param <E>
 *          Tipo da Entidade
 * @param <I>Tipo
 *          da primareKey
 */
public abstract class BasicController<E, I> {
  @Autowired
  @Qualifier("messageSource")
  private MessageSource messageSource;
  private String basePath;
  /**
   * Parametro do tipo de tela.
   */
  private static final String TYPE_TELA = "typeTela";

  /**
   * Inicializa o diretorio das paginas a serem renderizadas.
   */
  public BasicController() {
    this.basePath = this.processBasePath();
  }

  /**
   * Recupera da anotação da classe qual e o path a ser utilizado.
   * 
   * @return string do path base.
   */
  private String processBasePath() {
    String ret = "";
    if (this.getClass().isAnnotationPresent(RequestMapping.class)) {
      RequestMapping req = this.getClass().getAnnotation(RequestMapping.class);
      if (req.value().length == 1) {
        ret = req.value()[0];
      }
      return ret + "/";
    } else {
      return "";
    }
  }

  /**
   * Atualiza o paramentro de qual e o tipo de tela para cada tipo de model.
   * 
   * @param model
   *          model de request ou redirect
   * @param tipoTela
   *          tipo de tela
   */
  protected void setTypeTela(Model model, String tipoTela) {
    if (model instanceof RedirectAttributes) {
      ((RedirectAttributes) model).addFlashAttribute(TYPE_TELA, tipoTela);
    } else {
      model.addAttribute(TYPE_TELA, tipoTela);
    }
  }

  /**
   * Converte o objeto em uma string.
   * 
   * @param objeto
   *          objeto a ser convertido.
   * @return String com os dados convertidos.
   */
  protected String toString(Object objeto) {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    String ret = null;
    try {
      // ObjectMapper mapper = new ObjectMapper();
      // ObjectOutputStream oos = new ObjectOutputStream(baos);
      // oos.writeObject(mapper.writeValueAsString(o));
      // oos.close();
      // ret = new String(Base64.encodeBase64(baos.toByteArray()));
    } catch (Exception exp) {
      throw new org.hibernate.type.SerializationException("Não foi possível serializar o formulário", exp);
    }
    return ret;
  }

  /**
   * Trata mensagens de erro genericas
   * 
   * @param bindingResult
   *          bind result.
   * @param model
   *          atributos de request
   */
  protected void processErroMens(BindingResult bindingResult, Model model) {
    for (ObjectError error : bindingResult.getGlobalErrors()) {

      if (error.getArguments() != null) {
        this.addError(model, new Object[] { error.getCode(), error.getArguments()[0] });
      } else {
        this.addError(model, new Object[] { error.getCode(), null });
      }
    }
    if (bindingResult.getAllErrors() != null && !bindingResult.getAllErrors().isEmpty()) {
      for (ObjectError error : bindingResult.getAllErrors()) {
        this.addError(model, new Object[] { error.getDefaultMessage() });
      }
    }
  }

  /**
   * Adiciona mensagem de erro, no model.
   * 
   * @param model
   *          model a ser adicionado mensagem
   * @param objects
   *          mensagems a ser adicionado
   */
  public void addError(Model model, Object[]... objects) {
    List<String> mens = this.createMensagem(objects);
    if (model instanceof RedirectAttributes) {
      List<String> oldMens = (List<String>) ((RedirectAttributes) model).getFlashAttributes().get(Mensagem.ERRORS);
      if (oldMens != null) {
        mens.addAll(oldMens);
      }
      ((RedirectAttributes) model).addFlashAttribute(Mensagem.ERRORS, mens);
    } else {
      Object values = ((BindingAwareModelMap) model).get(Mensagem.ERRORS);
      if (!(values instanceof String)) {
        List<String> oldMens = (List<String>) values;
        if (oldMens != null) {
          mens.addAll(oldMens);
        }
      }
      model.addAttribute(Mensagem.ERRORS, mens);
    }
  }

  /**
   * Diretorio base de renderização das paginas.
   * 
   * @return the basePath
   */
  @ModelAttribute("basePath")
  public String getBasePath() {
    return basePath;
  }

  /**
   * Recupera informações de versão do maven.
   * 
   * @return Retorna o manifest gerado pelo maven
   */
  @ModelAttribute("manifest")
  public Manifest getManifest() {
    return Manifest.getInstance();
  }

  /**
   * Cria lista de mensagens
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

  public List<String> createMensagem(Object[]... mensagemCode) {
    List<String> msgs = new ArrayList<String>();
    for (int i = 0; i < mensagemCode.length; i++) {
      Object[] mens = mensagemCode[i];
      Object[] params = mensagemCode[i].clone();
      params = ArrayUtils.removeElement(params, mens[0]);
      try {
        msgs.add(this.messageSource.getMessage((String) mens[0], params, Locale.CANADA));
      } catch (NoSuchMessageException exp) {
        msgs.add((String) mens[0]);
      }
    }
    return msgs;
  }

}
