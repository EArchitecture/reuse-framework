package com.github.earchitecture.reuse.view.spring.controller;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.github.earchitecture.reuse.view.spring.TestAbstractController;
import com.github.earchitecture.reuse.view.spring.impl.controller.TodoListController;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

/**
 * Realiza teste no controller abstrato de listagem.
 * 
 * @author <a href="mailto:barcelos.cbc@gmail.com">Cleber Barcelos</a>
 * @version 0.1.0
 */
public class TodoListControllerTest extends TestAbstractController {

  @Autowired
  private TodoListController controller;

  /**
   * Testa chamada simples no controle da pagina index.
   * 
   * @throws Exception
   *           erro não esperado.
   */
  @Test
  public void testSucess() throws Exception {
    ResultActions result = super.getMvc().perform(get("/todo")).andDo(print());
    assertEquals(result.andReturn().getResponse().getForwardedUrl(), "/todo/list");

    result = super.getMvc().perform(get("/todo/")).andDo(print());
    assertEquals(result.andReturn().getResponse().getForwardedUrl(), "/todo/list");

    result = super.getMvc().perform(get("/todo/index")).andDo(print());
    assertEquals(result.andReturn().getResponse().getForwardedUrl(), "/todo/list");
  }

  /**
   * Realiza pesquisa com o auto seach habilitado.
   * 
   * @throws Exception
   *           erro não esperado.
   */
  @Test
  public void testSucessoAutoSearch() throws Exception {
    /* Habilita o auto search */
    boolean auto = this.controller.isAutoSearch();
    this.controller.enableAutoSearch();
    ResultActions result = super.getMvc().perform(get("/todo")).andDo(print());
    /* Restaura o valod padrão do auto search */
    if (auto) {
      this.controller.enableAutoSearch();
    } else {
      this.controller.disableAutoSearch();
    }
    assertEquals(result.andReturn().getResponse().getForwardedUrl(), "/todo/list");
  }
}
