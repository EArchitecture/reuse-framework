package com.github.earchitecture.reuse.datatable.thymeleaf.processor;

import java.util.LinkedHashMap;
import java.util.Map;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.AttributeValueQuotes;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IOpenElementTag;
import org.thymeleaf.processor.element.AbstractAttributeModelProcessor;
import org.thymeleaf.processor.element.IElementModelStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

/**
 * Instala Jquery DataTable
 * 
 * @author <a href="mailto:barcelos.cbc@gmail.com">Cleber Barcelos</a>
 * @see <a href="https://datatables.net/">DataTables</a>
 */
public class InstallDataTableModelProcessor extends AbstractAttributeModelProcessor {
  private static final String ATTR_NAME = "datatable";
  private static final int PRECEDENCE = 0;
  private static final String VERSION_DATA_TABLE = "1.10.13";

  public InstallDataTableModelProcessor(String dialectPrefix) {
    super(TemplateMode.HTML, dialectPrefix, null, false, ATTR_NAME, true, PRECEDENCE, true);
  }

  @Override
  protected void doProcess(ITemplateContext context, IModel model, AttributeName attributeName, String attributeValue, IElementModelStructureHandler structureHandler) {
    if (attributeValue != null && Boolean.parseBoolean(attributeValue)) {
      IModel modelInstall = createCssDataTable(context);
      model.insertModel(model.size(), modelInstall);
      modelInstall = createJsDataTable(context);
      model.insertModel(model.size(), modelInstall);
    }
  }

  /**
   * Cria parametrização de insert do javascript do datatable.
   * 
   * @param context
   *          Contexto a ser trabalhado
   * @return Model configurado.
   */
  private IModel createJsDataTable(ITemplateContext context) {
    final Map<String, String> jsDataTable = new LinkedHashMap<String, String>(3, 1.0f);
    jsDataTable.put("charset", "utf8");
    jsDataTable.put("type", "text/javascript");
    jsDataTable.put("src", context.buildLink("/reuse/jdatatables/" + VERSION_DATA_TABLE + "/js/jquery.dataTables.min.js", null));
    final IModel modelInstall = createElement(context.getModelFactory(), "script", jsDataTable);
    return modelInstall;
  }

  /**
   * Configura include do Css do datatable.
   * 
   * @param context
   *          Contexto a ser trabalhado
   * @return Model configurado.
   */
  private IModel createCssDataTable(ITemplateContext context) {
    final Map<String, String> cssDataTable = new LinkedHashMap<String, String>(4, 1.0f);
    cssDataTable.put("rel", "stylesheet");
    cssDataTable.put("type", "text/css");
    cssDataTable.put("media", "screen");
    cssDataTable.put("href", context.buildLink("/reuse/jdatatables/" + VERSION_DATA_TABLE + "/css/jquery.dataTables.min.css", null));
    final IModel modelInstall = createElement(context.getModelFactory(), "link", cssDataTable);
    return modelInstall;
  }

  /**
   * Cria o elemento e adiciona na model.
   * 
   * @param modelFactory
   *          fabrica de model.
   * @param tag
   *          Tag a ser criada
   * @param attributes
   *          Atributos a serem adicionados no elemento.
   * @return Model configurado com o novo elemento.
   */
  private IModel createElement(IModelFactory modelFactory, String tag, Map<String, String> attributes) {
    final IModel modelInstall = modelFactory.createModel();
    IOpenElementTag link = modelFactory.createOpenElementTag(tag, attributes, AttributeValueQuotes.DOUBLE, false);
    modelInstall.add(link);
    modelInstall.add(modelFactory.createCloseElementTag(tag));
    return modelInstall;
  }
}
