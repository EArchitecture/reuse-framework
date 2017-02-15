package com.github.earchitecture.reuse.view.spring.controller;

import com.github.earchitecture.reuse.core.version.Manifest;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

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

  @ModelAttribute("basePath")
  public String getBasePath() {
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
