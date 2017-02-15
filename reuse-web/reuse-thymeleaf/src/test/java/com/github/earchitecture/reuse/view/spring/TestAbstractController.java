package com.github.earchitecture.reuse.view.spring;

import com.github.earchitecture.reuse.view.spring.impl.config.TestWebAppContext;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Define configurações de contexto para executação de teste para o controller.
 * 
 * @author <a href="mailto:barcelos.cbc@gmail.com">Cleber Barcelos</a>
 * @version 0.1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestWebAppContext.class })
@WebAppConfiguration
public class TestAbstractController {
  @Autowired
  private WebApplicationContext context;
  private MockMvc mvc;

  @Before
  public void setup() throws Exception {
    mvc = MockMvcBuilders.webAppContextSetup(context).build();
  }

  /**
   * Retorna instancia do mock mvc.
   * 
   * @return the mvc
   */
  public MockMvc getMvc() {
    return mvc;
  }

}
