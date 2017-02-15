package com.github.earchitecture.reuse.core.version;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * 
 * @author <a href="mailto:barcelos.cbc@gmail.com">Cleber Barcelos</a>
 * @version 0.1.0
 */
public final class Manifest {
  private static final Log LOGGER = LogFactory.getLog(Manifest.class);
  private static Properties prop = new Properties();
  private static final String VERSION = "Implementation-Version";

  @Autowired
  private ApplicationContext appContext;

  private static Manifest instance;

  /**
   * 
   * @return Manifest instancia do manifest
   */
  public static Manifest getInstance() {
    if (instance == null) {
      instance = new Manifest();
      SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(instance);
      String name = "/META-INF/MANIFEST.MF";
      try {
        prop.load(instance.getAppContext().getResource(name).getInputStream());
      } catch (IOException e) {
        LOGGER.error("Erro ao carregar /META-INF/MANIFEST.MF");
      } catch (NullPointerException e) {
        LOGGER.error("Erro ao carregar /META-INF/MANIFEST.MF");
      }
    }
    return instance;
  }

  /**
   * 
   * @return ApplicationContext
   */
  public ApplicationContext getAppContext() {
    return appContext;
  }

  /**
   * Contexto da aplicação.
   * 
   * @param appContext
   *          Contexto
   */
  public void setAppContext(ApplicationContext appContext) {
    this.appContext = appContext;
  }

  /**
   * 
   * @return String versão do sistema
   */
  public String getVersion() {
    String value = prop.getProperty(VERSION);
    if (value == null) {
      StringBuffer sb = new StringBuffer("Favor mapear a propriedade 'addDefaultImplementationEntries' no seu pom da sequinte forma:\n");
      sb.append("<plugin>");
      sb.append("	<groupId>org.apache.maven.plugins</groupId>");
      sb.append("	<artifactId>maven-war-plugin</artifactId>");
      sb.append("	<configuration>");
      sb.append("		<archive>");
      sb.append("			<manifest>");
      sb.append("				<addDefaultImplementationEntries>true</addDefaultImplementationEntries>");
      sb.append("			</manifest>");
      sb.append("		</archive>");
      sb.append("	</configuration>");
      sb.append("</plugin>");
      LOGGER.error(sb.toString());
    }
    return value;
  }
}