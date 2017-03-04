package com.github.earchitecture.reuse.datatable.thymeleaf.processor;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.engine.CDATASection;
import org.thymeleaf.model.AttributeValueQuotes;
import org.thymeleaf.model.IAttribute;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IOpenElementTag;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.model.IStandaloneElementTag;
import org.thymeleaf.model.ITemplateEvent;
import org.thymeleaf.processor.element.AbstractAttributeModelProcessor;
import org.thymeleaf.processor.element.IElementModelStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.util.Validate;

public class DataTableAttributeModelProcessor extends AbstractAttributeModelProcessor {
  private static final String ATTR_NAME = "table";
  private static final int PRECEDENCE = 100;
  private Set<AbstractAttributeModelProcessor> processor = new HashSet<AbstractAttributeModelProcessor>();

  public DataTableAttributeModelProcessor(String dialectPrefix) {
    super(TemplateMode.HTML, dialectPrefix, null, false, ATTR_NAME, true, PRECEDENCE, true);
    this.processor.add(new ServerSideAttributeModelProcessor(dialectPrefix));

  }

  /*
   * (non-Javadoc)
   * 
   * @see org.thymeleaf.processor.element.AbstractAttributeModelProcessor#doProcess(org.thymeleaf.context.ITemplateContext, org.thymeleaf.model.IModel,
   * org.thymeleaf.engine.AttributeName, java.lang.String, org.thymeleaf.processor.element.IElementModelStructureHandler)
   */
  @Override
  protected void doProcess(ITemplateContext context, IModel model, AttributeName attributeName, String attributeValue, IElementModelStructureHandler structureHandler) {
    if (attributeValue != null && Boolean.parseBoolean(attributeValue)) {
      StringBuffer dataConfig = getDataConfig(context, model, structureHandler);
      final IModelFactory modelFactory = context.getModelFactory();
      final Map<String, String> headTag = new LinkedHashMap<String, String>(2, 1.0f);
      headTag.put("charset", "utf8");
      headTag.put("type", "text/javascript");
      IStandaloneElementTag script = modelFactory.createStandaloneElementTag("script", headTag, AttributeValueQuotes.DOUBLE, false, false);
      model.insert(model.size(), script);
      model.insert(model.size(), new CDATASection("/*<![CDATA[*/", dataConfig, "/*]]>*/"));
      model.insert(model.size(), modelFactory.createCloseElementTag("script"));
    }
  }

  /**
   * @param context
   * @param model
   * @param structureHandler
   * @return
   */
  private StringBuffer getDataConfig(ITemplateContext context, IModel model, IElementModelStructureHandler structureHandler) {
    StringBuffer dataConfig = new StringBuffer();

    IOpenElementTag table = (IOpenElementTag) model.get(0);
    IAttribute idAtr = table.getAttribute("id");
    Validate.notNull(idAtr, "Attribute id cannot be null or empty in DataTable Attribute Model Processor");
    Validate.notEmpty(idAtr.getValue(), "Attribute uidrl cannot be null or empty in DataTable Attribute Model Processor");

    dataConfig.append(this.openDataConfig(idAtr.getValue()));
    dataConfig.append(extractConfig(context, model, structureHandler)).append("\n");
    dataConfig.append(closeDataConfig());
    return dataConfig;
  }

  public String openDataConfig(String id) {
    StringBuffer js = new StringBuffer("\n");
    js.append("   $(document).ready(function(){").append("\n");
    js.append("      $('#").append(id).append("').DataTable({").append("\n");

    return js.toString();
  }

  public String closeDataConfig() {
    StringBuffer js = new StringBuffer("\n");
    js.append("      });").append("\n");
    js.append("   });").append("\n");
    return js.toString();
  }

  /**
   * Extrai configuração da data table
   * 
   * @param context
   *          context da pagina
   * @param model
   *          model element
   * @param structureHandler
   *          structureHandler
   * @return Configuração
   */
  private String extractConfig(ITemplateContext context, IModel model, IElementModelStructureHandler structureHandler) {
    StringBuffer confDataTable = new StringBuffer();
    StringBuffer colunns = new StringBuffer();
    for (int i = 0; i < model.size(); i++) {
      ITemplateEvent template = model.get(i);
      if (template instanceof IOpenElementTag) {
        IOpenElementTag openElement = (IOpenElementTag) template;
        IAttribute[] attribute = openElement.getAllAttributes();
        String conf = processAttribute(attribute, context, model, structureHandler);
        if (StringUtils.isNotBlank(conf)) {
          confDataTable.append(conf);
        }
      } else if (template instanceof IStandaloneElementTag) {
        IStandaloneElementTag standElement = (IStandaloneElementTag) template;
        String colunn = extractProperty(context, model, structureHandler, i, standElement);
        if (StringUtils.isNotBlank(colunn)) {
          colunns.append(colunn).append(",");
        }
      }
    }
    if (colunns.length() > 0) {
      /* retira a ultima virgula */
      colunns = new StringBuffer(colunns.substring(0, colunns.lastIndexOf(",")));
      colunns.append("\n");
      /* Adiciona n configuração da tabela */
      confDataTable.append("\n").append("'columns':[");
      confDataTable.append(colunns);
      confDataTable.append("]").append("\n");
    }
    return confDataTable.toString();
  }

  /**
   * @param context
   * @param model
   * @param structureHandler
   * @param i
   * @param standElement
   * @return
   */
  private String extractProperty(ITemplateContext context, IModel model, IElementModelStructureHandler structureHandler, int i, IStandaloneElementTag standElement) {
    String column = null;
    IAttribute attribute = standElement.getAttribute("dt:property");
    if (attribute != null) {
      /* Recupera a propriedde */
      PropertyAttributeModelProcessor prop = new PropertyAttributeModelProcessor(super.getDialectPrefix());
      prop.doProcess(context, model, attribute.getAttributeDefinition().getAttributeName(), attribute.getValue(), structureHandler);
      column = prop.getDataConfig();
      /* remove o atributo */
      IModelFactory mFactory = context.getModelFactory();
      IProcessableElementTag newFirstEvent = mFactory.removeAttribute(standElement, attribute.getAttributeDefinition().getAttributeName());
      model.replace(i, newFirstEvent);

    }
    return column;
  }

  /**
   * Executa o processor correspondente ao attribute
   * 
   * @param attributes
   *          Attributes a serem processados.
   * @param context
   *          contexto da pagina
   * @param model
   *          conteudo
   * @param structureHandler
   *          structureHandler
   * @return
   */
  private String processAttribute(IAttribute[] attributes, ITemplateContext context, IModel model, IElementModelStructureHandler structureHandler) {
    StringBuffer configData = new StringBuffer();
    for (IAttribute iAttribute : attributes) {
      for (AbstractAttributeModelProcessor processor : getProcessor()) {
        if (processor.getMatchingAttributeName().matches(iAttribute.getAttributeDefinition().getAttributeName())) {
          processor.process(context, model, structureHandler);
          String config = ((IDataTableConfig) processor).getDataConfig();
          if (StringUtils.isNotBlank(config)) {
            configData.append(config).append(",");
          }
        }
      }
    }
    return configData.toString();
  }

  private Set<AbstractAttributeModelProcessor> getProcessor() {
    return this.processor;
  }
}
